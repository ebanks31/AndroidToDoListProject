package com.example.android.todolist;


import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
/**
 * 
 * This class is a Custom OnItemSelectedListener for the spinner. 
 * It contains actions for when the item is selected in the spinner
 *  
 * @author Eric
 *
 */
public class CustomOnItemSelectedListener extends MyListFragment implements OnItemSelectedListener {
 
	static Context context;
	static Spinner spinnerTitles;
	static ArrayAdapter<String> spinnerAdapter;
	ArrayList<String> spinnerList;
	private OnItemSelectedListener listener;
  
	/**
	 * Overload constructor of CustomOnItemSelectedListener class. 
	 * Sets intial values that are needed for the CustomOnItemSelectedListener
	 * 
	 * @param context set the context from the Main Activity class
	 * @param listSpinner Current Spinner from the MyListFragment Class
	 * @param dataAdapter Adapter that is associated to the spinner
	 * @param spinnerList List of spinner items that are located int he spinner
	 */
	public CustomOnItemSelectedListener(Context context, Spinner listSpinner,
			ArrayAdapter<String> dataAdapter, ArrayList<String> spinnerList) {
		// TODO Auto-generated constructor stub
    	CustomOnItemSelectedListener.spinnerTitles = listSpinner;
    	CustomOnItemSelectedListener.context = context;
    	CustomOnItemSelectedListener.spinnerAdapter = dataAdapter;
    	this.spinnerList = spinnerList;
	}


	/**
	 * Default Constructor for CustomOnItemSelectedListener
	 */
	public CustomOnItemSelectedListener() {
		// TODO Auto-generated constructor stub
	}
	

	// Handler for when New List is selected in the spinner drop down menu.
	static Handler newListHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String listInput = bundle.getString("myKey");
				 spinnerAdapter.add(listInput);
				 spinnerAdapter.notifyDataSetChanged();
                 spinnerTitles.setAdapter(spinnerAdapter);

				 int spinnerPosition = spinnerAdapter.getPosition(listInput);

				 //set the selected spinner item according to value
                 spinnerTitles.setSelection(spinnerPosition);
				 MyListFragment.currentSpinner = listInput;

              ToDoListDbHelper db = new ToDoListDbHelper(context);
              ArrayList<SpinnerItem> stringList1 = db.getAllSpinnerTitle();

              SpinnerItem newListSpinnerItem = new SpinnerItem(listInput, stringList1.size()+1);

