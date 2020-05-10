package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.popularmovies.UI.MovieActivity;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.MovieEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MyViewHolder> {
    //fields
    private Context mContext;
    private List<MovieEntry> mData;
    RequestOptions option;

    //Constructor
    public MainViewAdapter(Context mContext, List<MovieEntry> mData) {
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.interstellar).error(R.drawable.interstellar);
    }

    //Override the methods
    @NonNull
    @Override
    public MainViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater myInflater = LayoutInflater.from(mContext);
        view = myInflater.inflate(R.layout.cardview_item_movie,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewAdapter.MyViewHolder holder, final int position) {
        Glide.with(mContext)
            .load(mData.get(position).getPosterPath()).apply(option)
            .into(holder.movie_poster_thumbnail);

       holder.tv_movie_title.setText(mData.get(position).getTitle());

       holder.cardviewId.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent startMovieActivity = new Intent(mContext, MovieActivity.class);

               startMovieActivity.putExtra("MovieID",mData.get(position).getMovieID());
               startMovieActivity.putExtra("MovieTitle",mData.get(position).getTitle());
               startMovieActivity.putExtra("MovieSynopsis",mData.get(position).getOverview());
               startMovieActivity.putExtra("MoviePoster",mData.get(position).getPosterPath());
               startMovieActivity.putExtra("ReleasedDate",mData.get(position).getReleaseDate());
               startMovieActivity.putExtra("MovieRating",mData.get(position).getMovieRating());
               startMovieActivity.putExtra("MovieFlag",mData.get(position).getMovieFlag());
               startMovieActivity.putExtra("IsChecked",mData.get(position).isChecked());

               mContext.startActivity(startMovieActivity);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setMovies(List<MovieEntry> movieList){
        mData = movieList;
        notifyDataSetChanged();
    }

    //MyViewHolder inner class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_poster_thumbnail;
        TextView tv_movie_title;
        CardView cardviewId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_poster_thumbnail = itemView.findViewById(R.id.movie_poster_id);
            tv_movie_title = itemView.findViewById(R.id.movie_title_id);
            cardviewId = itemView.findViewById(R.id.cardview_id);
        }



    }

}
