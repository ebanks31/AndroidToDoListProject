package com.example.android.todolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.preference.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;

import com.example.android.todolist.main.CustomOnItemSelectedListener;
import com.example.android.todolist.main.DetailFragment;
import com.example.android.todolist.main.DetailFragment.detailFragmentSelectedListener;
import com.example.android.todolist.database.ToDoListDbHelper;
import com.example.android.todolist.main.SpinnerFragment;
import com.example.android.todolist.model.ListItem;
import com.example.android.todolist.utility.ThemeUtils;
import com.example.android.todolist.utility.ToDoListUtility;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * 
 * This class creates the main Activity for the Android program
 * 
 * @author Eric Banks
 *
 */
public class ListOrganizerActivity extends Activity implements detailFragmentSelectedListener,SpinnerFragment.OnItemSelectedListener{


	private Context contextInfo;
	public static ArrayList<String> listValues;
	public static ArrayAdapter<String> listAdapter;
	//private ShareActionProvider mShareActionProvider;
    private ToDoListUtility todolistutility;
    private static final int SETTINGS_RESULT = 1;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    SpinnerFragment spinnerFragment;
    DetailFragment detailFragment;
    FragmentManager fragmentManager;

    public  ListOrganizerActivity()
    {
        todolistutility = new ToDoListUtility();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * 
     * Event occurs when Activity is created. This is initial start of the program.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


      //  Context contextApp = getApplicationContext();
      //  contextInfo = contextApp;
      //  context = (TextView) findViewById(R.id.contextmenu);



        /*
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // READ FROM DATABASE AND POPULATE VIEWS. ALSO POPULATE LIST TITLES in database. CREATE DATABASE LAYOUT(List of spinner title names, list of item in list fragments)
        // Think about database structure.

        ToDoListDbHelper db = new ToDoListDbHelper(this);
        registerForContextMenu(spinner);

        SpinnerFragment listFragment = (SpinnerFragment ) getFragmentManager()
                .findFragmentById(R.id.listfragment);
        registerForContextMenu(listFragment.getListView());
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detailFragment);
        registerForContextMenu(detailFragment.getListView());
*/



        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction ();
        spinnerFragment = new SpinnerFragment();
        detailFragment = new DetailFragment();
       // myFrag
         //       .setSpecialText ("Frag time:  " + System.currentTimeMillis ());
       // myFragList.add (myFrag);
        fragmentTransaction.add(R.id.main_activity, spinnerFragment, "SpinnerFragment");
        fragmentTransaction.add(R.id.main_activity, detailFragment, "DetailFragment");
        fragmentTransaction.addToBackStack("state");
        fragmentTransaction.commit();

        ThemeUtils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_rssfeed);
        //registerForContextMenu(detailFragment.getListView());
        //List<ListItem> contacts = db.getAllListItems();




        /*
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_rssfeed);
        Context contextApp = getApplicationContext();
        contextInfo = contextApp;
        context = (TextView) findViewById(R.id.contextmenu);
        Spinner spinner = (Spinner) findViewById ( R.id.spinner1 );
	   // READ FROM DATABASE AND POPULATE VIEWS. ALSO POPULATE LIST TITLES in database. CREATE DATABASE LAYOUT(List of spinner title names, list of item in list fragments)
	   // Think about database structure.

        ToDoListDbHelper db = new ToDoListDbHelper(this);
//        registerForContextMenu(spinner);

        SpinnerFragment listFragment = (SpinnerFragment ) getFragmentManager()
                .findFragmentById(R.id.listfragment);
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detailFragment);
        registerForContextMenu(detailFragment.getListView());

        /*
        FragmentManager fragmentManager = getFragmentManager();
// Or: FragmentManager fragmentManager = getSupportFragmentManager()
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentManager fragmentManager2 = getFragmentManager();
// Or: FragmentManager fragmentManager = getSupportFragmentManager()
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();

        if (listFragment == null) {
            listFragment = new SpinnerFragment();
            fragmentTransaction.add(R.id.main_activity, listFragment);
            fragmentTransaction.commit();
        }
/*
        if (detailFragment == null) {
            detailFragment = new DetailFragment();
            fragmentTransaction2.add(R.id.main_activity, detailFragment);
            fragmentTransaction2.commit();
        }*/

