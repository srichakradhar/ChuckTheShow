package com.chuckree.chucktheshow;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.chuckree.chucktheshow.interfaces.ApiClient;
import com.chuckree.chucktheshow.interfaces.ApiInterface;
import com.chuckree.chucktheshow.models.MovieResult;
import com.chuckree.chucktheshow.models.MoviesResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "a0d9f39596f4944adb268f1743681253";
//    private static JSONArray jarray = null;
    private GridView moviesGridView;
    private boolean isConnected;
    private Context context;
    private List<MovieResult> movies;
    private Call<MoviesResponse> call;
    private ApiInterface apiInterface;
    private Callback<MoviesResponse> moviesResponseCallback;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isConnected) {
            int i = item.getItemId();
            if (i == R.id.most_popular)
                call = apiInterface.getPopularMovies(API_KEY);
            else if (i == R.id.highest_rated)
                call = apiInterface.getTopRatedMovies(API_KEY);
            call.enqueue(moviesResponseCallback);
        }else
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        moviesGridView = (GridView) findViewById(R.id.gridView);
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        moviesResponseCallback = new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                movies = response.body().getResults();
                moviesGridView.setAdapter(new PosterAdapter(MainActivity.this, movies));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, "Failed to load movie response");
            }
        };

        if (isConnected) {

            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            call = apiInterface.getTopRatedMovies(API_KEY);
            call.enqueue(moviesResponseCallback);
        }
        else
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent movieIntent = new Intent(context, MovieDetails.class);
                movieIntent.putExtra("MOVIE_ID", movies.get(i).getId());
                startActivity(movieIntent);
            }
        });
    }

}
