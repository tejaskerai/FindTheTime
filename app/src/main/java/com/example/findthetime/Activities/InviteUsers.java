package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class InviteUsers extends AppCompatActivity {

    TextView eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_users);
        initializeUI();
    }

    private void initializeUI() {
        Intent intent = getIntent();

        //System.out.println(getIntent().getSerializableExtra("restDetails").toString());
        HashMap<String, String> restDetails = (HashMap<String, String>) intent.getSerializableExtra("restDetails");

        eventName = findViewById(R.id.eventName);
        eventName.setText(restDetails.get("restName"));

    }
}
