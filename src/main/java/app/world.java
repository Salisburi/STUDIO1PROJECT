package app;

public class world {
    private String worldCode;
    private int year;
    private double avgTemp;
    private long worldPopulation;
    private double tempPercentageChange;
    private double popPercentageChange;
    private double correlationValue;

    public world() {
        this.worldCode = "ZZZZ";
        this.year = 0;
        this.avgTemp = 0;
        this.worldPopulation = 0;
        this.tempPercentageChange = 0;
        this.popPercentageChange = 0;
        this.correlationValue =0;
    }

    //Setter methods

    public void setWorldCode(String worldCode) {
        this.worldCode = worldCode;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAvgTemp(Double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public void setWorldPopulation(long worldPopulation) {
        this.worldPopulation = worldPopulation;
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

    //Getter Methods

    public String getWorldCode() {
        return worldCode;
    }

    public int getYear() {
        return year;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public long getWorldPopulation() {
        return worldPopulation;
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
}
