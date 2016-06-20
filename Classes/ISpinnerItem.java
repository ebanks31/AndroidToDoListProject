package com.example.android.todolist;

/**
 * Created by Eric on 4/11/2016.
 */
public interface ISpinnerItem {

    /**
     * Get Spinner Title
     *
     * @return returns Spinner Title
     */
    public String getTitle();

    /**
     * Sets Spinner Title
     *
     * @param title Sets Spinner Title
     */
    public void setTitle(String title);

    /**
     * Get position in spinner title list
     *
     * @return position in spinner title list
     */
    public int getPosition();

    /**
     * Set position in spinner title list
     *
     * @param position position in spinner title list
     */
    public void setPosition(int position);

    /**
     * Get unique ID for each Spinner Item
     *
     * @return unique ID for each Spinner Item
     */
    public int getId();


    /**
     * Set unique ID for each Spinner Item
     *
     * @param id unique ID for each Spinner Item
     */
    public void setId(int id);
}
