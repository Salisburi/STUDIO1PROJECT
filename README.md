Fam this project is messed up ...

PageST3A task list

1. Configure basic HTML (wrapper, head meta information, footer) - Done
2. Add form and input/select + submit form option - Done
3. Test userInput form submit + send to contextParam variables - Done
4. Create methods based on queries - store results in respective objects —> return objects in arrayList
5. Create JDBC + arrayLists, run methods to store data into arrayLists
6. Iterate through arrayList to form table (PageST2A)

Notes:
Data calculated: Average Temperature across the time period from the starting year and the difference in average temperatures

Allow user to input into textbox, countryCodes to look up specific regions (will not affect global)

If statements to produce table depending on selected geographic region

*Filtering country results by temperature and population will only affect the geographic region of country (used to filter in SLQ queries)

Table Global:
GeoRegion Type | Year Range | AvgTemp | Difference in AvgTemp

Table Country:
GeoRegion Type | CountryCode | Year Range | AvgTemp | Difference in AvgTemp

Table State:
GeoRegion Type | Country Code | State | Year Range | AvgTemp | Difference in AvgTemp

Table City:
GeoRegion Type | CountryCode | City | Year Range | AvgTemp | Difference in AvgTemp
