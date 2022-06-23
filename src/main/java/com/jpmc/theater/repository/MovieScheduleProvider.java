package com.jpmc.theater.repository;

import com.jpmc.theater.Showing;

import java.util.List;

public interface MovieScheduleProvider {
    List<Showing> getMovieShowings();
}
