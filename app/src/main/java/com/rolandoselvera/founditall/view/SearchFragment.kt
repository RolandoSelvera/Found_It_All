package com.rolandoselvera.founditall.view

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.data.local.preferences.PreferencesProvider
import com.rolandoselvera.founditall.databinding.FragmentSearchBinding
import com.rolandoselvera.founditall.view.adapter.SearchAdapter
import com.rolandoselvera.founditall.viewmodel.SearchViewModel
import android.widget.AdapterView
import android.widget.Toast

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAdapter

    private var statesContainers = false

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

        checkUIContainers()

        setUpRecyclerAdapter()

        observeResults()

        observeInfo()

        setUpSearchButton()
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
        searchViewModel.resultInfo.observe(this.viewLifecycleOwner) { infoResult ->

            statesContainers = !infoResult.isNullOrEmpty()

            val title = infoResult?.get(0)?.name
            val type =
                infoResult?.get(0)?.type?.replaceFirstChar {  // Convierte el primer caracter del texto a mayúscula
                    it.uppercase()
                }
            val youtubeId = infoResult?.get(0)?.youTubeId.toString()
            val wikiTeaser = infoResult?.get(0)?.wikiTeaser.toString()

            binding.apply {
                containerInfo.title.text = title
                containerInfo.category.text = type

                // Muestra miniaturas (thumbnails) de videos de YouTube con Glide:
                Glide.with(containerInfo.image.context)
                    .asBitmap()
                    .error(R.drawable.ic_img_preview)  // Miniatura si ocurre un error al cargar imagen
                    .load(
                        containerInfo.image.context.getString(
                            R.string.youtube_url_thumbnail, youtubeId
                        )
                    )
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(containerInfo.image)

                containerInfo.root.setOnClickListener {
                    val action = SearchFragmentDirections
                        .actionSearchFragmentToDetailFragment(
                            arrayOf(
                                title.toString(),
                                type.toString(),
                                youtubeId,
                                wikiTeaser
                            )
                        )
                    findNavController().navigate(action)
                }

                checkUIContainers()
            }
        }
    }

    /**
     * Establece el criterio de búsqueda por categoría, añadiendo
     * el parámetro (prefijo) según la categoría (type).
     *
     * @param search Búsqueda del usuario.
     * @return Devuelve la categoría con la búsqueda del usuario.
     */
    private fun checkSelectedCategory(search: String): String {
        return when (binding.spinnerCategory.selectedItemPosition) {
            1 -> getString(R.string.search_by_author, search)
            2 -> getString(R.string.search_by_book, search)
            3 -> getString(R.string.search_by_game, search)
            4 -> getString(R.string.search_by_podcast, search)
            5 -> getString(R.string.search_by_movie, search)
            6 -> getString(R.string.search_by_music, search)
            7 -> getString(R.string.search_by_show, search)
            else -> search
        }
    }

    /**
     * Inicializa RecyclerView y Adapter.
     */
    private fun setUpRecyclerAdapter() {
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
     * Inicializa y establece acciones de búsqueda.
     */
    private fun setUpSearchButton() {
        binding.apply {
            search.setOnClickListener {
                val userSearch = fieldSearch.text.toString()
                val searchWithCategory = checkSelectedCategory(userSearch)

                if (userSearch.isNotBlank()) {
                    statesContainers = false

                    searchViewModel.invokeApiResults(searchWithCategory)
                    containerStates.progress.visibility = View.VISIBLE
                    containerStates.titleStates.text = getString(R.string.searching)

                    tilSearch.isErrorEnabled = false
                    tilSearch.error = null
                } else {
                    tilSearch.isErrorEnabled = true
                    tilSearch.error = getString(R.string.empty_field)
                }

                hideKeyboard()

                checkUIContainers()
            }
        }
    }

    /**
     * Muestra/oculta los contenedores de estado y resultados.
     */
    private fun checkUIContainers() {
        binding.apply {
            when (statesContainers) {
                true -> {
                    containerStates.root.visibility = View.GONE
                    containerInfo.root.visibility = View.VISIBLE
                    containerResults.root.visibility = View.VISIBLE

                    containerResults.similar.text = getString(
                        R.string.similar,
                        searchViewModel.resultModel.value?.size.toString()
                    )
                }
                false -> {
                    containerStates.root.visibility = View.VISIBLE
                    containerInfo.root.visibility = View.GONE
                    containerResults.root.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Inicializa Spinner.
     */
    private fun setUpSpinner() {
        val categories = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, categories)

        binding.apply {
            spinnerCategory.adapter = adapter

            spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    PreferencesProvider(context).saveCategory(position)
                    changeCategory(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    /**
     * Método que carga la última categoría seleccionada por el usuario
     * en el Spinner.
     *
     * @return Categoría seleccionada.
     */
    private fun loadPreferences(): Int {
        val categorySelected = PreferencesProvider(context).getCategory()

        when (categorySelected) {
            0 -> changeCategory(categorySelected)
            1 -> changeCategory(categorySelected)
            2 -> changeCategory(categorySelected)
            3 -> changeCategory(categorySelected)
            4 -> changeCategory(categorySelected)
            5 -> changeCategory(categorySelected)
            6 -> changeCategory(categorySelected)
            7 -> changeCategory(categorySelected)
        }

        return categorySelected
    }

    /**
     * Cambia la categoría del Spinner.
     *
     * @param itemSelected Item seleccionado.
     */
    private fun changeCategory(itemSelected: Int) {

        when (searchViewModel.selectedCategory(itemSelected)) {
            // Select:
            0 -> {
                binding.spinnerCategory.setSelection(0)
                enableSearch()
            }
            // Authors:
            1 -> {
                binding.spinnerCategory.setSelection(1)
                enableSearch()
            }
            // Books:
            2 -> {
                binding.spinnerCategory.setSelection(2)
                enableSearch()
            }
            // Games:
            3 -> {
                binding.spinnerCategory.setSelection(3)
                enableSearch()
            }
            // Podcasts:
            4 -> {
                binding.spinnerCategory.setSelection(4)
                enableSearch()
            }
            // Movies:
            5 -> {
                binding.spinnerCategory.setSelection(5)
                enableSearch()
            }
            // Music:
            6 -> {
                binding.spinnerCategory.setSelection(6)
                enableSearch()
            }
            // Shows:
            7 -> {
                binding.spinnerCategory.setSelection(7)
                enableSearch()
            }
        }
    }

    /**
     * Habilita/deshabilita campo de búsqueda.
     */
    private fun enableSearch() {
        binding.apply {
            if (spinnerCategory.selectedItemPosition == 0) {
                tilSearch.isEnabled = false
                search.isEnabled = false
            } else {
                tilSearch.isEnabled = true
                search.isEnabled = true
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        loadPreferences()
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