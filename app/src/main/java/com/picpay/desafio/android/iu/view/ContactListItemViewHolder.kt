package com.picpay.desafio.android.iu.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.lucaspires.domain.model.ContactsModel
import com.picpay.desafio.android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*

class ContactListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(contacts: ContactsModel) {
        itemView.name.text = contacts.name
        itemView.username.text = contacts.username
        itemView.progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(contacts.img)
            .error(R.drawable.ic_round_account_circle)
            .into(itemView.picture, object : Callback {
                override fun onSuccess() {
                    itemView.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    itemView.progressBar.visibility = View.GONE
                }
            })
    }
}