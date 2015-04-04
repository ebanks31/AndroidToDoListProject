package com.example.android.rssfeedlist;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * This class handles the database operation for the application. It handles CRUD operations for the application
 * 
 * @author Eric
 *
 */
public class CopyOfFeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
	private static final String SQL_CREATE_ENTRIES = null;
	private static final String SQL_DELETE_ENTRIES = null;

 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LIST_ITEM = "list_item";
    
    /**
     * Overloaded Constructed of FeedReaderDbHelper Class. Initialized context and database.
     * 
     * 
     * @param context Context of the application
     */
    public CopyOfFeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     * 
     * Create the Database based on String containing Create table statement
     */
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " 
        		+ KEY_TITLE + " TEXT,"
                + KEY_LIST_ITEM + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     * Caches for Online data. Delete data from database.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onDowngrade(android.database.sqlite.SQLiteDatabase, int, int)
     * Caches for Online data. Delete data from database.
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Adding new contact
    /**
     * Adds List Item object to database. Passed the title and listitem from ListItem Object.
     * 
     * @param listitem List Item Object
     */
    public void addListitem(ListItem listitem) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, listitem.getTitle()); // Contact Name
        values.put(KEY_LIST_ITEM, listitem.getListItem()); // Contact Phone
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    
  
    /**
     * 
     * Getting All List Items from database
     * @return List of List Items from Database
     * 
     */
    public List<ListItem> getAllListItems() {
        List<ListItem> contactList = new ArrayList<ListItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListItem contact = new ListItem();
                String id = cursor.getString(0);
                String name =  cursor.getString(1);
                String phonenumber = cursor.getString(2);
                
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setTitle(cursor.getString(1));
                contact.setListItem(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
    
  
    /**
     * 
     * Getting All ListItem By Title passed in parameter. Gets all ListItem from database.
     * 
     * @param spinnertitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public List<ListItem> getAllListItemsByTitle(String spinnertitle) {
        List<ListItem> contactList = new ArrayList<ListItem>();
        // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_TITLE +" = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  new String[] {spinnertitle});
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListItem contact = new ListItem();
                String id = cursor.getString(0);
                String name =  cursor.getString(1);
                String phonenumber = cursor.getString(2);
                
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setTitle(cursor.getString(1));
                contact.setListItem(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }

    /**
     * 
     * Updating single listitem on database
     * 
     * @param listitem listitem that will be updated
     * @return  the number of rows updated
     */
    public int updateListItem(ListItem listitem) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_ITEM, listitem.getListItem());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_TITLE + " = ?",
                new String[] { String.valueOf(listitem.getTitle()) });
    }
    
    /**
     * 
     * Updating listitem by title
     * 
     * @param listitem current Listitem that passed
     * @return the number of rows updated
     */
    public int updateListItemTitles111(ListItem listitem) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, listitem.getTitle());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_TITLE + " = ?",
                new String[] { String.valueOf(listitem.getTitle()) });
    }
    
    

    /**
     * 
     * Updating single listitem
     * 
     * 
     * @param listitem
     * @return  the number of rows updated
     */
    public int updateListItemTitle111(ListItem listitem) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, listitem.getTitle());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_TITLE + " = ?",
                new String[] { String.valueOf(listitem.getTitle()) });
    }
 
    
    /**
     *  Deleting single listitem
     * 
     * @param listitem list item to be deleted
     */
    public void deleteListItem(ListItem listitem) {
        SQLiteDatabase db = this.getWritableDatabase();
      db.delete(TABLE_CONTACTS, KEY_TITLE + " = ?" + " AND "+ KEY_LIST_ITEM + " = ?",
              new String[] { String.valueOf(listitem.getTitle()),String.valueOf(listitem.getListItem()) });

        db.close();
    }
    
    /**
     * 
     * Delete all listitem by title
     * 
     * @param listitem list item to be deleted
     * 
     */
    public void deleteListItemByTitle(ListItem listitem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_TITLE + " = ?",
                new String[] { String.valueOf(listitem.getTitle()) });
        db.close();
    }

}