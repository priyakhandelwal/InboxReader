package com.example.prkhandelwal.inboxreader;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.Manifest;
import android.view.Window;
import android.provider.ContactsContract;

import android.os.Build;
import android.annotation.TargetApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

//import java.util.jar.Manifest;

public class SMSRead extends AppCompatActivity {

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  --> Why is it not removing launcher bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsread);

        int hasReadSMSPermission = checkSelfPermission(Manifest.permission.READ_SMS);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.form_layout);
        TextView textView = new TextView(this);
        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        if (hasReadSMSPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
            Log.i("Permission ---->", "Reached here");
        } else {
            Log.i("Permission granted", "yes");
        }

//        String phoneNumber = getPhoneNumber("AM-HOMCEN", this);
//        Log.i("phoneNumber : ", phoneNumber);

        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSMSURI, new String[]{"_id", "address", "date", "body", "read"}, "address like '%MAX%'", null, "date desc");

        String sms = "";


        while (cursor.moveToNext()) {
            String number = cursor.getString(cursor.getColumnIndex("address"));
            String address = cursor.getString(cursor.getColumnIndex("body"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Date d = new Date(Long.parseLong(date));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateFormatted =dateFormat.format(d);
            sms += "From : " + number + "\n" + "Date :" + dateFormatted + "\n" + "Message : " + address + "\n\n\n";
        }

        textView.setText(sms);
        relativeLayout.addView(textView);
//        setContentView(textView);
    }

    //    @TargetApi(23)
//    public String getPhoneNumber(String name, Context context) {
//        Log.i("getPhoneNumber:", "Reached here");
//        int hasReadContactPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
//        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
//        if (hasReadContactPermission != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
//        }
//        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
//        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
//        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                projection, selection, null, null);
//        String number = "";
//        if (c.moveToFirst()) {
//            number = c.getString(0);
//        }
//        c.close();
//        if (number != null) {
//            number = number.replace("-", "");
//            number = number.replaceFirst("^0+(?!$)", "");
//        }
//
//        return "+91" + number;
//    }
}