              db.addSpinneritem(newListSpinnerItem);
				 MyListFragment.staticListener.clearFragmentList(1);
		     }
		 };
		 
		 
    // Handler for when "User created title" is selected in the spinner drop down menu
	static Handler userInputHandler = new Handler() {
		  @Override
		  public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String listInput = bundle.getString("mysecondKey");
			    ToDoListDbHelper db = new ToDoListDbHelper(context);
				List<ListItem> itemListByTitle = db.getAllListItemsByTitle(listInput);
				ArrayList<String> values = getListItems(itemListByTitle);
				MyListFragment.staticListener.updateList(values);

				 
		     }
		 };
		 
	/* (non-Javadoc)
	 * @see com.example.android.rssfeed.MyListFragment#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 * 
	 * Method is invoked when a spinner title is selected.
	 * Invokes action depending on what spinner title is selected.
	 */
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
            long id) {
         final String itemSelected = parent.getItemAtPosition(pos).toString();
         
         MyListFragment.currentSpinner = itemSelected;
		 if (itemSelected.equals("New List"))
		 {
			 AlertDialog.Builder alertDialog = null;

		     // Creating alert Dialog with one Button
			 alertDialog = new AlertDialog.Builder(context);

            // Setting Dialog Title
            alertDialog.setTitle("List Title");

            // Setting Dialog Message
            alertDialog.setMessage("Enter title for your list");
            final EditText input = new EditText(context);
  			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
  			input.setLayoutParams(lp);
  			alertDialog.setView(input); 

		    String listInput = input.getText().toString();
		    

            // Setting Positive "Yes" Button
            alertDialog.setNegativeButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            // Write your code here to execute after dialog

             final String listInput = input.getText().toString();
			//Checks if edittext is empty,space, or null. Not a valid list item.
			if (listInput.equals("") || listInput.equals(" ") || input == null) {
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            // Setting Dialog Title
            alertDialog.setTitle("Invalid Name");

            // Setting Dialog Message
            alertDialog.setMessage("Please Enter a valid list name");
            setPositiveAlertOptionOK(alertDialog);
            alertDialog.show();
				}

			else
			{
			
		 if (!checkDuplicates(spinnerList, listInput)) //check duplicate spinner items
		 {
			 try {

			Runnable runnable = new Runnable() { //Run in separate thread
		        public void run() {     	
			 
		        	//Use Handler(newListHandler) to update User Interface from another thread.
	            	Message msg = newListHandler.obtainMessage();
	    			Bundle bundle = new Bundle();
	    			bundle.putString("myKey", listInput);
	                msg.setData(bundle);
	                newListHandler.sendMessage(msg);
		        }
		      };
		      Thread myThread = new Thread(runnable);
              myThread.start();
		 
			 }
	      	catch (ArrayIndexOutOfBoundsException ex)
	      	{
	      		System.out.println("Index Out Of Bounds Exception has occurred" + ex.getMessage());
	      	}
	      	catch (NullPointerException ex)
	      	{
	      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
	      	}
		 }
		 else {
			 	//Spinner item is a duplicated
			 	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	            // Setting Dialog Title
	            alertDialog.setTitle("Duplicate item");

	            // Setting Dialog Message
	            alertDialog.setMessage("Cannot add duplicate item");
	            setPositiveAlertOptionOK(alertDialog);
	            alertDialog.show();
		 }

		 }
                        }}
                        );
            setPositiveAlertOptionNO(alertDialog);

    		// create alert dialog
            AlertDialog alertDialog1 = alertDialog.create();

			// show alert dialog
			alertDialog1.show();
        }
		 
			else if (itemSelected.equals("Sample List"))
			{
				MyListFragment.staticListener.sampleFragmentList(1);
		
			}
			else
			{
				
				try
				{
				
				//If Spinner is a an spinner item in the list created by the user. 
			Runnable runnable = new Runnable() { //Run in separate thread
		        public void run() {     	
			 
		        	//Use Handler(userinputhandler) to update User Interface from another thread.
	            	Message msg = userInputHandler.obtainMessage();
	    			Bundle bundle = new Bundle();
	    			bundle.putString("mysecondKey", MyListFragment.currentSpinner);
	                msg.setData(bundle);
	                userInputHandler.sendMessage(msg);
	                 
		        }
		      };
		      Thread mythread = new Thread(runnable);
		      mythread.start();
	
				}
				
	      	catch (ArrayIndexOutOfBoundsException ex)
	      	{
	      		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
	      		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred",ex);                  //Log error for Array Out of Bounds Exception
	      	}
	      	catch (NullPointerException ex)
	      	{
	      		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
	      		Log.e("Null", "Null Pointer Exception Error",ex);                  //Log error for Null Pointer Exception
	      	
			}
				}
	}
    
		
	/**
	 * 
	 * Gets List Items by Spinner Title
	 * 
	 * @param itemListByTitle List of ListItems
	 * @return ArrayList of String containing list items by spinner title.
	 */
	public static ArrayList<String> getListItems(final List<ListItem> itemListByTitle)
	{
		
		final ArrayList<String> finalList = new ArrayList<String>();
		
		Runnable runnable = new Runnable() { //Run in separate thread
	        public void run() {    
		for (ListItem listitem: itemListByTitle)
		{
            finalList.add(listitem.getListItem());
		}
	        }
		};
		
    	Thread myThread = new Thread(runnable);
        myThread.start();
		return finalList;
	}
	
	/**
	 * 
	 * Get List Item Objects by spinner title
	 * 
	 * @param itemList List of List Item Objects
	 * @param title selected Spinner title
	 * @return List ArrayList of string containing List Items by spinner title
	 */
	public ArrayList<String> getListTitleFromListItems(final List<ListItem> itemList, final String title)
	{
		final ArrayList<String> finalList = new ArrayList<String>();

		Runnable runnable = new Runnable() { //Run in separate thread
	        public void run() {     	
		
	
		for (ListItem listItem: itemList) {

            String currentTitle = listItem.getTitle();

            if (currentTitle != null)
            {
			if (currentTitle.equals(title))
            {
                finalList.add(listItem.getListItem());
			}
			}
		}
	        
	        
	        
	       }
    	};
    	Thread myThread = new Thread(runnable);
        myThread.start();
    	
		return finalList;
	}
	
	/**
	 * 
	 * Get List of title from List of List Item Objects
	 * 
	 * @param itemListByTitle List of List Item Objects
	 * @return ArrayList of string containing spinner titles.
	 */
	public ArrayList<String> getListTitles(final List<ListItem> itemListByTitle)
	{
		final ArrayList<String> finalList = new ArrayList<String>();
	
    	Runnable runnable = new Runnable() { //Run in separate thread
	        public void run() {     	
		
		for (ListItem listItem: itemListByTitle)
		{
            finalList.add(listItem.getTitle());
		}
		
	       
	        }};
	        
	        
	        Thread myThread = new Thread(runnable);
            myThread.start();
	        
			return finalList;
		
	}
		 
	/**
	 * 
	 * Check for duplicates in an ArrayList of String
	 * 
	 * @param arrayList ArrayList of String containing spinner titles
	 * @param input spinner title
	 * @return ArrayList of String containing spinner titles with duplicates removed
	 */
	public boolean checkDuplicates(ArrayList<String> arrayList, String input)
	{
		for (String listItem: arrayList)
		{
			if (listItem.equals(input))
			{
				return true;
			}
		}
		return false;
		
	}
    
 
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
 
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
    
    
    /**
     * 
     * Interface for communication between CustomerOnItemselectListener, other fragments, and activities.
     * 
     * @author Eric
     *
     */
    public interface OnItemSelectedListener {
	      void onRssItemSelected(int position);
	      void sampleFragmentList(int position);
	    }
}