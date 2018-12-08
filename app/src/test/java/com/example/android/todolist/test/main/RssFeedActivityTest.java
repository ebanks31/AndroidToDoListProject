

import java.util.ArrayList;

import org.junit.Test;

import com.robotium.solo.Solo;
import com.example.android.rssfeedlist.DetailFragment;
import com.example.android.rssfeedlist.MyListFragment;
import com.example.android.rssfeedlist.R;
import com.example.android.rssfeedlist.ListOrganizerActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class RssFeedActivityTest extends ActivityInstrumentationTestCase2<ListOrganizerActivity> {
	private ListOrganizerActivity mActivity;
	private Solo solo;
	private MyListFragment listfragment;
	private DetailFragment detailfragment;
	private Spinner spinner;
	
	private TextView mView; 
	@SuppressWarnings("deprecation")
	public RssFeedActivityTest() {
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
		   //helloText = (TextView) getActivity().findViewById(R.id.spinner); 
		mActivity = this.getActivity(); 
		listfragment = (MyListFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.listfragment);
		detailfragment =  (DetailFragment) mActivity.getFragmentManager()
	            .findFragmentById(R.id.detailFragment);
		spinner =  (Spinner) mActivity.findViewById
                (com.example.android.rssfeed.R.id.spinner1); 
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
		
	}
	
	
	public void testRemoveDuplicatesMethod() {
	
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		ArrayList<String> finallist =mActivity.removeDuplicates(list);
		
		assertEquals(2, finallist.size());
		
	}
	
	
	
	public void testRemoveDuplicatesMethodWithEmptyString() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		list.add("");
		list.add("");
		ArrayList<String> finallist =rs.removeDuplicates(list);
		
		assertEquals(3, finallist.size());
		
	}
	
	
	public void testRemoveDuplicatesMethodWithEmptySpaces() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		list.add(" ");
		list.add(" ");
		ArrayList<String> finallist =rs.removeDuplicates(list);
		
		assertEquals(3, finallist.size());
		
	}
	
	
	public void testRemoveDuplicatesMethodWithUpperCaseEndString() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("HellO");
		ArrayList<String> finallist =rs.removeDuplicates(list);
		
		assertEquals(2, finallist.size());
		
	}
	
	
	public void testupdateArrayWithValidItemFirstValue() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		String[] stringarray = {"Hello","Great","Time","Robot"};
		String item="Hello";
		String newitem="Hi";
		String[] finalstringarray =rs.updateArray(stringarray,item,newitem);
		
		assertEquals("Hi", finalstringarray[0]);
		
	}
	
	
	
	public void testupdateArrayWithValidItemLastItem() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		String[] stringarray = {"Hello","Great","Time","Robot"};
		String item="Robot";
		String newitem="Hi";
		String[] finalstringarray =rs.updateArray(stringarray,item,newitem);
		
		assertEquals("Hi", finalstringarray[2]);
		
	}
	
	
	public void testupdateArrayWithValidItemLastItemEmptyStringFirstItem() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		String[] stringarray = {"","Great","Time","Robot"};
		String item="Robot";
		String newitem="";
		String[] finalstringarray =rs.updateArray(stringarray,item,newitem);
		
		assertEquals("Robot", finalstringarray[2]);
		
	}
	
	
	public void testupdateArrayWithValidItemLastItemEmptyStringLastItem() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		String[] stringarray = {"Hello","Great","Time","Robot"};
		String item="Robot";
		String newitem="";
		String[] finalstringarray =rs.updateArray(stringarray,item,newitem);
		
		assertEquals("Robot", finalstringarray[2]);
		
	}

	
	public void testupdateArrayWithValidItemLastItemEmptyStringFirstAndLastItem() {
	
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		String[] stringarray = {"Robot","Great","Time","Robot"};
		String item="Robot";
		String newitem="";
		String[] finalstringarray =rs.updateArray(stringarray,item,newitem);
		
		assertEquals("", finalstringarray[2]);
		assertEquals("", finalstringarray[0]);
		
	}
	
	
	public void testConvertArrayListtoArrayWithValidArrayList() {
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("Hello");
		list.add("hello");
		list.add(" ");
		list.add(" ");
		String[] finalarraylist =rs.ConvertArrayListtoArray(list);
		
		assertEquals(5, finalarraylist.length);
		
	}
	
	
	public void testConvertArrayListtoArrayWithValidArrayListOneValue() {
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		String[] finalarraylist =rs.ConvertArrayListtoArray(list);
		
		assertEquals(1, finalarraylist.length);
		
	}
	
	
	public void testConvertArrayListtoArrayWithValidArrayListNoValue() {
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		String[] finalarraylist =rs.ConvertArrayListtoArray(list);
		
		assertEquals(0, finalarraylist.length);
		
	}
	
		
	
	public void testdeleteItemFromArrayWithValidValuesFoundInArrayList() {
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("hello");
		list.add(" ");

		String[] finalarraylist =rs.deleteItemFromArray(list,"Hello");
		
		assertEquals("hello", finalarraylist[0]);
		
	}
	
	
	public void testdeleteItemFromArrayWithValidValuesNotFoundInArrayList() {
		ListOrganizerActivity rs = new ListOrganizerActivity();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("hello");
		list.add(" ");

		String[] finalarraylist =rs.deleteItemFromArray(list,"Notfound");
		
		assertEquals("Hello", finalarraylist[0]);
		
	}
	
	public void testSpinnerNull()
	{
		assertNotNull(spinner);
	}
	

	
	public void testSpinnerCount()
	{
		assertEquals(10,spinner.getCount());
	}
	
	public void testSpinnerDefaultSelectedItem()
	{
		assertEquals("Sample List",spinner.getSelectedItem().toString());
	}
	
	public void testSpinnerSecondItem()
	{
		assertEquals("Windows",spinner.getAdapter().getItem(1));
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

	
	}

