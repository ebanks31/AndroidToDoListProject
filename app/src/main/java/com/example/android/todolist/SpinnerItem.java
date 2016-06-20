package com.example.android.todolist;

/**
 * 
 * This class initializes a SpinnerItem with a title and position.
 * This class can also set and get the spinner title and position.
 * 
 * @author Eric
 *
 */
public class SpinnerItem implements ISpinnerItem {


    private String title;
    private int position;
    private int id;

	/**
	 * Default Constructor for Spinner Item Class
	 */
	public SpinnerItem () {

	}

	/**
	 * Overloaded Constructor for Spinner Item. Initializes a Spinner Item with a title and position
	 *
	 * @param title title from the spinner
	 * @param position list item from the list that is associated to spinner
	 */
	public SpinnerItem (String title, int position) {
		this.title = title;
		this.position = position;
	}


    /**
     * Get Spinner Title
     * @return returns Spinner Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets Spinner Title
     * @param title Sets Spinner Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get position in spinner title list
     * @return position in spinner title list
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set position in spinner title list
     * @param position position in spinner title list
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get unique ID for each Spinner Item
     * @return unique ID for each Spinner Item
     */
    public int getId() {
        return id;
    }


    /**
     * Set unique ID for each Spinner Item
     * @param id unique ID for each Spinner Item
     */
    public void setId(int id) {
        this.id = id;
    }
}
