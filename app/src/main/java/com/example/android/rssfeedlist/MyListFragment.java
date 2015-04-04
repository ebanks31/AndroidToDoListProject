package com.example.android.rssfeedlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.example.android.rssfeedlist.R;
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
public class MyListFragment extends ListFragment implements OnItemSelectedListener{
  
  public OnItemSelectedListener listener;
	
  static OnItemSelectedListener staticlistener;
  AdapterView<ListAdapter> spinner1;
  public static Spinner listspinner;
  Context context;
  public static String currentspinner;
  
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
   listspinner = (Spinner)view.findViewById(R.id.spinner1);
    spinner1 = (AdapterView<ListAdapter>) view.findViewById(R.id.spinner1);
    ArrayList<String> list = new ArrayList<String>();
    list.add("Sample List");
    list.add("New List");
    
    
	FeedReaderDbHelper db = new FeedReaderDbHelper(getActivity());
    List<ListItem> contacts = db.getAllListItems();
    ArrayList<String> spinnerlist = getSpinnerTitles(contacts);
    ArrayList<String> finalspinnerlist = removeDuplicates(spinnerlist);
    
    list.addAll(finalspinnerlist);
    list.removeAll(Collections.singleton(null));
    
    
    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
 	       android.R.layout.simple_list_item_1, list);  
    listspinner.setAdapter(dataAdapter);
 
    addListenerOnSpinnerItemSelection(listspinner,dataAdapter,list);

    
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
   * Gets the spinner titles from List of ListItem objects.
   * 
 * @param itemlist List of Listitem objects containing title and list item
 * @return ArrayList of string containing spinner titles
 */
public ArrayList<String> getSpinnerTitles(final List<ListItem> itemlist)
  {
      ArrayList<String> list = new ArrayList<String>();
	  
  	  try
	  {
	  for(ListItem listitem:itemlist)
	  {
		  list.add(listitem.getTitle());
		  
	  }
	    return list;
	  }
	catch(ArrayIndexOutOfBoundsException ex)
  	{
  		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
  		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred",ex);                  //Log error for Array Out of Bounds Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("Null", "Null Pointer Exception Error",ex);                  //Log error for Null Pointer Exception
  	
	}
	  return null;
  }
  


  /**
   * Removes duplicates from an ArrayList of strings.
   * 
 * @param list ArrayList of String that contains a list of spinner items
 * @return ArrayList of String containing spinner items with duplicates removed
 */
public ArrayList<String> removeDuplicates(ArrayList<String> list)
  {
	list= new ArrayList<String>();
	try
	{
	  HashSet<String> hs = new HashSet<String>();
	  hs.addAll(list);
	  list.clear();
	  list.addAll(hs);
	}
	
  	catch(ArrayIndexOutOfBoundsException ex)
  	{
  		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
  		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred",ex);                  //Log error for Array Out of Bounds Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("Null", "Null Pointer Exception Error",ex);                  //Log error for Null Pointer Exception
  	
	}
	  return list;
  }

  /**
   * Set OnitemSelected Listener for the spinner
   * 
   * @param listener OnitemSelected Listener for the spinner
   */
public void setListener(OnItemSelectedListener listener)
  {
	 this.listener=listener;
  }
  

  
  /**
   * Sets a custom OnItemSelectedListener to the spinner  
   * 
 * @param listspinner Spinner of ListFragment
 * @param dataAdapter dataAdapter that is connected to the spinner
 * @param list List of spinner titles in the spinner.
 */
public void addListenerOnSpinnerItemSelection(Spinner listspinner,ArrayAdapter<String> dataAdapter,ArrayList<String> list){
	 
	try
	{
	listspinner.setOnItemSelectedListener(new CustomOnItemSelectedListener(context,listspinner,dataAdapter,list));
	  
}
  	catch(ArrayIndexOutOfBoundsException ex)
  	{
  		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
  		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred",ex);                  //Log error for Array Out of Bounds Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("Null", "Null Pointer Exception Error",ex);                  //Log error for Null Pointer Exception
  	
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
        staticlistener=listener;
        setListener(listener);
        context=activity;
      } else {
        throw new ClassCastException(activity.toString()
            + " must implement MyListFragment.OnItemSelectedListener");
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
      Activity activity=getActivity();
      if (activity instanceof OnItemSelectedListener) {
          listener = (OnItemSelectedListener) activity;
          setListener(listener);
          context=activity;
        } else {
          throw new ClassCastException(activity.toString()
              + " must implement MyListFragment.OnItemSelectedListener");
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
    for (int i = 0; i<menuItems.length; i++) {
      menu.add(Menu.NONE, i, i, menuItems[i]);
    
	}
	}
  	catch(ArrayIndexOutOfBoundsException ex)
  	{
  		System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
  		Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred",ex);                  //Log error for Array Out of Bounds Exception
  	}
  	catch(NullPointerException ex)
  	{
  		System.out.println("NullPointer Exception has occurred" + ex.getMessage());
  		Log.e("Null", "Null Pointer Exception Error",ex);                  //Log error for Null Pointer Exception
  	
	}
  }

	
	
    
	  public interface OnItemSelectedListener {
	      /**
	       * Interface for communicating between MyListFragment and Activity
	       * 
	     * @param position Position of spinner item
	     */
	    public void onRssItemSelected(int position);
	      public void sampleFragmentList(int position);
		  public void clearFragmentList(int position);
		public void setOnItemSelectedListener(
				android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener);
		public void updateList(ArrayList<String> values);
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

