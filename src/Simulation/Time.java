package Simulation;

public class Time {
    private int days;
    private int hours;
    private int minutes;
    private double seconds;

    /**
     * Default constructor
     */
    public Time() {
        this(0, 0, 0, 0);
    }

    /**
     * Parametric constructor
     * @param seconds
     */
    public Time(double seconds) {
        this(seconds % 60, ((int)seconds)/ 60);
    }

    /**
     * Parametric constructor
     * @param seconds
     * @param minutes
     */
    public Time(double seconds, int minutes) {
        this(seconds, minutes, minutes / 60);
    }

    /**
     * Parametric constructor
     * @param seconds
     * @param minutes
     * @param hours
     */
    public Time(double seconds, int minutes, int hours) {
        this(seconds, minutes, hours % 24, hours / 24);
    }

    /**
     * Parametric constructor
     * @param seconds
     * @param minutes
     * @param hours
     * @param days
     */
    public Time(double  seconds, int minutes, int hours, int days) {
        this.seconds = seconds % 60;
        this.minutes = (minutes+((int)seconds)/60)%60;
        this.hours = (hours +(minutes+((int)seconds)/60)/60)%24;
        this.days = days +(hours +(minutes+((int)seconds)/60)/60)/24;
    }

    /**
     * @return an array containing all the values of Time
     */
    public double[] getTime() {
        return new double[] {this.days, this.hours, this.minutes, this.seconds};
    }

    public void setTime(double  seconds, int minutes, int hours, int days) {
        this.seconds = seconds%60;
        this.minutes = (minutes+((int)seconds)/60)%60;
        this.hours = (hours +(minutes+((int)seconds)/60)/60)%24;
        this.days = days +(hours +(minutes+((int)seconds)/60)/60)/24;
    }

    /**
     * Update the date, taking seconds in input
     * @param timePast seconds passed since last update
     */
    public void updateTime(double timePast) {
        this.addSeconds(timePast);
        this.addMinutes(0);
        this.addHours(0);
    }

    /**
     * Update the time, with Time object as input
     * @param timePast time past since last update
     */
    public void updateTime(Time timePast) {
        this.addSeconds(timePast.getSeconds());
        this.addMinutes(timePast.getMinutes());
        this.addHours(timePast.getHours());
        this.days += timePast.getDays();
    }

    /**
     *
     * @return the CSA shift. 1, 2 or 3
     */
    public int getCSAShift() {
        if (this.hours >= 6 && this.hours < 14)
            return 1;
        else if (this.hours >= 14 && this.hours < 22)
            return 2;
        else
            return 3;
    }

    /**
     *
     * @return Time in seconds
     */
    public double toSeconds() {
        return ((this.days*24 + this.hours)*60 + this.minutes)*60 + this.seconds;
    }

    public String toString() {
        return "days " + this.days +
                ", hours " + this.hours +
                ", minutes " + this.minutes +
                ", seconds " + this.seconds;
    }

    public void add(Time t) {
        this.addSeconds(t.getSeconds());
        this.addMinutes(t.getMinutes());
        this.addHours(t.getHours());
        this.days += t.getDays();
    }

    /**
     * adds seconds to Time t
     * And corrects the value of seconds and minutes, if seconds bigger than 59
     * @param s
     */
    public void addSeconds(double s) {
        this.seconds += s;
        if (this.seconds >= 60.0) {
            this.seconds = this.seconds%60;
            this.minutes += (int) this.seconds/60;
        }
        this.addMinutes(0);
    }

    /**
     * Adds minutes m to Time t
     * And corrects the value of minutes and hours, if minutes is bigger than 59
     * @param m
     */
    public void addMinutes(int m) {
        this.minutes += m;
        if (this.minutes >= 60) {
            this.minutes = this.minutes%60;
            this.hours +=  this.minutes/60;
        }
        this.addHours(0);
    }

    /**
     * adds hours to Time t
     * And corrects the value of hours and days, if hours bigger than 23
     * @param h hours to be added to
     */
    public void addHours(int h) {
        this.hours += h;
        if (this.hours > 23) {
            this.hours = this.hours%24;
            this.days += this.days/24;
        }
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

    public double getSeconds() {
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