       //registerForContextMenu(listFragment.getListView());

        // Will have to register context menu to established list view to get the context menu to work.

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1 ){
            getFragmentManager().popBackStack();
        } else {
            this.finish();
            super.onBackPressed();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);

        }
    /**
     * 
     * Get Context from the RssFeedActivity
     * 
     * @return Context from the RssFeedActivity
     */
    public Context getContext() {
return contextInfo;
}

/**
 * 
 * Set Context from the RssFeedActivity
 * 
 * @param context Context from the RssFeedActivity
 */
public void setContext(Context context) {
this.contextInfo = context;
}


    //NEW
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     * 
     * Event occurs when action overflowed action item is clicked.
     * 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.mainmenu, menu);
      
     MenuItem shareItem = menu.findItem(R.id.action_overflow);
      
     ShareActionProvider mShareActionProvider = new ShareActionProvider(this);
     MenuItemCompat.setActionProvider(shareItem, mShareActionProvider);
      return super.onCreateOptionsMenu(menu);
    }
	
	
   	public static Date getCurrentDate() {
   	Calendar calobj = Calendar.getInstance();
	Date date = calobj.getTime();
	return date;
	}
	
	
    //NEW
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * 
     * Event that occurred when Action items are clicked in Action Bar.
     *
     * 
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.action_new:
    	     Toast.makeText(this, "Action new selected", Toast.LENGTH_SHORT)
             .show();
          addItemToListActionBar(item);
          return true;
      case R.id.action_refresh:
        Toast.makeText(this, "Action refresh selected", Toast.LENGTH_SHORT)
            .show();
        break;
      case R.id.action_overflow:
          Toast.makeText(this, "Action overflow selected", Toast.LENGTH_SHORT)
              .show();

          View menuItemView = findViewById(R.id.action_overflow); // SAME ID AS MENU ID
          PopupMenu popupMenu = new PopupMenu(this, menuItemView); 
          popupMenu.inflate(R.menu.items);

          popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem item) {
                  //  String title = item.getTitle().toString();
                  switch (item.getItemId()){
                      case R.id.Settings:
                        //  fragmentTransaction.hide(detailFragment);
                          // Display the fragment as the main content.
                       /*)   getFragmentManager().beginTransaction().hide(detailFragment)
                                  .replace(R.id.main_activity, new SettingsFragment())
                                  .addToBackStack(null)
                                  .commit();*/

                          getFragmentManager().beginTransaction().hide(detailFragment).hide(spinnerFragment)
                                  .add(R.id.main_activity, new SettingsFragment())
                                  .addToBackStack(null)
                                  .commit();
                          break;
                      case R.id.Options:

                          break;

                  }
                  return false;
              }
          });
          popupMenu.show();
          break;
      case R.id.action_settings:
        Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
            .show();

          // Intent i = new Intent(getApplicationContext(), UserSettingActivity.class);
          //  startActivityForResult(i, SETTINGS_RESULT);

          // Display the fragment as the main content.
          getFragmentManager().beginTransaction()
                  .replace(android.R.id.content, new SettingsFragment())
                  .commit();
        break;
     case R.id.sort_title:
		sortListByTitle ( item );
        Toast.makeText(this, "Sort Title selected", Toast.LENGTH_SHORT)
            .show();
        break;
		case R.id.date_modified:
		sortListByDateModified ( item );
        Toast.makeText(this, "Date Modified selected", Toast.LENGTH_SHORT)
            .show();
        break;
          case R.id.default_sort:
              sortListByDateModified ( item );
              Toast.makeText(this, "Default Sort selected", Toast.LENGTH_SHORT)
                      .show();
              break;
      default:
        break;
      }

      return true;
    }

    /**
     * Sort the List Items by Spinner Title
     *
     * @param item MenuItem that is clicked.
     */
    public void sortListByTitle(MenuItem item)
	{

        ToDoListDbHelper db = new ToDoListDbHelper(this);
	//	List<ListItem> alllistitemlist = db.getAllListItemsSortedByTitle();
	    //Need to get all List Items. Get the list item from titles.
	 	ArrayList<String> listItemList = db.getAllListStringItemsSortedByTitle(SpinnerFragment.currentSpinnerTitle);

        updateListView(listItemList);

        showAlertDialogPromptSortByTitle(db, listItemList );
		//UPDATE Database. Update Database Sort Method by Title in DB class. Remove everything by title and insert into table by title.*/
	
	}

    /**
     * Sort the List Items by Date Modified
     *
     * @param item MenuItem that is clicked.
     */
    public void sortListByDateModified(MenuItem item)
		{

        ToDoListDbHelper db = new ToDoListDbHelper(this);
		//Need to get all List Items. Get the list item from titles.
	 	ArrayList<String> listItemList = db.getAllListStringItemsSortedByDateModified(SpinnerFragment.currentSpinnerTitle);

            updateListView(listItemList);
            showAlertDialogPromptSortByTitle(db, listItemList );
        /*
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
    	android.R.layout.simple_list_item_1, listitemlist);
		SpinnerFragment.listspinner.setAdapter(dataAdapter);
		*/


		//UPDATE Database. Update Database Sort Method by Date Modified in DB class.Remove everything by title and insert into table by date modified.
	

	}

    /**
     *
     * Shows Alert Dialog Box with Yes or No Options
     *
     * @param db db obect for calling database calls
     * @param listItemList ArrayList of String for each List Items
     */
    public void showAlertDialogPromptSortByTitle(final ToDoListDbHelper db, final ArrayList<String> listItemList)
    {
        AlertDialog.Builder  alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Save Sort");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to save sort?");
        /*
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
*/
        // Setting Positive "Yes" Button

        alertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        List<ListItem> list1 = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle );

                        for (ListItem listitem:list1)
                        {
                            Log.d("Title", String.valueOf(listitem.getTitle()));
                            Log.d("ListItem", String.valueOf(listitem.getListItem()));
                            Log.d("Position", String.valueOf(listitem.getPosition()));

                        }

                        Date date = ListOrganizerActivity.getCurrentDate();
                        for (int i = 0; i < listItemList.size(); i++)
                        {
                            String listItem = listItemList.get (i);
                           // int listItemPosition = list1.get(i).getPosition();

                            //SORT BY POSITION
                            db.updateListItemPositionSorted(new ListItem(SpinnerFragment.currentSpinnerTitle, listItem, date, 0), date, i + 1 );
                        }


                    }
                });

        setPositiveAlertOptionNO(alertDialog);
        alertDialog.show();
    }


    /**
     * Called when the Add Button is clicked in Action Bar
     * 
     * @param item Menu item that is clicked
     */
    public void addItemToListActionBar(MenuItem item) {
       // CustomOnItemSelectedListener customlistener = new CustomOnItemSelectedListener();
//        final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
        int valToSet = (int) spinner.getSelectedItemId();
       // final int index = (info!=null) ? info.position : valToSet;


        final DetailFragment detailfragment = (DetailFragment)
                getFragmentManager().findFragmentById(R.id.detailFragment);

        AlertDialog.Builder  alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("List item");

        // Setting Dialog Message
        alertDialog.setMessage("Change your list item");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        // Setting Positive "Yes" Button

        alertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        // Write your code here to execute after dialog

                        String listInput = input.getText().toString();
                     //   Boolean whiteSpaceFound = todolistutility.checkWhiteSpaces(listInput);
                        Boolean validCharactersFound = todolistutility.checkValidCharacters(listInput);

                        //Checks Invalid characters. Checks if editText is empty,space, or null. Not a valid list item.
                        if (listInput.equals("") || validCharactersFound == false)
                        {

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
                            // Setting Dialog Title
                            alertDialog.setTitle("Invalid Name");

                            // Setting Dialog Message
                            alertDialog.setMessage("Please Enter a valid list name");
                            setPositiveAlertOptionOK(alertDialog);
                            alertDialog.show();

                        }
                        //else if(!SpinnerFragment.currentspinner.equals("New List") && !SpinnerFragment.currentspinner.equals("Sample List"))
                        else
                        {

                            CustomOnItemSelectedListener a = new CustomOnItemSelectedListener();
                            ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

                            Date date = ListOrganizerActivity.getCurrentDate();
                            // List<ListItem> itemlistbytitle = db.getAllListItems();
                            //ArrayList<String> values =a.getListTitleFromListItems(itemlistbytitle,SpinnerFragment.currentspinner);
                            List<ListItem> list = db.getAllListItemsBySpinnerTitle( SpinnerFragment.currentSpinnerTitle );


                            for (ListItem listItem:list)
                            {
                                Log.d("Title", String.valueOf(listItem.getTitle()));
                                Log.d("ListItem", String.valueOf(listItem.getListItem()));
                                Log.d("Position", String.valueOf(listItem.getPosition()));

                            }


                            if (ListOrganizerActivity.listValues.size() > 0)
                            {
                                db.addListitem(new ListItem( SpinnerFragment.currentSpinnerTitle, listInput, date, ListOrganizerActivity.listValues.size() + 1));
                                ListOrganizerActivity.listValues.add(ListOrganizerActivity.listValues.size(), listInput);
                            }
                            else
                            {
                                db.addListitem(new ListItem( SpinnerFragment.currentSpinnerTitle, listInput, date, 1));
                                ListOrganizerActivity.listValues.add(0, listInput);

                            }


                            List<ListItem> logList = db.getAllListItemsBySpinnerTitle( SpinnerFragment.currentSpinnerTitle );

                            for (ListItem listItem:logList)
                            {
                                Log.d("Title", String.valueOf(listItem.getTitle()));
                                Log.d("ListItem", String.valueOf(listItem.getListItem()));
                                Log.d("Position", String.valueOf(listItem.getPosition()));

                            }

                            updateListView(ListOrganizerActivity.listValues);

                        }
                    }
                });

        setPositiveAlertOptionNO(alertDialog);
        alertDialog.show();
    	  
    }
    
    /**
     * Removes Duplicates from ArrayList of strings
     * 
     * @param stringList Array List of string containing spinner titles
     * @return  ArrayList of strings with duplicates removed
     */
    public ArrayList<String> removeDuplicates(ArrayList<String> stringList)
    {
  	  HashSet hs = new HashSet();
  	  hs.addAll(stringList);
      stringList.clear();
      stringList.addAll(hs);
  	  return stringList;
    }

    
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 * 
	 * Create Context Menu when Long click event is triggered.
	 * 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
    	ContextMenuInfo menuInfo) {
		
	    menu.setHeaderTitle("Context Menu Options");  
	    
	     // DetailFragment detailFragment1 = (DetailFragment) getFragmentManager()
	   //           .findFragmentById(R.id.detailFragment);

        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentByTag("DetailFragment");

	    //  int listId = (int) detailFragment.getSelectedItemId();
	     
	     if (v.getId()== R.id.spinner1)
	     {
    			//AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
    			String[] menuItems = getResources().getStringArray(R.array.contextmenuitems);
    			for (int i = 0; i < menuItems.length; i++) {
      				menu.add(Menu.NONE, i, i, menuItems[i]);
    			}
	     }
    			else if (v == detailFragment.getListView())
    			{
      	      			String[] menuItemsContext = getResources().getStringArray(R.array.contextmenuitems);
      	      			for (int i = 0; i < menuItemsContext.length; i++) {
      	        				menu.add(Menu.NONE, i, i, menuItemsContext[i]);
      	      			}
    			}
      					
    }
  
	

 /* (non-Javadoc)
 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
 * Long Click Event handle that handles the Context Menu Options.
 * Does certain action depending on what options is clicked on Context Menu
 */
