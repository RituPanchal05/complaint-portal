package com.example.cp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Model> arrayList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView); // Initialize RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize and set up adapter
        adapter = new Adapter(this, arrayList);
        adapter.setOnItemClickListener(this); // Set the click listener
        recyclerView.setAdapter(adapter);

        if (arrayList.isEmpty()) {
            initializeArrayList();
        }
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click here
        if (position == 0) {
            // Redirect to another activity
            Intent intent = new Intent(this, Academic_issue.class);
            startActivity(intent);
        } else if(position == 1) {
            Intent intent = new Intent(this, Administrative_Issues.class);
            startActivity(intent);
        }
        else if(position == 2) {
            Intent intent = new Intent(this, Facilities_Infrastructure.class);
            startActivity(intent);
        }
        else if(position == 3) {
            Intent intent = new Intent(this, Campus_Services.class);
            startActivity(intent);
        }
        else if(position == 4) {
            Intent intent = new Intent(this, Health_Safety.class);
            startActivity(intent);
        }
        else if(position == 5) {
            Intent intent = new Intent(this, Extracurricular_Activities.class);
            startActivity(intent);
        }
        else if(position == 6) {
            Intent intent = new Intent(this, Communication_Information.class);
            startActivity(intent);
        }
        else if(position == 8) {
            Intent intent = new Intent(this, Technology_Online_Services.class);
            startActivity(intent);
        }
        else  {
            Toast.makeText(this, "iteam is clicked ", Toast.LENGTH_SHORT).show();
        }
    }


    // Other existing code

//    public void onItemClick(int position) {
//        if (position == 0) { // "Academic Issues" clicked
//            // Start AcademicFragment
//            Fragment AcademicFragment =new Fragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.cardView, AcademicFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        } else {
//            // Handle other item clicks as needed
//            Toast.makeText(this, "Activity " + position, Toast.LENGTH_SHORT).show();
//        }
//    }

    private void initializeArrayList() {
        arrayList.add(new Model("Academic Issues:", R.drawable.arrow_right));
        arrayList.add(new Model("Administrative Issues:", R.drawable.arrow_right));
        arrayList.add(new Model("Facilities and Infrastructure:", R.drawable.arrow_right));
        arrayList.add(new Model("Campus Services:", R.drawable.arrow_right));
        arrayList.add(new Model("Health and Safety:", R.drawable.arrow_right));
        arrayList.add(new Model("Extracurricular Activities:", R.drawable.arrow_right));
        arrayList.add(new Model("Communication and Information:", R.drawable.arrow_right));
        arrayList.add(new Model(" Technology and Online Services:", R.drawable.arrow_right));

        // Add more items as needed
    }
}
