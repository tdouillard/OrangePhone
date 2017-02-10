package com.example.douille.contactphone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by douille on 12/9/16.
 */

public class AddGroupActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1000;
    FragmentManager fragmentManager;
    ContactsFragment addGroupContactFragment;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    ViewPager viewPager;
    Toolbar mainToolbar;
    TabLayout mainTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_activity);
        Bundle extras = getIntent().getBundleExtra("GroupsList");
        NestedArrayListHelper tmp = (NestedArrayListHelper)extras.getSerializable("serialisable_List");
        HashMap<String, ArrayList<String>> test= tmp.getList();
        this.fragmentManager= getSupportFragmentManager();
        AddGroupContactFragment addGroupContactFragment = new AddGroupContactFragment();
        addGroupContactFragment.setArguments(extras);
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        this.fragmentTransaction.add(R.id.add_group,addGroupContactFragment);
        this.fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.group, menu);
        // Configure the search info and add any event listeners...

        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {
            // recreate your fragment here

        }
    }
}
