package com.example.android.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.todolist.main.SpinnerFragment;
import com.example.android.todolist.model.ListItem;
import com.example.android.todolist.model.SpinnerItem;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class handles the database operation for the application. It handles CRUD operations for the application
 *
 * @author Eric
 */
public class ToDoListDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String SQL_DELETE_ENTRIES = null;

    // List Organizer table name
    private static final String TABLE_LISTORGANIZER = "listorganizer";

    // List Organizer Table Columns names (Constants)
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LIST_ITEM = "list_item";
    private static final String KEY_DATE = "date";
    private static final String KEY_POSITION = "position";

    // List Organizer table name
    private static final String TABLE_SPINNERITEMS = "spinneritem";

    // List Organizer Table Columns names
    private static final String KEY_SPINNER_ID = "_id";
    private static final String KEY_TITLE_SPINNER = "title";
    private static final String KEY_POSITION_SPINNER = "position";

    /**
     * Overloaded Constructed of FeedReaderDbHelper Class. Initialized context and database.
     *
     * @param context Context of the application
     */
    public ToDoListDbHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     * 
     * Create the Database based on String containing Create table statement
     */
    public void onCreate(final SQLiteDatabase db) {
        try {
            String createListOrganizerTable = "CREATE TABLE " + TABLE_LISTORGANIZER + "("
                    + KEY_ID + " INTEGER PRIMARY KEY, "
                    + KEY_TITLE + " TEXT,"
                    + KEY_LIST_ITEM + " TEXT,"
                    + KEY_DATE + " TEXT,"
                    + KEY_POSITION + " INTEGER UNIQUE)";
            db.execSQL(createListOrganizerTable);

            String createSpinnerItemsTable = "CREATE TABLE " + TABLE_SPINNERITEMS + "("
                    + KEY_SPINNER_ID + " INTEGER PRIMARY KEY, "
                    + KEY_TITLE_SPINNER + " TEXT UNIQUE,"
                    + KEY_POSITION_SPINNER + " TEXT)";
            db.execSQL(createSpinnerItemsTable);
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        }
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     * Caches for Online data. Delete data from database.
     */
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion, final int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onDowngrade(android.database.sqlite.SQLiteDatabase, int, int)
     * Caches for Online data. Delete data from database.
     */
    public void onDowngrade(final SQLiteDatabase db,
                            final int oldVersion, final int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * Adds List Item object to database. Passed the title and listitem from ListItem Object.
     *
     * @param spinnerItem List Item Object
     */
    public void addSpinneritem(final SpinnerItem spinnerItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, spinnerItem.getTitle()); // Get Title from list item
            values.put(KEY_POSITION, spinnerItem.getPosition()); // Contact Phone

            // Inserting Row
            db.insert(TABLE_SPINNERITEMS, null, values);

        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Adds List Item object to database. Passed the title and listitem from ListItem Object.
     *
     * @param listItem List Item Object
     */
    public void addListitem(final ListItem listItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, listItem.getTitle()); // Get Title from list item
            values.put(KEY_LIST_ITEM, listItem.getListItem()); // List Item
            values.put(KEY_DATE, listItem.getDate().toString()); // get date from list item.
            values.put(KEY_POSITION, listItem.getPosition()); // Contact Phone

            // Inserting Row
            db.insert(TABLE_LISTORGANIZER, null, values);

        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Getting All List Items from database.
     *
     * @return List of List Items from Database
     */
    public List<ListItem> getAllListItems() {
        try {
            List<ListItem> listItemList = new ArrayList<ListItem>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ListItem listItem = new ListItem();
                    listItem.setID(Integer.parseInt(cursor.getString(0)));
                    listItem.setTitle(cursor.getString(1));
                    listItem.setListItem(cursor.getString(2));
                    listItem.setPosition(Integer.parseInt(cursor.getString(4)));

                    // Adding list item to list
                    listItemList.add(listItem);
                } while (cursor.moveToNext());
            }

            // return listitem list
            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        }

        return Collections.emptyList();
    }

    /**
     * Getting All ListItem By Title passed in parameter. Gets all ListItem from database.
     *
     * @return ListItem By Title passed in parameter
     */
    public List<SpinnerItem> getAllSpinnerTitle() {
        try {
            ArrayList<SpinnerItem> listItemList = new ArrayList<SpinnerItem>();
            // Select All Query
            String selectQuery = "SELECT DISTINCT * FROM " + TABLE_SPINNERITEMS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SpinnerItem spinnerItem = new SpinnerItem();
                    spinnerItem.setTitle(cursor.getString(1));
                    spinnerItem.setPosition(Integer.parseInt(cursor.getString(2)));

                    listItemList.add(spinnerItem);
                } while (cursor.moveToNext());
            }
            // return contact list
            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        }

        return Collections.emptyList();
    }

    /**
     * Getting All ListItem By Title passed in parameter. Gets all ListItem from database.
     *
     * @return ListItem By Title passed in parameter
     */
    public ArrayList<String> getAllSpinnerTitleListString() {
        ArrayList<String> listItemList = new ArrayList<String>();

        try {
            // Select All Query
            String selectQuery = "SELECT DISTINCT  * FROM " + TABLE_SPINNERITEMS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SpinnerItem spinnerItem = new SpinnerItem();
                    String id = cursor.getString(0);
                    String title = cursor.getString(1);
                    String position = cursor.getString(2);

                    //    spinnerItem.setID(Integer.parseInt(cursor.getString(0)));
                    spinnerItem.setTitle(title);
                    spinnerItem.setPosition(Integer.parseInt(cursor.getString(2)));

                    listItemList.add(title);
                } while (cursor.moveToNext());
            }
            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        }

        return listItemList;
    }


    /**
     * Getting All Spinner by position passed in by the parameter.
     *
     * @param position the position of spinner title
     * @return position position in list of Spinner title
     */
    public String getAllSpinnerTitleByPosition(final int position) {
        try {
            // Select All Query
            String selectQuery = "SELECT DISTINCT * FROM " + TABLE_SPINNERITEMS + " WHERE " + KEY_POSITION_SPINNER + " = ?";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(position)});
            String title = "";
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    title = cursor.getString(1);
                } while (cursor.moveToNext());
            }

            // return contact list
            return title;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        }

        return null;
    }

    /**
     * Getting All ListItem By Title passed in parameter. Gets all ListItem from database.
     *
     * @param spinnerTitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public List<ListItem> getAllListItemsByTitle(final String spinnerTitle) {
        try {
            List<ListItem> listItemList = new ArrayList<ListItem>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE + " = ?";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{spinnerTitle});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ListItem listItem = new ListItem();
                    listItem.setID(Integer.parseInt(cursor.getString(0)));
                    listItem.setTitle(cursor.getString(1));
                    listItem.setListItem(cursor.getString(2));

                    listItemList.add(listItem);
                } while (cursor.moveToNext());
            }

            // return contact list
            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        }

        return Collections.emptyList();
    }

    /**
     * Getting All List String Item By Title passed in parameter. Gets all ListItem from database by title.
     *
     * @param spinnerTitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public List<String> getAllListStringItemsByTitle(final String spinnerTitle) {
        SQLiteDatabase db = null;

        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE + " = ? ORDER BY " + KEY_POSITION;
            db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{spinnerTitle});
            ArrayList<String> listItemList = new ArrayList<String>();
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    listItemList.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }

            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves all list item(s) by title and sorted by date modified.
     *
     * @param spinnerTitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public List<String> getAllListStringItemsSortedByDateModified(final String spinnerTitle) {
        SQLiteDatabase db = null;

        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE + " = ?" + "ORDER BY " + KEY_DATE + " ASC";
            db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{spinnerTitle});
            ArrayList<String> listItemList = new ArrayList<String>();
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    listItemList.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }

            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves all list item(s) by title and sorted by title.
     *
     * @param spinnerTitle spinner title
     * @return ListItem By Title passed in parameter
     */
    public List<String> getAllListStringItemsSortedByTitle(final String spinnerTitle) {
        SQLiteDatabase db = null;

        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE + " = ? " + " ORDER BY " + KEY_LIST_ITEM + " ASC";

            db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{spinnerTitle});
            ArrayList<String> listItemList = new ArrayList<String>();

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    listItemList.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }

            // return listitem list
            return listItemList;
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return Collections.emptyList();
    }

    /**
     * Getting All List Items from database.
     *
     * @param spinnerTitle the spinner title
     * @return List of List Items from Database
     */
    public List<ListItem> getAllListItemsBySpinnerTitle(final String spinnerTitle) {
        List<ListItem> listItemList = new ArrayList<ListItem>();
        if (spinnerTitle == null) {
            return listItemList;
        }
        if (StringUtils.isNotEmpty(spinnerTitle)) {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE + " = ? ORDER BY " + KEY_POSITION;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{spinnerTitle});
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    ListItem listItem = new ListItem();

                    listItem.setPosition(Integer.parseInt(cursor.getString(4)));
                    listItem.setID(Integer.parseInt(cursor.getString(0)));
                    listItem.setTitle(cursor.getString(1));
                    listItem.setListItem(cursor.getString(2));
                    // Adding contact to list
                    listItemList.add(listItem);
                } while (cursor.moveToNext());
            }
        }

        // return contact list
        return listItemList;
    }

    /**
     * Updating single listitem on database
     *
     * @param listItem listitem that will be updated
     * @return the number of rows updated
     */
    public int updateListItemAdd(final ListItem listItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POSITION, listItem.getPosition() + 1);

        // updating row
        return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ? AND " + KEY_POSITION + "  = ?",
                new String[]{listItem.getTitle(), String.valueOf(listItem.getPosition())});
    }

    /**
     * Updating single listitem on database
     *
     * @param listItem listitem that will be updated
     * @param index    position of listItem
     * @return the number of rows updated
     */
    public void updateListItemAndAddPosition(final ListItem listItem, final int index) {
        List<ListItem> listItemList = getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

        for (int i = index + 1; i < listItemList.size(); i++) {
            updateListItemAdd(listItemList.get(i));
        }
    }

    /**
     * Updating single listitem on database
     *
     * @param index    position of listItem
     * @return the number of rows updated
     */
    public void updateListItemAndAddPositionWithoutListItem( final int index) {
        List<ListItem> listItemList = getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

        for (int i = index + 1; i < listItemList.size(); i++) {
            updateListItemAdd(listItemList.get(i));
        }
    }

    /**
     * Updating single listItem on database
     *
     * @param listItem listItem that will be updated
     * @param date     Date when listItem was added.
     * @return the number of rows updated
     */
    public int updateListItem(final ListItem listItem, final Date date) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            String position = String.valueOf(listItem.getPosition());
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_ITEM, listItem.getListItem());
            values.put(KEY_DATE, date.toString());
            values.put(KEY_POSITION, position);

            // updating row
            return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ? AND " + KEY_POSITION + " = ?",
                    new String[]{listItem.getTitle(), position});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    /**
     * Updating single listItem on database.
     *
     * @param listItem listItem that will be updated
     * @param date     Date when listItem was added.
     * @param position new position that listItem be updated to
     * @return the number of rows updated
     */
    public int updateListItemPosition(final ListItem listItem,
                                      final Date date, final int position) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            String listItemPosition = String.valueOf(listItem.getPosition());
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_ITEM, listItem.getListItem());
            values.put(KEY_DATE, date.toString());
            values.put(KEY_POSITION, position);

            // updating row
            return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ? AND " + KEY_POSITION + " = ?",
                    new String[]{listItem.getTitle(), listItemPosition});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    /**
     * Updating single listItem on database.
     *
     * @param listItem listItem that will be updated
     * @param date     Date when listItem was added.
     * @param position new position that listItem be updated to
     * @return the number of rows updated
     */
    public int updateListItemPositionSorted(final ListItem listItem,
                                            final Date date, final int position) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_LIST_ITEM, listItem.getListItem());
            values.put(KEY_DATE, date.toString());
            values.put(KEY_POSITION, position);

            // updating row
            return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ? ",
                    new String[]{listItem.getTitle()});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    /**
     * Updating single listItem on database
     *
     * @param listItem listItem that will be updated
     * @param date     Date when listItem was added.
     * @param position new position that listItem be updated to
     * @return the number of rows updated
     */
    public int updateListItemPositionAndSpinnerTitle(final ListItem listItem,
                                                     final Date date, final int position) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            String listItemPosition = String.valueOf(listItem.getPosition());
            ContentValues values = new ContentValues();
            values.put(KEY_LIST_ITEM, listItem.getListItem());
            values.put(KEY_DATE, date.toString());
            values.put(KEY_POSITION, position);

            // updating row
            return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ? AND " + KEY_POSITION + " = ?",
                    new String[]{listItem.getTitle(), listItemPosition});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }


    /**
     * Retrieves all spiner tiles by title and position.
     *
     * @param spinnerTitle the spinner title
     * @return list of spinner titles.
     */
    public List<String> getsAllSpinnerTitleByTitleAndPosition(final String spinnerTitle) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LISTORGANIZER + " WHERE " + KEY_TITLE + " = ? AND " + KEY_POSITION + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{spinnerTitle});
        List<String> spinnerTitleList = new ArrayList();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                spinnerTitleList.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // return contact list
        return spinnerTitleList;
    }

    /**
     * Updating single listitem on database.
     *
     * @param listItem listitem that will be updated
     * @return the number of rows updated
     */
    public int updateListItemDelete(final ListItem listItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POSITION, listItem.getPosition() - 1);

        // updating row
        return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ? AND " + KEY_POSITION + "  = ?",
                new String[]{listItem.getTitle(), String.valueOf(listItem.getPosition())});
    }

    /**
     * Updating single listitem on database
     *
     * @param listItem listitem that will be updated
     * @return the number of rows updated
     */
    public void updateListItemDeletePosition(final ListItem listItem) {
        List<ListItem> listItemList = getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

        int position = listItem.getPosition();

        for (int i = position; i < listItemList.size(); i++) {
            updateListItemDelete(listItemList.get(i));
        }


    }

    /**
     * Deleting single list item based on title and list item
     *
     * @param listItem List item containing title and list item
     */
    public void deleteListItem(final ListItem listItem) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            String title = listItem.getTitle();
            String listItemString = listItem.getListItem();
            String position = String.valueOf(listItem.getPosition());
            db.delete(TABLE_LISTORGANIZER, KEY_TITLE + " = ?" + " AND " + KEY_LIST_ITEM + " = ? AND " + KEY_POSITION + " = ?",
                    new String[]{title, listItemString, position});

        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Deleting all list item based on title.
     *
     * @param listItem List item containing title and list item
     */
    public void deleteListItemByTitle(final ListItem listItem) {
        SQLiteDatabase db = null;

        try {

            db = this.getWritableDatabase();
            db.delete(TABLE_LISTORGANIZER, KEY_TITLE + " = ?",
                    new String[]{listItem.getTitle()});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Adds a spinner title.
     *
     * @param spinnerTitle the spinner title
     */
    public void addSpinnerTitle(final String spinnerTitle) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, spinnerTitle); // Get Title from list item

            // Inserting Row
            db.insert(TABLE_SPINNERITEMS, null, values);

        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * Updating a spinner title from database
     *
     * @param spinnerTitle spinner title that will be updated.
     * @return the number of rows updated
     */
    public int updateSpinnerTitle(final String spinnerTitle) {
        updateSpinnerTitleListTable(spinnerTitle);
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, spinnerTitle);


            // updating row
            return db.update(TABLE_SPINNERITEMS, values, KEY_TITLE + " = ?",
                    new String[]{spinnerTitle});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }

        }
        return 0;

    }

    /**
     * Updating a spinner title from database.
     *
     * @param spinnerTitle spinner title that will be updated.
     * @return the number of rows updated
     */
    public int updateSpinnerTitleListTable(final String spinnerTitle) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, spinnerTitle);


            // updating row
            return db.update(TABLE_LISTORGANIZER, values, KEY_TITLE + " = ?",
                    new String[]{spinnerTitle});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }

        }
        return 0;

    }

    /**
     * Delete a single spinner title.
     *
     * @param spinnerTitle current spinner title
     */
    public void deleteSpinnerTitle(final String spinnerTitle) {
        SQLiteDatabase db = null;
        try {

            db = this.getWritableDatabase();
            db.delete(TABLE_LISTORGANIZER, KEY_TITLE + " = ?",
                    new String[]{spinnerTitle});

            db.delete(TABLE_SPINNERITEMS, KEY_TITLE + " = ?",
                    new String[]{spinnerTitle});
        } catch (SQLException ex) {
            Log.e("IO", "IO Exception Error", ex);                  //Log error for IO Exception
        } catch (NullPointerException ex) {
            Log.e("NULL", "NullPointerException Error", ex);         //Log error for Null Pointer Exception
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}

