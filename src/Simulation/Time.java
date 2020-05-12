package Simulation;

public class Time {
    private int days;
    private int hours;

    private int minutes;
    private int seconds;
    private int[] time;

    public Time() {
        this.days = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;

        time = new int[] {this.days, this.hours, this.minutes, this.seconds};
    }

    public Time(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

        time = new int[] {this.days, this.hours, this.minutes, this.seconds};
    }

    public int[] getTime() {
        return this.time;
    }

    public void setTime(int newDays, int newHours, int newMinutes, int newSeconds) {
        this.days = newDays;
        this.hours = newHours;
        this.minutes = newMinutes;
        this.seconds = newSeconds;
    }

    /**
     *
     * @param timePast seconds pasted since last update
     */
    public void updateTime(int timePast) {
        this.seconds += timePast%60;
        this.minutes += timePast/60;

        if (this.seconds >= 60) {
            this.minutes += this.seconds/60;
            this.seconds = this.seconds%60;
        }

        if (this.minutes >= 60) {
            this.hours += this.minutes/60;
            this.minutes = this.minutes%60;
        }

        if (this.hours > 23) {
            this.days += hours/24;
            this.hours = this.hours%24;
        }
    }

    public int getSeconds() {
        return this.seconds;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

}
