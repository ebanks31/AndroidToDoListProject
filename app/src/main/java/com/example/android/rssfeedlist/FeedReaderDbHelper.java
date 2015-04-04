package com.example.android.rssfeedlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * This class handles the database operation for the application. It handles CRUD operations for the application
 * 
 * @author Eric
 *
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
	private static final String SQL_CREATE_ENTRIES = null;
	private static final String SQL_DELETE_ENTRIES = null;

 
    // List Organizer table name
    private static final String TABLE_LISTORGANIZER = "listorganizer";
 
    // List Organizer Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LIST_ITEM = "list_item";
    private static final String KEY_DATE = "date";
	
    /**
     * Overloaded Constructed of FeedReaderDbHelper Class. Initialized context and database.
     * 
     * 
     * @param context Context of the application
     */
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     * 
     * Create the Database based on String containing Create table statement
     */
    public void onCreate(SQLiteDatabase db) {
    
    try
    {
        String CREATE_LISTORGANIZER_TABLE = "CREATE TABLE " + TABLE_LISTORGANIZER + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " 
        		+ KEY_TITLE + " TEXT,"
                + KEY_LIST_ITEM + " TEXT,"
				+ KEY_DATE + " TEXT)";
        db.execSQL(CREATE_LISTORGANIZER_TABLE);
    }
  	catch(SQLException ex)
  	{
  		System.out.println("SQL Exception has occurred" + ex.getMessage());
  		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
  	}
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

    /**
     * Adds List Item object to database. Passed the title and listitem from ListItem Object.
     * 
     * @param listitem List Item Object
     */
    public void addListitem(ListItem listitem) {
    	
        SQLiteDatabase db = this.getWritableDatabase();
    	try
    	{
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, listitem.getTitle()); // Get Title from list item
        values.put(KEY_LIST_ITEM, listitem.getListItem()); // List Item
   		values.put(KEY_DATE, listitem.getDate().toString()); // get date from list item.
        
		// Inserting Row
        db.insert(TABLE_LISTORGANIZER, null, values);
       
    	}
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
      	finally
      	{
      		if(db!=null)
      		{
              db.close();
      		}
      	}
    }
    
  
    /**
     * 
     * Getting All List Items from database
     * @return List of List Items from Database
     * 
     */
    public List<ListItem> getAllListItems() {
    	
    	try
    	{
        List<ListItem> listitemList = new ArrayList<ListItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListItem listitem = new ListItem();
             //  String id = cursor.getString(0);
             //  String name =  cursor.getString(1);
             //  String phonenumber = cursor.getString(2);
                
                listitem.setID(Integer.parseInt(cursor.getString(0)));
                listitem.setTitle(cursor.getString(1));
                listitem.setListItem(cursor.getString(2));
                // Adding listitem to list
                listitemList.add(listitem);
            } while (cursor.moveToNext());
        }
 
        // return listitem list
        return listitemList;
    }
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}

        return null;
    }

    
    /**
     * 
     * Getting All ListItem By Title passed in parameter. Gets all ListItem from database.
     * 
     * @param spinnertitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public List<ListItem> getAllListItemsByTitle(String spinnertitle) {
    	
 
    	try
    	{
        List<ListItem> contactList = new ArrayList<ListItem>();
        // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE +" = ?";
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
  	catch(SQLException ex)
  	{
  		System.out.println("SQL Exception has occurred" + ex.getMessage());
  		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
  	}

    return null;
    }

	 /**
     * 
     * Getting All List String Item By Title passed in parameter. Gets all ListItem from database by title.
     * 
     * @param spinnertitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public ArrayList<String> getAllListStringItemsByTitle(String spinnertitle) {
    	
    	SQLiteDatabase db=null;
    	
    	try
    	{
        // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE +" = ?";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  new String[] {spinnertitle});
 		ArrayList<String> list = new ArrayList<String>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
 
        // return listitem list
        return list;
    }
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
      	finally
      	{
      		if(db!=null)
      		{
              db.close();
      		}
      	}
        return null;
    }
	
		    /**
     * 
     * Getting All List String Item By Title passed in parameter. Gets all ListItem from database by title.
     * 
     * @param spinnertitle spinner title
     * @return ListItem By Title passed in parameter
     */
	public ArrayList<String> getAllListStringItemsSortedByDateModified(String spinnertitle) {
    	
    	 SQLiteDatabase db =null;
    	 
    	try
    	{
        // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE +" = ?" + "ORDER BY" + KEY_DATE +"ASC";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  new String[] {spinnertitle});
 		ArrayList<String> list = new ArrayList<String>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
 
        // return listitem list
        return list;
    	}
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
      	finally
      	{
      		if(db!=null)
      		{
              db.close();
      		}
      	}
        return null;
    }
	
	
			    /**
     * 
     * Getting All List String Item By Title passed in parameter. Gets all ListItem from database by title.
     * 
     * @param spinnertitle spinner title
     * @return ListItem By Title passed in parameter
     */
	public ArrayList<String> getAllListStringItemsSortedByTitle(String spinnertitle) {
    	
    	 SQLiteDatabase db =null;
    	 
    	try
    	{
        // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE +" = ?" + "ORDER BY" + KEY_TITLE +"ASC";
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,  new String[] {spinnertitle});
 		ArrayList<String> list = new ArrayList<String>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
 
        // return listitem list
        return list;
    	}
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
      	finally
      	{
      		if(db!=null)
      		{
              db.close();
      		}
      	}
        return null;
    }
    /**
     * 
     * Updating single listitem on database
     * 
     * @param listitem listitem that will be updated
     * @return  the number of rows updated
     */
    public int updateListItem(ListItem listitem, Date date) {
        
    	SQLiteDatabase db=null;
    	
    	try
    	{
    	db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_LIST_ITEM, listitem.getListItem());
 		values.put(KEY_DATE, date.toString());
		
        // updating row
        return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ?",
                new String[] { String.valueOf(listitem.getTitle()) });
    	}
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
      	finally
      	{
      		if(db!=null)
      		{
              db.close();
      		}
      	}
    	return 0;
    }
 
     /**
     * 
     * Updating a spinner title from database
     * 
     * @param title spinner title that will be updated.
     * @return  the number of rows updated
     */
    public int updateSpinnerTitle(String title) {
    	
    	SQLiteDatabase db=null;
    	
    	try
    	{
        db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
 
        
        // updating row
        return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ?",
                new String[] { title});
    	}
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
      	finally
      	{
      		if(db!=null)
      		{
              db.close();
      		}
             
      	}
		return 0;
        
    }
    
    /**
     * 
     * Deleting single list item based on title and list item
     * 
     * @param listitem List item containing title and list item
     */
    public void deleteListItem(ListItem listitem) {
    	
    SQLiteDatabase db = null;
    try{
      db = this.getWritableDatabase();
      db.delete(TABLE_LISTORGANIZER, KEY_TITLE + " = ?" + " AND "+ KEY_LIST_ITEM + " = ?",
              new String[] { String.valueOf(listitem.getTitle()),String.valueOf(listitem.getListItem()) });
    	}
  	catch(SQLException ex)
  	{
  		System.out.println("SQL Exception has occurred" + ex.getMessage());
  		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
  	}
  	finally
  	{
  		if(db!=null)
  		{
          db.close();
  		}
  	}
    }
    
     /**
     * 
     * Deleting single spinner title
     * 
     * @param spinnertitle current spinner title
     */
    public void deleteSpinnerTitle(String spinnertitle) {
       
    	SQLiteDatabase db=null;
    try
    	{
    	
      db = this.getWritableDatabase();
      db.delete(TABLE_LISTORGANIZER, KEY_TITLE + " = ?",
              new String[] { spinnertitle });
	}
  	catch(SQLException ex)
  	{
  		System.out.println("SQL Exception has occurred" + ex.getMessage());
  		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
  	}
	finally
	{
  		if(db!=null)
  		{
        db.close();
  		}
	}
    }
	
    /**
      * Deleting all list item based on title 
     * 
     * @param listitem List item containing title and list item
     * 
     */
    public void deleteListItemByTitle(ListItem listitem) {
    	SQLiteDatabase db=null;
    	try{
    		
        db = this.getWritableDatabase();
        db.delete(TABLE_LISTORGANIZER, KEY_TITLE + " = ?",
                new String[] { String.valueOf(listitem.getTitle()) });
    	}
      	catch(SQLException ex)
      	{
      		System.out.println("SQL Exception has occurred" + ex.getMessage());
      		Log.e("IO", "IO Exception Error",ex);                  //Log error for IO Exception
      	}
      	catch(NullPointerException ex)
      	{
      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
      		Log.e("NULL", "NullPointerException Error",ex);         //Log error for Null Pointer Exception
      	}
    	finally
    	{
      		if(db!=null)
      		{
            db.close();
      		}
    	}
    	}
		
		
    }

