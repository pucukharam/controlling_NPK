package com.example.applicationiot.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationiot.R
import com.example.applicationiot.databinding.RvItemNotificationBinding

class NotificationAdapter(
    activity: Activity
) :
    RecyclerView.Adapter<NotificationAdapter.ItemHolder>() {
    private val activity: Activity
    private val data: ArrayList<String> =
        ArrayList<String>()


    init {
        this.activity = activity
    }

    fun addData(data: ArrayList<String>?) {
        this.data.addAll(data!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            RvItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.tvNotification.text = data[position]
        if (position % 2 == 0) {
            holder.binding.tvNotification.background =
                ContextCompat.getDrawable(activity, R.drawable.background_gray_8)
        } else {
            holder.binding.tvNotification.background =
                ContextCompat.getDrawable(activity, R.drawable.background_green_8)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ItemHolder(itemView: RvItemNotificationBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: RvItemNotificationBinding

        init {
            binding = itemView
        }
    }
}