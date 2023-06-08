package app;

public class population {
    private int startYear;
    private int endYear;
    private long startPopulation;
    private long endPopulation;
    private int yearRange;

    public population() {
        this.startYear = 0;
        this.endYear = 0;
        this.startPopulation = 0;
        this.endPopulation = 0;
        this.yearRange = 0;
    }

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

    public int getYearRange(){
        yearRange = getEndYear() - getStartYear();
        return yearRange;
    }
}
