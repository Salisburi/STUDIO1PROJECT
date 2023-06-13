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
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        //Wrapper div
        html = html + "<div class ='wrapper'>";
        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";
        html = html + "<meta charset = 'UTF-8'>"; //Adds characterset standard UTF-8 encoding
        
        //Add Google Fonts Link for Roboto
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
            <div class='header-ST3A'>
            <h1>Similarity Temperature Comparison Thing</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        //Add HTML for web form
        //First ask user to select geographic region (Global, Country, State, City)
        //Second ask user to input start years - Placeholder value to show format ex. 1910, 1920, 1930
        //Third ask user to input time period - Input only one integer 4 digits
        //Check values of start years + time period - if exceeds 2013 when geographic = global, country, state, city - ERROR MESSAGE
        //If exeeds 2015 for global - ERROR MESSAGE

        //First section
        html = html + "<div class='first-section-ST3A'>";
        //Make section-container for inputs
        html = html + "<div class='section-container-ST3A'>";
        html = html + "<h2>Filters</h2>";

        //Form 1: Take user inputs and sent to database
        html = html + "<form action ='page3A.html' method = 'post'>"; //Creates form element with method post, data is from this html file

        //Create Dropdown for users to select geographic region (Global, Conutries, State, City)
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='geographic_region_drop'>Select the Geographic Region:</label>";
        html = html + "      <select id='geographic_region_drop' name='geographic_region_drop'>";
        html = html + "         <option>Global</option>";
        html = html + "         <option>Countries</option>";
        html = html + "         <option>State</option>";
        html = html + "         <option>City</option>";
        html = html + "      </select>";

        //Create TextBox for users to be able to input starting years starting from 1750
        //Use titles to displat error message | CSS for invalid inputs to change textbox to red
        html = html + "     <label for='starting_year'>Starting years:</label>";
        html = html + "     <input type='text' id='starting_year' name='starting_year' required title='Please enter a valid string' placeholder='1910, 1920, 1930'>";
        html = html + "     <label for='time_period'>Time Period:</label>";
        html = html + "     <input type='number' id='time_period' name='time_period' required title='Please enter a valid number' placeholder='10'>";
        html = html + "  </div>";

        //Create Dropdown for users to filter results
        //2nd row - filter by population range and average temperature range, give option to user via radio button
        //3rd row - filter by criterion and asc/desc - check pageST2A

        //Add radio buttons and respective filters - IF HAVE TIME MAYBE PUT JAVACRIPT THINGS IN
        //Population Select
        html = html + "   <div class='form-group'>";
        html = html + "     <input type='checkbox' id='population_select' name='population_select' value='population_true'>";
        html = html + "     <label for='population_select'>Population Range:</label>";
        html = html + "     <input type='number' id='population_start_range' name='population_start_range' required title='Please enter a valid number'>";
        html = html + "     <input type='number' id='population_end_range' name='population_end_range' required title='Please enter a valid number'>";

        html = html + "     <input type ='checkbox' id='avg_temp_select' name='avg_temp_select' value='temp_true'>";
        html = html + "     <label for='avg_temp_select'>Average Temperature Range:</label>";
        html = html + "     <input type='number' id='temperature_start_range' name='temperature_start_range' required title='Please enter a valid number'>";
        html = html + "     <input type='number' id='temperature_end_range' name='temperature_end_range' required title='Please enter a valid number'>";
        html = html + "  </div>";

        //Criterion and sorting filters
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='criterion_select'>Sort By Criterion:</label>";
        html = html + "      <select id='criterion_select' name='criterion_select'>";
        html = html + "         <option>Region Code</option>";
        html = html + "         <option>Average Temperature</option>";
        html = html + "         <option>Difference in Average Temperature</option>";
        html = html + "      </select>";
        html = html + "      <label for='asc_desc_drop'>Sort By:</label>";
        html = html + "      <select id='asc_desc_drop' name='asc_desc_drop'>";
        html = html + "         <option>Ascending</option>";
        html = html + "         <option>Descending</option>";
        html = html + "      </select>";
        html = html + "  </div>";

        //Add button to submit form
        html = html + "  <button type='submit' class='ST2A-button'>View Results</button>";

        //Closing divs
        html = html + "</form>"; //End Form
        html = html + "</div>"; //End section-container
        html = html + "</div>"; //End first-section

        //Get the form data - context.formParam
        //Ensure that there is an option for null

        //1st row of filters
        String geographic_region_drop = context.formParam("geographic_region_drop");
        String starting_year = context.formParam("starting_year");
        String time_period = context.formParam("time_period");

        //2nd row of filters
        String population_select = context.formParam("population_select");
        String population_start_range = context.formParam("population_start_range");
        String population_end_range = context.formParam("population_end_range");

        String avg_temp_select = context.formParam("avg_temp_select");
        String temperature_start_range = context.formParam("temperature_start_range");
        String temperature_end_range = context.formParam("temperature_end_range");

        //3rd row of filters
        String criterion_select = context.formParam("criterion_select");
        String asc_desc_drop = context.formParam("asc_desc_drop");

        //Console print variables - TESTED GETTING VALUES :) 
        
        //System.out.println(geographic_region_drop);
        //System.out.println(starting_year);
        //System.out.println(time_period);

        //System.out.println(population_select); //Returns null if not selected
        //System.out.println(population_start_range);
        //System.out.println(population_end_range);

        //System.out.println(avg_temp_select); //Returns null if not selected
        //System.out.println(temperature_start_range);
        //System.out.println(temperature_end_range);

        //System.out.println(criterion_select);
        //System.out.println(asc_desc_drop);
        

        //Form data is submitted and assigned to variablews
        html = html + "<div class='results_area'>"; 
        html = html + "<div class='second-section-ST2A'>";
        html = html + "<div class='section-container-ST2A'>";
        //Stuff goes below here thanks -DELETE AFTER PLEASE

        //Create JDBC object, ...

        //Above here thanks -DELETE AFTER PLEASE
        html = html + "</div>"; //End section-container
        html = html + "</div>"; //End second section
        html = html + "</div>"; //End results area


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
