package app;

public class global {
    private String conutryCode;
    private int year;
    private int timePeriod;
    private double avgTemp;
    private double diffTemp;

    public global() {
        this.conutryCode = "WLD";
        this.year = 0;
        this.timePeriod = 0;
        this.avgTemp = 0;
        this.diffTemp = 0;
    }

    //Setter methods

    public void setCountryCode(String countryCode) {
        this.conutryCode = countryCode;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public void setDiffTemp(double diffTemp) {
        this.diffTemp = diffTemp;
    }

    //Getter methods

    public String getCountryCode() {
        return conutryCode;
    }

    public int getYear() {
        return year;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getDiffTemp() {
        return diffTemp;
    }
}
