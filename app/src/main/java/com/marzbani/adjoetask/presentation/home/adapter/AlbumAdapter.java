package com.marzbani.adjoetask.presentation.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marzbani.adjoetask.data.models.Album;
import com.marzbani.adjoetask.databinding.ItemAlbumBinding;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private final List<Album> albums;

    public AlbumAdapter() {
        this.albums = new ArrayList<>();
    }

    public void setAlbums(List<Album> albums) {
        this.albums.clear();
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use ViewBinding to inflate the item layout
        ItemAlbumBinding binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind album data to the ViewHolder
        Album album = albums.get(position);
        holder.bind(album);
    }

    @Override
    public int getItemCount() {
        // Return the size of the dataset
        return albums.size();
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAlbumBinding binding;

        public ViewHolder(@NonNull ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Album album) {
            // Bind album data to views
            binding.titleTextView.setText(album.getTitle());
        }
    }
}
