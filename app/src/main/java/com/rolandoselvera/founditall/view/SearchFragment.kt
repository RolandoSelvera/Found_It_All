package com.rolandoselvera.founditall.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.rolandoselvera.founditall.R
import com.rolandoselvera.founditall.databinding.FragmentSearchBinding
import com.rolandoselvera.founditall.model.Result
import com.rolandoselvera.founditall.view.adapter.SearchAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        val list = listOf(
            Result(1, "Harry Potter", "Movie", "", "", ""),
            Result(2, "Disney Channel", "Series", "", "", ""),
            Result(3, "Harry Potter", "Movie", "", "", ""),
            Result(4, "Disney Channel", "Series", "", "", ""),
            Result(5, "Harry Potter", "Movie", "", "", ""),
            Result(6, "Disney Channel", "Series", "", "", ""),
            Result(7, "Harry Potter", "Movie", "", "", ""),
            Result(8, "Disney Channel", "Series", "", "", "")
        )

        val adapter = SearchAdapter { result ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToDetailFragment()
            this.findNavController().navigate(action)
        }

        adapter.submitList(list)

        binding.recyclerView.adapter = adapter


        binding.search.setOnClickListener {
            val action = SearchFragmentDirections
                .actionSearchFragmentToDetailFragment()
            this.findNavController().navigate(action)
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