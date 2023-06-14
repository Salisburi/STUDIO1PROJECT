package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import org.eclipse.jetty.server.Iso88591HttpWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Climate Change Awareness data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are the the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statments!
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au

 */
public class ClimateProcessCSV {

   // MODIFY these to load/store to/from the correct locations
   private static final String DATABASE = "jdbc:sqlite:database/climate.db";
   private static final String CSV_FILE = "database/GlobalYearlyLandTempByState.csv";


   public static void main (String[] args) {
      // Load up the Date table
      // This only needs to be done once - uncomment this to reload the Date table
      // loadYears();

      // Load the Country Temperature Observations
      loadTemperatures();

      return;
   }

   public static void loadYears() {
      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         connection = DriverManager.getConnection(DATABASE);

         for (int i = 1750; i != 2024; ++i) {
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();

            // Create Insert Statement
            String query = "INSERT into Date VALUES ("
                           + i
                           + ")";

            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void loadTemperatures() {
      // JDBC Database Object
      Connection connection = null;
  
      // We need some error handling.
      try {
          // Open A CSV File to process, one line at a time
          // CHANGE THIS to process a different file
          Scanner lineScanner = new Scanner(new File(CSV_FILE), StandardCharsets.UTF_8);
  
          // Read the first line of "headings"
          String header = lineScanner.nextLine();
          System.out.println("Heading row" + header + "\n");
  
          // Setup JDBC
          // Connect to JDBC database
          connection = DriverManager.getConnection(DATABASE);
  
          // Read each line of the CSV
          int row = 1;
          while (lineScanner.hasNext()) {
              // Always get scan by line
              String line = lineScanner.nextLine();
  
              // Create a new scanner for this line to delimit by commas (,)
              Scanner rowScanner = new Scanner(line);
              rowScanner.useDelimiter(",");
  
              // Get all of the columns in order
              String year = rowScanner.next();
              String avgTemp = rowScanner.next();
              String minTemp = rowScanner.next();
              String maxTemp = rowScanner.next();
              String state = rowScanner.next();
              String rawCountryName = rowScanner.next();
  
              // In this example, we don't have the population, so we'll leave that as zero for now
              //int population = 0;
  
              // Set a default country code
              String countryCode = "ZZZZ";
  
              // Some error handling
              if (avgTemp.equals("")) {
                  avgTemp = "0";
              }
              if (minTemp.equals("")) {
                  minTemp = "0";
              }
              if (maxTemp.equals("")) {
                  maxTemp = "0";
              }
  
              // Convert any Latin1 encoded country names to UTF-8
              String stateName = new String(state.getBytes("ISO-8859-1"), "UTF-8");
              String countryName = new String(rawCountryName.getBytes("ISO-8859-1"), "UTF-8");
              // We now need to look-up the country code from the name
              PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from Country WHERE CountryName = ?");
              preparedStatement.setString(1, countryName);
              System.out.println("Looking up: " + preparedStatement.toString());
              ResultSet results = preparedStatement.executeQuery();
              if (results.next()) {
                  countryCode = results.getString("CountryCode");
              }
  
              // Now we can insert the entry into the CountryTempObservation table
              // Prepare a new PREPARED SQL query & Set a timeout
              preparedStatement = connection.prepareStatement("INSERT into StateTempObservationTest VALUES (?, ?, ?, ?, ?, ?)");
  
              // Set the values for the prepared statement
              preparedStatement.setString(1, countryCode);
              preparedStatement.setString(2, stateName);
              preparedStatement.setString(3, year);
              preparedStatement.setString(4, avgTemp);
              preparedStatement.setString(5, minTemp);
              preparedStatement.setString(6, maxTemp);
  
              // Execute the INSERT
              System.out.println("Executing: " + preparedStatement.toString());
              preparedStatement.execute();
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  

}
