package com.example.douille.contactphone;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by douille on 12/6/16.
 */

public interface ContactsQuery {

    // An identifier for the loader
       /*
 * Defines an array that contains column names to move from
 * the Cursor to the ListView.
 */

    final static String family_list= "Maxence,Etienne";
    final static ArrayList<String> family_array_list= new ArrayList<>(Arrays.asList("Maxence","Etienne","Maman","Pierre","Flora","Clarisse GUILLOTEL"));
    final static ArrayList<String> coworker_array_list= new ArrayList<>(Arrays.asList("Mathieu Delmaire","Romain","Charles","Maxime Buguel","Quentin"));
    final static ArrayList<String> friend_array_list = new ArrayList<>(Arrays.asList("Thomas WIDMAIER","Tim","Palfrey","Noémie","LouLou","Ciaron","France","Gael","Hugues"));

    final int CONTACT_LOADER = 0;
    @SuppressLint("InlinedApi")
    final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    // columns requested from the database
    @SuppressLint("InlinedApi")
   static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.HAS_PHONE_NUMBER : ContactsContract.Contacts.HAS_PHONE_NUMBER,

            };

    // Defines the text expression
    static final String SELECTION_CONTACT_CRITERIA =" AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1 AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1" ;
    @SuppressLint("InlinedApi")
    static final String SELECTION =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME) +
                    "<>''" + " AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1 AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1" ;

    static final String SELECTION_FRIEND =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME)+ " LIKE ? " ;
    final static String SORT_ORDER =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.SORT_KEY_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME;


    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    final static int[] TO_IDS = {
            android.R.id.text1
    };

    // The query column numbers which map to each value in the projection
    final static int CONTACT_ID_INDEX = 0;
    final static int LOOKUP_KEY_INDEX = 1;
    final static int DISPLAY_NAME_INDEX = 2;
    final static int PHOTO_THUMBNAIL_DATA_INDEX = 3;
    final static int SORT_KEY_INDEX = 4;


}