@Override
public boolean onContextItemSelected(MenuItem item) {
    final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

    Spinner spinner = (Spinner) findViewById(R.id.spinner1);
    int valToSet = (int) spinner.getSelectedItemId();

   // final DetailFragment detailFragment1 = (DetailFragment)
   //         getFragmentManager().findFragmentById(R.id.detailFragment);
    final DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentByTag("DetailFragment");
   //final ListFragment listFragment = (ListFragment)
     //       getFragmentManager().findFragmentById(R.id.listfragment);

   // ArrayAdapter<String> adapter = ListOrganizerActivity.listAdapter;

    final int index = (info != null) ? info.position : valToSet;

    long idPosition = info.id;

    int menuItemIndex = item.getItemId();
    //get id another way if spinner is selected. Register the right menu.

    String[] menuItems = getResources().getStringArray(R.array.contextmenuitems);
    final String menuItemName = menuItems[menuItemIndex];
    ListAdapter listAdapter = detailFragment.getListAdapter();
    long listSelectedId = listAdapter.getItemId(index);
  //  final String currentItem = (String) listAdapter.getItem(index);

    long spinnerPosition = SpinnerFragment.listSpinner.getSelectedItemId();


  //  int parentId = ((View) info.targetView.getParent()).getId();

    if (menuItemName.equals("Add") && listSelectedId == index)
    //if(menuItemName.equals("Add") && parentId==R.id.detailFragment)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("List item");

        // Setting Dialog Message
        alertDialog.setMessage("Add a list item");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        // Setting Positive "Yes" Button

        alertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        String listInput = input.getText().toString();

                        // Boolean whiteSpaceFound = todolistutility.checkWhiteSpaces(listInput);

                        Boolean validCharactersFound = todolistutility.checkValidCharacters(listInput);
                        //Checks Invalid characters. Checks if edittext is empty,space, or null. Not a valid list item.
                        if (listInput.isEmpty() || validCharactersFound == false) {

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
                            // Setting Dialog Title
                            alertDialog.setTitle("Invalid Name");

                            // Setting Dialog Message
                            alertDialog.setMessage("Please Enter a valid list name");
                            setPositiveAlertOptionOK(alertDialog);
                            alertDialog.show();

                        } else {
                            //Add to Spinner

                            ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);
                            Date date = ListOrganizerActivity.getCurrentDate();
                            db.updateListItemAddPosition(new ListItem(SpinnerFragment.currentSpinnerTitle, listInput, date, index), index);


                            db.addListitem(new ListItem(SpinnerFragment.currentSpinnerTitle, listInput, date, index + 2));

				
				/*
				if(!SpinnerFragment.currentspinner.equals("New List") && !SpinnerFragment.currentspinner.equals("Sample List"))
				{
				db.addContact(new ListItem(SpinnerFragment.currentspinner, listinput));
				}

				ArrayList<String> lst3 = new ArrayList<String>();
				lst3.addAll(Arrays.asList(ListOrganizerActivity.values));
				lst3.add(index+1,listinput);
				ArrayList<String> lst4 =db.getAllListStringItemsByTitle(SpinnerFragment.currentspinner);
				*/

                            ListOrganizerActivity.listValues.add(index + 1, listInput);
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
                                    android.R.layout.simple_list_item_1, ListOrganizerActivity.listValues);
                            adapter.notifyDataSetChanged();
                            detailFragment.setListAdapter(adapter);

                        }
                    }
                });

        // Setting Positive "NO" Button
        setPositiveAlertOptionNO(alertDialog);

        alertDialog.show();
    } else if (menuItemName.equals("Edit") && listSelectedId == index)
    //else if(menuItemName.equals("Edit") && parentId==R.id.detailFragment)
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("List Item");

        // Setting Dialog Message
        alertDialog.setMessage("Change your list item");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        // Setting Positive "Yes" Button

        alertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        String listInput = input.getText().toString();
                        //Checks if edittext is empty,space, or null. Not a valid list item.
                        // Boolean whiteSpaceFound = todolistutility.checkWhiteSpaces(listInput);

                        Boolean validCharactersFound = todolistutility.checkValidCharacters(listInput);

                        //Checks if edittext is empty,space, or null. Not a valid list item.
                        if (listInput.isEmpty() || validCharactersFound == false) {

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
                            // Setting Dialog Title
                            alertDialog.setTitle("Invalid Name");

                            // Setting Dialog Message
                            alertDialog.setMessage("Please Enter a valid list name");
                            setPositiveAlertOptionOK(alertDialog);

                            alertDialog.show();

                        } else {
                            //Add to Spinner

                            ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

                            Date date = ListOrganizerActivity.getCurrentDate();
                            ArrayList<String> lst4 = db.getAllListStringItemsByTitle(SpinnerFragment.currentSpinnerTitle);

                            updateListView(lst4);

                            //String key = ((TextView) info.targetView).getText().toString();

                            if (!SpinnerFragment.currentSpinnerTitle.equals("New List") && !SpinnerFragment.currentSpinnerTitle.equals("Sample List")) {
                                //db.updateListItem(new ListItem(SpinnerFragment.currentSpinnerTitle, listInput), date);
                                int position = lst4.size();

                                List<ListItem> list = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

                                for (ListItem listItem : list) {
                                    Log.d("Title", String.valueOf(listItem.getTitle()));
                                    Log.d("ListItem", String.valueOf(listItem.getListItem()));
                                    Log.d("Position", String.valueOf(listItem.getPosition()));

                                }

                                System.out.println("Index " + index);
                                db.updateListItem(new ListItem(SpinnerFragment.currentSpinnerTitle, listInput, date, index + 1), date);


                                List<ListItem> currentList = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

                                for (ListItem listitem : currentList) {
                                    Log.d("Title", String.valueOf(listitem.getTitle()));
                                    Log.d("ListItem", String.valueOf(listitem.getListItem()));
                                    Log.d("Position", String.valueOf(listitem.getPosition()));

                                }
                            }

                            ArrayList<String> finalList = db.getAllListStringItemsByTitle(SpinnerFragment.currentSpinnerTitle);

                            List<ListItem> currentList = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

                            for (ListItem listitem : currentList) {
                                Log.d("Title", String.valueOf(listitem.getTitle()));
                                Log.d("ListItem", String.valueOf(listitem.getListItem()));
                                Log.d("Position", String.valueOf(listitem.getPosition()));

                            }
                            // final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ListOrganizerActivity.this,
                            //     android.R.layout.simple_list_item_1, lst4);


                            // adapter1.insert(listinput,index+1);
                            // adapter1.remove(listinput);

                            updateListView(finalList);

                /*
				ListOrganizerActivity.listValues.add(index+1, listInput);
				ListOrganizerActivity.listValues.remove(index);
			    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
		    	android.R.layout.simple_list_item_1, ListOrganizerActivity.listValues);
                adapter.notifyDataSetChanged();
				detailFragment.setListAdapter(adapter);*/

                        }
                    }
                });

        // Setting Negative "NO" Button
        setPositiveAlertOptionNO(alertDialog);

        alertDialog.show();
    } else if (menuItemName.equals("Delete") && listSelectedId == index) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Delete");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to delete?");
        alertDialog.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        String key = ((TextView) info.targetView).getText().toString();
                        ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

                        ArrayList<String> listItems = db.getAllListStringItemsByTitle(SpinnerFragment.currentSpinnerTitle);
                        Date date = ListOrganizerActivity.getCurrentDate();

                       // int position = index;

                        List<ListItem> list = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);
                         /*
                         ArrayList<Integer> positionList = new ArrayList<Integer>();

                         for (int i = 0; i < list.size(); i++)
                         {
                             Log.d("Position", String.valueOf(list.get(i).getPosition()));
                             Log.d("ListItem", String.valueOf(list.get(i).getListItem()));
                             Log.d("Title", String.valueOf(list.get(i).getTitle()));
                             positionList.add(list.get(i).getPosition());
                         }

                         for (int i = 0; i < positionList.size(); i++)
                         {

                             db.deleteListItem(new ListItem(SpinnerFragment.currentSpinnerTitle, listItems.get(i), date, positionList.get(i)));
                             ListOrganizerActivity.listValues.remove((positionList.get(i) - 1) - i );
                         }
*/

                        //Removing Single Item
                        db.deleteListItem(new ListItem(SpinnerFragment.currentSpinnerTitle, key, date, index + 1));
                        ListOrganizerActivity.listValues.remove(index);


                        List<ListItem> list1 = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

                        for (ListItem listitem : list1) {
                            Log.d("Title", String.valueOf(listitem.getTitle()));
                            Log.d("ListItem", String.valueOf(listitem.getListItem()));
                            Log.d("Position", String.valueOf(listitem.getPosition()));

                        }

                        for (int i = 0; i < list1.size(); i++) {
                            String listItem = list1.get(i).getListItem();
                            int listItemPosition = list1.get(i).getPosition();

                            //SORT BY POSITION
                            db.updateListItemPosition(new ListItem(SpinnerFragment.currentSpinnerTitle, listItem, date, listItemPosition), date, i + 1);
                        }

                        List<ListItem> list2 = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);

                        ArrayList<String> updatedListItems = todolistutility.getListTitleFromListItems(list2, SpinnerFragment.currentSpinnerTitle);
                        for (ListItem listitem : list2) {
                            Log.d("Title", String.valueOf(listitem.getTitle()));
                            Log.d("ListItem", String.valueOf(listitem.getListItem()));
                            Log.d("Position", String.valueOf(listitem.getPosition()));

                        }

                        updateListView(updatedListItems);
                    }
                });
        setPositiveAlertOptionNO(alertDialog);

        alertDialog.show();

    } else if (menuItemName.equals("Edit") && spinnerPosition == idPosition)
    //else if(menuItemName.equals("Edit") && parentId==R.id.spinner1)
    {

        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Change your title");

        // Setting Dialog Message
        alertDialog.setMessage("Change your title");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.key);

        // Setting Positive "Yes" Button
        alertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        String listInput = input.getText().toString();
                        //Checks if editText is empty,space, or null. Not a valid list item.
                        // Boolean whiteSpaceFound = todolistutility.checkWhiteSpaces(listInput);

                        Boolean validCharactersFound = todolistutility.checkValidCharacters(listInput);
                        //Checks if editText is empty,space, or null. Not a valid list item.
                        if (listInput.equals("") || validCharactersFound == false) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
                            // Setting Dialog Title
                            alertDialog.setTitle("PASSWORD");

                            // Setting Dialog Message
                            alertDialog.setMessage("Please Enter a valid list name");
                            setPositiveAlertOptionOK(alertDialog);
                            alertDialog.show();
                        } else {
                            //Add to Spinner
                            //Spinner spinner = (Spinner)findViewById(R.id.spinner1);

                            //Add to Spinner
                            String key = ((TextView) info.targetView).getText().toString();
                            ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

                            if (!SpinnerFragment.currentSpinnerTitle.equals("New List") && !SpinnerFragment.currentSpinnerTitle.equals("Sample List")) {
                                db.updateSpinnerTitle(key);
                            }

                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) SpinnerFragment.listSpinner.getAdapter();
                            adapter.notifyDataSetChanged();
                            SpinnerFragment.listSpinner.setAdapter(adapter);
                        }

                    }
                });
        // Setting Negative "NO" Button
        setPositiveAlertOptionNO(alertDialog);


        // closed

        // Showing Alert Message
        alertDialog.show();
    } else if (menuItemName.equals("Delete") && spinnerPosition == idPosition) {
        //Add to Spinner
        String key = ((TextView) info.targetView).getText().toString();
        ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

        if (!SpinnerFragment.currentSpinnerTitle.equals("New List") && !SpinnerFragment.currentSpinnerTitle.equals("Sample List")) {
            db.deleteSpinnerTitle(key);
        }


        ArrayAdapter<String> deleteadapter = (ArrayAdapter<String>) SpinnerFragment.listSpinner.getAdapter();
        deleteadapter.remove((String) key);
        deleteadapter.notifyDataSetChanged();
        SpinnerFragment.listSpinner.setAdapter(deleteadapter);
    } else //menu item = Share
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
       // shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
        // );
        /*
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("Hello Facebook")
                        .setContentDescription(
                                "The 'Hello Facebook' sample  showcases simple Facebook integration")
                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                        .build();
                linkContent.
                shareDialog.show(linkContent);
            }
*/
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);
            List<ListItem> listItemList = db.getAllListItemsBySpinnerTitle(SpinnerFragment.currentSpinnerTitle);
            String listItems = "List Items: ";

            ArrayList<String> updatedListItems = todolistutility.getListTitleFromListItems(listItemList, SpinnerFragment.currentSpinnerTitle);

            StringBuilder sb = new StringBuilder(listItems);

            for (String value : updatedListItems)
            {
                sb.append(value + " ");
            }

            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Title of List: " + SpinnerFragment.currentSpinnerTitle)
                    .setContentDescription(sb.toString())
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

                    shareDialog.show(linkContent);
        }
        }

        return true;
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
@Override
public void onRssItemSelected(int position) {

        }
 
