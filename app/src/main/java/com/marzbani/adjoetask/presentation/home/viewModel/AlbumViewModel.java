package com.marzbani.adjoetask.presentation.home.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.marzbani.adjoetask.data.models.Album;
import com.marzbani.adjoetask.domain.usecase.AlbumUseCase;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AlbumViewModel extends ViewModel {




    private final AlbumUseCase albumUseCase;
    private final MutableLiveData<List<Album>> albumsLiveData = new MutableLiveData<>();

    @Inject
    public AlbumViewModel(AlbumUseCase albumUseCase) {
        this.albumUseCase = albumUseCase;
    }

    /**
     * Provides a LiveData object for observing the sorted list of albums.
     */
    public LiveData<List<Album>> observeSortedAlbums() {
        // LiveData to observe sorted albums
        return Transformations.map(albumsLiveData, this::sortAlbumsById);
    }

    private List<Album> sortAlbumsById(List<Album> albums) {
        Collections.sort(albums, (album1, album2) -> Integer.compare(album1.getId(), album2.getId()));

        return albums;
    }

    public void fetchAlbums() {
        // Log the error or provide specific feedback to the UI
        albumUseCase.execute(
                // onSuccess
                albumsLiveData::postValue,
                // onError
                Throwable::printStackTrace,
                // onFinished
                () -> {
                    // Any additional cleanup or actions after the operation finishes
                }
        );
    }


    public LiveData<List<Album>> observeAlbums() {
        return albumsLiveData;
    }


}

