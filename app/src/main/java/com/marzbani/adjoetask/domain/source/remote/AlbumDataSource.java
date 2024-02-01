package com.marzbani.adjoetask.domain.source.remote;

import com.marzbani.adjoetask.data.models.Album;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface AlbumDataSource {
    Single<List<Album>> fetchAlbums();
}
