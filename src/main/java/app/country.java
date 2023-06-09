package app;

public class country {
    private String countryCode;
    private int startYear;
    private int endYear;
    private int avgTemp;
    private long countryPopulation;

    public country() {
        this.countryCode = "ZZZZ";
        this.startYear = 0;
        this.endYear = 0;
        this.avgTemp =0;
        this.countryPopulation =0;
    }

    //Setter methods

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setAvgTemp(int avgTemp) {
        this.avgTemp = avgTemp;
    }

    public void setCountryPopulation(long countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    //Getter Methods

    public String getCountryCode() {
        return countryCode;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getAvgTemp() {
        return avgTemp;
    }

    public long getCountryPopulation() {
        return countryPopulation;
    }
}
