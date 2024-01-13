package com.rula.etime;

import java.time.ZonedDateTime;

public class EasyTime {
    Month month;
    private byte monthn;
    private boolean visokosniy = false;
    private final float time;
    private final long year = 31536000000L;
    private final long day = 86400000L;
    private long systime;
    public EasyTime(float time){
        this.time=time;
    }
    public EasyTime(){
        String[] array = ZonedDateTime.now().toString().split("\\+")[1].split("\\[")[0].split(":");
        float hours = Float.parseFloat(array[0]);
        hours = hours + Float.parseFloat("0."+array[1]);
        this.time = hours;
    }
    public TimeData getTime(){
        byte count = 0;
        float countyears = 1973;
        float countdays = 0;
        float counthours = time;
        float countminutes = 0;
        float countseconds = 0;
        systime=System.currentTimeMillis();
        systime-=(3*year+day);
        while (systime >= year) {
            if (systime == 0) {
                break;
            }if (count == 3) {
                systime -= (year + day);
                count = 0;
                visokosniy = true;
            } else {
                systime -= year;
                count++;
                visokosniy = false;
            }
            countyears++;
        }
        countdays = iter(day,countdays);

        if (countdays<=31){
            month = Month.JANUARY;
            monthn = 1;
        }else if (visokosniy && countdays-31<=29) {
            countdays-=31;
            month = Month.FEBRUARY;
            monthn = 2;
        }else if (visokosniy && countdays-31-29<=31) {
            countdays-=(31+29);
            month = Month.MARCH;
            monthn = 3;
        }else if (visokosniy && countdays-31-29-31<=30) {
            countdays-=(31+29+31);
            month = Month.APRIL;
            monthn = 4;
        }else if (visokosniy && countdays-31-29-31-30<=31) {
            countdays-=(31+29+31+30);
            month = Month.MAY;
            monthn = 5;
        }else if (visokosniy && countdays-31-29-31-30-31<=30) {
            countdays-=(31+29+31+30+31);
            month = Month.JUNE;
            monthn = 6;
        }else if (visokosniy && countdays-31-29-31-30-31-30<=31) {
            countdays-=(31+29+31+30+31+30);
            month = Month.JULE;
            monthn = 7;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31<=31) {
            countdays-=(31+29+31+30+31+30+31);
            month = Month.AUGUST;
            monthn = 8;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31<=30) {
            countdays-=(31+29+31+30+31+30+31+31);
            month = Month.SEPTEMBER;
            monthn = 9;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31-30<=31) {
            countdays-=(31+29+31+30+31+30+31+31+30);
            month = Month.OCTOBER;
            monthn = 10;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31-30-31<=30) {
            countdays-=(31+29+31+30+31+30+31+31+30+31);
            month = Month.NOVEMBER;
            monthn = 11;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31-30-31-30<=31) {
            countdays-=(31+29+31+30+31+30+31+31+30+31+30);
            month = Month.DECEMBER;
            monthn = 12;
        }else if (countdays-31<=28) {
            countdays-=31;
            month = Month.FEBRUARY;
            monthn = 2;
        }else if (countdays-31-28<=31) {
            countdays-=(31+28);
            month = Month.MARCH;
            monthn = 3;
        }else if (countdays-31-28-31<=30) {
            countdays-=(31+28+31);
            month = Month.APRIL;
            monthn = 4;
        }else if (countdays-31-28-31-30<=31) {
            countdays-=(31+28+31+30);
            month = Month.MAY;
            monthn = 5;
        }else if (countdays-31-28-31-30-31<=30) {
            countdays-=(31+28+31+30+31);
            month = Month.JUNE;
            monthn = 6;
        }else if (countdays-31-28-31-30-31-30<=31) {
            countdays-=(31+28+31+30+31+30);
            month = Month.JULE;
            monthn = 7;
        }else if (countdays-31-28-31-30-31-30-31<=31) {
            countdays-=(31+28+31+30+31+30+31);
            month = Month.AUGUST;
            monthn = 8;
        }else if (countdays-31-28-31-30-31-30-31-31<=30) {
            countdays-=(31+28+31+30+31+30+31+31);
            month = Month.SEPTEMBER;
            monthn = 9;
        }else if (countdays-31-28-31-30-31-30-31-31-30<=31) {
            countdays-=(31+28+31+30+31+30+31+31+30);
            month = Month.OCTOBER;
            monthn = 10;
        }else if (countdays-31-28-31-30-31-30-31-31-30-31<=30) {
            countdays-=(31+28+31+30+31+30+31+31+30+31);
            month = Month.NOVEMBER;
            monthn = 11;
        }else if (countdays-31-28-31-30-31-30-31-31-30-31-30<=31) {
            countdays-=(31+28+31+30+31+30+31+31+30+31+30);
            month = Month.DECEMBER;
            monthn = 12;
        }

        counthours = iter(day/24,counthours);
        if (counthours%1!=0){
            systime+=(counthours%1*60*60*1000);
            counthours-=counthours%1;

        }if (counthours>24){
            counthours-=24;
            countdays++;
        }

        countminutes = iter(day/24/60,countminutes);
        countseconds = iter(1000,countseconds);

        return new TimeData((int)countyears, visokosniy, month, monthn, (int)countdays+1, (int)counthours, (int)countminutes, (int)countseconds, (int)systime);
    }
    public TimeData getConvTime(long systime){
        byte count = 0;
        float countyears = 1973;
        float countdays = 0;
        float counthours = time;
        float countminutes = 0;
        float countseconds = 0;
        this.systime=systime-(3*year+day);
        while (this.systime >= year) {
            if (this.systime == 0) {
                break;
            }if (count == 3) {
                this.systime -= (year + day);
                count = 0;
                visokosniy = true;
            } else {
                this.systime -= year;
                count++;
                visokosniy = false;
            }
            countyears++;
        }
        countdays = iter(day,countdays);

        if (countdays<=31){
            month = Month.JANUARY;
            monthn = 1;
        }else if (visokosniy && countdays-31<=29) {
            countdays-=31;
            month = Month.FEBRUARY;
            monthn = 2;
        }else if (visokosniy && countdays-31-29<=31) {
            countdays-=(31+29);
            month = Month.MARCH;
            monthn = 3;
        }else if (visokosniy && countdays-31-29-31<=30) {
            countdays-=(31+29+31);
            month = Month.APRIL;
            monthn = 4;
        }else if (visokosniy && countdays-31-29-31-30<=31) {
            countdays-=(31+29+31+30);
            month = Month.MAY;
            monthn = 5;
        }else if (visokosniy && countdays-31-29-31-30-31<=30) {
            countdays-=(31+29+31+30+31);
            month = Month.JUNE;
            monthn = 6;
        }else if (visokosniy && countdays-31-29-31-30-31-30<=31) {
            countdays-=(31+29+31+30+31+30);
            month = Month.JULE;
            monthn = 7;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31<=31) {
            countdays-=(31+29+31+30+31+30+31);
            month = Month.AUGUST;
            monthn = 8;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31<=30) {
            countdays-=(31+29+31+30+31+30+31+31);
            month = Month.SEPTEMBER;
            monthn = 9;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31-30<=31) {
            countdays-=(31+29+31+30+31+30+31+31+30);
            month = Month.OCTOBER;
            monthn = 10;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31-30-31<=30) {
            countdays-=(31+29+31+30+31+30+31+31+30+31);
            month = Month.NOVEMBER;
            monthn = 11;
        }else if (visokosniy && countdays-31-29-31-30-31-30-31-31-30-31-30<=31) {
            countdays-=(31+29+31+30+31+30+31+31+30+31+30);
            month = Month.DECEMBER;
            monthn = 12;
        }else if (countdays-31<=28) {
            countdays-=31;
            month = Month.FEBRUARY;
            monthn = 2;
        }else if (countdays-31-28<=31) {
            countdays-=(31+28);
            month = Month.MARCH;
            monthn = 3;
        }else if (countdays-31-28-31<=30) {
            countdays-=(31+28+31);
            month = Month.APRIL;
            monthn = 4;
        }else if (countdays-31-28-31-30<=31) {
            countdays-=(31+28+31+30);
            month = Month.MAY;
            monthn = 5;
        }else if (countdays-31-28-31-30-31<=30) {
            countdays-=(31+28+31+30+31);
            month = Month.JUNE;
            monthn = 6;
        }else if (countdays-31-28-31-30-31-30<=31) {
            countdays-=(31+28+31+30+31+30);
            month = Month.JULE;
            monthn = 7;
        }else if (countdays-31-28-31-30-31-30-31<=31) {
            countdays-=(31+28+31+30+31+30+31);
            month = Month.AUGUST;
            monthn = 8;
        }else if (countdays-31-28-31-30-31-30-31-31<=30) {
            countdays-=(31+28+31+30+31+30+31+31);
            month = Month.SEPTEMBER;
            monthn = 9;
        }else if (countdays-31-28-31-30-31-30-31-31-30<=31) {
            countdays-=(31+28+31+30+31+30+31+31+30);
            month = Month.OCTOBER;
            monthn = 10;
        }else if (countdays-31-28-31-30-31-30-31-31-30-31<=30) {
            countdays-=(31+28+31+30+31+30+31+31+30+31);
            month = Month.NOVEMBER;
            monthn = 11;
        }else if (countdays-31-28-31-30-31-30-31-31-30-31-30<=31) {
            countdays-=(31+28+31+30+31+30+31+31+30+31+30);
            month = Month.DECEMBER;
            monthn = 12;
        }

        counthours = iter(day/24,counthours);
        if (counthours%1!=0){
            this.systime+=(counthours%1*60*60*1000);
            counthours-=counthours%1;

        }if (counthours>24){
            counthours-=24;
            countdays++;
        }

        countminutes = iter(day/24/60,countminutes);
        countseconds = iter(1000,countseconds);

        return new TimeData((int)countyears, visokosniy, month, monthn, (int)countdays+1, (int)counthours, (int)countminutes, (int)countseconds, (int)this.systime);
    }
    private float iter(long coint, float count){
        while(systime >= coint){
            systime -= coint;
            count++;
        }
        return count;
    }
}
