package com.example.douille.contactphone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
/**
 * Created by douille on 12/8/16.
 */

public class introActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1000;
    FragmentManager fragmentManager;
    ContactsFragment contactsFragment;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    ViewPager viewPager;
    Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_selection_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar); // Attaching the layout to the toolbar object
//        setSupportActionBar(toolbar);
        this.fragmentManager = getSupportFragmentManager();
        this.contactsFragment = new ContactsFragment();
        Bundle data = new Bundle();
        data.putString("selection", ContactsQuery.SELECTION);
        this.fragmentTransaction = this.fragmentManager.beginTransaction();//toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        checkPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {
            // recreate fragment here
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("test", "permission received");
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("test", "permission granted");
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG);
                this.fragmentTransaction.add(R.id.main_activity, contactsFragment);
                this.fragmentTransaction.commit();
                return;
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            mngr.getDeviceId();
        }
        Log.d("test", "permission requestCode different");

    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                //request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                Log.d("test", "permission asked");

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            this.fragmentTransaction.add(R.id.main_activity_content, this.contactsFragment);
            this.fragmentTransaction.commit();
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {

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
        } else {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = mngr.getDeviceId();
            Log.w("test", deviceid);
        }
    }
}
