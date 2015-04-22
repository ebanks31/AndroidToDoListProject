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
    private ToDoListUtility todolistutility;
  
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
        todolistutility = new ToDoListUtility();
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
              int position = stringList1.size()+1;
              SpinnerItem newListSpinnerItem = new SpinnerItem(listInput, position);
              db.addSpinneritem(newListSpinnerItem);

              previousSpinnerPosition = position;
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
              ToDoListUtility todolistutilityHandler = new ToDoListUtility();
				ArrayList<String> values = todolistutilityHandler.getListItems(itemListByTitle);
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
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog

             final String listInput = input.getText().toString();

             Boolean validcharacter = todolistutility.checkValidCharacters(listInput);

			//Checks if edit text is empty,space, or null. Not a valid list item.
			if (listInput.equals("") || validcharacter == false) {
				 AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            // Setting Dialog Title
            alertDialog.setTitle("Invalid Name");

            // Setting Dialog Message
            alertDialog.setMessage("Please Enter a valid list name");
            setPositiveAlertOptionOK(alertDialog);
            alertDialog.show();
            updateSpinnerWithPreviousTitle ();


            }

			else
			{
			
		 if (!todolistutility.checkDuplicates(spinnerList, listInput)) //check duplicate spinner items
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
                // Communicate this fragment to the main activity
				MyListFragment.staticListener.sampleFragmentList(1);
                previousSpinnerPosition = 1;
		
			}
			else
			{
				
				try
				{
				
				//If Spinner is a an spinner item in the list created by the user. 
			Runnable runnable = new Runnable() { //Run in separate thread
		        public void run() {     	
			 
		        	// Use Handler(userinputhandler) to update User Interface from another thread.
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
                        updateSpinnerWithPreviousTitle();
                        dialog.cancel();
                    }
                });
    }

    /**
     * This method updates the spinner title to the previous selected Spinner Title.
     */
    public void updateSpinnerWithPreviousTitle()
    {
        ToDoListDbHelper db = new ToDoListDbHelper(context);
        String previousSpinner = db.getAllSpinnerTitleByPosition(MyListFragment.previousSpinnerPosition);
        spinnerTitles.setSelection(MyListFragment.previousSpinnerPosition);
        MyListFragment.currentSpinner = previousSpinner;

        spinnerAdapter.notifyDataSetChanged();
        spinnerTitles.setAdapter(spinnerAdapter);
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