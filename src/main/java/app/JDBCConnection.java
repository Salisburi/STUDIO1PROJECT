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

    //Method 1: Returns the year ranges for world pop and global temp
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

}
