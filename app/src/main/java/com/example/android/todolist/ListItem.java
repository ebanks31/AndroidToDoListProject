package com.example.android.todolist;

import java.util.Date;

/**
 * This class initializes a ListItem with a title and list item value.
 * This class can also set and get: title, list item, and id.
 *
 * @author Eric
 */
public class ListItem implements IListItem {

    private String title;
    private String listItem;
    private int id;
    private Date date;
    private int position;

    /**
     * Default Constructor for List Item Class.
     */
    public ListItem() {

    }

    /**
     * Overloaded Constructor for List Item.
     * Initializes a List Item with a title and list item.
     *
     * @param title    title from the spinner
     * @param listItem list item from the list that is associated to spinner
     * @param date     current date list was modified for title
     * @param position current position
     */
    public ListItem(final String title, final String listItem,
                    final Date date, final int position) {
        this.title = title;
        this.listItem = listItem;
        this.date = date;
        this.position = position;
    }

    /**
     * Gets the title of the spinner.
     *
     * @return title of the spnner
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the list item
     *
     * @param position position that will be set.
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    /**
     * Gets the title of the spinner
     *
     * @return title of the spnner
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the spinner.
     *
     * @param title title of the spinner
     */
    public void setTitle(final String title) {
        this.title = title;

    }

    /**
     * Gets the list item from the associated to the title.
     *
     * @return Item from the associated to the title
     */
    public String getListItem() {
        return listItem;
    }

    /**
     * Sets the list item from the associated to the title.
     *
     * @param listItem List Item from the associated to the title
     */
    public void setListItem(final String listItem) {
        this.listItem = listItem;

    }

    /**
     * Get unique ID for each List Item.
     *
     * @return unique ID for each List Item
     */
    public int getID() {
        return id;
    }

    /**
     * Set unique ID for each List Item.
     *
     * @param id unique ID for each List Item
     */
    public void setID(final int id) {
        this.id = id;
    }

    /**
     * Gets the date.
     *
     * @return date returns date
     */
    public Date getDate() {
        return date;
    }
}

