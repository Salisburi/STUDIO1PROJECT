package app;

public class globaltemp {
    private int startYear;
    private int endYear;
    private double startTemp;
    private double endTemp;
    private int yearRange;

    public  globaltemp() {
        this.startYear = 0;
        this.endYear = 0;
        this.startTemp = 0;
        this.endTemp = 0;
        this.yearRange = 0;
    }
    //getter and setter methods

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setStartTemp(Double startTemp) {
        this.startTemp = startTemp;
    }

    public void setEndTemp(Double endTemp) {
        this.endTemp = endTemp;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public double getStartTemp() {
        return startTemp;
    }

    public double getEndTemp(){
        return endTemp;
    }

    public int getYearRange(){
        yearRange = getEndYear() - getStartYear();
        return yearRange;
    }
}
