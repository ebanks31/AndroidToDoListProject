package com.example.android.todolist;

import com.example.android.todolist.SpinnerFragment.OnItemSelectedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import org.apache.commons.lang3.StringUtils;

/**
 * This class keeps track of the detail fragments.
 * It keeps track of the list items that are associated to an individual spinner title
 *
 * @author Eric
 */
public class DetailFragment extends ListFragment {

    DetailFragmentSelectedListener listener;
    private ToDoListUtility todolistutility;

    /**
     * Default Constructor for Detail Fragment. Initialized variables needed for this fragment.
     */
    public DetailFragment() {
        todolistutility = new ToDoListUtility();
    }

    /* (non-Javadoc)
  * @see android.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
  *
  * Sets the initial values in the spinner.
  */
    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rssitem_detail,
                container, false);


        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        listener.detailFragmentSelected(values, adapter);

        return view;
    }

    /* (non-Javadoc)
   * @see android.app.Fragment#onAttach(android.app.Activity)
   *
   * Attaches activity to detail fragment listener
   */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (DetailFragmentSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement SpinnerFragment.OnItemSelectedListener");
        }
    }

    /* (non-Javadoc)
   * @see android.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
   *
   * This method invokes when an individual list item is clicked.
   */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("PASSWORD");

        // Setting Dialog Message
        alertDialog.setMessage("Enter Password");
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.key);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addItemtoList(input);
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // closed

        // Showing Alert Message
        //alertDialog.show();
    }

    /**
     * Interface for detail fragment.
     * This interface allows communication between detail fragment and Main Activity Class
     *
     * @author Eric
     */
    public interface DetailFragmentSelectedListener {
        void detailFragmentSelected(String[] values, ArrayAdapter<String> adapter);
    }

    /**
     * Adds a item to spinner list.
     *
     * @param input the input from edit text box
     */
    public void addItemtoList(EditText input) {
        String listInput = input.getText().toString();

        //Check for invalid characters
        Boolean validCharactersFound = todolistutility.checkValidCharacters(listInput);

        //Checks if the edittext is empty,space, or null. Not a valid list item.
        if (StringUtils.isNotEmpty(listInput.trim()) || !validCharactersFound) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            // Setting Dialog Title
            alertDialog.setTitle("Invalid list name");

            // Setting Dialog Message
            alertDialog.setMessage("Please Enter a valid list name");
        } else {
            try {
                //Add to Spinner
                Spinner spinner = (Spinner) getView().findViewById(R.id.spinner1);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
                spinnerAdapter.add(input.getText().toString());
                spinnerAdapter.notifyDataSetChanged();

            } catch (ArrayIndexOutOfBoundsException ex) {
                Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred", ex);                  //Log error for Array Out of Bounds Exception
            } catch (NullPointerException ex) {
                Log.e("Null", "Null Pointer Exception Error", ex);                  //Log error for Null Pointer Exception

            }
        }
    }

} 