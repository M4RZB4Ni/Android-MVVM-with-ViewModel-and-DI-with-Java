package com.marzbani.adjoetask.infrastructure.network;

import android.util.Log;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

public class HttpService {

    private final boolean isDebug;

    @Inject
    public HttpService(boolean isDebug) {
        // Constructor for dependency injection
        this.isDebug = isDebug;
    }

    public String sendHttpRequest(String url, String method, String requestBody) throws ApiException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            // Create URL object
            URL apiUrl = new URL(url);

            // Open connection
            urlConnection = (HttpURLConnection) apiUrl.openConnection();
            urlConnection.setRequestMethod(method);

            if (method.equals("POST")) {
                urlConnection.setDoOutput(true);

                // Write request body for POST requests
                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            // Get the response code
            int responseCode = urlConnection.getResponseCode();
            Log.d("HttpService", "HTTP response code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response in a background thread
                try (InputStream inputStream = urlConnection.getInputStream()) {
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                }
            } else {
                throw new ApiException("HTTP error code: " + responseCode, isDebug);
            }

        } catch (IOException e) {
            // Log the error in debug mode
            if (isDebug) {
                Log.e("HttpService", "Debug mode error: " + e.getMessage());
            }

            throw new ApiException(e.getMessage(), isDebug);
        } finally {
            // Close resources
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response.toString();
    }

    // Example method for sending GET requests
    public String sendHttpGetRequest(String url) throws ApiException {
        return sendHttpRequest(url, "GET", null);
    }

    // Example method for sending POST requests
    public String sendHttpPostRequest(String url, String requestBody) throws ApiException {
        return sendHttpRequest(url, "POST", requestBody);
    }
}
