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

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";
               html = html + "<meta charset = 'UTF-8'>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Sub Task 2.A</a>
                <a href='page2B.html'>Sub Task 2.B</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Sub Task 3.B</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Subtask 2.A</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h1 class = 'main-content-heading'>Temperature and Population by World/Country</h1>
            """;
        
        //Add HTML for web Form
        //First ask user to select countries or world
        //Second ask users to enter start and end year in textbox
        //Third make results table displaying Region|Temperature Change|Population Change|Correlation?

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

        //Add button to submit form
        html = html + "   <button type='submit' class='btn btn-primary'>Submit Form!</button>";

        html = html + "</form>"; //End Form

        //Get the form data from dropdown list
        //Ensure that there is an option for null

        String country_population_drop = context.formParam("country_population_drop");
        if (country_population_drop == null) {
            html = html + "<h2><i>No Results</i></h2>";
        }
        else {
            html = html + "<h2><i>User Submitted - " + country_population_drop + " replace with method</i></h2>";
        }

        String start_year = context.formParam("start_year");
        if (start_year == null || start_year =="") {
            html = html + "<h2><i>No Results</i></h2>" ;
        }
        else {
            html = html + "<h2><i>User Submitted - " + start_year + " replace with method </i></h2>";
        }

        String end_year = context.formParam("end_year");
        if (end_year == null || end_year =="") {
            html = html + "<h2><i>No Results</i></h2>" ;
        }
        else {
            html = html + "<h2><i>User Submitted - " + end_year + " replace with method </i></h2>";
        }

        //Form data is submitted to country_population_drop, start_year and end_year
        //Use data to form SQL queries
        
        html = html + """
            <h1 class = 'main-content-heading'>Results</h1>
            """;
        
        
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
