package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";
        html = html + "<meta charset = 'UTF-8'>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        //Start of Header
        html = html + "<div style='background-color: #438c7c;'>";
        html = html + "<marquee><b>This section will portray any notice or any requests or any news!!</b></marquee>";
       
        html = html + "<center><a href='/'><img src='logo.png' alt='Rmit logo' width='200' height='70'></a></center><br>";
       
        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Home</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Sub Task 2.A</a>
                <a href='page2B.html'>Sub Task 2.B</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                        <a href='page3B.html'>Sub Task 3.B</a>
            </div>
        """;
       
        html = html + "</div>";

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        //Content is split into two sections
        //First section shows the data available and the starting and end years for world pop and global temp
        //Second section shows the values at each end of the range of values available - also show how many years of data is available (end range - start range get from database)
        
        //Make JDBC, globalTemp and population objects
        globaltemp globaltemp = new globaltemp();
        population population = new population();
        JDBCConnection jdbcConnection = new JDBCConnection();

        //Use JDBC method to store all the database values into created objects
        globaltemp = jdbcConnection.getGlobal();
        population = jdbcConnection.getPopulation();
        
        //First Section
        html = html + """
            <div class = 'data-available-content'>

            <h1 class = 'main-content-heading'>Data Available</h1>
            """;
        html = html + """
            <h2>World Population</h2>
            """;
        html = html + "<p class='population-year-heading'>Start Year:</p>";
        html = html + "<p class='population-year-value'>" + population.getStartYear() + "</p>";
        html = html + "<p class='population-year-heading'>End Year:</p>";
        html = html + "<p class='population-year-value'>" + population.getEndYear() + "</p>";
        html = html + "<p class='population-year-heading'>Year Range of Available Data:</p>";
        html = html + "<p class='population-year-value'>" + population.getYearRange() + "</p>";


        html = html + """
            <h2>Global Temperature</h2>
            """;
        html = html + "<p class='global-year-heading'>Start Year:</p>";
        html = html + "<p class='global-year-value'>" + globaltemp.getStartYear() + "</p>";
        html = html + "<p class='global-year-heading'>End Year:</p>";
        html = html + "<p class='global-year-value'>" + globaltemp.getEndYear() + "</p>";
        html = html + "<p class='global-year-heading'>Year Range of Available Data:</p>";
        html = html + "<p class='global-year-value'>" + globaltemp.getYearRange() + "</p>";

        html = html + "</div>";
        
        //Second Section
        html = html + """
            <div class = 'pop-temp-values'>
        """;

        html = html + "<h1 class = 'main-content-heading'>World Population Number</h1>";

        html = html + "<p class='population-year-heading'>Start Year:</p>";
        html = html + "<p class='population-year-value'>" + population.getStartPopulation() + "</p>";
        html = html + "<p class='population-year-heading'>End Year:</p>";
        html = html + "<p class='population-year-value'>" + population.getEndPopulation() + "</p>";

        html = html + "<h1 class = 'main-content-heading'>Global Land Temperature</h1>";

        html = html + "<p class='global-year-heading'>Start Year:</p>";
        html = html + "<p class='global-year-value'>" + globaltemp.getStartTemp() + "°C</p>";
        html = html + "<p class='global-year-heading'>End Year:</p>";
        html = html + "<p class='global-year-value'>" + globaltemp.getEndTemp() + "°C</p>";

        html = html + "</div>";

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <p>COSC2803 - Studio Project Starter Code (Apr23)</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    
}
