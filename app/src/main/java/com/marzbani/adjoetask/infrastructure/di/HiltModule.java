package com.marzbani.adjoetask.infrastructure.di;

// HiltModule.java
import com.marzbani.adjoetask.data.repository.AlbumRepositoryImpl;
import com.marzbani.adjoetask.data.source.AlbumRemoteDataSource;
import com.marzbani.adjoetask.domain.repository.AlbumRepository;
import com.marzbani.adjoetask.domain.source.remote.AlbumDataSource;
import com.marzbani.adjoetask.domain.usecase.AlbumUseCase;
import com.marzbani.adjoetask.infrastructure.network.ApiException;
import com.marzbani.adjoetask.infrastructure.network.HttpService;
import com.marzbani.adjoetask.presentation.home.adapter.AlbumAdapter;
import com.marzbani.adjoetask.presentation.home.viewModel.AlbumViewModel;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class HiltModule {

    @HiltQualifier(true)
    @Provides
    public static boolean provideDebugBoolean() {
        return true; // Set this to false for release builds
    }

    @Singleton
    @Provides
    public static HttpService provideHttpService(@HiltQualifier(true) boolean isDebug) {
        return new HttpService(isDebug);
    }

    @Singleton
    @Provides
    public static ApiException provideApiException(@HiltQualifier(true) boolean isDebug) {
        return new ApiException("", isDebug);
    }

    @Singleton
    @Provides
    public static AlbumDataSource provideAlbumDataSource(HttpService httpService) {
        return new AlbumRemoteDataSource(httpService);
    }

    @Singleton
    @Provides
    public static AlbumRepository provideAlbumRepository(AlbumDataSource albumDataSource) {
        return new AlbumRepositoryImpl(albumDataSource);
    }

    @Singleton
    @Provides
    public static AlbumUseCase provideAlbumUseCase(AlbumRepository albumRepository) {
        return new AlbumUseCase(albumRepository);
    }

    @Singleton
    @Provides
    public static AlbumViewModel provideAlbumViewModel(AlbumUseCase albumUseCase) {
        return new AlbumViewModel(albumUseCase);
    }

    @Singleton
    @Provides
    public static AlbumAdapter provideAlbumAdapter() {
        return new AlbumAdapter();
    }
}
