package com.pixelarts.contentprovider.adapter

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import java.lang.IllegalStateException

abstract class CursorAdapter <VH: RecyclerView.ViewHolder> ( cursor: Cursor):
    RecyclerView.Adapter<VH>() {

    private var mCursor: Cursor? = null
    private var dataValid: Boolean = false
    private var rowIdColumn: Int = 0

    init {
        swapCursor(cursor)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!dataValid)
            throw IllegalStateException("Failed to Bind ViewHolder")
        if (mCursor?.moveToPosition(position) == false)
            throw IllegalStateException("Failed to move cursor to position: $position")

        mCursor?.let {
            onBindViewHolder(
                holder,
                it
            )
        }
    }

    override fun getItemCount(): Int {
        return if (dataValid)
            mCursor!!.count
        else
            0
    }

    override fun getItemId(position: Int): Long {
        if(!dataValid)
            throw IllegalStateException("Cannot find id since cursor is state is invalid")
        if (mCursor?.moveToPosition(position) == false)
            throw IllegalStateException("Failed to move cursor to position: $position")

        return mCursor!!.getLong(rowIdColumn)
    }

    fun swapCursor(cursor: Cursor?){
        if (cursor == mCursor)
            return

        if (cursor !=  null){
            mCursor = cursor
            rowIdColumn = mCursor!!.getColumnIndexOrThrow("contactId")
            dataValid = true
            notifyDataSetChanged()
        }
        else{
            //notifyItemChanged(0, itemCount)
            notifyItemRangeInserted(0, itemCount)
            notifyDataSetChanged()
            mCursor = null
            rowIdColumn = -1
            dataValid = false
        }
    }

    abstract fun onBindViewHolder(holder: VH, cursor: Cursor)
}