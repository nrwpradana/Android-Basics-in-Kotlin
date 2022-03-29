package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.data.SettingsDataStore
import com.example.wordsapp.databinding.FragmentLetterListBinding
import kotlinx.coroutines.launch

class LetterListFragment : Fragment() {
    private var _binding: FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true

    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        chooseLayout()

        // Initialize SettingsDataStore
        settingsDataStore = SettingsDataStore(requireContext())
        settingsDataStore.preferenceFlow.asLiveData()
            .observe(viewLifecycleOwner) { isLinearManager ->
                isLinearLayoutManager = isLinearManager
                // update the RecyclerView layout.
                chooseLayout()
                //Once a menu is created, it's not redrawn every frame since it would be redundant
                // to redraw the same menu every frame.
                // The invalidateOptionsMenu() function tells Android to redraw the options menu.
                // You can call this function when you change something in the Options menu,
                // such as adding a menu item, deleting an item, or changing the menu text or icon.
                // In this case, the menu icon was changed. Calling this method declares that
                // the Options menu has changed, and so should be recreated.
                // The onCreateOptionsMenu(android.view.Menu) method is called the next time it needs to be displayed.
                activity?.invalidateOptionsMenu()
            }
    }

    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 4)
        }
        recyclerView.adapter = LetterAdapter()
    }

    private fun setIcon(menuItem: MenuItem?) {
        menuItem ?: return
        menuItem.icon =
            if (isLinearLayoutManager) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_grid_layout
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.ic_linear_layout)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_switch_layout -> {
            // Sets isLinearLayoutManager (a Boolean) to the opposite value
            isLinearLayoutManager = !isLinearLayoutManager
            chooseLayout()
            setIcon(item)
            lifecycleScope.launch {
                settingsDataStore.saveLayoutToPreferencesStore(
                    isLinearLayoutManager,
                    requireContext()
                )
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
