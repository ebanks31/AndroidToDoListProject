package com.example.android.todolist.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.todolist.DetailFragment;
import com.example.android.todolist.ListOrganizerActivity;
import com.example.android.todolist.SpinnerFragment;
import com.example.android.todolist.R;
import com.robotium.solo.Solo;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;

public class FragmentTest extends ActivityInstrumentationTestCase2<ListOrganizerActivity> {
	private ListOrganizerActivity mActivity;
	private Solo solo;
	private SpinnerFragment listfragment;
	private DetailFragment detailfragment;
	private Spinner spinner;
	
	private TextView mView; 
	@SuppressWarnings("deprecation")
	public FragmentTest() {
		super( ListOrganizerActivity.class);

	}
	
	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		try {
			solo.finishOpenedActivities();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
	

	TextView helloText;
	
	protected void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		mActivity = this.getActivity(); 
		listfragment = (SpinnerFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.listfragment);
		detailfragment =  (DetailFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.detailFragment);
		spinner =  (Spinner) mActivity.findViewById
                (com.example.android.todolist.R.id.spinner1);
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
		
	}

	@Test
	public void testMenuItems()
	{
		// open the menu
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Settings");
		solo.clickOnText("Options");
		Assert.assertTrue(solo.searchText("rtf"));
	}

	@Test
	public void testaddActionBarYes()
	{
		// open the menu
		solo.clickOnActionBarItem(R.id.action_new);
		solo.enterText(2, "robotium");
		solo.clickOnButton("Yes");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("robotium",listadapter.getItem(listadapter.getCount()-1));
	}

	@Test
	public void testaddActionBarYesWithInputEmpty()
	{
		// open the menu
		solo.clickOnActionBarItem(R.id.action_new);
		solo.enterText(2, "robotium");
		solo.clickOnButton("Yes");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		
		solo.clickOnButton("OK");
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("robotium",(String)listadapter.getItem(listadapter.getCount()-1));
	}
	@Test
	public void testaddActionBarYesWithInputSpace() {
		// open the menu
		solo.clickOnActionBarItem(R.id.action_new);
		solo.enterText(2, "robotium");
		solo.clickOnButton(" ");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		
		solo.clickOnButton("OK");
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("robotium",(String)listadapter.getItem(listadapter.getCount()-1));
	}

	@Test
	public void testaddActionBarYesWithInputMultipleSpaces()
	{
		// open the menu
		solo.clickOnActionBarItem(R.id.action_new);
		solo.enterText(2, "     ");
		solo.clickOnButton(" ");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		
		solo.clickOnButton("OK");
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("robotium",(String)listadapter.getItem(listadapter.getCount()-1));
	}

	@Test
	public void testaddActionBarNo()
	{
		// open the menu
		solo.clickOnActionBarItem(R.id.action_new);
		solo.enterText(2, "robotium");
		solo.clickOnButton("No");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("robotium",(String)listadapter.getItem(listadapter.getCount()-1));
	}

	@Test
	public void testaddListitemValid()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, "additem");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("additem",(String)listadapter.getItem(3));
	}

	@Test
	public void testaddListitemEmpty()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, "");
		
		solo.clickOnButton("OK");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("additem",(String)listadapter.getItem(2));
	}

	@Test
	public void testaddListitemOneSpaces()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, " ");
		
		solo.clickOnButton("OK");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("additem",(String)listadapter.getItem(2));
	}

	@Test
	public void testaddListitemSpaces()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, "     ");
		
		solo.clickOnButton("OK");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("additem",(String)listadapter.getItem(2));
	}

	@Test
	public void testeditListitemValid()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, "edititem");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("edititem",(String)listadapter.getItem(2));
	}

	@Test
	public void testeditListitemEmpty()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, "");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("edititem",(String)listadapter.getItem(2));
	}

	@Test
	public void testeditListitemSingleSpace()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, " ");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("edititem",(String)listadapter.getItem(2));
	}

	@Test
	public void testeditListitemMultipleSpaces()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, "    ");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("edititem",(String)listadapter.getItem(2));
	}

	@Test
	public void testdeleteListitemYes()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Delete");
		solo.clickOnText("Yes");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("Android",(String)listadapter.getItem(2));
	}

	@Test
	private void assertNotEquals(String string, String item) {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testdeleteListitemNo()
	{
		solo.clickLongInList(1,2);
		solo.clickOnText("Delete");
		solo.clickOnText("No");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);

		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("Android",(String)listadapter.getItem(2));
	}

	@Test
	public void testSpinnerNull()
	{
		assertNotNull(spinner);
	}

	@Test
	public void testSpinnerCount()
	{
		assertEquals(2,spinner.getCount());
	}

	@Test
	public void testSpinnerDefaultSelectedItem() {
		assertEquals("Sample List",spinner.getSelectedItem().toString());
	}

	@Test
	public void testSpinnerSecondItem()
	{
		assertEquals("New List",spinner.getAdapter().getItem(1));
	}

	@Test
	public void testSpinnerItemWithSoloFramework() {
		assertTrue(solo.isSpinnerTextSelected("Android1"));
	}

	@Test
	public void testMyDetailFragmentDefaultListItemCount() {
		assertNotNull(detailfragment);
		int count  = detailfragment.getListAdapter().getCount();
		assertEquals(10,count);
	}

	@Test
	public void testMyDetailFragmentgetFirstItemOnList() {
		assertNotNull(detailfragment);
		String value  = (String) detailfragment.getListAdapter().getItem(0);
		assertEquals("Android1",value);
	}

	@Test
	public void testSpinnerSelectSecondItemWithSolo() {
		
		View view1 = solo.getView(Spinner.class, 0);
		solo.clickOnView(view1);
		solo.scrollToTop();
		solo.clickOnView(solo.getView(TextView.class, 1)); 
		boolean spinnerselected = solo.isSpinnerTextSelected("New List");
		
		assertTrue(spinnerselected);
	}

	@Test
	public void testMyDetailFragmentGetFirstItem() {
		ArrayList<View> viewlist =solo.getCurrentViews();

		//get the list view
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		solo.clickOnView(myList.getChildAt(0));
		String firstItem = (String) myList.getAdapter().getItem(0);
		assertEquals("New List", firstItem);
	}

	@Test
	public void testMyDetailFragmentGetSecondItem() {
		ArrayList<View> viewlist =solo.getCurrentViews();
		
		//get the list view
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		String secondString = (String) myList.getAdapter().getItem(1);
		assertEquals("New List", secondString);
	}

	@Test
	public void testMyDetailFragmentGetSecondItemCount()  {
		ArrayList<View> viewlist =solo.getCurrentViews();
		
		//get the list view
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		assertEquals(8, myList.getAdapter().getCount());
	}
}

