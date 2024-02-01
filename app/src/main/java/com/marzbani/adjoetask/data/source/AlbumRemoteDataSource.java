package com.marzbani.adjoetask.data.source;

import android.util.Log;

import com.marzbani.adjoetask.data.models.Album;
import com.marzbani.adjoetask.domain.source.remote.AlbumDataSource;
import com.marzbani.adjoetask.infrastructure.network.HttpService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AlbumRemoteDataSource implements AlbumDataSource {

    private final HttpService httpService;

    @Inject
    public AlbumRemoteDataSource(HttpService httpService) {
        this.httpService = httpService;
    }


    @Override
    public Single<List<Album>> fetchAlbums() {
        return Single.create(emitter -> {
            try {
                String jsonResponse = httpService.sendHttpGetRequest("https://jsonplaceholder.typicode.com/albums/");
                Log.d("Albums", jsonResponse);

                List<Album> albums = Album.fromJsonArray(jsonResponse);
                emitter.onSuccess(albums);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

}
