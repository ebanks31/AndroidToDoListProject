package com.example.android.todolist;

import java.util.Date;

/**
 * 
 * This class initializes a ListItem with a title and list item string.
 * The class can also set and get: title, list item, and id.
 * 
 * @author Eric
 *
 */
public class SpinnerItem {


    private String title;
    private int position;

    private int id;
	/**
	 * Default Constructor for List Item Class
	 */
	public SpinnerItem () {

	}

	/**
	 * Overloaded Constructor for List Item. Initalizes a List Item with a title and listitem
	 *
	 * @param title title from the spinner
	 * @param position list item from the list that is associated to spinner
	 */
	public SpinnerItem (String title, int position) {
		this.title = title;
		this.position = position;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
