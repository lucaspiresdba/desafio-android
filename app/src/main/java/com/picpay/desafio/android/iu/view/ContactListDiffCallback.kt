package com.picpay.desafio.android.iu.view

import androidx.recyclerview.widget.DiffUtil
import br.com.lucaspires.domain.model.ContactsModel

class ContactListDiffCallback(
    private val oldList: List<ContactsModel>,
    private val newList: List<ContactsModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}