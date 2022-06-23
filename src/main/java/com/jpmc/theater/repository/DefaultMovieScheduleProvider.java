package com.jpmc.theater.repository;

import com.jpmc.theater.utils.DefaultLocalDateProvider;
import com.jpmc.theater.Movie;
import com.jpmc.theater.MovieType;
import com.jpmc.theater.Showing;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DefaultMovieScheduleProvider implements MovieScheduleProvider{
    
    final DefaultLocalDateProvider dateProvider;

    public DefaultMovieScheduleProvider(DefaultLocalDateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public List<Showing> getMovieShowings() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, MovieType.SPECIAL);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9);
        LocalDate localDate = dateProvider.currentDate();
        return List.of(
                new Showing(turningRed, 1, LocalDateTime.of(localDate, LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(localDate, LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(localDate, LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(localDate, LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(localDate, LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(localDate, LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(localDate, LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(localDate, LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(localDate, LocalTime.of(23, 0)))
        );
    }
}
