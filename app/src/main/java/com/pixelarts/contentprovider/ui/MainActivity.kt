package com.pixelarts.contentprovider.ui

import android.content.ContentValues
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.view.View
import android.widget.Toast
import com.pixelarts.contentprovider.R
import com.pixelarts.contentprovider.common.URL_LOADER
import com.pixelarts.contentprovider.providers.MyContentProviderContract
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportLoaderManager.initLoader(URL_LOADER, null, this)
    }

    fun insertContact(view : View){

    }

    override fun onCreateLoader(loaderId: Int, bundle: Bundle?): Loader<Cursor> {
        return when(loaderId){
            URL_LOADER -> CursorLoader(
                this,
                MyContentProviderContract.MyContentProviderEntity.CONTENT_URI,
                null,
                null, null, null
            )
            else -> throw  NullPointerException()
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        Toast.makeText(this, "Load Done", Toast.LENGTH_SHORT).show()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        val contentValues = ContentValues()

        contentValues.put(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_NAME, etContactName.text.toString())
        contentValues.put(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_RELATIONSHIP, etContactRelationship.text.toString())
        contentValues.put(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_PRIMARY_NUMBER, etContactSecondaryNumber.text.toString())
        contentValues.put(MyContentProviderContract.MyContentProviderEntity.COLUMN_CONTACT_SECONDARY_NUMBER, etContactSecondaryNumber.text.toString())

        contentResolver.insert(MyContentProviderContract.MyContentProviderEntity.CONTENT_URI, contentValues)
    }
}
