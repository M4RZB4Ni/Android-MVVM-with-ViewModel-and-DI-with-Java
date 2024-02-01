package com.marzbani.adjoetask.data.repository;

import com.marzbani.adjoetask.data.models.Album;
import com.marzbani.adjoetask.domain.repository.AlbumRepository;
import com.marzbani.adjoetask.domain.source.remote.AlbumDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AlbumRepositoryImpl implements AlbumRepository {

    private final AlbumDataSource remoteDataSource;

    @Inject
    public AlbumRepositoryImpl(AlbumDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }


    @Override
    public Single<List<Album>> fetchAlbums() {
       return remoteDataSource.fetchAlbums();
    }
}