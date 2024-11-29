package com.mobileapp.mymobileapp.models

import android.os.Parcelable

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>,
    val performers: List<Performer>,
    val comments: List<Comment>
)

//data class Track(
//    val id: Int,
//    val name: String,
//    val duration: String
//)

data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String?
): Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(birthDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Performer> {
        override fun createFromParcel(parcel: android.os.Parcel): Performer {
            return Performer(parcel)
        }

        override fun newArray(size: Int): Array<Performer?> {
            return arrayOfNulls(size)
        }
    }
}

data class Comment(
    val id: Int,
    val description: String,
    val rating: Int
): Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeInt(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: android.os.Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}