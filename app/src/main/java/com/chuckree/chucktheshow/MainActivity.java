package com.chuckree.chucktheshow;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    static JSONArray jarray = null;
    GridView moviesGridView;
    boolean isConnected;
    Context context;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                if (isConnected)
                    (new FetchMovieTask()).execute("https://api.themoviedb.org/3/movie/popular?api_key=a0d9f39596f4944adb268f1743681253");
                else
                    Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
                return true;
            case R.id.highest_rated:
                if (isConnected)
                    (new FetchMovieTask()).execute("https://api.themoviedb.org/3/movie/top_rated?api_key=a0d9f39596f4944adb268f1743681253");
                else
                    Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
                return true;
        }
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
        FetchMovieTask movieTask = new FetchMovieTask();

        if (isConnected)

            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        int statusCode = response.code();
                        List<MovieResult> movies = response.body().getResults();
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
                jarray = movieTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=a0d9f39596f4944adb268f1743681253").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        else
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();

        moviesGridView.setAdapter(new PosterAdapter(MainActivity.this, jarray));

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent movieIntent = new Intent(context, MovieDetails.class);
                try {
                    movieIntent.putExtra("JSON ARRAY", jarray.getJSONObject(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(movieIntent);
            }
        });
    }

}
