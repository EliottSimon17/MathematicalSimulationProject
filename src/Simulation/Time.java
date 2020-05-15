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
        this(seconds % 60, (int)seconds / 60);
    }

    /**
     * Parametric constructor
     * @param seconds
     * @param minutes
     */
    public Time(double seconds, int minutes) {
        this(seconds %60, (minutes + (int)seconds/60) % 60, (minutes +(int) seconds/60) / 60);
    }

    /**
     * Parametric constructor
     * @param seconds
     * @param minutes
     * @param hours
     */
    public Time(double seconds, int minutes, int hours) {
        this(seconds%60, (minutes + (int) seconds/60)%60, (hours + (minutes + (int) seconds/60)/60) % 24, (hours + (minutes + (int) seconds/60)/60) / 24);
    }

    /**
     * Parametric constructor
     * @param seconds
     * @param minutes
     * @param hours
     * @param days
     */
    public Time(double  seconds, int minutes, int hours, int days) {
        this.seconds = seconds%60;
        this.minutes = (minutes+(int)seconds/60)%60;
        this.hours = (hours +(minutes+(int)seconds/60)/60)%24;
        this.days = days +(hours +(minutes+(int)seconds/60)/60)/24;
    }

    /**
     * @return an array containing all the values of Time
     */
    public double[] getTime() {
        return new double[] {this.days, this.hours, this.minutes, this.seconds};
    }

    public void setTime(double  seconds, int minutes, int hours, int days) {
        this.seconds = seconds%60;
        this.minutes = (minutes+(int)seconds/60)%60;
        this.hours = (hours +(minutes+(int)seconds/60)/60)%24;
        this.days = days +(hours +(minutes+(int)seconds/60)/60)/24;
    }

    /**
     * Update the date, taking seconds in input
     * @param timePast seconds passed since last update
     */
    public void updateTime(double timePast) {
        this.seconds += timePast%60;
        this.minutes += (int) timePast/60;

        if (this.seconds >= 60) {
            this.minutes += (int) this.seconds/60;
            this.seconds = this.seconds%60;
        }

        if (this.minutes >= 60) {
            this.hours += this.minutes/60;
            this.minutes = this.minutes%60;
        }

        if (this.hours > 23) {
            this.days += this.hours/24;
            this.hours = this.hours%24;
        }

    }

    /**
     * Update the time, with Time object as input
     * @param timePast time past since last update
     */
    public void updateTime(Time timePast) {
        this.seconds += timePast.getSeconds();
        if (this.seconds >= 60) {
            this.minutes += (int) this.seconds/60;
            this.seconds = this.seconds%60;
        }

        this.minutes += timePast.getMinutes();
        if (this.minutes >= 60) {
            this.hours += this.minutes/60;
            this.minutes = this.minutes%60;
        }

        this.hours+= timePast.getHours();
        if (this.hours > 23) {
            this.days += hours/24;
            this.hours = this.hours%24;
        }

        this.days+=timePast.getDays();

    }

    /**
     *
     * @return Time in seconds
     */
    public double toSeconds() {
        return ((this.days*24 + this.hours)*60 + this.minutes)*60 + this.seconds;
    }

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

    public String toString() {
        return "days " + this.days +
                ", hours " + this.hours +
                ", minutes " + this.minutes +
                ", seconds " + this.seconds;
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
