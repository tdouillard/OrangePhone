package com.example.douille.contactphone;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

/**
 * Created by douille on 12/6/16.
 */

public class RecyclerViewCursorViewHolder extends RecyclerView.ViewHolder{

    /**
     * Constructor.
     * @param view The root view of the ViewHolder.
     *
     */
    public Cursor cursor;
    public int contact_id;
    public final TextView contact_name;
    public final ImageView quickContact;
    public final LinearLayout background;
    public RecyclerViewCursorViewHolder(View view) {
        super(view);
        contact_name = (TextView) view.findViewById(R.id.contact_name);
        quickContact = (ImageView) view.findViewById(R.id.quickcontact);
        background = (LinearLayout) view.findViewById(R.id.LinearLayout);
    }

    /**
     * Binds the information from a Cursor to the various UI elements of the ViewHolder.
     * @param cursor A Cursor representation of the data to be displayed.
     */
    public void bindCursor(Cursor cursor){
       contact_name.setText(cursor.getString(ContactsQuery.DISPLAY_NAME_INDEX));
       contact_id = cursor.getInt(ContactsQuery.CONTACT_ID_INDEX);
    }

}