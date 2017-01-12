package com.chuckree.chucktheshow;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Srichakradhar on 08-01-2017.
 */

class FetchMovieTask extends AsyncTask<String, Void, JSONArray> {



    protected void onPreExecute() {

    }

    protected JSONArray doInBackground(final String... args) {

        // get JSON data from URL
        return getJSONFromUrl(args[0]);
    }

    private JSONArray getJSONFromUrl(String urlString) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        JSONArray jarray = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;

        try {

            URL url = new URL(urlString);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        try {
            jarray = (new JSONObject(movieJsonStr)).getJSONArray("results");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jarray;
    }
}