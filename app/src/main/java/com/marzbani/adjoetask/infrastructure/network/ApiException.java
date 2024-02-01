package com.marzbani.adjoetask.infrastructure.network;


import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class ApiException extends Exception {

    private final boolean isDebug;

    public ApiException(String message, boolean isDebug) {
        super(message);
        this.isDebug = isDebug;

        if (isDebug) {
            // Log detailed error information in debug mode
            Log.e("ApiException", "Debug mode error: " + message);
        }
    }

    public boolean isDebug() {
        return isDebug;
    }

    // Example: Handle common errors based on error codes or specific conditions
    public boolean isNetworkError() {
        // Check specific exception types
        if (getCause() instanceof ConnectException || getCause() instanceof SocketTimeoutException) {
            return true;
        }

        // Check specific HTTP error codes
        if (getCause() instanceof IOException) {
            if (getCause().getMessage() != null) {
                // Check for specific error codes
                String lowerCaseMessage = getCause().getMessage().toLowerCase();
                return lowerCaseMessage.contains("connect") || lowerCaseMessage.contains("timeout");
            }
        }

        return false;
    }
    // Add more methods to handle other common error scenarios

    // Override toString to provide more information when logging
    @NonNull
    @Override
    public String toString() {
        return "ApiException{" +
                "message='" + getMessage() + '\'' +
                ", isDebug=" + isDebug +
                '}';
    }
}

