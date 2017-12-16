package com.example.android.todolist;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This is utility class to hold common methods for the To Do List Applications.
 */
public class ToDoListUtility {
    /**
     * Regular expression that checks for white spaces.
     */
    private static final String WHITE_SPACES_REG_EX = "^\\s";

    /**
     * Regular expression that checks for invalid character.
     * Check if character's length is at least 20 characters and must begin with a letter or number.
     */
    private static final String INVALID_CHARS_REG_EX = "^(?=[^\\s_])\\w(\\w|[!@#$%\\s]){0,20}$";

    /**
     * This method checks for whitespaces given a string.
     *
     * @param listInput string that will be checked for whitespaces.
     * @return true if string can invalid character, false it if doesn't contain whitespaces.
     */
    public static Boolean checkWhiteSpaces(final String listInput) {
        if (listInput == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(WHITE_SPACES_REG_EX);
        Matcher matcher = pattern.matcher(listInput);
        return matcher.find();
    }

    /**
     * This method checks for invalid character given a string.
     * Valid characters must start with a number or character and must be at minimum 20 characters.
     *
     * @param listInput string that will be checked for invalid characters.
     * @return true if string can invalid character, false it if doesn't contain invalid characters.
     */
    public static Boolean checkValidCharacters(final String listInput) {
        if (listInput == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(INVALID_CHARS_REG_EX);
        Matcher matcher = pattern.matcher(listInput);
        return matcher.find();
    }


    /**
     * Iterates through an ArrayList of Strings and looks for a current string.
     * If string is found, string is replaced by string that is mentioned in parameter.
     *
     * @param stringToBeReplaced         String that will be replaced
     * @param replaceString String that replace string mentioned in parameter
     * @param stringValues  String array of list items
     * @return String array with replaced value
     */
    public String[] replaceStringInArray(final String stringToBeReplaced, final  String replaceString,
                                         final String[] stringValues) {
        if (stringValues == null || stringToBeReplaced == null
                || replaceString == null) {
            return ArrayUtils.toArray();
        }

        String[] resultArray = new String[stringValues.length];

        for (int i = 0; i < stringValues.length; i++) {
            resultArray[i] = stringValues[i];

            if (stringValues[i].equals(stringToBeReplaced)) {
                resultArray[i] = stringToBeReplaced;
            }

        }

        return resultArray;

    }

    /**
     * Gets List Items by Spinner Title.
     *
     * @param itemListByTitle List of ListItems
     * @return List of String containing list items by spinner title.
     */
    public static List<String> getListItems(final List<ListItem> itemListByTitle) {
        if (CollectionUtils.isEmpty(itemListByTitle)) {
            return Collections.emptyList();
        }

        final ArrayList<String> finalList = new ArrayList<String>();

        for (ListItem listitem : itemListByTitle) {
            finalList.add(listitem.getListItem());
        }
        return finalList;
    }


    /**
     * Get List Item Objects by spinner title.
     *
     * @param itemList List of List Item Objects
     * @param title    selected Spinner title
     * @return List List of string containing List Items by spinner title
     */
    public List<String> getListTitleFromListItems(final List<ListItem> itemList,
                                                  final String title) {
        final ArrayList<String> finalList = new ArrayList<String>();

        if (CollectionUtils.isEmpty(itemList) || StringUtils.isBlank(title)) {
            return Collections.emptyList();
        }

        for (ListItem listItem : itemList) {

            String currentTitle = listItem.getTitle();

            if (currentTitle != null && currentTitle.equals(title)) {
                finalList.add(listItem.getListItem());
            }
        }

        return finalList;
    }


    /**
     * Update array list and swap current item with new item.
     *
     * @param stringArray the string array of list items.
     * @param currentItem the current urrent list item
     * @param newItem     New item that will replace current list item
     * @return String array containing new list item swap with the current list item
     */
    public String[] updateArray(final String[] stringArray,
                                final String currentItem, final String newItem) {
        if (stringArray == null || currentItem == null || newItem == null) {
            return ArrayUtils.toArray();
        }
        String[] finalArray = new String[stringArray.length];

        for (int i = 0; i < stringArray.length - 1; i++) {
            finalArray[i] = stringArray[i];

            if (stringArray[i].equals(currentItem)) {
                finalArray[i] = newItem;
            }

        }

        return finalArray;

    }

    /**
     * Delete a specified item in ArrayList if item is found.
     *
     * @param stringArrayList ArrayList of string containing list items
     * @param currentItem     Contains current list item
     * @return String array with list item deleted from parameter
     */
    public String[] deleteItemFromArray(final List<String> stringArrayList,
                                        final String currentItem) {
        if (stringArrayList == null || currentItem == null) {
            return ArrayUtils.toArray();
        }

        List<String> finalList = new ArrayList<String>();

        for (int i = 0; i < stringArrayList.size() - 1; i++) {

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
    public List<String> getSpinnerTitles(final List<ListItem> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return Collections.emptyList();
        }

        ArrayList<String> list = new ArrayList<String>();

        try {
            for (ListItem listItem : itemList) {
                list.add(listItem.getTitle());

            }
            return list;
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred", ex);
        } catch (NullPointerException ex) {
            Log.e("Null", "Null Pointer Exception Error", ex);
        }

        return Collections.emptyList();
    }

    /**
     * Removes duplicates from an ArrayList of strings.
     *
     * @param list ArrayList of String
     * @return ArrayList of String with duplicates removed
     */
    public List<String> removeDuplicates(final List<String> list) {

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            HashSet<String> hs = new HashSet<String>();
            hs.addAll(list);
            list.clear();
            list.addAll(hs);
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.e("ArrayIOO", "Array Index Out of Bound Exception has occurred", ex);
        } catch (NullPointerException ex) {
            Log.e("Null", "Null Pointer Exception Error", ex);
        }
        return list;
    }

    /**
     * Check for duplicates in an ArrayList of String.
     *
     * @param spinnerTitleList ArrayList of String containing spinner titles
     * @param spinnerTitle     spinner title
     * @return ArrayList of String containing spinner titles with duplicates removed
     */
    public static boolean checkDuplicates(final List<String> spinnerTitleList,
                                          final String spinnerTitle) {
        if (CollectionUtils.isNotEmpty(spinnerTitleList)
                || spinnerTitle == null) {
            return false;
        }
        for (String listItem : spinnerTitleList) {
            if (listItem.equals(spinnerTitle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds Single Quote to the beginning and end of string.
     *
     * @param listItemString String from list item
     * @return String with single quote at beginning and the end of string
     */
    public String addSingleQuoteFrontAndBack(final String listItemString) {
        if (StringUtils.isEmpty(listItemString)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder(listItemString);
        stringBuilder.insert(0, "\'");
        stringBuilder.insert(listItemString.length() + 1, "\'");
        return stringBuilder.toString();
    }

    /**
     * Removes Duplicates from ArrayList of strings
     *
     * @param stringList Array List of string containing spinner titles
     * @return ArrayList of strings with duplicates removed
     */
    public ArrayList<String> removeDuplicates(final ArrayList<String> stringList) {
        HashSet hs = new HashSet();
        hs.addAll(stringList);
        stringList.clear();
        stringList.addAll(hs);
        return stringList;
    }

}
