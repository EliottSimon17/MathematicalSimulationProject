package Simulation;

public class Time {
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public Time() {
        this(0, 0, 0, 0);
    }

    public Time(int seconds) {
        this(seconds % 60, seconds / 60);
    }

    public Time(int seconds, int minutes) {
        this(seconds, minutes % 60, minutes / 60);
    }

    public Time(int seconds, int minutes, int hours) {
        this(seconds, minutes, hours % 24, hours / 24);
    }

    public Time(int seconds, int minutes, int hours, int days) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
    }

    public int[] getTime() {
        return new int[] {this.days, this.hours, this.minutes, this.seconds};
    }

    public void setTime(int newDays, int newHours, int newMinutes, int newSeconds) {
        this.days = newDays;
        this.hours = newHours;
        this.minutes = newMinutes;
        this.seconds = newSeconds;
    }

    /**
     *
     * @param timePast seconds passed since last update
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

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }
}
