package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.models.CreateAlbum
import com.mobileapp.mymobileapp.network.AlbumsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import com.mobileapp.mymobileapp.ui.adapters.AlbumsAdapter
import kotlinx.coroutines.launch

class CreateAlbumFragment : Fragment() {

    private lateinit var adapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_create_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrofit API
        val api = RetrofitClient.instance.create(AlbumsApi::class.java)

        val nameInput = view.findViewById<EditText>(R.id.nameInput)
        val coverInput = view.findViewById<EditText>(R.id.coverInput)
        val releaseDateInput = view.findViewById<EditText>(R.id.releaseDateInput)
        val descriptionInput = view.findViewById<EditText>(R.id.descriptionInput)
        val genreInput = view.findViewById<EditText>(R.id.genreInput)
        val recordLabelInput = view.findViewById<EditText>(R.id.recordLabelInput)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val newAlbum = CreateAlbum(
                name = nameInput.text.toString(),
                cover = coverInput.text.toString(),
                releaseDate = releaseDateInput.text.toString(),
                description = descriptionInput.text.toString(),
                genre = genreInput.text.toString(),
                recordLabel = recordLabelInput.text.toString()
            )

            // Llamada al endpoint
            lifecycleScope.launch {
                val result = api.createAlbum(newAlbum)
                if (result.isSuccessful) {
                    //adapter.addAlbum(newAlbum)
                    Toast.makeText(context, "Álbum creado exitosamente", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Error al crear el álbum", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun setAdapter(albumsAdapter: AlbumsAdapter) {
        adapter = albumsAdapter
    }
}