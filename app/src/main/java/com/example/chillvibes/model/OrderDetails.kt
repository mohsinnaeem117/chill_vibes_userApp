package com.example.chillvibes.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

class OrderDetails() : Serializable {
    var userId: String? = null
    var userName: String? = null
    var foodNames: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        foodItemName: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemQuantities: ArrayList<Int>,
        address: String,
        totalPrice: String,
        phone: String,
        time: Long,
        itemPushKey: String?,
        b: Boolean,
        b1: Boolean
    ) : this(){
        this.userId = userId
        this.userName = name
        this.foodNames = foodItemName
        this.foodPrices = foodItemPrice
        this.foodImages = foodItemImage
        this.foodQuantities = foodItemQuantities
        this.address = address
        this.totalPrice = totalPrice
        this.phoneNumber = phone
        this.currentTime = time
        this.itemPushKey = itemPushKey
        this.orderAccepted = orderAccepted
        this.paymentReceived = paymentReceived
    }

    fun describeContents(): Int {
       return 0
    }

    fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(userId)
        dest.writeString(userName)
        dest.writeStringList(foodNames)
        dest.writeStringList(foodImages)
        dest.writeStringList(foodPrices)
        dest.writeIntArray(foodQuantities?.toIntArray())
        dest.writeString(address)
        dest.writeString(totalPrice)
        dest.writeString(phoneNumber)
        dest.writeByte(if (orderAccepted) 1 else 0)
        dest.writeByte(if (paymentReceived) 1 else 0)
        dest.writeString(itemPushKey)
        dest.writeLong(currentTime)
    }


    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}