package com.jpmc.theater;

import com.google.gson.Gson;
import com.jpmc.theater.repository.DefaultMovieScheduleProvider;
import com.jpmc.theater.repository.MovieScheduleProvider;
import com.jpmc.theater.utils.DefaultLocalDateProvider;
import com.jpmc.theater.utils.LocalDateProvider;
import org.apache.commons.lang3.Validate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Theater {

    private LocalDateProvider localDateProvider;
    private List<Showing> schedule;

    public Theater(MovieScheduleProvider movieScheduleProvider, LocalDateProvider localDateProvider) {
        this.localDateProvider = localDateProvider;
        schedule = movieScheduleProvider.getMovieShowings();
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        int index = sequence - 1;
        Validate.isTrue(index >= 0 && index < schedule.size() - 1, "not able to find any showing for given sequence " + sequence);
        Showing showing = schedule.get(index);
        return new Reservation(customer, showing, howManyTickets);
    }

    // kept for backward compatibility
    public void printSchedule(){
        System.out.println(scheduleInFormat(MovieListingFormat.TEXT));
    }

    public String scheduleInFormat(MovieListingFormat format) {
        if (format == MovieListingFormat.TEXT) {
            return scheduleTextFormat();
        } else  if (format == MovieListingFormat.JSON){
            return scheduleJsonFormat();
        } else{
            throw new IllegalArgumentException("Unimplemented  display format");
        }

    }

    public String scheduleTextFormat() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(localDateProvider.currentDate()).append("\n");
        buffer.append("===================================================").append("\n");
        schedule.forEach(s ->
                buffer.append(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee()).append("\n")
        );
        buffer.append("===================================================").append("\n");
        return buffer.toString();
    }

    public String scheduleJsonFormat() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("date", formatDate(localDateProvider.currentDate()));
        jsonMap.put("schedules",
        schedule.stream()
                .map(s -> {
                    Map<String, Object> movieMap = new HashMap<>();
                    movieMap.put("sequenceOfDay", s.getSequenceOfTheDay());
                    movieMap.put("startTime", formatDateTime(s.getStartTime()));
                    movieMap.put("title", s.getMovie().getTitle());
                    movieMap.put("runningTime", humanReadableFormat(s.getMovie().getRunningTime()));
                    movieMap.put("runningTime"," $" + s.getMovieFee());
                    return movieMap;
                }).collect(Collectors.toList())
        );
        Gson gson = new Gson();
        return gson.toJson(jsonMap);
    }


    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());
        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        return value == 1 ? "" : "s";
    }

    private String formatDateTime(LocalDateTime ll){
        return DateTimeFormatter.ISO_DATE_TIME.format(ll);
    }

    private String formatDate(LocalDate ll){
        return DateTimeFormatter.ISO_DATE.format(ll);
    }

    public enum MovieListingFormat{
        TEXT, JSON
    }

    public static void main(String[] args) {
        DefaultLocalDateProvider dateProvider = DefaultLocalDateProvider.singleton();
        Theater theater = new Theater(new DefaultMovieScheduleProvider(dateProvider), dateProvider);
        theater.printSchedule();
    }
}
