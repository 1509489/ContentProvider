package com.pixelarts.contentprovider.adapter

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pixelarts.contentprovider.R
import com.pixelarts.contentprovider.providers.MyContentProviderContract

class ContactAdapter( cursor: Cursor): CursorAdapter<ContactAdapter.ViewHolder>(cursor){

    private lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.rv_contact_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor) {
        val contactName = cursor.getString(cursor.getColumnIndex(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_NAME))
        val contactRelationship = cursor.getString(cursor.getColumnIndex(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_RELATIONSHIP))
        val contactPrimaryNumber = cursor.getString(cursor.getColumnIndex(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_PRIMARY_NUMBER))
        val contactSecondaryNumber = cursor.getString(cursor.getColumnIndex(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_SECONDARY_NUMBER))

        holder.tvContactName.text = contactName
        holder.tvContactRelationship.text = contactRelationship
        holder.tvContactPrimaryNumber.text = contactPrimaryNumber
        holder.tvContactSecondaryNumber.text = contactSecondaryNumber
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContactName: TextView = itemView.findViewById(R.id.tvContactName)
        val tvContactRelationship: TextView = itemView.findViewById(R.id.tvContactRelationship)
        val tvContactPrimaryNumber: TextView = itemView.findViewById(R.id.tvContactPrimaryNum)
        val tvContactSecondaryNumber: TextView = itemView.findViewById(R.id.tvContactSecondaryNum)
    }
}