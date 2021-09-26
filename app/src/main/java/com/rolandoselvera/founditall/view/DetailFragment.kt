package com.rolandoselvera.founditall.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoId = "S0Q4gqBUs7c"

        youTubePlayerReady(videoId)

        binding.subDescription.text = getString(
            R.string.sub_description,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam luctus nulla eget ligula bibendum imperdiet. Fusce et urna elementum nibh bibendum placerat eget aliquam nulla. Phasellus mollis est tempus, rutrum erat eget, elementum orci. Integer fringilla velit quis enim faucibus, aliquet fringilla tellus semper. Nunc tempus tortor ut libero tincidunt gravida. Nunc fermentum lectus quam, ut commodo augue placerat nec. Duis lobortis augue eget felis laoreet, sed commodo enim vulputate. Proin accumsan leo id nisl tincidunt sodales. Nunc faucibus commodo condimentum. Aenean in orci sit amet urna malesuada tristique. Duis mollis mattis quam quis euismod. Aenean porta dui vel nibh pulvinar, non ultrices arcu lobortis. In vel convallis magna.\n" +
                    "\n" +
                    "Sed ultricies dolor ac tellus sagittis, eget varius ligula finibus. Vestibulum a velit ut metus feugiat aliquet in vel diam. In euismod felis vel dapibus vehicula. Suspendisse ut nunc id elit dictum pretium. Maecenas et fermentum libero, id sollicitudin mauris. Aliquam elit nibh, condimentum a lacus eu, luctus malesuada ex. Ut luctus vitae lectus vitae mattis. Curabitur maximus pulvinar convallis. Phasellus sed tristique dui. Nam non mauris efficitur, varius nunc a, venenatis sem. Aenean et neque dolor."
        )
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
     * Método llamado cuando el fragment es destruido.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}