package com.example.chillvibes.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chillvibes.Adapter.MenuAdapter
import com.example.chillvibes.databinding.FragmentSearchBinding
import com.example.chillvibes.model.MenuItems
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems = mutableListOf<MenuItems>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve menu items from Firebase
        retrieveMenuItem()

        // Setup SearchView
        setupSearchView()

        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodReference: DatabaseReference = database.reference.child("menu")

        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear original list in case of updates
                originalMenuItems.clear()

                for (foodSnapshot in snapshot.children) {
                    val menuItems = foodSnapshot.getValue(MenuItems::class.java)
                    menuItems?.let {
                        originalMenuItems.add(it)
                    }
                }
                // After retrieving data, initialize the adapter
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here (show a message to the user)
            }
        })
    }

    private fun showAllMenu() {
        if (originalMenuItems.isNotEmpty()) {
            val filteredMenuItems = ArrayList(originalMenuItems)
            setAdapter(filteredMenuItems)
        }
    }

    private fun setAdapter(filteredMenuItems: List<MenuItems>) {
        // Initialize adapter after data is ready
        val adapter = MenuAdapter(filteredMenuItems, requireContext())
        binding.menuRecyclerView.adapter = adapter
    }

    // Setup SearchView listener
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    // Filter the menu items based on the query
    private fun filterMenuItems(query: String) {
        val filteredMenuItems = originalMenuItems.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems)
    }
}