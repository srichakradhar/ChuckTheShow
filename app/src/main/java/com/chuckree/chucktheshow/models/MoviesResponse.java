package com.chuckree.chucktheshow.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srichakradhar on 13-01-2017.
 */

public class MoviesResponse {

    @SerializedName("page") private Integer page;
    @SerializedName("results") private List<MovieResult> results = new ArrayList<>();

    public MoviesResponse(Integer page, List<MovieResult> results){
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieResult> getResults() {
        return results;
    }

    public void setResults(List<MovieResult> results) {
        this.results = results;
    }
}
