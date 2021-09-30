package com.rolandoselvera.founditall.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.core.internet.ConnectivityUtil
import com.rolandoselvera.founditall.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recibe estos datos temporalmente de esta forma, idealmente hacerlo por consulta a BD mediante ID.
        val navArrayDetail = navigationArgs.name
        val name = navArrayDetail[0]
        val category = navArrayDetail[1].replaceFirstChar {
            it.uppercase()
        }
        val videoId = navArrayDetail[2]
        val description = navArrayDetail[3]

        binding.apply {
            titleName.text = getString(R.string.title_name, name)
            subCategory.text = getString(R.string.sub_category, category)
            subDescription.text = getString(R.string.sub_description, description)
        }

        isVideoAvailable(videoId)
    }

    /**
     * Establece el preview de detalle (video si hay un 'id' de YouTube disponible, imagen si no lo hay).
     *
     * @param videoId Id de video de YouTube.
     */
    private fun isVideoAvailable(videoId: String) {
        if (checkConnectivity()) {
            if (!videoId.isNullOrBlank() and (videoId != "null")) {
                binding.youTubePlayer.visibility = View.VISIBLE
                youTubePlayerReady(videoId)
            } else {
                binding.image.visibility = View.VISIBLE
            }
        } else {
            binding.image.visibility = View.VISIBLE
        }
    }

    /**
     * Registra el reproductor de YouTube en el ciclo de vida del Activity/Fragment.
     */
    private fun initYouTubePlayer() {
        viewLifecycleOwner.lifecycle.addObserver(binding.youTubePlayer)
    }

    /**
     * Listener del reproductor de YouTube. Si el reproductor se inicializó correctamente,
     * muestra la miniatura del video, a la espera de la reproducción. De lo contrario,
     * lanza error.
     *
     * @param youTubeId Id de video de YouTube.
     */
    private fun youTubePlayerReady(youTubeId: String) {

        initYouTubePlayer()

        binding.youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            // Se llama a este método cuando el reproductor está listo para reproducir videos:
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // El método cueVideo() muestra la miniatura del video sin reproducirlo automáticamente.
                // Si se requiere la reproducción automática, llamar al método loadVideo().
                youTubePlayer.cueVideo(youTubeId, 0f)
            }

            // Llamada a método cuando ocurre un error en el reproductor:
            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.youtube_player_error),
                    Toast.LENGTH_LONG
                ).show()
                Log.e("ERROR", error.toString())
            }
        })
    }

    /**
     * Comprueba si el dispositivo cuenta con conexión a internet.
     *
     * @return Devuelve 'true' si tiene conexión a internet y 'false' si no la tiene.
     */
    private fun checkConnectivity(): Boolean {
        return ConnectivityUtil(context).checkConnectivity()
    }

    /**
     * Método llamado cuando el fragment es destruido.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}