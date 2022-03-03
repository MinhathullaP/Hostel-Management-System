package com.example.hostel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class student_view_bill extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener{
    ListView l1;
    SharedPreferences sh;
    String[] uid,bid,date,amount,statuss,sname,no_of_leave,no_of_present,proom_rent,pmess_rent,value;
    public static String bids,amounts,no_of_leaves,no_of_presents,proom_rents,pmess_rents,dates,snames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_bill);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lv);
        l1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) student_view_bill.this;
        String q="/student_view_bill?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                bid=new String[ja1.length()];
                uid = new String[ja1.length()];
                date=new String[ja1.length()];
                sname=new String[ja1.length()];
                no_of_leave=new String[ja1.length()];
                no_of_present=new String[ja1.length()];
                proom_rent=new String[ja1.length()];
                pmess_rent=new String[ja1.length()];
                statuss=new String[ja1.length()];
                amount=new String[ja1.length()];
                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    bid[i]=ja1.getJSONObject(i).getString("bill_id");
                    date[i]=ja1.getJSONObject(i).getString("bill_date");
                    sname[i]=ja1.getJSONObject(i).getString("sname");
                    no_of_leave[i]=ja1.getJSONObject(i).getString("no_of_leave");
                    no_of_present[i]=ja1.getJSONObject(i).getString("no_of_present");
                    proom_rent[i]=ja1.getJSONObject(i).getString("proom_rent");
                    pmess_rent[i]=ja1.getJSONObject(i).getString("pmess_rent");
                    statuss[i]=ja1.getJSONObject(i).getString("bstatus");
                    amount[i]=ja1.getJSONObject(i).getString("amount");
                    value[i] ="Date: "+date[i]+"\nAmount : "+amount[i]+"\nStatus: "+statuss[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
            else{
                Toast.makeText(getApplicationContext(),"No Values",Toast.LENGTH_LONG).show();
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,int position, long id) {
        bids = bid[position];
        amounts = amount[position];
        no_of_leaves = no_of_leave[position];
        no_of_presents = no_of_present[position];
        proom_rents = proom_rent[position];
        pmess_rents = pmess_rent[position];
        dates=date[position];
        snames=sname[position];



        if(statuss[position].equalsIgnoreCase("pending")) {

            final CharSequence[] items = {"Pay", "Cancel",};

            AlertDialog.Builder builder = new AlertDialog.Builder(student_view_bill.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Pay")) {
                        startActivity(new Intent(getApplicationContext(), student_make_payment.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
        else if(statuss[position].equalsIgnoreCase("paid")) {

            final CharSequence[] items = {"View Bill", "Cancel",};

            AlertDialog.Builder builder = new AlertDialog.Builder(student_view_bill.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("View Bill")) {
                        startActivity(new Intent(getApplicationContext(), View_bill.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
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