/* (non-Javadoc)
 * @see com.example.android.rssfeed.SpinnerFragment.OnItemSelectedListener#sampleFragmentList(int)
 * 
 * Provides sample List that the Detail Fragment populates
 * 
 */
@Override
public void sampleFragmentList(int position) {

 //   DetailFragment detailFragment1 = (DetailFragment) getFragmentManager()
//            .findFragmentById(R.id.detailFragment);

    DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentByTag("DetailFragment");
        if (detailFragment != null) {
       	    String[] values = new String[] { "Android1", "iPhone", "WindowsMobile",
    	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
    	        "Linux", "OS/2" };
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, values);
            detailFragment.setListAdapter(adapter);
       }
 
}


/* (non-Javadoc)
 * @see com.example.android.rssfeed.SpinnerFragment.OnItemSelectedListener#clearFragmentList(int)
 * clear detail fragments list. Clear list items from List View.
 */
@Override
public void clearFragmentList(int position) {

  //  DetailFragment detailFragment1 = (DetailFragment) getFragmentManager()
  //          .findFragmentById(R.id.detailFragment);
   //     SpinnerFragment listfragment = (SpinnerFragment ) getFragmentManager()
//            .findFragmentById(R.id.listfragment);
    DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentByTag("DetailFragment");
        if (detailFragment != null) {
       	    String[] values = new String[] { "Android22222222", "iPhone", "WindowsMobile",
    	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
    	        "Linux", "OS/2" };
    		ArrayList<String> lst1 = new ArrayList<String>();
    		lst1.addAll(Arrays.asList(values));
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, lst1);

    	    adapter.notifyDataSetChanged();
    	    detailFragment.setListAdapter(adapter);
       }
}


