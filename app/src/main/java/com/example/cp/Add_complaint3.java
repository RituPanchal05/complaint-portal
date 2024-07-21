package com.example.cp;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Add_complaint3 extends AppCompatActivity {

    EditText addComp;
    public String addComText;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RATINGBAR IDs

        setContentView(R.layout.activity_add_complaint3);
        addComp = findViewById(R.id.addComp);
        Button button = findViewById(R.id.saveButton);



        // Initialize Button

        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Validate if the EditText is not empty
                    EditText addComp = findViewById(R.id.addComp);
                    String complaintText = addComp.getText().toString().trim();
                    if (complaintText.isEmpty()) {
                        // Show a toast message indicating that the complaint is required
                        Toast.makeText(Add_complaint3.this, "Complaint is required", Toast.LENGTH_SHORT).show();
                    } else {
                        addComText = String.valueOf(addComp.getText());
                        addToDatabase(addComText);
                    }
                }
            });
        }
    }
    private void addToDatabase(String addComText) {
        String url = "http://192.168.117.51/my.php?column=Communication_Information";
        RequestQueue queue = Volley.newRequestQueue(Add_complaint3.this);


        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(Add_complaint3.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                Toast.makeText(Add_complaint3.this, "Your Response has been stored" , Toast.LENGTH_SHORT).show();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Add_complaint3.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("addComText",addComText);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

}