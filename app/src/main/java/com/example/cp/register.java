package com.example.cp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

public class register extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText, departmentIdEditText;
    private Button signUpButton;
    private static final String TAG = Tag.class.getSimpleName();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.reg_Password);
        confirmPasswordEditText = findViewById(R.id.ConfirmPassword);
        departmentIdEditText = findViewById(R.id.dep_id);
        signUpButton = findViewById(R.id.SignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String departmentId = departmentIdEditText.getText().toString();

                // Call the registerUser method with the input values
                if (!username.isEmpty() && !password.isEmpty() && !departmentId.isEmpty()) {
                    registerUser(username, password, String.valueOf(Integer.parseInt(departmentId)));
                } else {
                    showToast("Please fill all fields");
                }
            }
        });
    }

    private void registerUser(String username, String password, String departId)
    {
        String url = "http://192.168.117.51/regi.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.contains("Registration successful")) {
                    Log.d(TAG, "Registration successful");
                    Toast.makeText(getApplicationContext(), "registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this, loginstd.class);
                    startActivity(intent);


                } else {
                    Log.d(TAG, "Registration failed: " + response);
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "" + error);
                // Optionally, display a toast message indicating the error
                Toast.makeText(getApplicationContext(), "Error registering owner", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("dep_id", departId);
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}