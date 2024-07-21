package com.example.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_complaint extends AppCompatActivity {

    EditText addComp;
    TextView clg_eno, dept_id;
    String departmentId;
    String username = "testuser"; // Hardcoded username
    String password = "testpassword"; // Hardcoded password
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        addComp = findViewById(R.id.addComp);
        clg_eno = findViewById(R.id.clg_eno);
        dept_id = findViewById(R.id.dept_id);
        Button button = findViewById(R.id.saveButton);

        // Initialize the RequestQueue
        queue = Volley.newRequestQueue(this);

        // Fetch user data
        fetchUserData();

        // Set click listener for the button
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String complaintText = addComp.getText().toString().trim();
                    if (complaintText.isEmpty()) {
                        // Show a toast message indicating that the complaint is required
                        Toast.makeText(Add_complaint.this, "Complaint is required", Toast.LENGTH_SHORT).show();
                    } else {
                        addToDatabase(complaintText);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the RequestQueue
        if (queue != null) {
            queue.stop();
        }
    }

    private void fetchUserData() {
        String url = "http://192.168.117.51/login.php";

        // Create a String request
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FetchUserData", "Response: " + response); // Log the response
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        // Fetch successful
                        String enrollmentNumber = jsonObject.getString("enrollment_number");
                        departmentId = jsonObject.getString("dep_id");

                        // Update the TextViews with fetched data
                        clg_eno.setText(enrollmentNumber);
                        dept_id.setText(departmentId);

                        Log.d("FetchUserData", "Enrollment Number: " + enrollmentNumber);
                        Log.d("FetchUserData", "Department ID: " + departmentId);
                    } else {
                        // Error occurred
                        String errorMsg = jsonObject.getString("message");
                        Toast.makeText(Add_complaint.this, "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error message for Volley errors
                Toast.makeText(Add_complaint.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Volley Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Post parameters to the PHP script
                Map<String, String> params = new HashMap<>();
                // Use hardcoded username and password for testing
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(request);
    }

    private void addToDatabase(String addComText) {
        String url = "http://192.168.192.204/demo/my.php";

        // Create a String request
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Check if the response contains 'error' key
                    if (!jsonObject.getBoolean("error")) {
                        // Success message
                        Toast.makeText(Add_complaint.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        // Error message
                        Toast.makeText(Add_complaint.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error message for Volley errors
                Toast.makeText(Add_complaint.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Volley Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Post parameters to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("column", "Academic_complaint");
                params.put("department_id", departmentId);
                params.put("addComText", addComText);
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(request);
    }
}
