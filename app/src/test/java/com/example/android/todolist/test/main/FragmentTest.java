

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.robotium.solo.Solo;
import com.example.android.rssfeedlist.DetailFragment;
import com.example.android.rssfeedlist.MyListFragment;
import com.example.android.rssfeedlist.R;
import com.example.android.rssfeedlist.ListOrganizerActivity;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class FragmentTest extends ActivityInstrumentationTestCase2<ListOrganizerActivity> {
	private ListOrganizerActivity mActivity;
	private Solo solo;
	private MyListFragment listfragment;
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
		solo.finishOpenedActivities();
	}
	

	TextView helloText;
	
	protected void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		mActivity = this.getActivity(); 
		listfragment = (MyListFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.listfragment);
		detailfragment =  (DetailFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.detailFragment);
		spinner =  (Spinner) mActivity.findViewById
                (com.example.android.rssfeed.R.id.spinner1); 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
		
	}
	
	public void testMenuItems()
	{
		// open the menu
		solo.sendKey(Solo.MENU);
		solo.clickOnText("Settings");
		solo.clickOnText("Options");
		Assert.assertTrue(solo.searchText("rtf"));
	}

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
	    public void testaddActionBarYesWithInputSpace()
	{
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
	
	public void testaddListitemValid()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, "additem");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("additem",(String)listadapter.getItem(3));
	}
	
	
	public void testaddListitemEmpty()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, "");
		
		solo.clickOnButton("OK");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("additem",(String)listadapter.getItem(2));
	}
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
	
	public void testaddListitemSpaces()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Add");
		solo.enterText(2, "     ");
		
		solo.clickOnButton("OK");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("additem",(String)listadapter.getItem(2));
	}
	
	
	public void testeditListitemValid()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, "edititem");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("edititem",(String)listadapter.getItem(2));
	}
	
	public void testeditListitemEmpty()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, "");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("edititem",(String)listadapter.getItem(2));
	}
	
	public void testeditListitemSingleSpace()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, " ");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("edititem",(String)listadapter.getItem(2));
	}
	
	public void testeditListitemMultipleSpaces()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Edit");
		solo.enterText(2, "    ");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("edititem",(String)listadapter.getItem(2));
	}
	
	public void testdeleteListitemYes()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Delete");
		solo.clickOnText("Yes");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertNotEquals("Android",(String)listadapter.getItem(2));
	}
	
	
	private void assertNotEquals(String string, String item) {
		// TODO Auto-generated method stub
		
	}

	public void testdeleteListitemNo()
	{
		ArrayList<View> list = solo.getCurrentViews();
		solo.clickLongInList(1,2);
		solo.clickOnText("Delete");
		solo.clickOnText("No");
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		ListAdapter listadapter = myList.getAdapter();
		assertEquals("Android",(String)listadapter.getItem(2));
	}
	
	public void testSpinnerNull()
	{
		assertNotNull(spinner);
	}
	

	public void testSpinnerCount()
	{
		assertEquals(2,spinner.getCount());
	}
	
	public void testSpinnerDefaultSelectedItem()
	{
		assertEquals("Sample List",spinner.getSelectedItem().toString());
	}
	
	public void testSpinnerSecondItem()
	{
		assertEquals("New List",spinner.getAdapter().getItem(1));
	}
	
	
	public void testSpinnerItemWithSoloFramework()
	{
		assertTrue(solo.isSpinnerTextSelected("Android1"));
	}
	
	public void testMyDetailFragmentDefaultListItemCount()
	{
		assertNotNull(detailfragment);
		int count  = detailfragment.getListAdapter().getCount();
		assertEquals(10,count);
	}
	
	public void testMyDetailFragmentgetFirstItemOnList()
	{
		assertNotNull(detailfragment);
		String value  = (String) detailfragment.getListAdapter().getItem(0);
		assertEquals("Android1",value);
	}
	
	public void testSpinnerSelectSecondItemWithSolo()
	{
		
		View view1 = solo.getView(Spinner.class, 0);
		solo.clickOnView(view1);
		solo.scrollToTop();
		solo.clickOnView(solo.getView(TextView.class, 1)); 
		boolean spinnerselected = solo.isSpinnerTextSelected("New List");
		
		assertTrue(spinnerselected);
	}
	
	public void testMyDetailFragmentGetFirstItem()
	{
		ArrayList<View> viewlist =solo.getCurrentViews();

		//get the list view
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		solo.clickOnView(myList.getChildAt(0));
		String firstitem = (String) myList.getAdapter().getItem(0);
		assertEquals("New List",firstitem);
	}
	
	
	public void testMyDetailFragmentGetSecondItem()
	{
		ArrayList<View> viewlist =solo.getCurrentViews();
		
		//get the list view
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		String secondstring = (String) myList.getAdapter().getItem(1);
		assertEquals("New List",secondstring);
	}
	
	public void testMyDetailFragmentGetSecondItemCount()
	{
		ArrayList<View> viewlist =solo.getCurrentViews();
		
		//get the list view
		ListView myList = (ListView)solo.getView(R.id.detailFragment);
		//get the list element at the position you want
		assertEquals(8,myList.getAdapter().getCount());
	}
	

	
	}

