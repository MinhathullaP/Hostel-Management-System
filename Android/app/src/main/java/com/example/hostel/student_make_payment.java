package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class student_make_payment extends AppCompatActivity implements JsonResponse{
    EditText e1,e2,card_no,e4,ed_exp_date;
    Button b1;
    String holder,cvv,exp_date,amount,bid;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_make_payment);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.et1);
        e2=(EditText)findViewById(R.id.et2);
        ed_exp_date=(EditText)findViewById(R.id.et3);
        e4=(EditText)findViewById(R.id.et4);
        card_no=(EditText)findViewById(R.id.etacc);

        b1=(Button)findViewById(R.id.btn1);
        Toast.makeText(getApplicationContext(),student_view_bill.bids, Toast.LENGTH_LONG).show();
        e4.setText(student_view_bill.amounts);
        e4.setEnabled(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder=e1.getText().toString();
                cvv=e2.getText().toString();
                exp_date=ed_exp_date.getText().toString();

                if (holder.equalsIgnoreCase("")){
                    e1.setError("Enter Card Holder Name");
                    e1.setFocusable(true);
                }
                else if (cvv.equalsIgnoreCase("") || cvv.length()!=3){
                    e2.setError("Enter CVV");
                    e2.setFocusable(true);
                }
                else if (exp_date.equalsIgnoreCase("")){
                    ed_exp_date.setError("Enter Exp Date");
                    ed_exp_date.setFocusable(true);
                }
                else if (card_no.length() != 16) {
                    card_no.setError("Enter Valid card number");
                    card_no.setFocusable(true);
                }
                else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) student_make_payment.this;
                    String q = "/student_make_payment?lid=" + sh.getString("log_id", "") + "&amount=" + student_view_bill.amounts + "&bid=" + student_view_bill.bids;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });

        ed_exp_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                exp_date = ed_exp_date.getText().toString();
                if (exp_date.length() == 2 && !exp_date.contains("/")) {
                    ed_exp_date.setText(exp_date + "/");
                    ed_exp_date.setSelection(ed_exp_date.getText().length());
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

                Toast.makeText(getApplicationContext(), "Payment Success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),student_home.class));

            }
            else if(status.equalsIgnoreCase("duplicate")){


                startActivity(new Intent(getApplicationContext(), student_view_bill.class));
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

            }

            else
            {
                startActivity(new Intent(getApplicationContext(),student_view_bill.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}