package com.example.android.todolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.ShareActionProvider;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todolist.DetailFragment.detailFragmentSelectedListener;

/**
 * 
 * This class creates the main Activity for the Android program
 * 
 * @author Eric
 *
 */
public class ListOrganizerActivity extends Activity implements detailFragmentSelectedListener,MyListFragment.OnItemSelectedListener{
	TextView context;
	private Context contextInfo;
	public static ArrayList<String> listValues;
	public static ArrayAdapter<String> listAdapter;
	private ShareActionProvider mShareActionProvider;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * 
     * Event occurs when Activity is created. This is initial start of the program.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);
        Context contextApp = getApplicationContext();
        contextInfo = contextApp;
        context = (TextView) findViewById(R.id.contextmenu);
        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
	   // READ FROM DATABASE AND POPULATE VIEWS. ALSO POPULATE LIST TITLES in database. CREATE DATABASE LAYOUT(List of spinner title names, list of item in list fragments)
	   // Think about database structure.

        ToDoListDbHelper db = new ToDoListDbHelper(this);
        registerForContextMenu(spinner);
       
       MyListFragment listFragment = (MyListFragment) getFragmentManager()
    	          .findFragmentById(R.id.listfragment);
       registerForContextMenu(listFragment.getListView());
       DetailFragment detailFragment = (DetailFragment) getFragmentManager()
    	          .findFragmentById(R.id.detailFragment);
       registerForContextMenu(detailFragment.getListView());
       
       //List<ListItem> contacts = db.getAllListItems();
       
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
    	     AddItemToList(item);
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
          // ...
          popupMenu.show();
          break;
      case R.id.action_settings:
        Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
            .show();
        break;
     case R.id.sort_title:
		 SortListByTitle(item);
        Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
            .show();
        break;
		case R.id.date_modified:
		    	   SortListByDateModified(item);
        Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
            .show();
        break;
	
      default:
        break;
      }

      return true;
    }
	
	public void SortListByTitle(MenuItem item)
	{
		/*
	    FeedReaderDbHelper db = new FeedReaderDbHelper(this);
		List<ListItem> alllistitemlist = db.getAllListItemsSortedByTitle();
	    //Need to get all List Items. Get the list item from titles.
	 	ArrayList<String> listitemlist = db.getAllListStringItemsSortedByTitle(MyListFragment.currentspinner);

		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
    	android.R.layout.simple_list_item_1, listitemlist);
		MyListFragment.listspinner.setAdapter(dataAdapter);
		
		//UPDATE Database. Update Database Sort Method by Title in DB class. Remove everything by title and insert into table by title.*/
	
	}
    public void SortListByDateModified(MenuItem item)
		{
		/*
	    FeedReaderDbHelper db = new FeedReaderDbHelper(this);
		//Need to get all List Items. Get the list item from titles.
	 	ArrayList<String> listitemlist = db.getAllListStringItemsSortedByDateModified(MyListFragment.currentspinner);
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
    	android.R.layout.simple_list_item_1, listitemlist);
		MyListFragment.listspinner.setAdapter(dataAdapter);
		
		//UPDATE Database. Update Database Sort Method by Date Modified in DB class.Remove everything by title and insert into table by date modified.
	
	*/
	}
	

    /**
     * Called when the Add Button is clicked in Action Bar
     * 
     * @param item Menu item that is clicked
     */
    public void AddItemToList(MenuItem item) {
    	CustomOnItemSelectedListener customlistener = new CustomOnItemSelectedListener();
    	final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
   	    Spinner spinner = (Spinner)findViewById(R.id.spinner1);
   	    int valToSet = (int) spinner.getSelectedItemId();
   	    final int index = (info!=null) ? info.position : valToSet;
   	 
   	    final DetailFragment detailFragment = (DetailFragment)
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
    	        
    	        Boolean whiteSpaceFound = ListOrganizerActivity.checkWhiteSpaces(listInput);
    	        
    	        Boolean invalidCharactersFound = ListOrganizerActivity.checkInvalidCharacters(listInput);
    				//Checks if edittext is empty,space, or null. Not a valid list item.
    				if (listInput.equals("") || listInput == null || whiteSpaceFound == true ||invalidCharactersFound == true)
    				{
    					
    					 AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
    	            // Setting Dialog Title
    	            alertDialog.setTitle("Invalid Name");

    	            // Setting Dialog Message
    	            alertDialog.setMessage("Please Enter a valid list name");
    	            setPositiveAlertOptionOK(alertDialog);
    	            alertDialog.show();
    					
    				}
    				else {
    					CustomOnItemSelectedListener a = new CustomOnItemSelectedListener();
    					ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);
    					
						Date date = ListOrganizerActivity.getCurrentDate();

    					db.addListitem(new ListItem(MyListFragment.currentSpinner, listInput, date));
						 
						 ListOrganizerActivity.listValues.add(listInput);
    				    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
    			    	        android.R.layout.simple_list_item_1, ListOrganizerActivity.listValues);
    				    adapter.notifyDataSetChanged();
    				    
    					detailFragment.setListAdapter(adapter);

    		
    	
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
	    
	      DetailFragment detailFragment = (DetailFragment) getFragmentManager()
	              .findFragmentById(R.id.detailFragment);
	      int listId = (int) detailFragment.getSelectedItemId();
	     
	     if (v.getId()== R.id.spinner1)
	     {
    			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
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
	 
	 Spinner spinner = (Spinner)findViewById(R.id.spinner1);
	 int valToSet = (int) spinner.getSelectedItemId();
	 
	 final DetailFragment detailFragment = (DetailFragment)
			    getFragmentManager().findFragmentById(R.id.detailFragment);
	 
	 final ListFragment listFragment = (ListFragment)
			    getFragmentManager().findFragmentById(R.id.listfragment);
	 
		ArrayAdapter<String> adapter =ListOrganizerActivity.listAdapter;
		
		//ArrayList<String> lst = new ArrayList<String>();
		//lst.addAll(Arrays.asList(ListOrganizerActivity.listvalues));
		
	//    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
    	//        android.R.layout.simple_list_item_1, lst);
	 
	 final int index = (info != null) ? info.position : valToSet;

	 long idPosition = info.id;
	 
  int menuItemIndex = item.getItemId();
  //get id another way if spinner is selected. Register the right menu.

  String[] menuItems = getResources().getStringArray(R.array.contextmenuitems);
  final String menuItemName = menuItems[menuItemIndex];
  //  String listItemName = Countries[info.position];
  //int index = info.position;
  ListAdapter listAdapter= detailFragment.getListAdapter();
  long listSelectedId = listAdapter.getItemId(index);
  final String currentItem=(String) listAdapter.getItem(index);
  
  long spinnerPosition =  MyListFragment.listSpinner.getSelectedItemId();
  
  
  int parentId = ((View) info.targetView.getParent()).getId();
  
  if (menuItemName.equals("Add") && listSelectedId == index)
  //if(menuItemName.equals("Add") && parentId==R.id.detailFragment)
  {
		AlertDialog.Builder  alertDialog = new AlertDialog.Builder(this);

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
                        public void onClick(DialogInterface dialog,int which) {
                            // Write your code here to execute after dialog

        String listInput = input.getText().toString();

        Boolean whiteSpaceFound = ListOrganizerActivity.checkWhiteSpaces(listInput);
        
        Boolean invalidCharactersFound = ListOrganizerActivity.checkInvalidCharacters(listInput);
			//Checks Invalid characters. Checks if edittext is empty,space, or null. Not a valid list item.
			if (listInput.equals("") || listInput == null || whiteSpaceFound == true || invalidCharactersFound == true)
			{
				
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("Invalid Name");

            // Setting Dialog Message
            alertDialog.setMessage("Please Enter a valid list name");
            setPositiveAlertOptionOK(alertDialog);
            alertDialog.show();
				
			}
			else
			{
		  //Add to Spinner
	
				ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);
				
				Date date = ListOrganizerActivity.getCurrentDate();
				db.addListitem(new ListItem(MyListFragment.currentSpinner, listInput,date));
				
				/*
				if(!MyListFragment.currentspinner.equals("New List") && !MyListFragment.currentspinner.equals("Sample List"))
				{
				db.addContact(new ListItem(MyListFragment.currentspinner, listinput));  
				}*/
				/*
				ArrayList<String> lst3 = new ArrayList<String>();
				lst3.addAll(Arrays.asList(ListOrganizerActivity.values));
				lst3.add(index+1,listinput);
				ArrayList<String> lst4 =db.getAllListStringItemsByTitle(MyListFragment.currentspinner);
				*/
				ListOrganizerActivity.listValues.add(index+1,listInput);
			    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
		    	        android.R.layout.simple_list_item_1, ListOrganizerActivity.listValues);;
                adapter.notifyDataSetChanged();
				detailFragment.setListAdapter(adapter);
				 
			}
                        }
                        });
				
		         // Setting Positive "NO" Button
		      setPositiveAlertOptionNO(alertDialog);

	            alertDialog.show();
  }
 else if (menuItemName.equals("Edit") && listSelectedId == index)
    //else if(menuItemName.equals("Edit") && parentId==R.id.detailFragment)
  {
	   
		AlertDialog.Builder  alertDialog = new AlertDialog.Builder(this);

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
                        public void onClick(DialogInterface dialog,int which) {
                            // Write your code here to execute after dialog

        String listInput = input.getText().toString();
			//Checks if edittext is empty,space, or null. Not a valid list item.
        Boolean whiteSpaceFound = ListOrganizerActivity.checkWhiteSpaces(listInput);
        
        Boolean invalidCharactersFound = ListOrganizerActivity.checkInvalidCharacters(listInput);
        
			//Checks if edittext is empty,space, or null. Not a valid list item.
			if (listInput.equals("") || listInput == null || whiteSpaceFound == true || invalidCharactersFound == true)
			{
				
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("Invalid Name");

            // Setting Dialog Message
            alertDialog.setMessage("Please Enter a valid list name");
            setPositiveAlertOptionOK(alertDialog);

            alertDialog.show();
				
			}
			else
			{
		  //Add to Spinner
				   String key = ((TextView) info.targetView).getText().toString();
			       ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);
					
					Date date = ListOrganizerActivity.getCurrentDate();
					if (!MyListFragment.currentSpinner.equals("New List") && !MyListFragment.currentSpinner.equals("Sample List"))
					{
					db.updateListItem(new ListItem(MyListFragment.currentSpinner, listInput), date);
					}

				ListOrganizerActivity.listValues.add(index+1, listInput);
				ListOrganizerActivity.listValues.remove(index);
			    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
		    	android.R.layout.simple_list_item_1, ListOrganizerActivity.listValues);
                adapter.notifyDataSetChanged();
				detailFragment.setListAdapter(adapter);
			
			}
                        }
                        });
				
		         // Setting Negative "NO" Button
		      setPositiveAlertOptionNO(alertDialog);

	            alertDialog.show();
		      }
		           
   else if (menuItemName.equals("Delete") && listSelectedId == index)
  {
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

                		db.deleteListItem(new ListItem(MyListFragment.currentSpinner, key));
						
						ListOrganizerActivity.listValues.remove(index);
        				//ArrayList<String> lst4 =db.getAllListStringItemsByTitle(MyListFragment.currentspinner);
        			    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
        		    	        android.R.layout.simple_list_item_1, ListOrganizerActivity.listValues);

                	  adapter.notifyDataSetChanged();
                      detailFragment.setListAdapter(adapter);
                	 
                	  
  
                    	 
                     }
                 });
         setPositiveAlertOptionNO(alertDialog);

         alertDialog.show();

  }
  else if (menuItemName.equals("Edit") && spinnerPosition == idPosition)
  //else if(menuItemName.equals("Edit") && parentId==R.id.spinner1)
  {
  			
	  		// Creating alert Dialog with one Button
  			AlertDialog.Builder  alertDialog = new AlertDialog.Builder(this);

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
                        public void onClick(DialogInterface dialog,int which) {
                            // Write your code here to execute after dialog
                        	String listInput = input.getText().toString();
							//Checks if editText is empty,space, or null. Not a valid list item.
                	        Boolean whiteSpaceFound = ListOrganizerActivity.checkWhiteSpaces(listInput);
                	        
                	        Boolean invalidCharactersFound = ListOrganizerActivity.checkInvalidCharacters(listInput);
                				//Checks if editText is empty,space, or null. Not a valid list item.
             if (listInput.equals("") || listInput == null || whiteSpaceFound == true || invalidCharactersFound == true)
             {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("PASSWORD");

            // Setting Dialog Message
            alertDialog.setMessage("Please Enter a valid list name");
            setPositiveAlertOptionOK(alertDialog);
            alertDialog.show();
			}
			else
			{
		  //Add to Spinner
		  		 //Spinner spinner = (Spinner)findViewById(R.id.spinner1);
				 /*
		  		Spinner spinner1 = (Spinner)info.targetView;
				 ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ListOrganizerActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);
		
		 spinner1.setAdapter(spinnerAdapter);
		 spinnerAdapter.remove(menuItemName);
		 spinnerAdapter.add(input.getText().toString());
		 spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinnerAdapter.notifyDataSetChanged();
	     dialog.cancel();
		 */
		 
		 
		 		  //Add to Spinner
				   String key = ((TextView) info.targetView).getText().toString();
			       ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

					if (!MyListFragment.currentSpinner.equals("New List") && !MyListFragment.currentSpinner.equals("Sample List"))
					{
					    db.updateSpinnerTitle(key);
					}

			        ArrayAdapter<String> adapter = (ArrayAdapter<String>) MyListFragment.listSpinner.getAdapter();
                    adapter.notifyDataSetChanged();
				  	MyListFragment.listSpinner.setAdapter(adapter);
		 }
		 
                    }});
            // Setting Negative "NO" Button
            setPositiveAlertOptionNO(alertDialog);


            // closed

            // Showing Alert Message
            alertDialog.show();
            }
  
            else if (menuItemName.equals("Delete") && spinnerPosition == idPosition)
            {
		 		  //Add to Spinner
				   String key = ((TextView) info.targetView).getText().toString();
			       ToDoListDbHelper db = new ToDoListDbHelper(ListOrganizerActivity.this);

					if (!MyListFragment.currentSpinner.equals("New List") && !MyListFragment.currentSpinner.equals("Sample List"))
					{
					db.deleteSpinnerTitle(key);  
					}
					

			     ArrayAdapter<String> deleteadapter = (ArrayAdapter<String>) MyListFragment.listSpinner.getAdapter();
                 deleteadapter.remove((String) key);
                 deleteadapter.notifyDataSetChanged();
				 MyListFragment.listSpinner.setAdapter(deleteadapter);
         }
        else //menu item = Share
         {
  
         }
           return true;
    }


