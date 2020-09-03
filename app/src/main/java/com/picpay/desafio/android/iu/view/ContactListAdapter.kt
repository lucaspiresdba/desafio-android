package com.picpay.desafio.android.iu.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.lucaspires.domain.model.ContactsModel
import com.picpay.desafio.android.R

class ContactListAdapter : RecyclerView.Adapter<ContactListItemViewHolder>() {

    var contacts = emptyList<ContactsModel>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                ContactListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return ContactListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactListItemViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}