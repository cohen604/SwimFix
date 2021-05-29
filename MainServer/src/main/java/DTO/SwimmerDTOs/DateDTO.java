package DTO.SwimmerDTOs;

import java.time.LocalDateTime;

public class DateDTO {

    private int year;
    private int month;
    private int day;

    public DateDTO(LocalDateTime localDateTime) {
        this.year = localDateTime.getYear();
        this.month = localDateTime.getMonthValue();
        this.day = localDateTime.getDayOfMonth();
    }

    public DateDTO(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}