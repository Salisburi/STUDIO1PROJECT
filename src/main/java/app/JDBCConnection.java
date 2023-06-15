package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/climate.db";
    // public static final String DATABASE = "jdbc:sqlite:database/climate.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    // TODO: Add your required methods here

    //Method 1 SUB-TASKA: Returns the year ranges and temp at lowest and highest year value for global
    public globaltemp getGlobal() {
        //Create globalTemp object to return - object will contain the years and temp regarding globalYear database
        globaltemp containerGlobaltemp = new globaltemp();

        //Setup JDBC Connection
        Connection connection = null;

        try {
            //Connect to the JDBC Database
            connection = DriverManager.getConnection(DATABASE);

            //Prepare SQL query & set timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //SQL query
            String query = "SELECT * FROM GlobalTempObservation"; //Will select the entire table of the GlobalTempObservation from database

            //Execute query and store result from query
            ResultSet results = statement.executeQuery(query); //Will execute the above query and store into an "arraylist" results

            //Processing results
            //We are using the methods inside the globaltemp class to store the values we need, we will use the getter methods in the page files
            //Iterate through the resultSet, finding max and min years in the year and start and end temps

            while (results.next()) {
                int year = results.getInt("Year");
                Double temp = results.getDouble("AvgTemperature");

                //update start and end years
                if (year < containerGlobaltemp.getStartYear() || containerGlobaltemp.getStartYear() == 0) {
                    containerGlobaltemp.setStartYear(year);
                }

                if (year > containerGlobaltemp.getEndYear()) {
                    containerGlobaltemp.setEndYear(year);
                }

                //update start and end temps FIX: GETTING LOWEST VALUE AND HIGHEST, NOT FIRST VALUE AND LAST VALUE
                if (year == containerGlobaltemp.getStartYear() || containerGlobaltemp.getStartYear() == 0) {
                    containerGlobaltemp.setStartTemp(temp);
                }

                if (year == containerGlobaltemp.getEndYear()) {
                    containerGlobaltemp.setEndTemp(temp);
                }
            }

            //Close statement
            statement.close();

        }
        catch (SQLException e) {
            //If there is an error, print error
            System.err.println(e.getMessage());
        }
        finally {
            //Safety Code
            try {
                if (connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //connection close failed
                System.err.println(e.getMessage());
            }
        }

        //return object
        return containerGlobaltemp;
    }

    //Method 2 SUB-TASKA: Return the population year ranges and population value at those years
    public population getPopulation() {
        //Create population object to return - object will contain years and population data
        population population = new population();

        //Setup JDBC Connection
        Connection connection = null;

        try {
            //Connect to the JDBC Database
            connection = DriverManager.getConnection(DATABASE);

            //Prepare SQL query & set timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //SQL query
            String query = "SELECT Year, Population FROM WorldPopulation"; //Will select the entire table of the WorldPopulation from database

            //Execute query and store result from query
            ResultSet results = statement.executeQuery(query); //Will execute the above query and store into an "arraylist" results

            //Processing results
            //We are using the methods inside the globaltemp class to store the values we need, we will use the getter methods in the page files
            //Iterate through the resultSet, finding max and min years in the year

            while(results.next()) {
                int year = results.getInt("Year");
                long populationvalue = results.getLong("Population");

                //find first and last year - min and max
                if (year < population.getStartYear() || population.getStartYear() == 0) {
                    population.setStartYear(year);
                }

                if (year > population.getEndYear()) {
                    population.setEndYear(year);
                }

                //find first and last population
                if (year == population.getStartYear() || population.getStartYear() == 0) {
                    population.setStartPopulation(populationvalue);
                }

                if (year == population.getEndYear()) {
                    population.setEndPopulation(populationvalue);
                }
            }

            //Close Statement
            statement.close();
        }
        catch (SQLException e) {
            //If there is an error, print error
            System.err.println(e.getMessage());
        }
        finally {
            //Safety Code
            try {
                if (connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //connection close failed
                System.err.println(e.getMessage());
            }
        }
        //return object
        return population;
    }

    //Method 3: Level2A Store values for Region, Temperature Change, Population Change, Correlation in country arrayList
    public ArrayList<country> getCountryArrayList(String startYear, String endYear, String criterion, String sort) {
        //Create country ArrayList to return
        //User input will dictate the query.
        //ArrayList will contain the Country Code, temperature difference, population difference, % change and correlation
        //Method will iterate through the resultSet and store each of these values in an object --> object into ArrayList which is returned
        ArrayList<country> country = new ArrayList<country>();

        //Setup the variable for JDBC connection
        Connection connection = null;
        
        try {
            //Connect to the JDBC database
            connection = DriverManager.getConnection(DATABASE);

            //Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The Query
            //Filter the table to only display the following columns based on user inputted year
            //If statements to sort query using criterion and sort inputs
            String query = "SELECT " +
                "CountryCode, " +
                "TempDiff, " +
                "PopDiff, " +
                "(TempDiff / CAST(AvgTemp AS REAL)) * 100 AS TempPercentageChange, " +
                "(PopDiff / CAST(Population AS REAL)) * 100 AS PopPercentageChange, " +
                "((TempDiff / CAST(AvgTemp AS REAL)) * 100 * (PopDiff / CAST(Population AS REAL)) * 100) / 100 AS CorrelationValue " +
                "FROM " +
                "(SELECT " +
                "t1.CountryCode, " +
                "(t2.AvgTemp - t1.AvgTemp) AS TempDiff, " +
                "(t2.Population - t1.Population) AS PopDiff, " +
                "t1.AvgTemp, " +
                "t1.Population " +
                "FROM " +
                "CountryTempPopulation AS t1 " +
                "JOIN " +
                "CountryTempPopulation AS t2 ON t1.CountryCode = t2.CountryCode " +
                "WHERE " +
                "t1.Year = " + startYear + " AND t2.Year = " + endYear + ") AS subquery";
            
            // Add sorting conditions based on criterion and sort parameters
            if (criterion != null && criterion.equals("Temperature")) {
                query += " ORDER BY TempDiff";
            } else if (criterion != null && criterion.equals("Population")) {
                query += " ORDER BY PopDiff";
            }

            if (sort != null && sort.equals("Ascending")) {
                query += " ASC";
            } else if (sort != null && sort.equals("Descending")) {
                query += " DESC";
            }

            //Execute query and store result from query
            ResultSet results = statement.executeQuery(query);

            //Processing results
            //Use methods inside the country class to store values into an object
            //The object will be added to the arrayList, continue process through iteration

            while (results.next()) {
                //Create the country object
                country tempCountry = new country();
                //Store values from table into temp vars
                Double tempAvgTemp = results.getDouble("TempDiff");
                String tempCountryCode = results.getString("CountryCode");
                long tempPopulation = results.getLong("PopDiff");
                Double tempPercentageChange = results.getDouble("TempPercentageChange");
                Double popPercentageChange = results.getDouble("PopPercentageChange");
                Double correlationValue = results.getDouble("CorrelationValue");

                //Use setter methods to set values from table into object
                //Want CountryCode, tempdifference, popdifference, % change and correlation
                tempCountry.setAvgTemp(tempAvgTemp);
                tempCountry.setCountryCode(tempCountryCode);
                tempCountry.setCountryPopulation(tempPopulation);
                tempCountry.setTempPercentageChange(tempPercentageChange);
                tempCountry.setPopPercentageChange(popPercentageChange);
                tempCountry.setCorrelationValue(correlationValue);

                //Add the temp object into the returning arrayList
                country.add(tempCountry);
            }

            //Close Statement
            statement.close();
        }
        catch (SQLException e) {
            //If there is an error, print error
            System.err.println(e.getMessage());
        }
        finally {
            //Safety Code
            try {
                if (connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //connection close failed
                System.err.println(e.getMessage());
            }
        }
        //return arrayList
        return country;
    }

    //Method 4: Level2A Store values for Region, Temperature Change, Population Change, Correlation in world arrayList
    public ArrayList<world> getWorldArrayList(String startYear, String endYear, String criterion, String sort) {
        //Create world ArrayList to return
        //User input will dictate the query
        //ArrayList will contain the world code, temperature difference, population difference, % change and correlation
        //Method will iterate through the resultSet and store each of these values in an object --> added to arrayList which is returned
        ArrayList<world> world = new ArrayList<world>();

        //Setup the variable for JDBC connection
        Connection connection = null;

        try {
            //Connect to the JDBC database
            connection = DriverManager.getConnection(DATABASE);

            //Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The Query
            //Filter the table to only display the following columns based on user inputted year
            String query = "SELECT " +
                "CountryCode, " +
                "TempDiff, " +
                "PopDiff, " +
                "(TempDiff / CAST(AvgTemperature AS REAL)) * 100 AS TempPercentageChange, " +
                "(PopDiff / CAST(Population AS REAL)) * 100 AS PopPercentageChange, " +
                "((TempDiff / CAST(AvgTemperature AS REAL)) * 100 * (PopDiff / CAST(Population AS REAL)) * 100) / 100 AS CorrelationValue " +
                "FROM " +
                "(SELECT " +
                "t1.CountryCode, " +
                "(t2.AvgTemperature - t1.AvgTemperature) AS TempDiff, " +
                "(t2.Population - t1.Population) AS PopDiff, " +
                "t1.AvgTemperature, " +
                "t1.Population " +
                "FROM " +
                "WorldTempPopulation AS t1 " +
                "JOIN " +
                "WorldTempPopulation AS t2 ON t1.CountryCode = t2.CountryCode " +
                "WHERE " +
                "t1.Year = " + startYear + " AND t2.Year = " + endYear + ") AS subquery";

            // Add sorting conditions based on criterion and sort parameters
            if (criterion != null && criterion.equals("Temperature")) {
                query += " ORDER BY TempDiff";
            } else if (criterion != null && criterion.equals("Population")) {
                query += " ORDER BY PopDiff";
            }

            if (sort != null && sort.equals("Ascending")) {
                query += " ASC";
            } else if (sort != null && sort.equals("Descending")) {
                query += " DESC";
            }

            //Execute query and store result from query
            ResultSet results = statement.executeQuery(query);

            //Processing results
            //Use methods inside the world class to store values into an object
            //Object will be added to the arrayList, continue process through iteration

            while (results.next()) {
                //Create the world object
                world tempWorld = new world();
                //Store values from table into temp vars
                Double tempAvgTemp = results.getDouble("TempDiff");
                String tempWorldCode = results.getString("CountryCode");
                long tempPopulation = results.getLong("PopDiff");
                Double tempPercentageChange = results.getDouble("TempPercentageChange");
                Double popPercentageChange = results.getDouble("PopPercentageChange");
                Double correlationValue = results.getDouble("CorrelationValue");

                //Use setter methods to set values from table into object
                //Want worldCode, tempdifference, popdifference, % change and correlation
                tempWorld.setAvgTemp(tempAvgTemp);
                tempWorld.setWorldCode(tempWorldCode);
                tempWorld.setWorldPopulation(tempPopulation);
                tempWorld.setTempPercentageChange(tempPercentageChange);
                tempWorld.setPopPercentageChange(popPercentageChange);
                tempWorld.setCorrelationValue(correlationValue);

                //Add the temp object into the returning arrayList
                world.add(tempWorld);
            }

            //Close Statement
            statement.close();
        }
        catch (SQLException e) {
            //If there is an error, print error
            System.err.println(e.getMessage());
        }
        finally {
            //Safety Code
            try {
                if (connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //connection close failed
                System.err.println(e.getMessage());
            }
        }
        //Return arrayList
        return world;
    }

    //Method 5: Level3A Store values for CountryCode, Starting Year, Time Period, Average Temperature, Difference in Average Temperature - COUNTRY GEO
    public ArrayList<country> getGeoCountryArrayList(String startingYears, String timePeriod, String populationStart, String populationEnd, String avgTempStart, String avgTempEnd, String criterion, String sort, String populationSelect, String tempSelect) {
        //Create country ArrayList to return
        //User input will dictate the query
        //ArrayList will contain CountryCode, Starting Year, Time Period, Average Temperature, Average Temperature Difference
        //Method will iterate through the resultSet and store each of these values in an object --> added to arrayList which is returned
        ArrayList<country> country = new ArrayList<country>();

        //Setup the variable for JDBC connection
        Connection connection = null;

        try {
            //Connect to the JDBC database
            connection = DriverManager.getConnection(DATABASE);

            //Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The Query
            String query = "SELECT " +
            "c.CountryCode, " +
            "t.StartingYear, " +
            timePeriod + " AS TimePeriod, " +
            "AVG(c.AvgTemp) AS AverageTemperature, " +
            "AVG(c.AvgTemp) - LAG(AVG(c.AvgTemp)) OVER(PARTITION BY c.CountryCode ORDER BY t.StartingYear) AS DifferenceAverageTemperature " +
            "FROM " +
            "(SELECT " +
            "CountryCode, " +
            "CAST((Year / 10) * 10 AS INTEGER) AS StartingYear, " +
            "AvgTemp, " +
            "(ROW_NUMBER() OVER(PARTITION BY CountryCode ORDER BY Year) - 1) / 2 AS RowNum " +
            "FROM CountryTempPopulation " +
            "WHERE Year >= 1750 AND Year <= 2013";
    
            // Check if populationStart and populationEnd are not null
            if (populationSelect != null) {
                query += " AND Population >= " + populationStart + " AND Population <= " + populationEnd;
            }
            
            // Check if avgTempStart and avgTempEnd are not null
            if (tempSelect != null) {
                query += " AND AvgTemp >= " + avgTempStart + " AND AvgTemp <= " + avgTempEnd;
            }
            
            query += ") t " +
                    "JOIN CountryTempPopulation c ON c.CountryCode = t.CountryCode AND c.Year >= t.StartingYear AND c.Year < t.StartingYear + " + timePeriod + " " +
                    "WHERE t.StartingYear IN (" + startingYears + ") " +
                    "GROUP BY t.CountryCode, t.StartingYear";
            
            // Add sorting conditions based on criterion and sort parameters
            if (criterion != null && criterion.equals("Region Code")) {
                query += " ORDER BY c.CountryCode";
            } else if (criterion != null && criterion.equals("Average Temperature")) {
                query += " ORDER BY c.AvgTemp";
            } else if (criterion != null && criterion.equals("Difference in Average Temperature")) {
                query += " ORDER BY DifferenceAverageTemperature";
            }
            
            if (sort != null && sort.equals("Ascending")) {
                query += " ASC";
            } else if (sort != null && sort.equals("Descending")) {
                query += " DESC";
            }
    

                //Execute query and store result from query
                ResultSet results = statement.executeQuery(query);

                //Processing Results
                //Use methods inside the country class to store values into an object
                //Object will be added to the arrayList, continue process through iteration

                while (results.next()) {
                    //Create the country object
                    country tempCountry = new country();
                    //Store values from table into temp variable
                    String tempCountryCode = results.getString("CountryCode");
                    int tempStartingYears = results.getInt("StartingYear");
                    int tempTimePeriod = results.getInt("TimePeriod");
                    Double tempAvgTemp = results.getDouble("AverageTemperature");
                    Double tempDiffTemp = results.getDouble("DifferenceAverageTemperature");

                    //Use setter methods to set values from table into object
                    tempCountry.setCountryCode(tempCountryCode);
                    tempCountry.setYear(tempStartingYears);
                    tempCountry.setTimePeriod(tempTimePeriod);
                    tempCountry.setAvgTemp(tempAvgTemp);
                    tempCountry.setDiffTemp(tempDiffTemp);

                    //Add the temp object into the returning arrayList
                    country.add(tempCountry);
                }
                //Close Statement
                statement.close();
        }
        catch (SQLException e) {
            //If there is an error, print error
            System.err.println(e.getMessage());
        }
        finally {
            //Safety Code
            try {
                if (connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                //connection close failed
                System.err.println(e.getMessage());
            }
        }
        //Return arrayList
        return country;
    }
}
