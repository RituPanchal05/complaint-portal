package com.example.cp;

import static android.text.method.HideReturnsTransformationMethod.*;
import static android.view.View.*;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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



public class loginstd extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private static final String TAG = loginstd.class.getSimpleName();
    private RequestQueue requestQueue;
    private TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginstd);

        usernameEditText = findViewById(R.id.Username);
        passwordEditText = findViewById(R.id.reg_Password);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);

        //show pass using eyecon
        ImageView showpass = findViewById(R.id.showPass);
        showpass.setImageResource(R.drawable.img);
        showpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (passwordEditText.getTransformationMethod().equals(getInstance())) {
                    // if pass is visiabke
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showpass.setImageResource(R.drawable.img_1);
                }
                else {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showpass.setImageResource(R.drawable.img);
                }
            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Call the loginUser method with the input values
                if (!username.isEmpty() && !password.isEmpty()) {
                    loginUser(username, password);
                } else {
                    showToast("Please fill all fields");
                }
            }
        });

        // Set OnClickListener for the SignUp TextView
        signUpTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SignUp activity
                Intent intent = new Intent(loginstd.this, register.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String username, String password) {
        String url = "http://192.168.117.51/login.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean error = jsonResponse.getBoolean("error");

                    if (!error) {
                        // Login successful
                        Toast.makeText(getApplicationContext(), "login successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginstd.this, dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Login failed
                        String errorMsg = jsonResponse.getString("message");
                        showToast("Login failed: " + errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
                showToast("Error logging in");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
