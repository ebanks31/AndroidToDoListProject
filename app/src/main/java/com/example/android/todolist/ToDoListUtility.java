package com.example.android.todolist;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eric on 4/22/2015.
 */
public class ToDoListUtility {



    /**
     * This method checks for whitespaces given a string
     *
     *
     * @param listInput string that will be checked for whitespaces.
     * @return true if string can invalid character, false it if doesn't contain whitespaces.
     */
    public static Boolean checkWhiteSpaces(String listInput)
    {
        Pattern pattern = Pattern.compile("^\\s");
        Matcher matcher = pattern.matcher(listInput);
        boolean found = matcher.find();
        return found;
    }





    /**
     * This method checks for invalid character given a string
     *
     *
     * @param listInput string that will be checked for invalid characters.
     * @return true if string can invalid character, false it if doesn't contain invalid characters.
     */
    public static Boolean checkValidCharacters(String listInput)
    {
        Pattern pattern = Pattern.compile("^(?=[^\\s_])\\w(\\w|[!@#$%\\s]){0,20}$");
        Matcher matcher = pattern.matcher(listInput);
        boolean found = matcher.find();
        return found;
    }



    /**
     *
     * Iterates through an ArrayList of Strings and looks for a current string.
     * If string is found, string is replaced by string that is mentioned in parameter.
     *
     * @param input String that will be replaced
     * @param replaceString String that replace string mentioned in parameter
     * @param stringValues String array of list items
     * @return String array with replaced value
     */
    public String[] replaceString(String input, String replaceString, String[] stringValues)
    {
        String [] resultArray = new String[stringValues.length];

        for (int i = 0; i < stringValues.length; i++)
        {
            resultArray[i] = stringValues[i];

            if (stringValues[i].equals(input)) {
                resultArray[i] = input;
            }

        }

        return resultArray;

    }

    /**
     *
     * Gets List Items by Spinner Title
     *
     * @param itemListByTitle List of ListItems
     * @return ArrayList of String containing list items by spinner title.
     */
    public static ArrayList<String> getListItems(final List<ListItem> itemListByTitle)
    {

        final ArrayList<String> finalList = new ArrayList<String>();

        Runnable runnable = new Runnable() { //Run in separate thread
            public void run() {
                for (ListItem listitem: itemListByTitle)
                {
                    finalList.add(listitem.getListItem());
                }
            }
        };

        Thread myThread = new Thread(runnable);
        myThread.start();
        return finalList;
    }


    /**
     *
     * Get List Item Objects by spinner title
     *
     * @param itemList List of List Item Objects
     * @param title selected Spinner title
     * @return List ArrayList of string containing List Items by spinner title
     */
    public ArrayList<String> getListTitleFromListItems(final List<ListItem> itemList, final String title)
    {
        final ArrayList<String> finalList = new ArrayList<String>();

        Runnable runnable = new Runnable() { //Run in separate thread
            public void run() {


                for (ListItem listItem: itemList) {

                    String currentTitle = listItem.getTitle();

                    if (currentTitle != null)
                    {
                        if (currentTitle.equals(title))
                        {
                            finalList.add(listItem.getListItem());
                        }
                    }
                }



            }
        };
        Thread myThread = new Thread(runnable);
        myThread.start();

        return finalList;
    }


    /**
     *
     * Update array list and swap current item with new item.
     *
     * @param stringArray String array of list items.
     * @param currentItem Current list item
     * @param newItem New item that will replace current list item
     * @return String array containing new list item swap with thte current list item
     */
    public String[] updateArray(String[] stringArray, String currentItem, String newItem)
    {
        String [] finalArray = new String[stringArray.length];

        for (int i = 0; i < stringArray.length-1; i++)
        {
            finalArray[i] = stringArray[i];

            if (stringArray[i].equals(currentItem)) {
                finalArray[i] = newItem;
            }

        }

        return finalArray;

    }


    /**
     *
     * Delete a specified item in ArrayList if item is found
     *
     * @param stringArrayList ArrayList of string containing list items
     * @param currentItem Contains current list item
     * @return  String array with list item deleted from parameter
     */
    public String[] deleteItemFromArray(ArrayList<String> stringArrayList, String currentItem)
    {
        ArrayList<String> finalList = new ArrayList<String>();

        for (int i=0; i < stringArrayList.size()-1; i++)
        {

            if (!stringArrayList.get(i).equals(currentItem)) {
                finalList.add(stringArrayList.get(i));
            }

        }

        String[] finalArray = new String[finalList.size()];
        finalArray = finalList.toArray(finalArray);
        return finalArray;

    }



    /**
     * Gets the spinner titles from List of ListItem objects.
     *
     * @param itemList List of Listitem objects containing title and list item
     * @return ArrayList of string containing spinner titles
     */
    public ArrayList<String> getSpinnerTitles(final List<ListItem> itemList)
    {
        ArrayList<String> list = new ArrayList<String>();

        try
        {
            for (ListItem listItem: itemList)
            {
                list.add(listItem.getTitle());

            }
            return list;
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
            Log.e ( "ArrayIOO", "Array Index Out of Bound Exception has occurred", ex );                  //Log error for Array Out of Bounds Exception
        }
        catch (NullPointerException ex)
        {
            System.out.println("NullPointer Exception has occurred" + ex.getMessage());
            Log.e("Null", "Null Pointer Exception Error", ex);                  //Log error for Null Pointer Exception

        }
        return null;
    }



    /**
     * Removes Duplicates from ArrayList of strings
     *
     * @param stringList Array List of string containing spinner titles
     * @return  ArrayList of strings with duplicates removed

    public ArrayList<String> removeDuplicates(ArrayList<String> stringList)
    {
        HashSet hs = new HashSet();
        hs.addAll(stringList);
        stringList.clear();
        stringList.addAll(hs);
        return stringList;
    }
     */


    /**
     * Removes duplicates from an ArrayList of strings.
     *
     * @param list ArrayList of String that contains a list of spinner items
     * @return ArrayList of String containing spinner items with duplicates removed
     */
    public ArrayList<String> removeDuplicates(ArrayList<String> list) {
        list = new ArrayList<String>();
        try{
            HashSet<String> hs = new HashSet<String>();
            hs.addAll(list);
            list.clear();
            list.addAll(hs);
        }

        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Array Index Out Of Bounds Exception has occurred" + ex.getMessage());
            Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred",ex);                  //Log error for Array Out of Bounds Exception
        }
        catch (NullPointerException ex)
        {
            System.out.println("NullPointer Exception has occurred" + ex.getMessage());
            Log.e("Null", "Null Pointer Exception Error",ex);                  //Log error for Null Pointer Exception

        }
        return list;
    }


    /**
     *
     * Check for duplicates in an ArrayList of String
     *
     * @param arrayList ArrayList of String containing spinner titles
     * @param input spinner title
     * @return ArrayList of String containing spinner titles with duplicates removed
     */
    public boolean checkDuplicates(ArrayList<String> arrayList, String input)
    {
        for (String listItem: arrayList)
        {
            if (listItem.equals(input))
            {
                return true;
            }
        }
        return false;

    }

}
