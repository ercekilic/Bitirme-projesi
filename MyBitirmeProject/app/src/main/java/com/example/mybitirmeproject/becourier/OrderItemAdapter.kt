package com.example.mybitirmeproject.becourier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybitirmeproject.data.Order
import com.example.mybitirmeproject.databinding.ItemOrderBinding

class OrderItemAdapter (private var dataSet:ArrayList<Order>) :
    RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {
    lateinit var myClickListener:OnItemListener

    class ViewHolder(val binding:ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Order,myClickListener: OnItemListener){
            binding.executePendingBindings()
            binding.item=item
            binding.tvItemName.setText(item.name)
            binding.root.setOnClickListener {
                myClickListener.onItemClick(item)
            }
        }

    }
    constructor(
        items: ArrayList<Order>,
        listener: OnItemListener
    ) :this(items){
        this.myClickListener=listener
        this.dataSet=items

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemOrderBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position],myClickListener)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
    interface OnItemListener{
        fun onItemClick(item: Order){

        }
    }
}