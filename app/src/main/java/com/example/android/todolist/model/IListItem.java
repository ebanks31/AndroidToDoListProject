package com.example.android.todolist.model;

import java.util.Date;

/**
 * This is an interface for the a List Item in the To Do List Android Applications.
 * List item are items from a list category from the title that the user has created.
 *
 */
public interface IListItem {
    /**
     * Gets the title of the spinner.
     *
     * @return title of the spinner
     */
    int getPosition();

    /**
     * Sets the position of the list item.
     *
     * @param position position that will be set.
     */
    void setPosition(int position);

    /**
     * Gets the title of the spinner.
     *
     * @return title of the spnner
     */
    String getTitle();

    /**
     * Sets the title of the spinner.
     *
     * @param title title of the spinner
     */
    void setTitle(String title);

    /**
     * Gets the list item from the associated to the title.
     *
     * @return Item from the associated to the title
     */
    String getListItem();

    /**
     * Sets the list item from the associated to the title.
     *
     * @param listItem List Item from the associated to the title
     */
    void setListItem(String listItem);

    /**
     * Gets the unique ID for each List Item.
     *
     * @return unique ID for each List Item
     */
    int getID();

    /**
     * Sets the unique ID for each List Item.
     *
     * @param id unique ID for each List Item
     */
    void setID(int id);

    /**
     * Gets the date.
     *
     * @return date returns date
     */
    Date getDate();
}
