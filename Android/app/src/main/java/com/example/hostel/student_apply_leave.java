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

public class student_apply_leave extends AppCompatActivity implements JsonResponse{
    EditText e1,e2;
    Button b1;
    String reason,date;
    SharedPreferences sh;
    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_apply_leave);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.et1);
        e2=(EditText)findViewById(R.id.et2);
        b1=(Button)findViewById(R.id.btn1);

        e2.setInputType(InputType.TYPE_NULL);
        e2.requestFocus();
        setDateTimeField();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reason=e1.getText().toString();
                date=e2.getText().toString();
                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) student_apply_leave.this;
                String q="/student_apply_leave?lid="+sh.getString("log_id","")+"&reason="+reason+"&date="+date;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });

    }

    private void setDateTimeField() {
        e2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                fromDatePickerDialog.show();
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog =new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                e2.setText(dateFormatter.format(newDate.getTime()));
//                bdate=e2.getText().toString();

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){

                Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),student_home.class));

            }
            else if(status.equalsIgnoreCase("duplicate")){


                startActivity(new Intent(getApplicationContext(), student_apply_leave.class));
                Toast.makeText(getApplicationContext(), "Leave already applied", Toast.LENGTH_LONG).show();

            }

            else
            {
                startActivity(new Intent(getApplicationContext(),student_apply_leave.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
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