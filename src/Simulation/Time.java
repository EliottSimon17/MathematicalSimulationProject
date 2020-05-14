package Simulation;

/* TODO:
    Needs a toString method for Machine.java (where we print the time)
        or we remove the print, but not recommended (ask Valentin if question why)

 */

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
    
    //Less/less equal by negation
    //This > >= == than the other
    //NoDay does not consider the day
    public boolean greater(Time other) {
        return this.greaterEq(other) && !this.eq(other);
    }
    
    public boolean greaterEq(Time other) {
        if(this.getDays() >= other.getDays())
            return true;
        else
            return this.greaterEqNoDay(other);
    }
    
    public boolean greaterNoDay(Time other) { 
        return this.greaterEqNoDay(other) && !this.eqNoDay(other);
    }
    
    public boolean greaterEqNoDay(Time other) {
        boolean ge = false;        
        if(this.getHours() > other.getHours()) {
            ge = true;
        }else if(this.getHours() == other.getHours()) {
            if(this.getMinutes()> other.getMinutes()) {
                ge = true; 
            }else if(this.getMinutes() == other.getMinutes()) {
                if(this.getSeconds() >= other.getSeconds()) {
                    ge = true;
                }
            }
        }
        return ge;
    }
    
    public boolean eq(Time other) { 
        return this.getDays() == other.getDays() && this.eqNoDay(other);
    }
    
    public boolean eqNoDay(Time other) {
        return (this.getHours() == other.getHours()) && (this.getMinutes()== other.getMinutes()) && (this.getSeconds()== other.getSeconds());
    }
    
    public boolean in(Time[] range) { return this.greaterEq(range[0]) && !this.greater(range[1]); }
    public boolean inNoDay(Time[] range) { return this.greaterEqNoDay(range[0]) && !this.greaterNoDay(range[1]); }
    public boolean in(Time t1, Time t2) { return this.greaterEq(t1) && !this.greater(t2); }
    public boolean inNoDay(Time t1, Time t2) { return this.greaterEqNoDay(t1) && !this.greaterNoDay(t2); }
}
