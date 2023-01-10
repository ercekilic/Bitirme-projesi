package com.example.mybitirmeproject.service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionEvent
import com.google.android.gms.location.ActivityTransitionResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class ActivityTransitionReceiver : BroadcastReceiver() {
    private var mAuth: FirebaseAuth? = null
    val database = FirebaseDatabase.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            mAuth=FirebaseAuth.getInstance()
            val result = ActivityTransitionResult.extractResult(intent)!!

            for (event in result.transitionEvents){
                val info =
                    "Kullanıcı: " + SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())+" itibarıyla " +ActivityTransitionsUtil.toActivityString(event.activityType) +
                            " " +ActivityTransitionsUtil.toTransitionType(event.transitionType) + " "
                Toast.makeText(context, info, Toast.LENGTH_LONG).show()
                val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null
                if (isUserLoggedIn==false){
                    Log.i(
                        "Firebase",
                        "Oturum yokOturum yokOturum yokOturum yokOturum yokOturum yok"
                    )
                }
                else{

                    if (event.transitionType==ActivityTransition.ACTIVITY_TRANSITION_ENTER){

                        val ref=database.getReference("Users").child(mAuth!!.currentUser?.uid.toString())//
                        ref.addListenerForSingleValueEvent(object:ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.child("activity").value.toString() == ActivityTransitionsUtil.toActivityString(event.activityType)){

                                }else {
                                    ref.child("activity").setValue(ActivityTransitionsUtil.toActivityString(event.activityType))
                                    aktiviteKaydet(event)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })


                    }

                }

            }

        }

    }

    private fun aktiviteKaydet(event: ActivityTransitionEvent) {
        val myRef = database.getReference("Users").child(mAuth!!.currentUser?.uid.toString())
        myRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val tarih=Calendar.getInstance().time
                val format = SimpleDateFormat("dd-MM-yyyy_HH:mm:ss")
                val current_time=format.format(tarih)

                val clockFormat=SimpleDateFormat("HH:mm:ss")
                val currentClock=clockFormat.format(tarih)

                val eylem=ActivityTransitionsUtil.toActivityString(event.activityType)
                val yer=snapshot.child("Yakın_Yer").value.toString()

                database.getReference("Kayitlar").child(mAuth?.currentUser?.uid.toString()).child(current_time.toString()).child("hareket").setValue("aktivite")
                database.getReference("Kayitlar").child(mAuth?.currentUser?.uid.toString()).child(current_time.toString()).child("eylem").setValue(eylem)
                database.getReference("Kayitlar").child(mAuth?.currentUser?.uid.toString()).child(current_time.toString()).child("yer").setValue(yer)

            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}

