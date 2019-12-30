package com.example.findthetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import JSONService.CalendarJSON;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String NAME = "com.example.findthetime.NAME";
    public static final String EMAIL = "com.example.findthetime.EMAIL";
    CalendarJSON calendar = new CalendarJSON();


    /* Azure AD v2 Configs */
    final static String AUTHORITY = "https://login.microsoftonline.com/common";


    /* UI & Debugging Variables */
    Button signInButton;

    /* Azure AD Variables */
    private ISingleAccountPublicClientApplication mSingleAccountApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("In main actvity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeUI();

        // Creates a PublicClientApplication object with res/raw/auth_config_single_account.json
        PublicClientApplication.createSingleAccountPublicClientApplication(MainActivity.this,
                R.raw.auth_config_single_account,
                new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        /**
                         * This test app assumes that the app is only going to support one account.
                         * This requires "account_mode" : "SINGLE" in the config json file.
                         **/
                        mSingleAccountApp = application;
                        System.out.println("loading account");
                        //loadAccount();
                        signOut();
                    }

                    @Override
                    public void onError(MsalException exception) {
                        displayError(exception);
                    }
                });
    }

    private void initializeUI() {
        signInButton = findViewById(R.id.btn_signIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSingleAccountApp == null) {
                    return;
                }

                mSingleAccountApp.signIn(MainActivity.this, null, getScopes(), getAuthInteractiveCallback());

            }

        });
    }

    private void displayError(@NonNull final Exception exception) {
        System.out.println("An error occurred on log in");
    }

    /**
     * Load the currently signed-in account, if there's any.
     */
    private void loadAccount() {
        if (mSingleAccountApp == null) {
            return;
        }

        mSingleAccountApp.getCurrentAccountAsync(new ISingleAccountPublicClientApplication.CurrentAccountCallback() {
            @Override
            public void onAccountLoaded(@Nullable IAccount activeAccount) {
                // You can use the account data to update your UI or your app database.
                updateUI(activeAccount);

            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                displayError(exception);
            }
        });
    }

    /**
     * Updates UI based on the current account.
     */
    private void updateUI(@Nullable final IAccount account) {
        if (account != null) {
            openHomepage();
            System.out.println("Account has been loaded");
        } else {
            signInButton.setEnabled(true);

        }
    }

    public void openHomepage(){
        Intent homePage = new Intent(MainActivity.this, Homepage.class);
        System.out.println("Name:  " + calendar.getName());

        //homePage.putExtra(NAME, "tejas");
        startActivity(homePage);
    }



    /**
     * Extracts a scope array from a text field,
     * i.e. from "User.Read User.ReadWrite" to ["user.read", "user.readwrite"]
     */
    private String[] getScopes() {
        return "user.read".split(" ");
    }

    /**
     * Callback used for interactive request.
     * If succeeds we use the access token to call the Microsoft Graph.
     * Does not check cache.
     */
    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getAccount().getClaims().get("id_token"));

                System.out.println("1 graph");
                /* call graph */
                callGraphAPI(authenticationResult);
                

                System.out.println("finish graph");
                System.out.println("2 update");
                /* Update account */
                updateUI(authenticationResult.getAccount());
                System.out.println("finish update");


            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());
                displayError(exception);

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private void performOperationOnSignOut() {
        final String signOutText = "Signed Out.";
        Toast.makeText(MainActivity.this, signOutText, Toast.LENGTH_SHORT)
                .show();
    }

    public void signOut() {

        if (mSingleAccountApp == null) {
            System.out.println("account is null");
            return;
        }

        /**
         * Removes the signed-in account and cached tokens from this app (or device, if the device is in shared mode).
         */
        mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
            @Override
            public void onSignOut() {
                updateUI(null);
                //nothing
                System.out.println("reached here");
                System.out.println("user has successfully logged out");
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                displayError(exception);
            }
        });
    }

    /**
     * Make an HTTP request to obtain MSGraph data
     */
    private void callGraphAPI(final IAuthenticationResult authenticationResult)  {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                MainActivity.this,
//                graphResourceTextView.getText().toString()
                "https://graph.microsoft.com/v1.0/me",
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /* Successfully called graph, process data and send to UI */
                        calendar.parseName(response);
                        calendar.parseEmail(response);
                        System.out.println("name from call: " + calendar.getName());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        displayError(error);
                    }
                });
    }


}
