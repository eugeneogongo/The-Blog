package com.yourstar.cenotomy

import android.util.Log

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class FireBaseToken : FirebaseMessagingService() {

    override fun onNewToken(refreshedToken: String?) {
        super.onNewToken(refreshedToken)
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
