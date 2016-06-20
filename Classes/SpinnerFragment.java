package com.example.android.todolist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.example.android.todolist.R;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 *
 * This class contains a ListFragments that holds the Spinner.
 * The Spinner contains a list of titles that is associated to it own list items.
 *
 * @author Eric
 *
 */
public class SpinnerFragment extends ListFragment implements OnItemSelectedListener {

  public OnItemSelectedListener listener;

  public static OnItemSelectedListener staticListener;
  public static ArrayList<String> spinnerlist;
  AdapterView<ListAdapter> spinnerAdapter;
  public static Spinner listSpinner;
  public static String currentSpinnerTitle;
  public static int previousSpinnerPosition;
    Context context;
    AdapterView<ListAdapter> spinnerAdapter1;
    public static Spinner listSpinner1;


  /**
  * Default Constructor for SpinnerFragment Fragment. Initialized variables needed for this fragment.
  */
  public SpinnerFragment ()
  {
      previousSpinnerPosition = 1;
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

    View view = inflater.inflate(R.layout.fragment_rsslist_overview,
            container, false);
    listSpinner = (Spinner)view.findViewById(R.id.spinner1);
    spinnerAdapter = (AdapterView<ListAdapter>) view.findViewById(R.id.spinner1);

    ToDoListDbHelper db = new ToDoListDbHelper(getActivity());

    ArrayList<String> list = new ArrayList<String>();

      String sampleList = getResources().getString(R.string.sampleList);
      String newList = getResources().getString(R.string.newList);

      ArrayList<String> stringList = db.getAllSpinnerTitleListString();

    if (!stringList.contains(newList) || !stringList.contains(sampleList)) {
        list.add(sampleList);
        list.add(newList);
        SpinnerItem sampleListSpinnerItem = new SpinnerItem(sampleList, 1);
        SpinnerItem newListSpinnerItem = new SpinnerItem(newList, 2);
        db.addSpinneritem(sampleListSpinnerItem);
        db.addSpinneritem(newListSpinnerItem);
    }

      ArrayList<String> stringList1 = db.getAllSpinnerTitleListString();

    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
 	       android.R.layout.simple_list_item_1, stringList);
    listSpinner.setAdapter(dataAdapter);
 
    addListenerOnSpinnerItemSelection(listSpinner, dataAdapter, stringList1);


      String addSpinner = getResources().getString(R.string.addSpinner);
      String editSpinner = getResources().getString(R.string.editSpinner);
      String deleteSpinner = getResources().getString(R.string.deleteSpinner);

      ArrayList<String> stringList2 = new ArrayList<String>();
      stringList2.add(addSpinner);
      stringList2.add(editSpinner);
      stringList2.add(deleteSpinner);


      listSpinner1 = (Spinner)view.findViewById(R.id.spinner2);
      spinnerAdapter1 = (AdapterView<ListAdapter>) view.findViewById(R.id.spinner2);
      ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
              android.R.layout.simple_list_item_1, stringList2);
      listSpinner1.setAdapter(dataAdapter1);

    return view;
  }

  /**
   *
   * Get OnItemSelectedListener Listener
   * @return OnItemSelectedListener Listener
   */
  public OnItemSelectedListener getListener()
  {
	  return listener;
  }



  /**
   * Set OnitemSelected Listener for the spinner
   *
   * @param listener OnitemSelected Listener for the spinner
   */
   public void setListener(OnItemSelectedListener listener)
  {
	 this.listener = listener;
  }



  /**
   * Sets a custom OnItemSelectedListener to the spinner
   *
   * @param listSpinner Spinner of ListFragment
   * @param dataAdapter dataAdapter that is connected to the spinner
   * @param list List of spinner titles in the spinner.
   */
   public void addListenerOnSpinnerItemSelection(Spinner listSpinner, ArrayAdapter<String> dataAdapter, ArrayList<String> list){
	 
	try
	{
        listSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(context, listSpinner, dataAdapter, list));
	  
}
  	catch (ArrayIndexOutOfBoundsException ex)
  	{
  		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
  		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred", ex);                  //Log error for Array Out of Bounds Exception
  	}
  	catch (NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("Null", "Null Pointer Exception Error", ex);                  //Log error for Null Pointer Exception
  	
	}
}
  
  
  
  /* (non-Javadoc)
 * @see android.app.Fragment#onAttach(android.app.Activity)
 * 
 * Attaches the activity to the OnItemSelectedListener.
 */
@Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      if (activity instanceof OnItemSelectedListener) {
        listener = (OnItemSelectedListener) activity;
        staticListener = listener;
        setListener(listener);
        context = activity;
      } else {
        throw new ClassCastException(activity.toString()
            + " must implement SpinnerFragment.OnItemSelectedListener");
      }
    }
  
  /* (non-Javadoc)
 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
 * 
 * Assigns the activity to the OnItemSelectedListener.
 */
@Override
  public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      Activity activity = getActivity();
      if (activity instanceof OnItemSelectedListener) {
          listener = (OnItemSelectedListener) activity;
          setListener(listener);
          context = activity;
        } else {
          throw new ClassCastException(activity.toString()
              + " must implement SpinnerFragment.OnItemSelectedListener");
        }

 
  }
 
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 * 
	 * Context that appears when the Spinner item is long clicked. 
	 * 
	 */
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    ContextMenuInfo menuInfo) {
	
	try
	{
		
    String[] menuItems = getResources().getStringArray(R.array.country_arrays);
    for (int i = 0; i < menuItems.length; i++) {
      menu.add(Menu.NONE, i, i, menuItems[i]);
    
	}
	}
  	catch (ArrayIndexOutOfBoundsException ex)
  	{
  		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
  		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred", ex);                  //Log error for Array Out of Bounds Exception
  	}
  	catch (NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("Null", "Null Pointer Exception Error", ex);                  //Log error for Null Pointer Exception
  	
	}
  }




	  public interface OnItemSelectedListener {
	      /**
	       * Interface for communicating between SpinnerFragment and Activity
	       *
	     * @param position Position of spinner item
	     */
	    public void onRssItemSelected(int position);
	       void sampleFragmentList(int position);
		   void clearFragmentList(int position);
		public void setOnItemSelectedListener(
				android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener);
		public void updateListView ( ArrayList< String > values );
	    }


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
} 

