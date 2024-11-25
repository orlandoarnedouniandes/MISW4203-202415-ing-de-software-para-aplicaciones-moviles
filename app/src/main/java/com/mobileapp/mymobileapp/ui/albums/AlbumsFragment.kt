package com.mobileapp.mymobileapp.ui.albums

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobileapp.mymobileapp.R
import com.mobileapp.mymobileapp.data.repositories.AlbumRepository
import com.mobileapp.mymobileapp.database.AlbumDatabase
import com.mobileapp.mymobileapp.databinding.FragmentAlbumsBinding
import com.mobileapp.mymobileapp.models.Performer
import com.mobileapp.mymobileapp.network.AlbumsApi
import com.mobileapp.mymobileapp.network.RetrofitClient
import com.mobileapp.mymobileapp.ui.adapters.AlbumsAdapter
import java.text.Normalizer


class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    private lateinit var binding: FragmentAlbumsBinding
    private lateinit var adapter: AlbumsAdapter
    private lateinit var viewModel: AlbumsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumsBinding.bind(view)

        // Retrofit API
        val api = RetrofitClient.instance.create(AlbumsApi::class.java)

        // Room Database and DAO
        val albumDao = AlbumDatabase.getDatabase(requireContext()).albumDao()

        // Repository
        val repository = AlbumRepository(api, albumDao)

        // ViewModel Factory
        val factory = AlbumsViewModelFactory(repository)

        // ViewModel
        viewModel = ViewModelProvider(this, factory).get(AlbumsViewModel::class.java)

        setupRecyclerView()

        viewModel.albums.observe(viewLifecycleOwner, Observer { albumList ->
            adapter.submitList(albumList)
        })

        viewModel.fetchAlbums()

        val editText: EditText = binding.editTextText

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // por implementar no necesario por ahora
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length > 2) {
                    viewModel.albums.observe(viewLifecycleOwner, Observer { albumList ->
                        val albumsFiltradas = albumList.filter { replaceCharacteres(it.name.trim()).lowercase().contains(replaceCharacteres(s.trim().toString()).lowercase()) || nombreArtista(it.performers, s)  }
                        adapter.submitList(albumsFiltradas)
                    })
                    /*Toast.makeText(context, "¡Dialogo out!", Toast.LENGTH_SHORT)
                        .show()*/
                } else {
                    viewModel.albums.observe(viewLifecycleOwner, Observer { albumList ->
                        adapter.submitList(albumList)
                    })
                }
            }

            override fun afterTextChanged(s: Editable) {
                // por implementar no necesario por ahora
            }
        })

        val fab: FloatingActionButton = binding.fab
        fab.setOnClickListener {

            Toast.makeText(context, "¡Pantalla no implementada!", Toast.LENGTH_SHORT)
                .show()
        }

    }

    private fun nombreArtista(performers: List<Performer>, s: CharSequence): Boolean {
        val peformFilter = performers.filter { replaceCharacteres(it.name.trim().lowercase()).contains(replaceCharacteres(s.trim().toString()).lowercase())  }
        return peformFilter.count() > 0
    }

    private fun replaceCharacteres(texto: String): String {
        val normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
        return normalizado.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
    }

    private fun setupRecyclerView() {
        adapter = AlbumsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@AlbumsFragment.adapter
        }
    }

}