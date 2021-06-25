package com.emrys.chipselectionrecyclerview

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.emrys.chipselectionrecyclerview.databinding.FragmentFirstBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<FirstViewModel>()

    private lateinit var adapter: CategoryAdapter

    var tracker: SelectionTracker<Long>? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = FlexboxLayoutManager(context)
//        layoutManager.flexDirection = FlexDirection.COLUMN
//        layoutManager.justifyContent = JustifyContent.FLEX_END
        binding.rvCategories.layoutManager = layoutManager

        adapter = CategoryAdapter()
        binding.rvCategories.adapter = adapter

        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            binding.rvCategories,
            StableIdKeyProvider(binding.rvCategories),
            MyItemDetailsLookup(binding.rvCategories),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter.tracker = tracker

        viewModel.categoryLive.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.btnShowSelected.setOnClickListener {
            val selectedCat = viewModel.categoryLive.value!!.filterIndexed { index, s ->
                tracker!!.selection.contains(index.toLong())
            }
            Toast.makeText(requireContext(), selectedCat.joinToString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}