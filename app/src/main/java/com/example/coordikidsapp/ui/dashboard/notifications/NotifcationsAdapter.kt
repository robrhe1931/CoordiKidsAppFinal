package com.example.coordikidsapp.ui.dashboard.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coordikidsapp.R
import com.example.coordikidsapp.databinding.LayoutItemNotificationBinding
import com.example.coordikidsapp.ui.dashboard.notifications.NotificationsAdapter.NotificationsViewHolder

class NotificationsAdapter(private val context : Context, private val notificationList : List<String>)  :
    RecyclerView.Adapter<NotificationsViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationsViewHolder {
        return NotificationsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_notification,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NotificationsViewHolder,
        position: Int
    ) {

    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    inner class NotificationsViewHolder(val layoutItemNotificationBinding: LayoutItemNotificationBinding) :
        RecyclerView.ViewHolder(layoutItemNotificationBinding.root)
}