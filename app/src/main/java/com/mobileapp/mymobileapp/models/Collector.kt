package com.mobileapp.mymobileapp.models

import android.os.Parcel
import android.os.Parcelable

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<Performer>,
    val collectorAlbums: List<CollectorAlbum>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Comment.CREATOR) ?: emptyList(),
        parcel.createTypedArrayList(Performer.CREATOR) ?: emptyList(),
        parcel.createTypedArrayList(CollectorAlbum.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(telephone)
        parcel.writeTypedList(favoritePerformers)
        parcel.writeTypedList(collectorAlbums)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Collector> {
        override fun createFromParcel(parcel: Parcel): Collector {
            return Collector(parcel)
        }

        override fun newArray(size: Int): Array<Collector?> {
            return arrayOfNulls(size)
        }
    }
}


data class CollectorAlbum(
    val id: Int,
    val price: Int,
    val status: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(price)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CollectorAlbum> {
        override fun createFromParcel(parcel: Parcel): CollectorAlbum {
            return CollectorAlbum(parcel)
        }

        override fun newArray(size: Int): Array<CollectorAlbum?> {
            return arrayOfNulls(size)
        }
    }
}