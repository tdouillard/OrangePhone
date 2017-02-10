package com.example.douille.contactphone;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by douille on 12/9/16.
 */

public class AddGroupContactFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private String mSearchString;
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = {mSearchString};
    // Define global mutable variables
    // Define a ListView object
    RecyclerView mContactsList;
    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private RecyclerViewCursorAdapter mCursorAdapter;
    private RecyclerViewAddGroup mAdapter;
    HashMap<String,ArrayList<String>> group_list;
    ArrayList<String> selection_list_parameters;
    // Empty public constructor, required by the system
    public AddGroupContactFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            NestedArrayListHelper tmp = (NestedArrayListHelper)bundle.getSerializable("serialisable_List");
            this.group_list = tmp.getList();
            selection_list_parameters =  new ArrayList<String>();
            for (Map.Entry<String,ArrayList<String>>  group : group_list.entrySet()) {
                selection_list_parameters.addAll(group.getValue());
            }
        }
        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
    }

    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.add_group_activity,
                container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Gets the ListView from the View list of the parent activity
//        mContactsList = (ListView)getView().findViewById(R.id.contact_list_item);
        mContactsList = (RecyclerView) getView().findViewById(R.id.add_contacts_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContactsList.setLayoutManager(linearLayoutManager);
        mCursorAdapter = new RecyclerViewCursorAdapter(getContext(), null);
        // Gets a CursorAdapter
//        mCursorAdapter = new SimpleCursorAdapter(;
//                getActivity(),
//                R.layout.contacts_list_item,
//                null,
//                FROM_COLUMNS, TO_IDS,
//                0);
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);

        // Initializes the loader
        getLoaderManager().initLoader(ContactsQuery.CONTACT_LOADER, null, this);

    }


    @Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {
        // Get the Cursor
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        // Get the _ID value
        mContactId = cursor.getLong(ContactsQuery.CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        mContactKey = cursor.getString(ContactsQuery.LOOKUP_KEY_INDEX);
        // Create the contact's content Uri
        mContactUri = ContactsContract.Contacts.getLookupUri(mContactId, mContactKey);

//        mAdapter.toggleSelection(position);
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        String[] loader_selection_filter = new String[selection_list_parameters.size()];
        loader_selection_filter  = selection_list_parameters.toArray(loader_selection_filter);
//        String filter = " IN (" + loader_selection_filter. + ")"  ;
//        String selection = ContactsQuery.SELECTION_FRIEND.concat(filter.concat(ContactsQuery.SELECTION_CONTACT_CRITERIA));
        String selection = ContactsContract.Contacts.DISPLAY_NAME + " NOT in (";
        for (int i = 0; i < loader_selection_filter.length; i++) {
            selection += "?, ";
        }
//        String filter = " IN (" + loader_selection_filter. + ")"  ;
//        String selection = ContactsQuery.SELECTION_FRIEND.concat(filter.concat(ContactsQuery.SELECTION_CONTACT_CRITERIA));
        selection = selection.substring(0, selection.length() - 2) + ")";
        selection = selection.concat(ContactsQuery.SELECTION_CONTACT_CRITERIA);
        //mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                ContactsQuery.PROJECTION,
                selection,
                loader_selection_filter,
                ContactsQuery.SORT_ORDER
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case ContactsQuery.CONTACT_LOADER:
                mCursorAdapter.swapCursor(data);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case ContactsQuery.CONTACT_LOADER:
                mCursorAdapter.swapCursor(null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }

    }
}
