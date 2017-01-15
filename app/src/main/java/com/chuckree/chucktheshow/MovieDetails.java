package com.chuckree.chucktheshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuckree.chucktheshow.interfaces.ApiClient;
import com.chuckree.chucktheshow.interfaces.ApiInterface;
import com.chuckree.chucktheshow.models.MovieResult;
import com.chuckree.chucktheshow.models.MoviesResponse;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.transfuse.annotations.Bind;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    private static final String API_KEY = "a0d9f39596f4944adb268f1743681253";

    @BindView(R.id.moviePoster) ImageView posterImageView;
    @BindView(R.id.voteAverage) TextView voteAverage;
    @BindView(R.id.titleTextView) TextView titleTextView;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.plotSynopsis) TextView plotSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getMovieDetails(b.getInt("MOVIE_ID"), API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                int statusCode = response.code();
                MovieResult movie = response.body();
                Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185" + movie.getPoster_path()).into(posterImageView);
                titleTextView.setText(movie.getTitle());
                releaseDate.setText(getString(R.string.Release) + movie.getRelease_date());
                voteAverage.setText(getString(R.string.VoteAverage) + movie.getVote_average());
                plotSynopsis.setText(movie.getOverview());
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e(TAG, "Failed to load movie details");
            }
        });
    }
}
