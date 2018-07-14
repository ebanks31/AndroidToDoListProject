package com.example.android.todolist.model;

/**
 * Created by Eric on 4/11/2016.
 */
public interface ISpinnerItem {

    /**
     * Get the spinner title.
     *
     * @return returns Spinner Title
     */
    String getTitle();

    /**
     * Sets the spinner title.
     *
     * @param title Sets Spinner Title
     */
    void setTitle(String title);

    /**
     * Gets the position in spinner title list.
     *
     * @return position in spinner title list
     */
    int getPosition();

    /**
     * Sets the position in spinner title list.
     *
     * @param position position in spinner title list
     */
     void setPosition(int position);

    /**
     * Gets the unique ID for each Spinner Item.
     *
     * @return unique ID for each Spinner Item
     */
     int getId();

    /**
     * Sets the unique ID for each Spinner Item.
     *
     * @param id unique ID for each Spinner Item
     */
     void setId(int id);
}