/**
 * This method checks for whitespaces given a string
 * 
 * 
 * @param listInput string that will be checked for whitespaces.
 * @return true if string can invalid character, false it if doesn't contain whitespaces.
 */
public static Boolean checkWhiteSpaces(String listInput)
{
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(listInput);
		boolean found = matcher.find();
		return found;
}





/**
 * This method checks for invalid character given a string
 * 
 * 
 * @param listInput string that will be checked for invalid characters.
 * @return true if string can invalid character, false it if doesn't contain invalid characters.
 */
public static Boolean checkInvalidCharacters(String listInput)
{
		Pattern pattern = Pattern.compile("^\\w\\.@-");
		Matcher matcher = pattern.matcher(listInput);
		boolean found = matcher.find();
		return found;
}



/**
 * 
 * Iterates through an ArrayList of Strings and looks for a current string.
 * If string is found, string is replaced by string that is mentioned in parameter.
 * 
 * @param input String that will be replaced
 * @param replaceString String that replace string mentioned in parameter
 * @param stringValues String array of list items
 * @return String array with replaced value
 */
public String[] replaceString(String input, String replaceString, String[] stringValues)
{
	String [] resultArray = new String[stringValues.length];

	for (int i = 0; i < stringValues.length; i++)
	{
        resultArray[i] = stringValues[i];

		if (stringValues[i].equals(input)) {
            resultArray[i] = input;
		}
		
	}
	
	return resultArray;
	
}

		
@Override
public void onRssItemSelected(int position) {

        }
 
