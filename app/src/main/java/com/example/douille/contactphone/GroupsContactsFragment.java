package com.example.douille.contactphone;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by douille on 11/30/16.
 */

public class GroupsContactsFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    // Defines a variable for the search string
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
    int loader_index;
    int max_loader_index;
    Map<Integer,RecyclerViewGroupAdapter> map;
    // An adapter that binds the result Cursor to the ListView
    private RecyclerViewGroupAdapter mGroupAdapter;
    private CursorAdapter mAdapter;
    HashMap<String,ArrayList<String>> groupList;

    public void GroupsContactsFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.group_list_fragment,
                container, false);
    }
    /*
* Defines an array that contains column names to move from
* the Cursor to the ListView.
*/

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            NestedArrayListHelper tmp = (NestedArrayListHelper)bundle.getSerializable("GroupsList");
          this.groupList = tmp.getList();
            this.max_loader_index =groupList.size() -1;
        }
        this.map = new HashMap<Integer, RecyclerViewGroupAdapter>();
        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Gets the ListView from the View list of the parent activity
//        mContactsList = (ListView)getView().findViewById(R.id.contact_list_item);
        Context context = getContext();
//        ViewGroup layout = (ViewGroup)getView().findViewById(R.id.contact_groups);
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.contact_groups);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        for (Map.Entry<String,ArrayList<String>>  group : groupList.entrySet()) {
            // Gets a CursorAdapter
            View inflated = this.getView().findViewById(R.id.group);
            View groupLayout = inflater.inflate(R.layout.recycler_view_group, (ViewGroup) inflated, false);
            TextView textView=new TextView(this.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            textView.setText(group.getKey());
            int index;
            if(loader_index != 0){
                index = loader_index -1;
            }else{
                index = 0;
            }
            layout.addView(textView);
            mContactsList = (RecyclerView) groupLayout.findViewById(R.id.contacts_group_recycler_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),6);
            gridLayoutManager.setOrientation(1);
            mContactsList.setLayoutManager(gridLayoutManager);
            mGroupAdapter = new RecyclerViewGroupAdapter(getContext(),null,group.getValue());
            mContactsList.setAdapter(mGroupAdapter);
            map.put(loader_index,mGroupAdapter);
            Bundle data = new Bundle();
            data.putStringArrayList("Group",group.getValue());
            layout.addView(groupLayout);
            getLoaderManager().initLoader(loader_index, data, this);
            loader_index++;
        }
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
//         */
        ArrayList<String> group = (ArrayList<String>)args.get("Group");
        String[] loader_selection_filter = new String[group.size()];
       loader_selection_filter  = group.toArray(loader_selection_filter);
//        String filter = " IN (" + loader_selection_filter. + ")"  ;
//        String selection = ContactsQuery.SELECTION_FRIEND.concat(filter.concat(ContactsQuery.SELECTION_CONTACT_CRITERIA));
        String selection = ContactsContract.Contacts.DISPLAY_NAME + " in (";
        for (int i = 0; i < loader_selection_filter.length; i++) {
            selection += "?, ";
        }
        selection = selection.substring(0, selection.length() - 2) + ")";
        selection = selection.concat(ContactsQuery.SELECTION_CONTACT_CRITERIA);
//        mSelectionArgs[0] = "%" + mSearchString + "%";
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
        int loader_id = loader.getId();
        if( loader_id <= max_loader_index ) {
            map.get(loader_id).swapCursor(data);
        }else{
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        int loader_id = loader.getId();
        if( loader_id <= max_loader_index ) {
            map.get(loader_id).swapCursor(null);
        }else{
            throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }

    }
}

