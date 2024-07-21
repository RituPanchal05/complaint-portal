package com.example.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Technology_Online_Services extends AppCompatActivity {

    private static final String TAG = "Technology_Online_Services";
    private static final String URL = "http://192.168.117.51/fatch.php?column=Technology_Online_Services";

    private List<String> idList = new ArrayList<>();
    private List<String> complaintList = new ArrayList<>();
    private TextView textViewLoremIpsum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_online_services);
        Log.d(TAG, "onCreate: Activity started");

        textViewLoremIpsum = findViewById(R.id.textViewLoremIpsum);

        fetchDataFromServer();

        // Initialize Button
        Button nextButton = findViewById(R.id.addBtn);

        // Set OnClickListener for the button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity
                // Change Add_complaint.class to your desired activity
                 Intent intent = new Intent(Technology_Online_Services.this, Add_complaint7.class);
                 startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        idList.clear();
                        complaintList.clear();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject complaint = response.getJSONObject(i);
                                String id = complaint.getString("id");
                                String complaintText = complaint.getString("complain");
                                idList.add(id);
                                complaintList.add(complaintText);
                            }

                            updateUI();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Technology_Online_Services.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    private void updateUI() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < complaintList.size(); i++) {
            data.append(" ").append(idList.get(i)).append(" ");
            data.append(" ").append(complaintList.get(i)).append("\n\n");
        }
        textViewLoremIpsum.setText(data.toString());
    }
}
