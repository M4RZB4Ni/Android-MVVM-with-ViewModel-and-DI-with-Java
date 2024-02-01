package com.marzbani.adjoetask.presentation.home.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marzbani.adjoetask.R;
import com.marzbani.adjoetask.infrastructure.Constants;
import com.marzbani.adjoetask.infrastructure.NotificationUtil;
import com.marzbani.adjoetask.infrastructure.StringUtil;
import com.marzbani.adjoetask.infrastructure.services.UserPresentReceiver;
import com.marzbani.adjoetask.presentation.home.adapter.AlbumAdapter;
import com.marzbani.adjoetask.presentation.home.viewModel.AlbumViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    Handler mHandler;
    Runnable runnable;
    SharedPreferences sharedPreferences;

    private AlbumViewModel albumViewModel;
    private AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBackgrounds();
        setupNotificationPermission();
        setupViewModel();
        setupRecyclerView();
        observeViewModel();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mHandler != null && runnable != null){
            mHandler.removeCallbacks(runnable);
        }
    }


    private void setupBackgrounds()
    {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        if (!sharedPreferences.contains(Constants.PHONE_UNLOCK_TIME)){
            sharedPreferences.edit().putLong(Constants.PHONE_UNLOCK_TIME, System.currentTimeMillis()).apply();
        }

        mHandler = new Handler();
    }
    private void setupNotificationPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission();
            } else {
                // Permission is already granted, you can proceed with your method
                registerReceiver(
                        new UserPresentReceiver(),new IntentFilter(Constants.USER_PRESENT) );
            }
        } else {
            // For versions below Android 8, no special notification permission is needed
            // Proceed with your method
            registerReceiver(
                    new UserPresentReceiver(),new IntentFilter(Constants.USER_PRESENT) );
        }
    }
    private void setupViewModel() {
        albumViewModel = new ViewModelProvider(this).get(AlbumViewModel.class);
        albumViewModel.fetchAlbums();
    }



        private void setupRecyclerView () {
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            albumAdapter = new AlbumAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(albumAdapter);
        }

        private void observeViewModel () {
            albumViewModel.observeSortedAlbums().observe(this, albums -> {
                // Update the UI with the new list of albums
                albumAdapter.setAlbums(albums);
            });
        }
    private void showOpenSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("Please grant the notification permission in settings.")
                .setPositiveButton("Open Settings", (dialog, which) -> {
                    // Open app settings
//                    intent.setData(Uri.parse(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
            // Explain to the user why the permission is needed
            showExplanationDialog();
        } else {
            // Request the permission
            requestPermission();

        }
    }
    private void showExplanationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This permission is required for notifications. Please grant the permission.")
                .setPositiveButton("Grant", (dialog, which) -> {
                    // Request the permission
                    requestPermission();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // User declined, show a message or take appropriate action
                    Toast.makeText(MainActivity.this, "Permission declined", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with your method
                registerReceiver(
                        new UserPresentReceiver(),new IntentFilter("android.intent.action.USER_PRESENT") );
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                // Optionally, you can prompt the user to open settings and grant the permission manually
//                showOpenSettingsDialog();
            }
        }
    }

    private void setTimer() {

        runnable = new Runnable() {
            @Override
            public void run() {

                long time = sharedPreferences.getLong(Constants.PHONE_UNLOCK_TIME, System.currentTimeMillis());
                NotificationUtil.showNotification(MainActivity.this, StringUtil.formatTime(time));
                mHandler.postDelayed(this, 5000);
            }
        };

        mHandler.post(runnable);
    }


    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                123);
    }




    }