/* (non-Javadoc)
 * @see com.example.android.rssfeed.SpinnerFragment.OnItemSelectedListener#setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener)
 * Sets OnItemSelectedListener
 * 
 */
@Override
public void setOnItemSelectedListener(
		OnItemSelectedListener onItemSelectedListener) {
	
}

/* (non-Javadoc)
 * @see com.example.android.rssfeed.DetailFragment.detailFragmentSelectedListener#detailFragmentSelected(java.lang.String[], android.widget.ArrayAdapter)
 * 
 * Updates the current list item values and adapters.
 */
@Override
public void detailFragmentSelected(String[] values, ArrayAdapter<String> adapter) {
	ListOrganizerActivity.listAdapter = adapter;
}

/* (non-Javadoc)
 * @see com.example.android.rssfeed.SpinnerFragment.OnItemSelectedListener#updateListView(java.util.ArrayList)
 *
 * Updates Detail Fragment given a ArrayList of Strings
 */
@Override
public void updateListView(ArrayList<String> values) {
   // DetailFragment detailFragment1 = (DetailFragment) getFragmentManager()
         //   .findFragmentById(R.id.detailFragment);

    //DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentByTag("DetailFragment");
        if (detailFragment != null) {
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, values);
            detailFragment.setListAdapter(adapter);
			ListOrganizerActivity.listValues = values;
       }
}

