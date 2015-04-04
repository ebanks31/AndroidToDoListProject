package com.example.android.rssfeedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.acra.ACRA;
import org.acra.ErrorReporter;

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

import com.example.android.rssfeedlist.DetailFragment.detailFragmentSelectedListener;
import com.parse.Parse;

/**
 * 
 * This class creates the main Activity for the Android program
 * 
 * @author Eric
 *
 */
public class ListOrganizerActivity extends Activity implements detailFragmentSelectedListener,MyListFragment.OnItemSelectedListener{
	TextView context;
	private Context contextinfo;
	public static ArrayList<String> listvalues;
	public static ArrayAdapter<String> listadapter;
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
        Context contextapp =getApplicationContext();
        contextinfo=contextapp;
        context = (TextView) findViewById(R.id.contextmenu);
        Spinner spinner = (Spinner)findViewById(R.id.spinner1);
	   // READ FROM DATABASE AND POPULATE VIEWS. ALSO POPULATE LIST TITLES in database. CREATE DATABASE LAYOUT(List of spinner title names, list of item in list fragments)
	   // Think about database structure.

        FeedReaderDbHelper db = new FeedReaderDbHelper(this);
        registerForContextMenu(spinner);
       
       MyListFragment listfragment = (MyListFragment) getFragmentManager()
    	          .findFragmentById(R.id.listfragment);
       registerForContextMenu(listfragment.getListView());
       DetailFragment detailfragment = (DetailFragment) getFragmentManager()
    	          .findFragmentById(R.id.detailFragment);
       registerForContextMenu(detailfragment.getListView());
       
       List<ListItem> contacts = db.getAllListItems();
       
    }


    /**
     * 
     * Get Context from the RssFeedActivity
     * 
     * @return Context from the RssFeedActivity
     */
    public Context getContext() {
return contextinfo;
}

/**
 * 
 * Set Context from the RssFeedActivity
 * 
 * @param context Context from the RssFeedActivity
 */
