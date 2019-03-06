package com.yourstar.cenotomy

import android.util.Log

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FireBaseToken : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(FireBaseToken::class.java.canonicalName, "Refreshed token: " + refreshedToken!!)
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(refreshedToken: String?) {
        if (refreshedToken == null) {
            return
        } else {
            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Token")
            myRef.push().setValue(refreshedToken)

        }
    }
}
