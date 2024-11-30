package com.mobileapp.mymobileapp.models

import android.os.Parcel
import android.os.Parcelable

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    var tracks: List<Track>,
    var performers: List<Performer>,
    val comments: List<Comment>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        mutableListOf<Track>().apply {
            parcel.readList(this, Track::class.java.classLoader)
        },
        mutableListOf<Performer>().apply {
            parcel.readList(this, Performer::class.java.classLoader)
        },
        mutableListOf<Comment>().apply {
            parcel.readList(this, Comment::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(cover)
        parcel.writeString(releaseDate)
        parcel.writeString(description)
        parcel.writeString(genre)
        parcel.writeString(recordLabel)
        parcel.writeList(tracks)
        parcel.writeList(performers)
        parcel.writeList(comments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}



data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String?,
    val creationDate: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
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
    constructor(parcel: Parcel) : this(
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