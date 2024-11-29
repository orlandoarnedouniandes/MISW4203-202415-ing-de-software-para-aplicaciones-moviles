package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.models.Track
import com.mobileapp.mymobileapp.network.AlbumsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import kotlinx.coroutines.launch

class AddTrackToAlbumFragment : Fragment() {

    private lateinit var nameInput: EditText
    private lateinit var durationInput: EditText
    private lateinit var saveButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_track_to_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitClient.instance.create(AlbumsApi::class.java)

        nameInput = view.findViewById(R.id.nameInput)
        durationInput = view.findViewById(R.id.durationInput)
        saveButton = view.findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val albumId = arguments?.getString("ALBUM_ID") ?: run {
                Toast.makeText(context, "Album ID not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val track = Track(
                name = nameInput.text.toString(),
                duration = durationInput.text.toString()
            )

            lifecycleScope.launch {
                val result = api.addTrackToAlbum(albumId, track)
                if (result.isSuccessful) {
                    Toast.makeText(context, "Track added successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Failed to add track", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
