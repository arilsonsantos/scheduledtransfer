package br.com.orion.scheduledtransfer.infrastructure.utils;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateUtils {

    public static int daysBetween(LocalDate initialDate, LocalDate finalDate){
        int days = (int) DAYS.between(initialDate, finalDate);
        return days;
    }

}
