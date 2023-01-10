package com.example.mybitirmeproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val name:String,
    val description:String,
    val adress:String,
    val deliveryAdress:String,
    val cost:Int,
    val demandedTime:String?,
    val orderId:String,
    val inOrder:Boolean,
    val ownerId:String?,
    val isReceivedFromCourier:Boolean,
    val isPackageOnTheWay:Boolean,
    val isPackageArrived:Boolean,
    //val courierId:String
): Parcelable
