package com.example.findthetime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;

import java.util.List;

import JSONService.OpenCageService;

public class Homepage extends AppCompatActivity {

    private ISingleAccountPublicClientApplication mSingleAccountApp;


    /* UI & Debugging Variables */
    Button signOutButton;
    Button createActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        signOutButton = findViewById(R.id.btn_signOut);
        // TODO: Add sign out function from main activity
        // Use mSingleAccountApp.signOut() to sign the user out
        // You may also be required to redirect user back to the signin page manually
        // Call mSingleAccountApp.signOut() from a separate thread


        initializeUI();


    }

    private void initializeUI(){
        createActivity = findViewById(R.id.btn_create_activity);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newActivity = new Intent(Homepage.this, CreateNewActivity.class);
                startActivity(newActivity);
            }
        });


        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("sign out clicked");

                Intent login = new Intent(Homepage.this, MainActivity.class);
                startActivity(login);

            }
        });
    }

    /**
     * Display the error message
     */
    private void displayError(@NonNull final Exception exception) {
        System.out.println("Error occurred in sign out");
    }

    private void SignOut() {

    }
    public void update(){
        System.out.println("sign out from update");
    }
}