public void setContext(Context context) {
this.contextinfo = context;
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
	
	
   	public static Date getCurrentDate()
	{
   	Calendar calobj = Calendar.getInstance();
	Date date = calobj.getTime();
	return date;
	//System.out.println(df.format(calobj.getTime()));
	}
	
	
    //NEW
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * 
     * Event that occured when Action items are clicked in Action Bar.
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
    public void AddItemToList(MenuItem item)
    {
    	CustomOnItemSelectedListener customlistener = new CustomOnItemSelectedListener();
    	final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
   	 Spinner spinner = (Spinner)findViewById(R.id.spinner1);
   	 int valToSet = (int) spinner.getSelectedItemId();
   	 final int index = (info!=null) ? info.position : valToSet;
   	 
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

    	        String listinput = input.getText().toString();
    	        
    	        Boolean whitespacefound = ListOrganizerActivity.checkWhiteSpaces(listinput);
    	        
    	        Boolean invalidcharactersfound = ListOrganizerActivity.checkInvalidCharacters(listinput);
    				//Checks if edittext is empty,space, or null. Not a valid list item.
    				if(listinput.equals("") || listinput==null || whitespacefound==true ||invalidcharactersfound==true)
    				{
    					
    					 AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOrganizerActivity.this);
    	            // Setting Dialog Title
    	            alertDialog.setTitle("Invalid Name");

    	            // Setting Dialog Message
    	            alertDialog.setMessage("Please Enter a valid list name");
    	            setPositiveAlertOptionOK(alertDialog);
    	            alertDialog.show();
    					
    				}
    				//else if(!MyListFragment.currentspinner.equals("New List") && !MyListFragment.currentspinner.equals("Sample List"))
    				else	
    				{
    					
    					
    				
    					CustomOnItemSelectedListener a = new CustomOnItemSelectedListener();
    					FeedReaderDbHelper db = new FeedReaderDbHelper(ListOrganizerActivity.this);
    					
						Date date = ListOrganizerActivity.getCurrentDate();

    					db.addListitem(new ListItem(MyListFragment.currentspinner, listinput,date));  
						 
						 ListOrganizerActivity.listvalues.add(listinput);
    				    final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ListOrganizerActivity.this,
    			    	        android.R.layout.simple_list_item_1, ListOrganizerActivity.listvalues);
    				    //adapter3.add(listinput);
    				    adapter3.notifyDataSetChanged();
    				    
    					detailfragment.setListAdapter(adapter3);
    					
    					//ListOrganizerActivity.values=ConvertArrayListtoArray(values);
    					
    		
    	
    				}
    	                        }
    	                        });
    					
    			      setPositiveAlertOptionNO(alertDialog);
    		            alertDialog.show();
    	  
    }
    
    /**
     * Removes Duplicates from ArrayList of strings
     * 
     * @param stringlist Array List of string containing spinner titles
     * @return  ArrayList of strings with duplicates removed
     */
    public ArrayList<String> removeDuplicates(ArrayList<String> stringlist)
    {
  	  HashSet hs = new HashSet();
  	  hs.addAll(stringlist);
  	  stringlist.clear();
  	  stringlist.addAll(hs);
  	  return stringlist;
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
	    
	      DetailFragment detailfragment = (DetailFragment) getFragmentManager()
	              .findFragmentById(R.id.detailFragment);
	      int listid = (int) detailfragment.getSelectedItemId();
	     
	     if(v.getId()== R.id.spinner1)
	     {
    			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
    			String[] menuItems = getResources().getStringArray(R.array.contextmenuitems);
    			for (int i = 0; i<menuItems.length; i++) {
      				menu.add(Menu.NONE, i, i, menuItems[i]);
    			}
	     }
    			else if(v==detailfragment.getListView())
    			{
      	      			String[] menuItems1 = getResources().getStringArray(R.array.contextmenuitems);
      	      			for (int i = 0; i<menuItems1.length; i++) {
      	        				menu.add(Menu.NONE, i, i, menuItems1[i]);
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
	 
	 final DetailFragment detailfragment = (DetailFragment)
			    getFragmentManager().findFragmentById(R.id.detailFragment);
	 
	 final ListFragment listfragment = (ListFragment)
			    getFragmentManager().findFragmentById(R.id.listfragment);
	 
		ArrayAdapter<String> adapter =ListOrganizerActivity.listadapter;
		
		//ArrayList<String> lst = new ArrayList<String>();
		//lst.addAll(Arrays.asList(ListOrganizerActivity.listvalues));
		
	//    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
    	//        android.R.layout.simple_list_item_1, lst);
	 
	 final int index = (info!=null) ? info.position : valToSet;

	 long idposition = info.id;
	 
  int menuItemIndex = item.getItemId();
  //get id another way if spinner is selected. Register the right menu.

  String[] menuItems = getResources().getStringArray(R.array.contextmenuitems);
  final String menuItemName = menuItems[menuItemIndex];
  //  String listItemName = Countries[info.position];
  //int index = info.position;
  ListAdapter listadapter= detailfragment.getListAdapter();
  long listselectedid = listadapter.getItemId(index);
  final String currentitem=(String) listadapter.getItem(index);
  
  long spinnerposition =  MyListFragment.listspinner.getSelectedItemId();
  
  
  int parentId = ((View) info.targetView.getParent()).getId();
  
  if(menuItemName.equals("Add") && listselectedid==index)
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

        String listinput = input.getText().toString();

        Boolean whitespacefound = ListOrganizerActivity.checkWhiteSpaces(listinput);
        
        Boolean invalidcharactersfound = ListOrganizerActivity.checkInvalidCharacters(listinput);
			//Checks Invalid characters. Checks if edittext is empty,space, or null. Not a valid list item.
			if(listinput.equals("") || listinput==null || whitespacefound==true ||invalidcharactersfound==true)
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
	
				FeedReaderDbHelper db = new FeedReaderDbHelper(ListOrganizerActivity.this);
				
				Date date = ListOrganizerActivity.getCurrentDate();
				db.addListitem(new ListItem(MyListFragment.currentspinner, listinput,date));  
				
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
				ListOrganizerActivity.listvalues.add(index+1,listinput);
			    final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(ListOrganizerActivity.this,
		    	        android.R.layout.simple_list_item_1, ListOrganizerActivity.listvalues);
			   // adapter3.remove(listinput);
			   // adapter3.insert(listinput,index+1);
			    adapter3.notifyDataSetChanged();
				detailfragment.setListAdapter(adapter3);
				
				//ListOrganizerActivity.values=ConvertArrayListtoArray(lst3);
				 
			}
                        }
                        });
				
		         // Setting Positive "NO" Button
		      setPositiveAlertOptionNO(alertDialog);

	            alertDialog.show();
  }
 else if(menuItemName.equals("Edit") && listselectedid==index)
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

        String listinput = input.getText().toString();
			//Checks if edittext is empty,space, or null. Not a valid list item.
        Boolean whitespacefound = ListOrganizerActivity.checkWhiteSpaces(listinput);
        
        Boolean invalidcharactersfound = ListOrganizerActivity.checkInvalidCharacters(listinput);
        
			//Checks if edittext is empty,space, or null. Not a valid list item.
			if(listinput.equals("") || listinput==null || whitespacefound==true ||invalidcharactersfound==true)
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
			       FeedReaderDbHelper db = new FeedReaderDbHelper(ListOrganizerActivity.this);
					
					Date date = ListOrganizerActivity.getCurrentDate();
					if(!MyListFragment.currentspinner.equals("New List") && !MyListFragment.currentspinner.equals("Sample List"))
					{
					db.updateListItem(new ListItem(MyListFragment.currentspinner, listinput),date);  
					}

				ListOrganizerActivity.listvalues.add(index+1,listinput);
				ListOrganizerActivity.listvalues.remove(index);
			    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ListOrganizerActivity.this,
		    	android.R.layout.simple_list_item_1, ListOrganizerActivity.listvalues);
				adapter1.notifyDataSetChanged();
				detailfragment.setListAdapter(adapter1);
			
			}
                        }
                        });
				
		         // Setting Negative "NO" Button
		      setPositiveAlertOptionNO(alertDialog);

	            alertDialog.show();
		      }
		           
   else if(menuItemName.equals("Delete") && listselectedid==index)
  //else if(menuItemName.equals("Delete") && parentId==R.id.detailFragment)
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
                		FeedReaderDbHelper db = new FeedReaderDbHelper(ListOrganizerActivity.this);

                		db.deleteListItem(new ListItem(MyListFragment.currentspinner, key));  
						
						ListOrganizerActivity.listvalues.remove(index);
        				ArrayList<String> lst4 =db.getAllListStringItemsByTitle(MyListFragment.currentspinner);
        			    final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ListOrganizerActivity.this,
        		    	        android.R.layout.simple_list_item_1, ListOrganizerActivity.listvalues);

                	  adapter2.notifyDataSetChanged();
                	  detailfragment.setListAdapter(adapter2);
                	 
                	  
  
                    	 
                     }
                 });
         setPositiveAlertOptionNO(alertDialog);

         alertDialog.show();

  }
  else if(menuItemName.equals("Edit") && spinnerposition==idposition)
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
                        	String listinput = input.getText().toString();
							//Checks if edittext is empty,space, or null. Not a valid list item.
                	        Boolean whitespacefound = ListOrganizerActivity.checkWhiteSpaces(listinput);
                	        
                	        Boolean invalidcharactersfound = ListOrganizerActivity.checkInvalidCharacters(listinput);
                				//Checks if edittext is empty,space, or null. Not a valid list item.
                				if(listinput.equals("") || listinput==null || whitespacefound==true ||invalidcharactersfound==true)
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
			       FeedReaderDbHelper db = new FeedReaderDbHelper(ListOrganizerActivity.this);

					if(!MyListFragment.currentspinner.equals("New List") && !MyListFragment.currentspinner.equals("Sample List"))
					{
					db.updateSpinnerTitle(key);  
					}
					
				//ArrayList<String> lst = new ArrayList<String>();
				//lst.addAll(Arrays.asList(ListOrganizerActivity.listvalues));
				
				//lst3.add(index+1,listinput);
			//	lst3.remove(index);
		   // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ListOrganizerActivity.this,
 	       //android.R.layout.simple_list_item_1, lst3);  

			     ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) MyListFragment.listspinner.getAdapter();
				    //adapter1.insert(listinput,index+1);
				    //adapter1.remove(listinput);
				   // adapter1.remove((String) key);
			     adapter1.notifyDataSetChanged();
				  	MyListFragment.listspinner.setAdapter(adapter1);
				
				//ListOrganizerActivity.values=updateArray(ListOrganizerActivity.values,currentitem,listinput);
		 }
		 
                    }});
            // Setting Negative "NO" Button
            setPositiveAlertOptionNO(alertDialog);


            // closed

            // Showing Alert Message
            alertDialog.show();
            }
  
  else if(menuItemName.equals("Delete") && spinnerposition==idposition)
  //else if(menuItemName.equals("Delete") && parentId==R.id.spinner1)
  {
		 		  //Add to Spinner
				String key = ((TextView) info.targetView).getText().toString();
			       FeedReaderDbHelper db = new FeedReaderDbHelper(ListOrganizerActivity.this);

					if(!MyListFragment.currentspinner.equals("New List") && !MyListFragment.currentspinner.equals("Sample List"))
					{
					db.deleteSpinnerTitle(key);  
					}
					

			     ArrayAdapter<String> adapter2 = (ArrayAdapter<String>) MyListFragment.listspinner.getAdapter();
			     adapter2.remove((String) key);
			     adapter2.notifyDataSetChanged();
				    MyListFragment.listspinner.setAdapter(adapter2);
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
 * @param listinput string that will be checked for whitespaces.
 * @return true if string can invalid character, false it if doesn't contain whitespaces.
 */
public static Boolean checkWhiteSpaces(String listinput)
{
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(listinput);
		boolean found = matcher.find();
		return found;
}





/**
 * This method checks for invalid character given a string
 * 
 * 
 * @param listinput string that will be checked for invalid characters.
 * @return true if string can invalid character, false it if doesn't contain invalid characters.
 */
public static Boolean checkInvalidCharacters(String listinput)
{
		Pattern pattern = Pattern.compile("^\\w\\.@-");
		Matcher matcher = pattern.matcher(listinput);
		boolean found = matcher.find();
		return found;
}



/**
 * 
 * Iterates through an ArrayList of Strings and looks for a current string.
 * If string is found, string is replaced by string that is mentioned in parameter.
 * 
 * @param input String that will be replaced
 * @param replacestring String that replace string mentioned in parameter 
 * @param stringvalues String array of list items
 * @return String array with replaced value
 */
public String[] replaceString(String input,String replacestring,String[] stringvalues)
{
	String [] resultarray = new String[stringvalues.length];
	for(int i=0;i<stringvalues.length;i++)
	{
		resultarray[i]=stringvalues[i];
		if(stringvalues[i].equals(input))
		{
			resultarray[i]=input;
		}
		
	}
	
	return resultarray;
	
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

    DetailFragment detailfragment = (DetailFragment) getFragmentManager()
            .findFragmentById(R.id.detailFragment);
        if (detailfragment != null && detailfragment.isInLayout()) {
       	    String[] values = new String[] { "Android1", "iPhone", "WindowsMobile",
    	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
    	        "Linux", "OS/2" };
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, values);
    	    detailfragment.setListAdapter(adapter);
       } else {


        }
 
}


