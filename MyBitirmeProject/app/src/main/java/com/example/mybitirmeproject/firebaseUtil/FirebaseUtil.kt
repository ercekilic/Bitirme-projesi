package com.example.mybitirmeproject.firebaseUtil

import com.google.firebase.database.FirebaseDatabase

class FirebaseUtil {
    companion object{
        fun addSingleValue(category:String,
                           uid:String,
                           field:String,
                           value:Any){

            FirebaseDatabase.getInstance().getReference(category).
            child(uid).
            child(field).
            setValue(value)

        }

    }

}