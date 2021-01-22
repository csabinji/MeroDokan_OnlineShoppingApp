package com.sabin.onlineshoppingportal.model

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val ProductId : Int? = null,
    val ProductName : String? = null,
    val ProductImage : String? = null,
    val ProductPrice : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(ProductId)
        parcel.writeString(ProductName)
        parcel.writeString(ProductImage)
        parcel.writeString(ProductPrice)
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