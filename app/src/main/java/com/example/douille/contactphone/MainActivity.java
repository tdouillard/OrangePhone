package com.example.douille.contactphone;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends  AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1000;
    FragmentManager fragmentManager;
    ContactsFragment contactsFragment;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    ViewPager viewPager;
    Toolbar mainToolbar;
    TabLayout mainTabLayout;
//    ArrayList<ArrayList<String>> groups_list;
    HashMap<String,ArrayList<String>> groups_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groups_list = new HashMap<String,ArrayList<String>>();
        groups_list.put("Family",ContactsQuery.family_array_list);
        groups_list.put("Work",ContactsQuery.coworker_array_list);
        groups_list.put("Friends",ContactsQuery.friend_array_list);

//        groups_list= new ArrayList<ArrayList<String>>();
//        groups_list.add(ContactsQuery.family_array_list);
//        groups_list.add(ContactsQuery.coworker_array_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orange phone");
        this.fragmentManager= getSupportFragmentManager();
        this.contactsFragment = new ContactsFragment();
        this.fragmentTransaction = this.fragmentManager.beginTransaction();
        viewPager = (ViewPager)findViewById(R.id.main_activity_content);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Groups");
        tabLayout.getTabAt(1).setText("Contacts");
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        checkPermission();
    }

    /**
     * create tabs
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.fragmentManager);
        Bundle bundle = new Bundle();
        bundle.putSerializable("GroupsList", new NestedArrayListHelper(groups_list));
        GroupsContactsFragment groupsContactsFragment = new GroupsContactsFragment();
        groupsContactsFragment.setArguments(bundle);
        adapter.AddFragment(groupsContactsFragment,"Groups");
        adapter.AddFragment(new ContactsFragment(),"Contacts");

        viewPager.setAdapter(adapter);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.category, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners...

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SwapFragment(item);
        return true;
    }

    public void SwapFragment(MenuItem item){
        int action_id = item.getItemId();
        switch (action_id){
            case R.id.add_contact:
                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivityForResult(contactIntent, 10001);
                break;
            case R.id.add_group:
                //Intent groupIntent = new Intent(this,AddGroupActivity.class);
                //Bundle extras= new Bundle();
                //extras.putSerializable("serialisable_List", new NestedArrayListHelper(groups_list));
                //groupIntent.putExtra("GroupsList",extras);
                //startActivityForResult(groupIntent, 1001);
                break;
//            case R.id.action_settings:
////                this.fragmentTransaction.replace(R.id.main_activity,new());
////                this.fragmentTransaction.commit();
//                break;
        }
        return;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {
                // recreate your fragment here
                setupViewPager(viewPager);
            }
            if((requestCode == 10001) && (resultCode == Activity.RESULT_OK)){
                setupViewPager(viewPager);
            }
    }
        @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        Log.d("test", "permission received");
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                this.fragmentTransaction.add(R.id.main_activity,contactsFragment);
                this.fragmentTransaction.commit();
                return;
            }
        }
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE){
            TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            mngr.getDeviceId();
        }
        Log.d("test", "permission requestCode different");

    }

    public void checkPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                Log.d("test", "permission asked");

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            this.fragmentTransaction.add(R.id.main_activity_content,this.contactsFragment);
            this.fragmentTransaction.commit();
        }

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                Log.d("test", "permission asked");

            }
        }else{
            TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = mngr.getDeviceId();
            Log.w("test",deviceid);
        }
    }
}
