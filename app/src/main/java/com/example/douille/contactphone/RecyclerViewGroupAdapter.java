package com.example.douille.contactphone;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by douille on 12/8/16.
 */

public class RecyclerViewGroupAdapter extends RecyclerView.Adapter<RecyclerViewGroupViewHolder>{
    CursorAdapter mCursorAdapter;
    RecyclerViewGroupViewHolder myViewHolder;
    ArrayList<String> contactList;
    Context mContext;
    Cursor mCursor;
    public RecyclerViewGroupAdapter(Context context, Cursor c, ArrayList<String> contact_list) {
        //todo: check list not null
        mContext = context;
        contactList = contact_list;
        mCursor = c;
//        c.moveToPosition(0);
//        String contact_name = mCursorAdapter.getCursor().getString(ContactsQuery.DISPLAY_NAME_INDEX);
//     if(contactList != null && contactList.contains(contact_name)) {
//            final LayoutInflater layout_inflater_given = inflater;
         mCursorAdapter = new CursorAdapter(mContext, c, 0) {
             @Override
             public View newView(Context context, Cursor cursor, ViewGroup parent) {
//                    View view;
//                    LayoutInflater layoutInflater;
//                    if(Arrays.asList(ContactsQuery.familiy_list).contains(contact_name)){
////                       layoutInflater = layout_inflater_given;
//                    }else {
//                        layoutInflater = LayoutInflater.from(context);
                 return LayoutInflater.from(context).inflate(R.layout.group_contact_item, parent, false);

             }

             @Override
             public void bindView(View view, Context context, Cursor cursor) {
                 // Binding operations
                 //load picture from contact
//                    final Uri contactUri = ContactsContract.Contacts.getLookupUri(
//                            cursor.getLong(ContactsQuery.CONTACT_ID_INDEX),
//                            cursor.getString(ContactsQuery.LOOKUP_KEY_INDEX));
//                    Picasso.with(context).load(contactUri).transform(new RoundedCornersTransformation(10, 10)).into(myViewHolder.quickContact);
                 String contact_name = cursor.getString(ContactsQuery.DISPLAY_NAME_INDEX);
                 Log.v("contact_name",contact_name);
                 if (contact_name.length() > 0) {
//                     myViewHolder.quickContact.setImageBitmap(generateCircleBitmap(context, getMaterialColor(contact_name), (float) 40, contact_name.substring(0, 1)));
                     TextDrawable drawable = TextDrawable.builder().buildRect(contact_name.substring(0, 1), getMaterialColor(contact_name));
                     myViewHolder.quickContact.setImageDrawable(drawable);
                 }

                 myViewHolder.bindCursor(cursor);
             }
         };
//     }
    }
    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }


    @Override
    public RecyclerViewGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewGroupViewHolder(mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent));
    }

    public void swapCursor(Cursor cursor) {
        this.mCursorAdapter.swapCursor(cursor);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerViewGroupViewHolder holder, int position) {
        // Passing the inflater job to the cursor-adapter
        // Move cursor to this position
        mCursorAdapter.getCursor().moveToPosition(position);
        this.myViewHolder =  holder;
        // Bind this view
            mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(holder.contact_id));
                    intent.setData(uri);
                    v.getContext().startActivity(intent);
                }
            });


    }
//
//    public static Bitmap generateCircleBitmap(Context context, int circleColor, float diameterDP, String text){
//        final int textColor = 0xffffffff;
//
//        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
//        float diameterPixels = diameterDP * (metrics.densityDpi / 160f);
//        float radiusPixels = diameterPixels/2;
//
//        // Create the bitmap
//        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels,
//                Bitmap.Config.ARGB_8888);
//
//        // Create the canvas to draw on
//        Canvas canvas = new Canvas(output);
//        canvas.drawARGB(0, 0, 0, 0);
//
//        // Draw the circle
//        final Paint paintC = new Paint();
//        paintC.setAntiAlias(true);
//        paintC.setColor(circleColor);
//        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC);
//
//        // Draw the text
//        if (text != null && text.length() > 0) {
//            final Paint paintT = new Paint();
//            paintT.setColor(textColor);
//            paintT.setAntiAlias(true);
//            paintT.setTextSize(radiusPixels * 2);
////            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Thin.ttf");
////            paintT.setTypeface(typeFace);
//            final Rect textBounds = new Rect();
//            paintT.getTextBounds(text, 0, text.length(), textBounds);Color
//            canvas.drawText(text, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT);
//        }
//
//        return output;
//    }
    private static List<Integer> materialColors = Arrays.asList(
            0xffe57373,
            0xfff06292,
            0xffba68c8,
            0xff9575cd,
            0xff7986cb,
            0xff64b5f6,
            0xff4fc3f7,
            0xff4dd0e1,
            0xff4db6ac,
            0xff81c784,
            0xffaed581,
            0xffff8a65,
            0xffd4e157,
            0xffffd54f,
            0xffffb74d,
            0xffa1887f,
            0xff90a4ae
    );
    public static int getMaterialColor(Object key) {
        return materialColors.get(Math.abs(key.hashCode()) % materialColors.size());
    }
}
