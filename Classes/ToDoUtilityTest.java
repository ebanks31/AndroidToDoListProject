package com.example.android.todolist.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.android.todolist.ListOrganizerActivity;
import com.example.android.todolist.utility.ToDoListUtility;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Eric on 12/16/2017.
 */
public class ToDoUtilityTest extends ActivityInstrumentationTestCase2<ListOrganizerActivity> {

    private ToDoListUtility toDoListUtility;

    public ToDoUtilityTest() {
        super( ListOrganizerActivity.class);
    }

    @BeforeClass
    public void setUp() {
        toDoListUtility = new ToDoListUtility();
    }

    @Test
    public void checkWhiteSpaceEmptyString() {
        assertFalse(ToDoListUtility.checkWhiteSpaces(StringUtils.EMPTY));
    }

    @Test
    public void checkWhiteSpaceNull() {
        assertFalse(ToDoListUtility.checkWhiteSpaces(null));
    }

    @Test
    public void checkWhiteSpaceValueWithNoWhiteSpaces() {
        assertFalse(ToDoListUtility.checkWhiteSpaces("test"));
    }

    @Test
    public void checkWhiteSpaceValueWithWhiteSpaces() {
        assertTrue(ToDoListUtility.checkWhiteSpaces("test test"));
    }

    @Test
    public void checkWhiteSpaceValueWithOneSpace() {
        assertTrue(ToDoListUtility.checkWhiteSpaces(" "));
    }

    @Test
    public void checkWhiteSpaceValueWithMultipleSpaces() {
        assertTrue(ToDoListUtility.checkWhiteSpaces("   "));
    }

    @Test
    public void checkWhiteSpaceValueWithMultipleSpacesWithWords() {
        assertTrue(ToDoListUtility.checkWhiteSpaces("The fox will catch the hound."));
    }

    @Test
    public void checkValidCharactersValidCharacters() {
        assertTrue(ToDoListUtility.checkValidCharacters("Hello"));
    }

    @Test
    public void checkValidCharactersWithNumber() {
        assertTrue(ToDoListUtility.checkValidCharacters("123456"));
    }

    @Test
    public void checkValidCharactersWithSpaces() {
        assertTrue(ToDoListUtility.checkValidCharacters(" "));
    }

    @Test
    public void checkValidCharactersWithMultipleSpaces() {
        assertTrue(ToDoListUtility.checkValidCharacters("    "));
    }

    @Test
    public void checkValidCharactersWithSpaceAtFirstCharacter() {
        assertTrue(ToDoListUtility.checkValidCharacters(" a"));
    }

    @Test
    public void checkValidCharactersWithEmptyString() {
        assertTrue(ToDoListUtility.checkValidCharacters(StringUtils.EMPTY));
    }

    @Test
    public void checkValidCharactersWithNull() {
        assertTrue(ToDoListUtility.checkValidCharacters(null));
    }
}
