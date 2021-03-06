package com.chuckree.chucktheshow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chuckree.chucktheshow.models.MovieResult;
import com.chuckree.chucktheshow.models.MoviesResponse;
import com.squareup.picasso.Picasso;

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
import java.util.List;

/**
 * Created by Srichakradhar on 23-08-2016.
 */
class PosterAdapter extends BaseAdapter {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<String> posterPaths;
    private Context context;
    private static LayoutInflater inflater = null;

    PosterAdapter(MainActivity mainActivity, List<MovieResult> movieResults) {

        posterPaths = new ArrayList<>();

        for (MovieResult movieResult : movieResults)
            posterPaths.add(movieResult.getPoster_path());

        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return posterPaths.size();
    }

    @Override
    public Object getItem(int i) {
        return posterPaths.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        PosterViewHolder posterViewHolder;
        if (view == null) {
            posterViewHolder = new PosterViewHolder();
            view = inflater.inflate(R.layout.layout_poster, viewGroup, false);
            posterViewHolder.posterImageView = (ImageView) view.findViewById(R.id.posterImageView);
            view.setTag(posterViewHolder);
        }else posterViewHolder = (PosterViewHolder) view.getTag();

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + posterPaths.get(i)).into(posterViewHolder.posterImageView);

        return view;
    }
}
