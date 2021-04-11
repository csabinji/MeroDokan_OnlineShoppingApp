package com.sabin.onlineshoppingportal.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    val _id : String? = null,
    val name : String? = null,
    val dec : String? = null,
    val price : String? = null,
    val category : String? = null,
    val image : String? = null
        ):Parcelable{
        @PrimaryKey(autoGenerate = true)
        var productID : Int = 0

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
                productID = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(_id)
                parcel.writeString(name)
                parcel.writeString(dec)
                parcel.writeString(price)
                parcel.writeString(category)
                parcel.writeString(image)
                parcel.writeInt(productID)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Product> {
                override fun createFromParcel(parcel: Parcel): Product {
                        return Product(parcel)
                }

                override fun newArray(size: Int): Array<Product?> {
                        return arrayOfNulls(size)
                }
        }
}