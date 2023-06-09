package app;

public class population {
    private int startYear;
    private int endYear;
    private long startPopulation;
    private long endPopulation;
    private int yearRange;
    private int avgTemperature;

    public population() {
        this.startYear = 0;
        this.endYear = 0;
        this.startPopulation = 0;
        this.endPopulation = 0;
        this.yearRange = 0;
        this.avgTemperature = 0;
    }

    //Setter Methods
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setStartPopulation(long startPopulation) {
        this.startPopulation = startPopulation;
    }

    public void setEndPopulation(long endPopulation) {
        this.endPopulation = endPopulation;
    }

    public void setAvgTemp(int avgTemperature) {
        this.avgTemperature = 0;
    }

    //Getter Methods

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public long getStartPopulation() {
        return startPopulation;
    }

    public long getEndPopulation() {
        return endPopulation;
    }

    public int getAvgTemp() {
        return avgTemperature;
    }


    //Other methods
    public int getYearRange(){
        yearRange = getEndYear() - getStartYear();
        return yearRange;
    }
}
