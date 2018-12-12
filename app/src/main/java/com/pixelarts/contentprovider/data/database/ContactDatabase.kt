package com.pixelarts.contentprovider.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.pixelarts.contentprovider.common.DATABASE_VERSION
import com.pixelarts.contentprovider.data.dao.ContactDao
import com.pixelarts.contentprovider.data.entities.Contact

@Database(entities = [Contact::class], version = DATABASE_VERSION)
abstract class ContactDatabase: RoomDatabase(){
    abstract fun contactDao(): ContactDao
}