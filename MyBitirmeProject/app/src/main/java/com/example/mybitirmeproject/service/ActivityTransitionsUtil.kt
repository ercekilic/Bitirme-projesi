package com.example.mybitirmeproject.service

import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity

object ActivityTransitionsUtil {
    private fun getTransitions(): MutableList<ActivityTransition> {
        val transitions = mutableListOf<ActivityTransition>()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.RUNNING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()

        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.RUNNING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()






        return transitions
    }

    fun getActivityTransitionRequest() = ActivityTransitionRequest(getTransitions())
    fun toActivityString(activity: Int): String {
        return when (activity) {
            DetectedActivity.STILL -> "hareketsiz (Bekleme)"
            DetectedActivity.WALKING -> "Y??r??me"
            DetectedActivity.IN_VEHICLE -> "Ara?? i??inde hareket"
            DetectedActivity.RUNNING -> "Ko??ma"
            else -> "Bilinmeyen"
        }
    }

    fun toTransitionType(transitionType: Int): String {
        return when (transitionType) {
            ActivityTransition.ACTIVITY_TRANSITION_ENTER -> "durumuna ge??ti."
            ActivityTransition.ACTIVITY_TRANSITION_EXIT -> "durumundan ayr??ld??."
            else -> "bilinmeyen"
        }
    }

}