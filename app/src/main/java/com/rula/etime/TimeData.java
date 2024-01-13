package com.rula.etime;

public class TimeData {
    private final boolean bigyear;
    private final int year, month, day, hour, minute, second, millisecond;
    private final Month monthn;
    public TimeData(int year, boolean bigyear, Month monthn, int month, int day, int hour, int minute, int second, int millisecond) {
        this.year = year;
        this.bigyear = bigyear;
        this.monthn = monthn;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
    }
    public boolean isYB() {
        return bigyear;
    }
    public int Y() {
        return year;
    }
    public int M() {
        return month;
    }
    public Month MN(){
        return monthn;
    }
    public int D() {
        return day;
    }
    public int H() {
        return hour;
    }
    public int m() {
        return minute;
    }
    public int s() {
        return second;
    }
    public int ms() {
        return millisecond;
    }
}