/**
 * Set Positive Alert Button with String No
 * 
 * @param alertDialog Alert Dialog that is passed in 
 */
public void setPositiveAlertOptionNO(AlertDialog.Builder alertDialog)
{
    // Setting Negative "NO" Button
    alertDialog.setPositiveButton("NO",
            new DialogInterface.OnClickListener() {
                /* (non-Javadoc)
                 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
                 */
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog
                    dialog.cancel();
                }
            });
}

/**
 * Set Positive Alert Button with String OK
 * 
 * @param alertDialog Alert Dialog that is passed in 
 */
public void setPositiveAlertOptionOK(AlertDialog.Builder alertDialog)
{
    // Setting Negative "NO" Button
    alertDialog.setPositiveButton("OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog
                    dialog.cancel();
                }
            });
}

    public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{

        public static final String KEY_PREF_COLOR_CHANGE = "prefColorChange";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.user_settings);
        }
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                              String key) {
            if (key.equals(KEY_PREF_COLOR_CHANGE)) {
                Preference connectionPref = findPreference(key);


                // Set summary to be the user-description for the selected value
                connectionPref.setSummary(sharedPreferences.getString(key, ""));

                String value = sharedPreferences.getString(key, "");


               if(value.equals("Blue"))
               {

                   ThemeUtils.changeToTheme(super.getActivity(), ThemeUtils.BLUE);
               }
               else
               {
                   ThemeUtils.changeToTheme(super.getActivity(), ThemeUtils.BLACK);

               }



            }
        }


        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }

} 
    
