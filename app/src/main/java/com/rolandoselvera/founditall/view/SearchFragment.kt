package com.rolandoselvera.founditall.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.databinding.FragmentSearchBinding
import com.rolandoselvera.founditall.view.adapter.SearchAdapter
import com.rolandoselvera.founditall.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAdapter

    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinner()

        setUpContainers()

        setUpAdapter()

        observeResults()

        observeInfo()

        setUpSearchButton()
    }

    private fun setUpAdapter() {
        adapter = SearchAdapter { result ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToDetailFragment(
                    arrayOf(
                        result.name.toString(),
                        result.type.toString(),
                        result.youTubeId,
                        result.wikiTeaser
                    )
                )
            this.findNavController().navigate(action)
        }
        binding.containerResults.recyclerView.adapter = adapter
    }

    /**
     * Método que observa los resultados de consulta a Endpoint 'Results'
     * y los muestra al usuario.
     */
    private fun observeResults() {
        searchViewModel.resultModel.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    /**
     * Método que observa los resultados de consulta a Endpoint 'Info'
     * y los muestra al usuario.
     */
    private fun observeInfo() {
        searchViewModel.resultInfo.observe(this.viewLifecycleOwner) {
            binding.apply {
                containerInfo.title.text = it?.get(0)?.name
                containerInfo.category.text =
                    it?.get(0)?.type?.replaceFirstChar {  // Convierte el primer caracter del texto a mayúscula
                        it.uppercase()
                    }
                // Muestra miniaturas (thumbnails) de videos de YouTube con Glide:
                Glide.with(containerInfo.image.context)
                    .asBitmap()
                    .error(R.drawable.ic_img_preview)  // Miniatura si ocurre un error al cargar imagen
                    .load(
                        containerInfo.image.context.getString(
                            R.string.youtube_url_thumbnail, it?.get(0)?.youTubeId
                        )
                    )
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(containerInfo.image)
            }
        }
    }

    private fun checkState(): Boolean? {
        return searchViewModel.isLoadingModel.value
    }

    // TODO: Revisar visibilidad de contenedores.
    private fun setUpContainers() {
        val states = checkState()

        binding.apply {
            if (states == false) {
                containerStates.root.visibility = View.GONE
                containerInfo.root.visibility = View.VISIBLE
                containerResults.root.visibility = View.VISIBLE
            } else {
                containerStates.root.visibility = View.VISIBLE
                containerInfo.root.visibility = View.GONE
                containerResults.root.visibility = View.GONE
            }
        }
    }

    private fun setUpSearchButton() {
        binding.search.setOnClickListener {
            val search = binding.fieldSearch.text.toString()
            searchViewModel.onCreate(search)
            hideKeyboard()

            setUpContainers()
        }
    }

    private fun setUpSpinner() {
        val categories = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, categories)
        binding.apply {
            spinnerCategory.setAdapter(adapter)

            spinnerCategory.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    hideKeyboard()
                }
            }
        }
    }

    /**
     * Oculta el teclado.
     */
    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    /**
     * Método llamado cuando el fragment es destruido.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
        _binding = null
    }
}