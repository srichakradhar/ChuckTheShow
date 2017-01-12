package com.chuckree.chucktheshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.transfuse.annotations.Bind;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();

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
        JSONObject jObject;
        try {
            jObject = new JSONObject(b.getString("JSON ARRAY"));
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185" + jObject.getString("poster_path")).into(posterImageView);
            titleTextView.setText(jObject.getString("title"));
            releaseDate.setText(getString(R.string.Release) + jObject.getString("release_date"));
            voteAverage.setText(getString(R.string.VoteAverage) + jObject.getString("vote_average"));
            plotSynopsis.setText(jObject.getString("overview"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
