package com.example.android.todolist;

/**
 * Created by Eric Banks on 6/28/2015.
 */
import android.app.Activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;

public class themeUtils extends ActionBarActivity

{

    private static int cTheme;


    public final static int BLUE = 0;
    public final static int BLACK = 1;





    public static void changeToTheme(Activity activity, int theme)

    {

        cTheme = theme;

        activity.finish();



        activity.startActivity(new Intent(activity, activity.getClass()));


    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {
//Change to ActionBar v7
            default:
                activity.setTheme(R.style.AppBaseTheme);
                //activity=(ActionBarActivity) Context.getApplicationContext();

                ActionBar actionBar =activity.getActionBar();


                actionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayShowTitleEnabled(true);
            case BLUE:

                activity.setTheme(R.style.AppBaseTheme);
                ActionBar actionBar1 =activity.getActionBar();


                actionBar1.setBackgroundDrawable(new ColorDrawable(0xff006699));
                actionBar1.setDisplayShowTitleEnabled(false);
                actionBar1.setDisplayShowTitleEnabled(true);
                break;

            case BLACK:

                activity.setTheme(R.style.AppBaseTheme1);
                ActionBar actionBar2 =activity.getActionBar();


                actionBar2.setBackgroundDrawable(new ColorDrawable(0xFFFF6666));
                actionBar2.setDisplayShowTitleEnabled(false);
                actionBar2.setDisplayShowTitleEnabled(true);
                break;


        }

    }

}
