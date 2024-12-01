package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

        val albumId = arguments?.getString("albumId")
        Log.d("AddTrackToAlbumFragment", "Received Album ID: $albumId")
        if (albumId == null) {
            Toast.makeText(context, "Album ID not found", Toast.LENGTH_SHORT).show()
        }

        val api = RetrofitClient.instance.create(AlbumsApi::class.java)

        nameInput = view.findViewById(R.id.nameInput)
        durationInput = view.findViewById(R.id.durationInput)
        saveButton = view.findViewById(R.id.saveButton)

        durationInput.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return

                isEditing = true

                val input = s.toString().replace("[^0-9]".toRegex(), "")
                val formatted = when {
                    input.length > 4 -> input.substring(0, 2) + ":" + input.substring(2, 4)
                    input.length > 2 -> input.substring(0, 2) + ":" + input.substring(2)
                    else -> input
                }

                durationInput.setText(formatted)
                durationInput.setSelection(formatted.length)


                if (formatted.contains(":")) {
                    val parts = formatted.split(":")
                    if (parts.size == 2) {
                        val seconds = parts[1].toIntOrNull()
                        if (seconds != null && seconds > 59) {
                            Toast.makeText(context, "Duracion invalida: los segundos deben ser menor de 60", Toast.LENGTH_SHORT).show()
                            durationInput.error = "Los segundos deben ser menor de 60"
                        } else {
                            durationInput.error = null
                        }
                    }
                }

                isEditing = false
            }
        })

        saveButton.setOnClickListener {
            albumId ?: run {
                Toast.makeText(context, "Album ID no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val track = Track(
                name = nameInput.text.toString(),
                duration = durationInput.text.toString()
            )

            lifecycleScope.launch {
                val result = api.addTrackToAlbum(albumId, track)
                if (result.isSuccessful) {
                    Toast.makeText(context, "Cancion adicionada con exito", Toast.LENGTH_SHORT).show()
                    val action = AddTrackToAlbumFragmentDirections.actionAddTrackToAlbumFragmentToAlbumsFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "La adicion ha fallado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
