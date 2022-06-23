package com.jpmc.theater.utils;

import java.time.LocalDate;

public class DefaultLocalDateProvider implements LocalDateProvider {
    private static DefaultLocalDateProvider instance = null;

    /**
     * @return make sure to return singleton instance
     */
    public static DefaultLocalDateProvider singleton() {
        if (instance == null) {
            synchronized (DefaultLocalDateProvider.class) {
                if (instance == null) {
                    instance = new DefaultLocalDateProvider();
                }
            }
        }
        return instance;
    }

    public LocalDate currentDate() {
        return LocalDate.now();
    }

    // no instances allowed
    private DefaultLocalDateProvider() {
    }
}
