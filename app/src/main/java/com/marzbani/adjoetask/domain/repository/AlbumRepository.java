package com.marzbani.adjoetask.domain.repository;

import com.marzbani.adjoetask.data.models.Album;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AlbumRepository {
    Single<List<Album>> fetchAlbums();
}
