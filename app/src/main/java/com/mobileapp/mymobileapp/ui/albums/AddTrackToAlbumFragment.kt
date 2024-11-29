package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.mobileapp.mymobileapp.R
import okhttp3.*
import java.io.IOException

class AddTrackToAlbumFragment : Fragment() {

    private lateinit var nameInput: EditText
    private lateinit var durationInput: EditText
    private lateinit var saveButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_track_to_album, container, false)

        nameInput = view.findViewById(R.id.nameInput)
        durationInput = view.findViewById(R.id.durationInput)
        saveButton = view.findViewById(R.id.saveButton)

        saveButton.setOnClickListener { saveTrack() }
        return view
    }

    private fun saveTrack() {
        val albumId = arguments?.getString("ALBUM_ID") ?: return
        val trackName = nameInput.text.toString()
        val trackDuration = durationInput.text.toString()

        val json = """
            {
              "name": "$trackName",
              "duration": "$trackDuration"
            }
        """.trimIndent()

        val client = OkHttpClient()
        val requestBody = RequestBody.create(MediaType.parse("application/json"), json)
        val request = Request.Builder()
            .url("http://35.209.56.12:3000/albums/$albumId/tracks")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Handle success, navigate back to AlbumDetailFragment
                    requireActivity().runOnUiThread {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        })
    }
}