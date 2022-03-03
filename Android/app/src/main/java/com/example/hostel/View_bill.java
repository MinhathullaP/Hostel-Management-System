package com.example.hostel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class View_bill extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        t1=(TextView) findViewById(R.id.tvname);
        t2=(TextView) findViewById(R.id.tvdate);
        t3=(TextView) findViewById(R.id.tvnop);
        t4=(TextView) findViewById(R.id.tvnol);
        t5=(TextView) findViewById(R.id.tvroom);
        t6=(TextView) findViewById(R.id.tvmess);
        t7=(TextView) findViewById(R.id.tvtotal);

        t1.setText(student_view_bill.snames);
        t2.setText(student_view_bill.dates);
        t3.setText(student_view_bill.no_of_presents);
        t4.setText(student_view_bill.no_of_leaves);
        t5.setText(student_view_bill.proom_rents);
        t6.setText(student_view_bill.pmess_rents);
        t7.setText(student_view_bill.amounts);



//        JsonReq JR=new JsonReq();
//        JR.json_response=(JsonResponse) View_bill.this;
//        String q="/View_bill?bids="+student_view_bill.bids;
//        q=q.replace(" ","%20");
//        JR.execute(q);


    }
}