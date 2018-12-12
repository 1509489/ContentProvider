package com.pixelarts.contentprovider.providers

import android.arch.persistence.room.Room
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.pixelarts.contentprovider.common.DATABASE_NAME
import com.pixelarts.contentprovider.common.TABLE_NAME
import com.pixelarts.contentprovider.data.database.ContactDatabase
import com.pixelarts.contentprovider.data.entities.Contact
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyContentProvider: ContentProvider(){

    val CONTACT_URI_CODE = 10
    private lateinit var contactDatabase: ContactDatabase
    private lateinit var uriMatcher: UriMatcher

    override fun onCreate(): Boolean {
        contactDatabase = Room.databaseBuilder(context, ContactDatabase::class.java, DATABASE_NAME).build()
        uriMatcher = createUriMatcher()
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)){
            CONTACT_URI_CODE ->{
                values?.let {
                    Completable.fromAction{

                        contactDatabase.contactDao().insertContact(Contact(
                            contactName = it[MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_NAME] as String,
                            contactRelationship =  it[MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_RELATIONSHIP] as String,
                            contactPrimaryNumber = it[MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_PRIMARY_NUMBER] as String,
                            contactSecondaryNumber = it[MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_SECONDARY_NUMBER] as String
                        ))
                    }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe{context?.contentResolver?.notifyChange(uri, null)}
                }
            }
        }
        return MyContentProviderContract.BASE_CONTENT_URI
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor?  = contactDatabase.contactDao().getCursorContacts()


    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createUriMatcher(): UriMatcher{
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        val authority = MyContentProviderContract.CONTENT_AUTHORITY

        matcher.addURI(authority, TABLE_NAME, CONTACT_URI_CODE)
        return matcher
    }

}