package com.example.android.todolist.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.todolist.DetailFragment;
import com.example.android.todolist.ListOrganizerActivity;
import com.example.android.todolist.R;
import com.example.android.todolist.SpinnerFragment;
import com.example.android.todolist.ToDoListUtility;
import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.ArrayList;

public class ListOrganizerActivityTest extends ActivityInstrumentationTestCase2<ListOrganizerActivity> {
	private ListOrganizerActivity mActivity;
	private Solo solo;
	private SpinnerFragment listfragment;
	private DetailFragment detailfragment;
	private Spinner spinner;
	
	private TextView mView;
	ToDoListUtility toDoListUtility;

	@SuppressWarnings("deprecation")
	public ListOrganizerActivityTest() {
		super( ListOrganizerActivity.class);

	}
	
	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		try {
			solo.finalize();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	TextView helloText;
	
	protected void setUp() throws Exception {
		   //helloText = (TextView) getActivity().findViewById(R.id.spinner); 
		mActivity = this.getActivity(); 
		listfragment = (SpinnerFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.listfragment);
		detailfragment =  (DetailFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.detailFragment);
		spinner =  (Spinner) mActivity.findViewById
                (com.example.android.todolist.R.id.spinner1);
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
		toDoListUtility = new ToDoListUtility();
	}

	@Test
	public void testRemoveDuplicatesMethod() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		ArrayList<String> finallist = toDoListUtility.removeDuplicates(list);
		
		assertEquals(2, finallist.size());
		
	}

	@Test
	public void testRemoveDuplicatesMethodWithEmptyString() {
		ListOrganizerActivity listOrganizerActivity = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		list.add("");
		list.add("");
		ArrayList<String> finallist = toDoListUtility.removeDuplicates(list);
		
		assertEquals(3, finallist.size());
		
	}

	@Test
	public void testRemoveDuplicatesMethodWithEmptySpaces() {
	
		ListOrganizerActivity listOrganizerActivity = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		list.add(" ");
		list.add(" ");
		ArrayList<String> finallist = toDoListUtility.removeDuplicates(list);
		
		assertEquals(3, finallist.size());
		
	}

	@Test
	public void testRemoveDuplicatesMethodWithUpperCaseEndString() {
	
		ListOrganizerActivity listOrganizerActivity = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("HellO");
		ArrayList<String> finallist = toDoListUtility.removeDuplicates(list);
		
		assertEquals(2, finallist.size());
		
	}

	@Test
	public void testUpdateArrayWithValidItemFirstValue() {
		String[] stringarray = {"Hello","Great","Time","Robot"};
		String item="Hello";
		String newitem="Hi";
		String[] finalstringarray = toDoListUtility.updateArray(stringarray,item,newitem);
		
		assertEquals("Hi", finalstringarray[0]);
		
	}

	@Test
	public void testUpdateArrayWithValidItemLastItem() {
		String[] stringarray = {"Hello","Great","Time","Robot"};
		String item="Robot";
		String newitem="Hi";
		String[] finalstringarray = toDoListUtility.updateArray(stringarray,item,newitem);
		
		assertEquals("Hi", finalstringarray[2]);
		
	}

	@Test
	public void testUpdateArrayWithValidItemLastItemEmptyStringFirstItem() {
		String[] stringarray = {"","Great","Time","Robot"};
		String item="Robot";
		String newitem="";
		String[] finalstringarray = toDoListUtility.updateArray(stringarray,item,newitem);
		
		assertEquals("Robot", finalstringarray[2]);
		
	}

	@Test
	public void testUpdateArrayWithValidItemLastItemEmptyStringLastItem() {
		String[] stringarray = {"Hello","Great","Time","Robot"};
		String item="Robot";
		String newitem="";
		String[] finalstringarray = toDoListUtility.updateArray(stringarray,item,newitem);
		
		assertEquals("Robot", finalstringarray[2]);
		
	}

	@Test
	public void testUpdateArrayWithValidItemLastItemEmptyStringFirstAndLastItem() {
		String[] stringarray = {"Robot","Great","Time","Robot"};
		String item="Robot";
		String newitem="";
		String[] finalstringarray = toDoListUtility.updateArray(stringarray,item,newitem);
		
		assertEquals("", finalstringarray[2]);
		assertEquals("", finalstringarray[0]);
		
	}

	@Test
	public void testConvertArrayListtoArrayWithValidArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		list.add(" ");
		list.add(" ");
		
	}

	@Test
	public void testDeleteItemFromArrayWithValidValuesFoundInArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("hello");
		list.add(" ");

		String[] finalarraylist = toDoListUtility.deleteItemFromArray(list,"Hello");
		
		assertEquals("hello", finalarraylist[0]);
		
	}

	@Test
	public void testDeleteItemFromArrayWithValidValuesNotFoundInArrayList() {
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("hello");
		list.add(" ");

		String[] finalarraylist = toDoListUtility.deleteItemFromArray(list,"Notfound");
		
		assertEquals("Hello", finalarraylist[0]);
		
	}

	@Test
	public void testSpinnerNull()
	{
		assertNotNull(spinner);
	}

	@Test
	public void testSpinnerCount()
	{
		assertEquals(10,spinner.getCount());
	}

	@Test
	public void testSpinnerDefaultSelectedItem() {
		assertEquals("Sample List",spinner.getSelectedItem().toString());
	}

	@Test
	public void testSpinnerSecondItem()
	{
		assertEquals("Windows",spinner.getAdapter().getItem(1));
	}

	@Test
	public void testMyDetailFragmentDefaultListItemCount()  {
		assertNotNull(detailfragment);
		int count  = detailfragment.getListAdapter().getCount();
		assertEquals(10,count);
	}

	@Test
	public void testDetailFragmentgetFirstItemOnList() {
		assertNotNull(detailfragment);
		String value  = (String) detailfragment.getListAdapter().getItem(0);
		assertEquals("Android1",value);
	}
}