/* (non-Javadoc)
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#sampleFragmentList(int)
 * 
 * Provides sample List that the Detail Fragment populates
 * 
 */
@Override
public void sampleFragmentList(int position) {

    DetailFragment detailFragment = (DetailFragment) getFragmentManager()
            .findFragmentById(R.id.detailFragment);
        if (detailFragment != null && detailFragment.isInLayout()) {
       	    String[] values = new String[] { "Android1", "iPhone", "WindowsMobile",
    	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
    	        "Linux", "OS/2" };
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, values);
            detailFragment.setListAdapter(adapter);
       }
 
}


/* (non-Javadoc)
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#clearFragmentList(int)
 * clear detail fragments list. Clear list items from List View.
 */
@Override
public void clearFragmentList(int position) {

    DetailFragment detailFragment = (DetailFragment) getFragmentManager()
            .findFragmentById(R.id.detailFragment);
        MyListFragment listfragment = (MyListFragment) getFragmentManager()
            .findFragmentById(R.id.listfragment);
        
        if (detailFragment != null && detailFragment.isInLayout()) {
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


/**
 * 
 * Update array list and swap current item with new item.
 * 
 * @param stringArray String array of list items.
 * @param currentItem Current list item
 * @param newItem New item that will replace current list item
 * @return String array containing new list item swap with thte current list item
 */
public String[] updateArray(String[] stringArray, String currentItem, String newItem)
{
	String [] finalArray = new String[stringArray.length];
	
	for (int i = 0; i < stringArray.length-1; i++)
	{
        finalArray[i] = stringArray[i];

		if (stringArray[i].equals(currentItem)) {
            finalArray[i] = newItem;
		}
		
	}

return finalArray;
	
}


/**
 * 
 * Converts ArrayList of String to StringArray
 * 
 * @param stringArrayList ArrayList that is entered
 * @return Array that is converted from Array List of Strings
 */
public String[] ConvertArrayListtoArray(ArrayList<String> stringArrayList) {
	String[] finalArray = new String[stringArrayList.size()];
    finalArray = stringArrayList.toArray(finalArray);
    return finalArray;
}

/**
 * 
 * Delete a specified item in ArrayList if item is found
 * 
 * @param stringArrayList ArrayList of string containing list items
 * @param currentItem Contains current list item
 * @return  String array with list item deleted from parameter
 */
public String[] deleteItemFromArray(ArrayList<String> stringArrayList, String currentItem)
{
	ArrayList<String> finalList = new ArrayList<String>();
	
	for (int i=0; i < stringArrayList.size()-1; i++)
	{
		
		if (!stringArrayList.get(i).equals(currentItem)) {
            finalList.add(stringArrayList.get(i));
		}
		
	}

	String[] finalArray = new String[finalList.size()];
    finalArray = finalList.toArray(finalArray);
    return finalArray;
	
}

/* (non-Javadoc)
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener)
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
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#updateList(java.util.ArrayList)
 * 
 * Updates Detail Fragment given a ArrayList of Strings
 */
@Override
public void updateList(ArrayList<String> values) {
	// TODO Auto-generated method stub
    DetailFragment detailFragment = (DetailFragment) getFragmentManager()
            .findFragmentById(R.id.detailFragment);
        if (detailFragment != null && detailFragment.isInLayout()) {
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

} 
    
