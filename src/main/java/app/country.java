package app;

public class country {
    private String countryCode;
    private int year;
    private double avgTemp;
    private long countryPopulation;
    private double tempPercentageChange;
    private double popPercentageChange;
    private double correlationValue;
    private int timePeriod;
    private double diffTemp;

    public country() {
        this.countryCode = "ZZZZ";
        this.year = 0;
        this.avgTemp =0;
        this.countryPopulation =0;
        this.tempPercentageChange = 0;
        this.popPercentageChange = 0;
        this.correlationValue = 0;
    }

    //Setter methods

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAvgTemp(Double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public void setCountryPopulation(long countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    public void setTempPercentageChange(Double tempPercentageChange) {
        this.tempPercentageChange = tempPercentageChange;
    }

    public void setPopPercentageChange(Double popPercentageChange) {
        this.popPercentageChange = popPercentageChange;
    }

    public void setCorrelationValue(Double correlationValue) {
        this.correlationValue = correlationValue;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setDiffTemp(double diffTemp) {
        this.diffTemp = diffTemp;
    }

    //Getter Methods

    public String getCountryCode() {
        return countryCode;
    }

    public int getYear() {
        return year;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public long getCountryPopulation() {
        return countryPopulation;
    }

    public double getTempPercentageChange() {
        return tempPercentageChange;
    }

    public double getPopPercentageChange() {
        return popPercentageChange;
    }

    public double getCorrelationValue() {
        return correlationValue;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public double getDiffTemp() {
        return diffTemp;
    }
}