/* (non-Javadoc)
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#clearFragmentList(int)
 * clear detail fragments list. Clear list items from List View.
 */
@Override
public void clearFragmentList(int position) {

    DetailFragment detailfragment = (DetailFragment) getFragmentManager()
            .findFragmentById(R.id.detailFragment);
        MyListFragment listfragment = (MyListFragment) getFragmentManager()
            .findFragmentById(R.id.listfragment);
        
        if (detailfragment != null && detailfragment.isInLayout()) {
       	    String[] values = new String[] { "Android22222222", "iPhone", "WindowsMobile",
    	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
    	        "Linux", "OS/2" };
    		ArrayList<String> lst1 = new ArrayList<String>();
    		lst1.addAll(Arrays.asList(values));
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, lst1);

    	    adapter.notifyDataSetChanged();
    	    detailfragment.setListAdapter(adapter);
       } else {


        }
 
}


/**
 * 
 * Update array list and swap current item with new item.
 * 
 * @param stringarray String array of list items. 
 * @param currentitem Current list item
 * @param newitem New item that will replace current list item
 * @return String array containing new list item swap with thte current list item
 */
public String[] updateArray(String[] stringarray, String currentitem,String newitem)
{
	String [] finalarray = new String[stringarray.length];
	
	for (int i=0;i<stringarray.length-1;i++)
	{
		finalarray[i]=stringarray[i];
		if(stringarray[i].equals(currentitem))
		{
			finalarray[i]=newitem;
		}
		
	}

return finalarray;
	
}


