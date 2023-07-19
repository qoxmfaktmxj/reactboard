package com.app.kms.reactboard.api.mapper;

import com.ivanfranchin.movieapi.model.Movie;
import com.ivanfranchin.movieapi.rest.dto.CreateMovieRequest;
import com.ivanfranchin.movieapi.rest.dto.MovieDto;

public interface MovieMapper {

    Movie toMovie(CreateMovieRequest createMovieRequest);

    MovieDto toMovieDto(Movie movie);
}