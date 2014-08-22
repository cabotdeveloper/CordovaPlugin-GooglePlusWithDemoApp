package com.cabot.plugins.googleplus;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;


import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class ConnectGoogle extends CordovaPlugin implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private static final String PLUS_CLIENT_LOGIN = "cordovaGooglePlusLogin";
	private static final String PLUS_CLIENT_LOGOUT = "cordovaGooglePlusLogout";
	private static final String ACTION_AVAILABLE_EVENT = "available";
	// PlusClient object for performing google log in 
	
	public GoogleApiClient mGoogleApiClient;

	// Holds the connectionResult for checking purpose 
	public ConnectionResult mConnectionResult;
	
	// Progress dialog for showing the progress status of connection login task 
	public ProgressDialog mConnectionProgressDialog;
	
	 private static final int RC_SIGN_IN = 0;
	 
	 private CallbackContext callbackContext;
	 
	 /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
	
	boolean returnStatus;
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		// TODO Auto-generated method stub
		
		
		
		// Set up the activity result callback to this class
	    cordova.setActivityResultCallback(this);
				
		 //Defining the progress dialog and setting the properties 
		mConnectionProgressDialog = new ProgressDialog(cordova.getActivity());
		mConnectionProgressDialog.setMessage("Signing in...");
		
		 // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(cordova.getActivity(),this,this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
       
       
       
		super.initialize(cordova, webView);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int responseCode, Intent intent) {
		Log.i("", "Inside Activity result");
		 if (requestCode == RC_SIGN_IN) {
			 Log.i("", "Inside Activity result-requestCode == RC_SIGN_IN");
			    mIntentInProgress = false;

			   /* if (!mGoogleApiClient.isConnecting()) {
			      mGoogleApiClient.connect();
			    }*/
			  }
		  if(responseCode==Activity.RESULT_OK){
			  Log.i("", "Inside Activity result- Result ok");
			  if (!mGoogleApiClient.isConnecting())
				  mGoogleApiClient.connect();
		  }else
		  {
			  callbackContext.error("Login Cancelled");
			  Log.i("", "Inside Activity result- Cancelled");
			  if (mConnectionProgressDialog.isShowing())
				  mConnectionProgressDialog.dismiss();
			  signOut();
		  }
		}
	@Override
	public boolean execute(final String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
		// TODO Auto-generated method stub
		//return super.execute(action, args, callbackContext);
		//final boolean returnStatus;
		 if (PLUS_CLIENT_LOGIN.equals(action)) { 
			 mConnectionProgressDialog.show();
			
			    	cordova.getThreadPool().execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mGoogleApiClient.connect();
						}
					});
			    	
			      
			  return true;
			
		}
		 else if(PLUS_CLIENT_LOGOUT.equals(action)){
			 cordova.getThreadPool().execute(new Runnable() {
					
					@Override
					public void run() {
						signOut();
					}
				});
			 return true;
		 }
		else{
			
	    	 callbackContext.error("Invalid action.....");
		     return false;
	     }
		
	}
	// Getting Gender from integer to string
	  private static String getGender(int gender) {
	    switch (gender) {
	      case 0:
	        return "Male";
	      case 1:
	        return "Female";
	      default:
	        return "Other";
	    }
	  }
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		Log.e("","OnConnection Failed:"); 
		// Set up the activity result callback to this class
		cordova.setActivityResultCallback(this);
		 if (!result.hasResolution()) {
			 Log.i("", "Inside OnConnection Failed result.hasResolution()");
		        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), cordova.getActivity(),
		                0).show();
		        return;
		    }
		 
		    if (!mIntentInProgress) {
		    	Log.i("", "Inside OnConnection Failed result.hasResolution()");
		        // Store the ConnectionResult for later usage
		        mConnectionResult = result;
		 
		            // The user has already clicked 'sign-in' so we attempt to
		            // resolve all
		            // errors until the user is signed in, or they cancel.
		            resolveSignInError();
		       
		    }
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		// Set up the activity result callback to this class
				cordova.setActivityResultCallback(this);
		if (mConnectionProgressDialog.isShowing())
					mConnectionProgressDialog.dismiss();
		mConnectionResult = null;
		// TODO Auto-generated method stub
		Toast.makeText(cordova.getActivity(), "User is connected!", Toast.LENGTH_LONG).show();
		//mConnectionProgressDialog.dismiss();
	    // Get user's information
	    JSONObject userProfile= getProfileInformation();
	    
		try{
			if(userProfile!=null){
				 Log.i("", "User's name:"+userProfile.get("Name"));
				 //callbackContext.success(userProfile);
				 //callbackContext.success(userProfile);
				 callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, userProfile));
				 signOut();
			}
			else{
				callbackContext.error("User information could not be retrieved");
				signOut();
			}
		}
		catch(JSONException e)
		{
			Log.i("", "Error :"+e);
			 callbackContext.error(e.getMessage());
		}
		
	   
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		 if (mConnectionProgressDialog.isShowing())
				mConnectionProgressDialog.dismiss();
		super.onDestroy();
		
	}
	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		Log.e("","resolveSignInError:"); 
	    if (mConnectionResult.hasResolution()) {
	        try {
	            mIntentInProgress = true;
	            mConnectionResult.startResolutionForResult(cordova.getActivity(), RC_SIGN_IN);
	        } catch (SendIntentException e) {
	            mIntentInProgress = false;
	            mGoogleApiClient.connect();
	        }
	    }
	}
	public void signOut() {
		if (mGoogleApiClient.isConnected()) {
			//Logger.show(Log.INFO, "", "Signing out..");
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
		}
		}
	
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private JSONObject getProfileInformation() {
	    try {
	    	JSONObject userProfile=new JSONObject();
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	            Person currentPerson = Plus.PeopleApi
	                    .getCurrentPerson(mGoogleApiClient);
	            userProfile.put("Name", currentPerson.getDisplayName());
	            String personName = currentPerson.getDisplayName();
	            userProfile.put("Gender",  getGender(currentPerson.getGender()));
	            
	           // userProfile.put("ProfileUrl", currentPerson.getUrl());
	            /*String personPhotoUrl = currentPerson.getImage().getUrl();
	            String personGooglePlusProfile = currentPerson.getUrl();*/
	            int gender=currentPerson.getGender();
	           // userProfile.put("Gender", gender);
	            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	            userProfile.put("Email",email);
	           
	            Log.e("LoginPlugin", "Name: " + personName + ",Gender: "
	                    +  getGender(gender) + ", email: " + email
	                    );
	 
	          /*  txtName.setText(personName);
	            txtEmail.setText(email);
	 
	            // by default the profile url gives 50x50 px image only
	            // we can replace the value with whatever dimension we want by
	            // replacing sz=X
	            personPhotoUrl = personPhotoUrl.substring(0,
	                    personPhotoUrl.length() - 2)
	                    + PROFILE_PIC_SIZE;
	 
	            new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);*/
	            return userProfile;
	        } else {
	            Toast.makeText(cordova.getActivity(),
	                    "Person information is not available", Toast.LENGTH_LONG).show();
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
	}

}