/**
 * 
 * Converts ArrayList of String to StringArray
 * 
 * @param stringarraylist ArrayList that is entered
 * @return Array that is converted from Array List of Strings
 */
public String[] ConvertArrayListtoArray(ArrayList<String> stringarraylist)
{

	String[] finalarray = new String[stringarraylist.size()];
	finalarray = stringarraylist.toArray(finalarray);
    return finalarray;
	
}

/**
 * 
 * Delete a specified item in ArrayList if item is found
 * 
 * @param stringarraylist ArrayList of string containing list items 
 * @param currentitem Contains current list item
 * @return  String array with list item deleted from parameter
 */
public String[] deleteItemFromArray(ArrayList<String> stringarraylist, String currentitem)
{
	ArrayList<String> finallist = new ArrayList<String>();
	
	for (int i=0;i<stringarraylist.size()-1;i++)
	{
		
		if(!stringarraylist.get(i).equals(currentitem))
		{
			finallist.add(stringarraylist.get(i));
		}
		
	}

	String[] finalarray = new String[finallist.size()];
	finalarray = finallist.toArray(finalarray);
return finalarray;
	
}

/* (non-Javadoc)
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener)
 * Sets OnItemSelectedListener
 * 
 */
@Override
public void setOnItemSelectedListener(
		OnItemSelectedListener onItemSelectedListener) {
	// TODO Auto-generated method stub
	
}

/* (non-Javadoc)
 * @see com.example.android.rssfeed.DetailFragment.detailFragmentSelectedListener#detailFragmentSelected(java.lang.String[], android.widget.ArrayAdapter)
 * 
 * Updates the current list item values and adapters.
 */
@Override
public void detailFragmentSelected(String[] values, ArrayAdapter<String> adapter) {
	// TODO Auto-generated method stub
	//ListOrganizerActivity.listvalues=values;
	ListOrganizerActivity.listadapter=adapter;
}

/* (non-Javadoc)
 * @see com.example.android.rssfeed.MyListFragment.OnItemSelectedListener#updateList(java.util.ArrayList)
 * 
 * Updates Detail Fragment given a ArrayList of Strings
 */
@Override
public void updateList(ArrayList<String> values) {
	// TODO Auto-generated method stub
    DetailFragment detailfragment = (DetailFragment) getFragmentManager()
            .findFragmentById(R.id.detailFragment);
        if (detailfragment != null && detailfragment.isInLayout()) {
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, values);
    	    detailfragment.setListAdapter(adapter);
			ListOrganizerActivity.listvalues=values;
       } else {


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
    
