package com.pixelarts.contentprovider.data.dao

import android.arch.persistence.room.*
import android.database.Cursor
import com.pixelarts.contentprovider.data.entities.Contact
import io.reactivex.Flowable

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact): Long

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contacts")
    fun getContacts():Flowable<List<Contact>>

    @Query("SELECT * FROM contacts")
    fun getCursorContacts():Cursor
}