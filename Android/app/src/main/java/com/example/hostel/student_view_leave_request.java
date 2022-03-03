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

public class student_view_leave_request extends AppCompatActivity implements JsonResponse{
    ListView l1;
    SharedPreferences sh;
    String[] uid,rid,date,reason,statuss,leavedate,value;
    public static String rids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_leave_requestl);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lv);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) student_view_leave_request.this;
        String q="/student_view_leave_request?lid="+sh.getString("log_id","");
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

                uid = new String[ja1.length()];
                leavedate=new String[ja1.length()];
                date=new String[ja1.length()];
                statuss=new String[ja1.length()];
                reason=new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    uid[i]=ja1.getJSONObject(i).getString("student_id");
                    leavedate[i]=ja1.getJSONObject(i).getString("leavedate");
                    date[i]=ja1.getJSONObject(i).getString("date");
                    statuss[i]=ja1.getJSONObject(i).getString("status");
                    reason[i]=ja1.getJSONObject(i).getString("reason");
                    value[i] ="leavedate: "+leavedate[i]+"\nstatus: "+statuss[i]+"\nreason:"+reason[i]+"\ndate:"+date[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
            else{
                Toast.makeText(getApplicationContext(),"Not Values",Toast.LENGTH_LONG).show();
            }
        }

        catch (Exception e) {
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