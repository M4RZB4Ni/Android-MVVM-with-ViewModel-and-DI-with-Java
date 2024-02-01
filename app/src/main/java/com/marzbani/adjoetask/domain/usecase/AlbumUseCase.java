package com.marzbani.adjoetask.domain.usecase;

import com.marzbani.adjoetask.data.models.Album;
import com.marzbani.adjoetask.domain.repository.AlbumRepository;
import com.marzbani.adjoetask.infrastructure.useCase.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AlbumUseCase extends SingleUseCase<List<Album>> {

    private final AlbumRepository albumRepository;

    @Inject
    public AlbumUseCase(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Single<List<Album>> getAlbums() {
        return  albumRepository.fetchAlbums();
    }

    @Override
    protected Single<List<Album>> buildUseCaseSingle() {
        return albumRepository.fetchAlbums();
    }
}
