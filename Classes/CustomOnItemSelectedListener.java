package com.example.android.todolist.main;


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

import com.example.android.todolist.database.ToDoListDbHelper;
import com.example.android.todolist.model.ListItem;
import com.example.android.todolist.model.SpinnerItem;
import com.example.android.todolist.utility.ToDoListUtility;

/**
 * This class is a Custom OnItemSelectedListener for the spinner.
 * It contains actions for when the item is selected in the spinner
 *
 * @author Eric
 */
public class CustomOnItemSelectedListener extends SpinnerFragment implements OnItemSelectedListener {

    public static Context context;
    public static Spinner spinnerTitles;
    public static ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> spinnerList;

    /**
     * Overload constructor of CustomOnItemSelectedListener class.
     * Sets intial values that are needed for the CustomOnItemSelectedListener
     *
     * @param context     set the context from the Main Activity class
     * @param listSpinner Current Spinner from the SpinnerFragment Class
     * @param dataAdapter Adapter that is associated to the spinner
     * @param spinnerList List of spinner items that are located int he spinner
     */
    public CustomOnItemSelectedListener(Context context, Spinner listSpinner,
                                        ArrayAdapter<String> dataAdapter, ArrayList<String> spinnerList) {
        this.context = context;
        this.spinnerList = spinnerList;
        spinnerAdapter = dataAdapter;
        spinnerTitles = listSpinner;
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
            Log.d("listInput", "listInput: " + listInput);

            spinnerAdapter.add(listInput);
            spinnerAdapter.notifyDataSetChanged();
            spinnerTitles.setAdapter(spinnerAdapter);

            int spinnerPosition = spinnerAdapter.getPosition(listInput);

            //set the selected spinner item according to value
            spinnerTitles.setSelection(spinnerPosition);
            SpinnerFragment.currentSpinnerTitle = listInput;

            ToDoListDbHelper db = new ToDoListDbHelper(context);
            ArrayList<SpinnerItem> stringList1 = db.getAllSpinnerTitle();
            int position = stringList1.size() + 1;
            SpinnerItem newListSpinnerItem = new SpinnerItem(listInput, position);
            db.addSpinneritem(newListSpinnerItem);

            previousSpinnerPosition = position;
            SpinnerFragment.staticListener.clearFragmentList(1);
        }
    };


    // Handler for when "User created title" is selected in the spinner drop down menu
    static Handler userInputHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String listInput = bundle.getString("mysecondKey");
            int position = bundle.getInt("position");

            ToDoListDbHelper db = new ToDoListDbHelper(context);
            List<ListItem> itemListByTitle = db.getAllListItemsByTitle(listInput);
            ToDoListUtility todolistutilityHandler = new ToDoListUtility();
            ArrayList<String> values = todolistutilityHandler.getListItems(itemListByTitle);
            SpinnerFragment.staticListener.updateListView(values);

            previousSpinnerPosition = position;

        }
    };


    /**
     * Method is invoked when a spinner title is selected.
     * Invokes action depending on what spinner title is selected.
     *
     * @param parent      the parent adapter view
     * @param currentView the current view
     * @param position    the position of currentView from patent
     * @param id          the id of the current view.
     */
    public void onItemSelected(AdapterView<?> parent, View currentView, final int position,
                               long id) {
        final String itemSelected = parent.getItemAtPosition(position).toString();

        SpinnerFragment.currentSpinnerTitle = itemSelected;

        switch (itemSelected) {
            case "New List":
                createNewList();
                break;
            case "Sample List":
                createSampleList();
                break;
            default:
                startListThread("mysecondKey", SpinnerFragment.currentSpinnerTitle, position);
        }
    }

    /**
     * Creates a new list based on the information that is inputted from the alert dialog.
     *
     * @param context the context of the current view
     */
    private void createNewList(Context context) {
        AlertDialog.Builder listAlertDialog = setAlertDialogFields("List Title", "Enter title for your list", context);

        final EditText lineInput = new EditText(context);

        //Sets the line layour parameters for the newly created list.
        LinearLayout.LayoutParams lineInputLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lineInput.setLayoutParams(lineInputLayoutParams);
        listAlertDialog.setView(lineInput);
        System.out.println("lineInput:" + lineInput);

        //Sets the positive and negative actions when these button are touched for the alert dialog.
        setNegativeAlertOption(listAlertDialog);
        setPositiveAlertOption(listAlertDialog);

        // create alert dialog
        AlertDialog alertDialog1 = listAlertDialog.create();

        // show alert dialog
        alertDialog1.show();
    }

    /**
     * Create a sample list.
     */
    private void createSampleList() {
        // Communicate this fragment to the main activity
        SpinnerFragment.staticListener.sampleFragmentList(1);
        previousSpinnerPosition = 1;
    }

    /**
     * This method starts the a new thread with the current spinner title and current position of the spinnner.
     */
    private void startListThread(String bundleKey, String bundleValue, int position) {
        try {

            //If Spinner is a an spinner item in the list created by the user.
            Runnable runnable = new Runnable() { //Run in separate thread
                public void run() {

                    // Use Handler(userinputhandler) to update User Interface from another thread.
                    Message message = userInputHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString(bundleKey, bundleValue);
                    bundle.putInt("position", position);
                    message.setData(bundle);
                    userInputHandler.sendMessage(message);

                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
            Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred", ex);                  //Log error for Array Out of Bounds Exception
        } catch (NullPointerException ex) {
            System.out.println("NullPointer Exception has occurred" + ex.getMessage());
            Log.e("Null", "Null Pointer Exception Error", ex);                  //Log error for Null Pointer Exception

        }
    }

    /**
     * Sets the alert dialog fields.
     *
     * @param title   the title of the alert
     * @param message the message of the alert
     * @param context the context of the current view
     */
    private void setAlertDialogFields(String title, String message, Context context) {
        //Creates an alert dialog based on the context of the current view.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting dialog title
        alertDialog.setTitle("Duplicate item");

        // Setting dialog message
        alertDialog.setMessage("Cannot add duplicate item");
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * Set Positive Alert Button with String No.
     *
     * @param alertDialog Alert Dialog that is passed in
     */
    public void setPositiveAlertOption(AlertDialog.Builder alertDialog) {
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
     * Set Negative Alert Button with String YES.
     *
     * @param alertDialog Alert Dialog that is passed in
     */
    public void setNegativeAlertOption(AlertDialog.Builder listAlertDialog) {
        // Setting Positive "Yes" Button
        listAlertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        final String listInput = input.getText().toString();

                        Boolean validcharacter = ToDoListUtility.checkValidCharacters(listInput);

                        //Checks if edit text is empty,space, or null. Not a valid list item.
                        if (listInput.isEmpty() || validcharacter == false) {
                            AlertDialog.Builder invalidNameAlertDialog = ("Invalid Name",
                            "Please Enter a valid list name", context)
                            setPositiveAlertOptionOK(invalidNameAlertDialog);
                            listAlertDialog.show();
                            updateSpinnerWithPreviousTitle();

                        } else {

                            if (!ToDoListUtility.checkDuplicates(spinnerList, listInput)) //check duplicate spinner items
                            {
                                try {
                                    startListThread("myKey", listInput, position);
                                } else{
                                AlertDialog.Builder duplicateItemAlertDialog = ("Duplicate item",
                                "Cannot add duplicate item", context)
                                setPositiveAlertOptionOK(listAlertDialog);
                                duplicateItemAlertDialog.show();
                            }

                            }
                        }
                    }
            );
                }

                /**
                 * This method updates the spinner title to the previous selected Spinner Title.
                 */
        public void updateSpinnerWithPreviousTitle () {
            ToDoListDbHelper db = new ToDoListDbHelper(context);
            String previousSpinner = db.getAllSpinnerTitleByPosition(SpinnerFragment.previousSpinnerPosition);
            // spinnerTitles.setSelection(SpinnerFragment.previousSpinnerPosition);
            spinnerTitles.setSelection(3);
            SpinnerFragment.currentSpinnerTitle = previousSpinner;
            spinnerTitles.setAdapter(spinnerAdapter);
            spinnerAdapter.notifyDataSetChanged();

        }

        /**
         * Set Positive Alert Button with String OK
         *
         * @param alertDialog Alert Dialog that is passed in
         */
        public void setPositiveAlertOptionOK (AlertDialog.Builder alertDialog){
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
         * Interface for communication between CustomerOnItemselectListener, other fragments, and activities.
         *
         * @author Eric
         */
        public interface OnItemSelectedListener {
            void onRssItemSelected(int position);

            void sampleFragmentList(int position);
        }
    }