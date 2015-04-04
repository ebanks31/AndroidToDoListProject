package com.example.android.rssfeedlist;

import java.util.Date;

/**
 * 
 * This class initializes a ListItem with a title and list item string.
 * The class can also set and get: title, list item, and id.
 * 
 * @author Eric
 *
 */
public class ListItem {

	private String title;
	private String listitem;
	private int id;
	private Date date;
	
	
	/**
	 * Default Constructor for List Item Class
	 */
	public ListItem()
	{
		
	}
	
	/**
	 * Overloaded Constructor for List Item. Initalizes a List Item with a title and listitem
	 * 
	 * @param title title from the spinner
	 * @param listitem list item from the list that is associated to spinner
	 */
	public ListItem(String title, String listitem)
	{
		this.title=title;
		this.listitem=listitem;
	}
	
		/**
	 * Overloaded Constructor for List Item. Initalizes a List Item with a title and listitem
	 * 
	 * @param title title from the spinner
	 * @param listitem list item from the list that is associated to spinner
	 * @param date current date list was modified for title
	 */
	public ListItem(String title, String listitem, Date date)
	{
		this.title=title;
		this.listitem=listitem;
		this.date=date;
	}
	
	/**
	 * Gets the title of the spinner
	 * @return title of the spnner
	 */
	public String getTitle() {	
		return title;
	}

	/**
	 * get List Item from the associated to the title.
	 * 
	 * @return Item from the associated to the title
	 */
	public String getListItem() {
		return listitem;
	}




	/**
	 * Get unique ID for each List Item
	 * @return unique ID for each List Item
	 */
	public int getID() {
		return id;
	}

	/**
	 * Gets the date
	 * @return date returns date
	 */
	public Date getDate() {	
		return date;
	}

	
	/**
	 * Set date
	 * @param date date that will be set
	 */
	public void setDate(Date date) {
		this.date=date;
	}
	/**
	 * Set unique ID for each List Item
	 * @param id unique ID for each List Item
	 */
	public void setID(int id) {
		this.id=id;
	}

	/**
	 * Sets the title of the spinner
	 * @param title title of the spinner
	 */
	public void setTitle(String title) {
		this.title=title;
		
	}

	
	/**
	 * set List Item from the associated to the title.
	 * 
	 * @param listitem List Item from the associated to the title
	 */
	public void setListItem(String listitem) {
		this.listitem=listitem;
		
	}

}
