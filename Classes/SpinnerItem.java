package com.example.android.todolist;

/**
 * This class initializes a SpinnerItem with a title and position.
 * This class can also set and get the spinner title and position.
 *
 * @author Eric Banks
 */
public class SpinnerItem implements ISpinnerItem {
    private String title;
    private int position;
    private int id;

    /**
     * Default Constructor for Spinner Item Class
     */
    public SpinnerItem() {
    }

    /**
     * Overloaded Constructor for Spinner Item. Initializes a Spinner Item with a title and position
     *
     * @param title    title from the spinner
     * @param position list item from the list that is associated to spinner
     */
    public SpinnerItem(final String title, final int position) {
        this.title = title;
        this.position = position;
    }

    /**
     * Gets the spinner title.
     *
     * @return returns Spinner Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the spinner title.
     *
     * @param title the spinner title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Gets the  position in spinner title list.
     *
     * @return position in spinner title list
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position in spinner title list.
     *
     * @param position position in spinner title list
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    /**
     * Gets the unique ID for each spinner item.
     *
     * @return unique ID for each Spinner Item
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for each spinner item.
     *
     * @param id unique ID for each spinner item
     */
    public void setId(final int id) {
        this.id = id;
    }
}
