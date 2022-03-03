package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Change_password extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3;
    Button b1;
    String old,newpass,confirm;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText)findViewById(R.id.etpass);
        e2=(EditText)findViewById(R.id.etnew);
        e3=(EditText)findViewById(R.id.etconfirm);

        b1=(Button)findViewById(R.id.btsave);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                old = e1.getText().toString();
                newpass = e2.getText().toString();
                confirm = e3.getText().toString();

                if (old.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Old Password");
                    e1.setFocusable(true);
                } else if (newpass.equalsIgnoreCase("")) {
                    e2.setError("Enter Your New Password");
                    e2.setFocusable(true);
                } else if (confirm.equalsIgnoreCase("") || !confirm.equals(newpass)) {
                    e3.setError("Confirm Password Does't Match");
                    e3.setFocusable(true);
                } else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Change_password.this;
                    String q = "/Change_password?lid=" + sh.getString("log_id", "") + "&confirm=" + confirm + "&newpass=" + newpass;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });

    }


    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){

                Toast.makeText(getApplicationContext(), "Success. You Have To Login Again..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),login.class));

            }

            else
            {
                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Change_password.class));


            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),student_home.class);
        startActivity(b);
    }
}