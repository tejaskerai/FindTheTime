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

public class Homepage extends AppCompatActivity {

    private ISingleAccountPublicClientApplication mSingleAccountApp;
    Button signOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        signOutButton = findViewById(R.id.btn_signOut);
        // TODO: Add sign out function from SingleAccount mode

        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                System.out.println("Sign out clicked");
//                Intent signOut = new Intent(v.getContext(), SingleAccountModeFragment.class);
//                startActivity(signOut);
            }
        });


    }
}
