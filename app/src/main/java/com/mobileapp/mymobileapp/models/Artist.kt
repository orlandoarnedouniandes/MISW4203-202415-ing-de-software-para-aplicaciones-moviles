package com.mobileapp.mymobileapp.models

import android.os.Parcelable

data class Artist(
    val id: Int,
    val birthDate: String,
    val albums: List<Album>,
    val performerPrizes: List<PerformerPrize>,
    val name: String,
    val image: String,
    val description: String
):Parcelable{
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        TODO("albums"),
        TODO("performerPrizes"),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(birthDate)
        parcel.writeString(name)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: android.os.Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }
}

data class Bands(
    val creationDate: String,
    val albums: List<Album>,
    val performerPrizes: List<PerformerPrize>,
    val id: Int,
    val name: String,
    val image: String,
    val description: String
)


data class PerformerPrize(
    val id: Int,
    val premiationDate: String
)