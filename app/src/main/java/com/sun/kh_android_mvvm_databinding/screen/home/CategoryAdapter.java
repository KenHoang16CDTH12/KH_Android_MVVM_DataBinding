package com.sun.kh_android_mvvm_databinding.screen.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.databinding.ItemMovieBinding;
import com.sun.kh_android_mvvm_databinding.databinding.LayoutCategoryBinding;
import com.sun.kh_android_mvvm_databinding.screen.MovieViewModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ObservableList<ObservableList<Movie>> mMovies;
    private ObservableList<String> mCategories;
    private CategoryClickListener mListener;

    public CategoryAdapter(CategoryClickListener listener) {
        mListener = listener;
        mMovies = new ObservableArrayList<>();
        mCategories = new ObservableArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        LayoutCategoryBinding categoryBinding =
                DataBindingUtil.inflate(inflater, R.layout.layout_category, viewGroup, false);
        return new ViewHolder(categoryBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mMovies.get(i), mCategories.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void update(ObservableList<ObservableList<Movie>> movies, ObservableList<String> categories) {
        mMovies = movies;
        mCategories = categories;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LayoutCategoryBinding  mCategoryBinding;
        private CategoryClickListener mListener;

        public ViewHolder(@NonNull LayoutCategoryBinding bindingLayout, CategoryClickListener listener) {
            super(bindingLayout.getRoot());
            mListener = listener;
            mCategoryBinding = bindingLayout;
            mCategoryBinding.recyclerCategory.setAdapter(
                    new MoviesAdapter((MoviesAdapter.MovieItemClickListener) mListener));
            mCategoryBinding.textCategory.setOnClickListener(this);
            mCategoryBinding.buttonViewAll.setOnClickListener(this);
        }


        public void bindData(ObservableList<Movie> movies, String category) {
            mCategoryBinding.textCategory.setText(category);
            mCategoryBinding.setCategoryMovies(movies);
        }

        @Override
        public void onClick(View view) {
            mListener.onCategoryClick(mCategoryBinding.textCategory.getText().toString());
        }

        public static class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
            private ObservableList<Movie> mMovies;
            private MovieItemClickListener mListener;
            private Context mContext;

            public MoviesAdapter(MovieItemClickListener listener) {
                mListener = listener;
                mMovies = new ObservableArrayList<>();
            }

            @Override
            public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                mContext = viewGroup.getContext();
                ItemMovieBinding binding = DataBindingUtil.inflate(
                        LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_movie,
                        viewGroup,
                        false
                );
                return new MovieViewHolder(mContext, binding, mListener);
            }

            @Override
            public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
                movieViewHolder.bindData(mMovies.get(i));
            }

            @Override
            public int getItemCount() {
                return mMovies == null ? 0 : mMovies.size();
            }

            public void update(ObservableList<Movie> movies) {
                mMovies = movies;
                notifyDataSetChanged();
            }

            public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                private ItemMovieBinding mMovieBinding;
                private MovieItemClickListener mListener;
                private Context mContext;

                public MovieViewHolder(Context context, ItemMovieBinding binding, MovieItemClickListener listener) {
                    super(binding.getRoot());
                    mMovieBinding = binding;
                    mListener = listener;
                    mContext = context;
                }

                public void bindData(Movie movie) {
                    mMovieBinding.setMovieVM(new MovieViewModel(mContext));
                    mMovieBinding.getMovieVM().setMovie(movie);
                    mMovieBinding.imageDeleteFavorities.setOnClickListener(this);
                    mMovieBinding.itemMovie.setOnClickListener(this);
                    mMovieBinding.executePendingBindings();
                }

                @Override
                public void onClick(View view) {
                    Movie movie = mMovieBinding.getMovieVM().getMovie();
                    if (view.getId() == R.id.image_delete_favorities) {
                        mListener.onFavoriteImageClick(movie);
                        mMovieBinding.getMovieVM().checkFavorite();
                        return;
                    }
                    mListener.onMovieItemClick(movie);
                }
            }

            public interface MovieItemClickListener {
                void onMovieItemClick(Movie movie);

                void onFavoriteImageClick(Movie movie);
            }
        }
    }

    public interface CategoryClickListener {
        void onCategoryClick(String type);
    }
}
