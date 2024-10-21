package com.example.chillvibes.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chillvibes.DetailsActivity
import com.example.chillvibes.databinding.MenuItemsBinding
import com.example.chillvibes.model.MenuItems

class MenuAdapter(
    private val menuItems: List<MenuItems>,
    private val requireContext: Context
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewholder>() {
    inner class MenuViewholder(private val binding: MenuItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }
            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]

            //Intent to open detail activity
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemPrice", menuItem.foodPrice)
                putExtra("MenuItemDescription", menuItem.foodDescription)
                putExtra("MenuItemIngredients", menuItem.foodIngredients)
                putExtra("MenuItemImage", menuItem.foodImage)
            }
            //start activity
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice

                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewholder {
        val binding = MenuItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewholder(binding)
    }

    override fun getItemCount(): Int = menuItems.size

    override fun onBindViewHolder(holder: MenuViewholder, position: Int) {
        holder.bind(position)
    }

    interface OnClickListener {
        fun onItemClick(position: Int)

    }
}

