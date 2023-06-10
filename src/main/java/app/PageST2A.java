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
public class PageST2A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        //Wrapper div
        html = html + "<div class='wrapper'>";
        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";
               html = html + "<meta charset = 'UTF-8'>";
        //Add Google Fonts Link For Roboto
        html = html + "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto'>";
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

        // Add header content block
        html = html + """
            <div class='header-ST2A'>
            <h1>Temperature and Population by World/Country</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";
        
        //Add HTML for web Form
        //First ask user to select countries or world
        //Second ask users to enter start and end year in textbox
        //Third make results table displaying Region|Temperature Change|Population Change|Correlation?

        //First section
        html = html + "<div class='first-section-ST2A'>";
        //Make section-container for inputs
        html = html + "<div class='section-container-ST2A'>";
        html = html + "<h2>Filters</h2>";
        //Form 1: Take user inputs to send to database
        html = html + "<form action ='/page2A.html' method = 'post'>"; //Creates form element with method post, data is from this html file
        
        //Create Dropdown for users to select Countries or World
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='country_population_drop'>Select the Region Type:</label>";
        html = html + "      <select id='country_population_drop' name='country_population_drop'>";
        html = html + "         <option>Countries</option>";
        html = html + "         <option>World</option>";
        html = html + "      </select>";
        html = html + "   </div>";

        //Create TextBox for users to be able to input a year between 1750 and 2013 | NOTE: CAN MAKE METHOD IN JDBCONNECTION IF HAVE TIME LATER 
        //Use titles to display error message | CSS for invalid inputs to change textbox to red
        html = html + "   <div class='form-group'>";
        html = html + "     <label for='start_year'>Start Year:</label>";
        html = html + "     <input type='number' id='start_year' name='start_year' min='1750' max='2013' pattern='\\d{4}' required title='Please enter a valid year between 1750 and 2013' placeholder='1750'>";
        html = html + "     <label for='end_year'>End Year:</label>";
        html = html + "     <input type='number' id='end_year' name='end_year' min='1750' max='2013' pattern='\\d{4}' required title='Please enter a valid year between 1750 and 2013' placeholder='2013'>";
        html = html + "  </div>";

        //Create Dropdown for users to filter results

        html = html + "   <div class='form-group'>";
        html = html + "      <label for='temp_pop_drop'>Sort By Criterion:</label>";
        html = html + "      <select id='temp_pop_drop' name='temp_pop_drop'>";
        html = html + "         <option>Temperature</option>";
        html = html + "         <option>Population</option>";
        html = html + "      </select>";
        html = html + "      <label for='asc_desc_drop'>Sort By:</label>";
        html = html + "      <select id='asc_desc_drop' name='asc_desc_drop'>";
        html = html + "         <option>Ascending</option>";
        html = html + "         <option>Descending</option>";
        html = html + "      </select>";
        html = html + "  </div>";

        //Add button to submit form
        html = html + "  <button type='submit' class='ST2A-button'>View Results</button>";

        html = html + "</form>"; //End Form
        html = html + "</div>"; //End section-container
        html = html + "</div>"; //End first-section

        //Get the form data - context.formParam
        //Ensure that there is an option for null

        String country_population_drop = context.formParam("country_population_drop");
        String start_year = context.formParam("start_year");
        String end_year = context.formParam("end_year");
        String temp_pop_drop = context.formParam("temp_pop_drop");
        String asc_desc_drop = context.formParam("asc_desc_drop");

        //Form data is submitted and assigned to variables
        html = html + "<div class='results_area'>"; 

        html = html + "<div class='second-section-ST2A'>";
        html = html + "<div class='section-container-ST2A'>";
        ///Create JDBC, country arrayList and world arrayList
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<country> country = new ArrayList<country>();
        ArrayList<world> world = new ArrayList<world>();
        //Store JDBC method 3 and 4 into country arrayList and world arrayList
        country = jdbc.getCountryArrayList(start_year, end_year, temp_pop_drop, asc_desc_drop);
        world = jdbc.getWorldArrayList(start_year, end_year, temp_pop_drop, asc_desc_drop);
        //country and world now has all the data, iterate and display on webpage
        //Create if statements depending on country_population_drop to display which table
        // Check the value of country_population_drop and create the corresponding table
    if ("Countries".equals(country_population_drop)) {
        // Create table for country ArrayList
        html += "<table class='ST2A-table'>";
        html += "<thead>";
        html += "<tr>";
        html += "<th>Region Code</th>";
        html += "<th>Average Temperature Difference \u00B0C</th>";
        html += "<th>Population Difference</th>";
        html += "<th>% Average Temperature Difference</th>";
        html += "<th>% Population Difference</th>";
        html += "<th>Correlation Value</th>";
        html += "</tr>";
        html += "</thead>";
        
        for (country c : country) {
            html += "<tbody>";
            html += "<tr>";
            html += "<td>" + c.getCountryCode() + "</td>";
            html += "<td>" + c.getAvgTemp() + "</td>";
            html += "<td>" + c.getCountryPopulation() + "</td>";
            html += "<td>" + c.getTempPercentageChange() + "</td>";
            html += "<td>" + c.getPopPercentageChange() + "</td>";
            html += "<td>" + c.getCorrelationValue() + "</td>";
            html += "</tr>";
            html += "</tbody>";
        }
        
        html += "</table>";
    } else if ("World".equals(country_population_drop)) {
        // Create table for world ArrayList
        html += "<table class='ST2A-table'>";
        html += "<thead>";
        html += "<tr>";
        html += "<th>Region Code</th>";
        html += "<th>Average Temperature Difference</th>";
        html += "<th>Population Difference</th>";
        html += "<th>% Average Temperature Difference</th>";
        html += "<th>% Population Difference</th>";
        html += "<th>Correlation Value</th>";
        html += "</tr>";
        html += "</thead>";
        
        for (world w : world) {
            html += "<tbody>";
            html += "<tr>";
            html += "<td>" + w.getWorldCode() + "</td>";
            html += "<td>" + w.getAvgTemp() + "</td>";
            html += "<td>" + w.getWorldPopulation() + "</td>";
            html += "<td>" + w.getTempPercentageChange() + "</td>";
            html += "<td>" + w.getPopPercentageChange() + "</td>";
            html += "<td>" + w.getCorrelationValue() + "</td>";
            html += "</tr>";
            html += "</tbody>";
        }
        
        html += "</table>";
    }
        html = html + "</div>"; //End section-container
        html = html + "</div>"; //End second section
        html = html + "</div>"; //End results area

        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div style='background-color: #438c7c; height: 320px;'>
            <div style='background-color: #438c7c; padding-bottom: 150px;'>
            <div style='float: left; width: 50%; font-size:large;'>
                <center><h2 style='color: #f2f2f2; font-weight: bold;'>Main Menu</h2>
                <a href='/' style='color: #f2f2f2;'>Home</a><br><br>
                <a href='mission.html' style='color: #f2f2f2;'>Our Mission</a><br><br>
                <a href='page2A.html' style='color: #f2f2f2;'>Sub Task 2.A</a><br><br>
                <a href='page2B.html' style='color: #f2f2f2;'>Sub Task 2.B</a><br><br>
                <a href='page3A.html' style='color: #f2f2f2;'>Sub Task 3.A</a><br><br>
                <a href='page3B.html' style='color: #f2f2f2;'>Sub Task 3.B</a><br><br>
                </center>
            </div>
            <div style='width: 50%; margin-left: 50%; padding-top: 70px;'>
                <center><img src='logo.png' alt='Rmit logo' width='300' height='100'></center>
            </div>
        </div>
                """;
        html = html + """
            <div class='footer'>
                <p>Programming Studio 1 Project(June 2023)</p>
            </div>
        """;

        //End Wrapper
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
