package com.example.douille.contactphone;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by douille on 12/8/16.
 */

public class RecyclerViewGroupViewHolder extends RecyclerView.ViewHolder {
    /**
     * Constructor.
     * @param view The root view of the ViewHolder.
     *
     */
    public Cursor cursor;
    public int contact_id;
    public final ImageView quickContact;
    public RecyclerViewGroupViewHolder(View view) {
        super(view);
        quickContact = (ImageView) view.findViewById(R.id.quickcontact);
    }

    /**
     * Binds the information from a Cursor to the various UI elements of the ViewHolder.
     * @param cursor A Cursor representation of the data to be displayed.
     */
    public void bindCursor(Cursor cursor){
        contact_id = cursor.getInt(ContactsQuery.CONTACT_ID_INDEX);
    }
}
