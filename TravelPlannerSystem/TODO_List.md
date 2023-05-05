# TODO List ---- Back-end

## Functionality for model:

* Model
    * Stop - Class
        * Name - String
        * Location? (ask on Tuesday)
    * Routes - Class
        * Name - String
        * ArrayList of stops
        * ArrayList of trips
    * Trips - Class
        * Timetable - Map<String, Stops>
            * String - the time
            * Stops - assume each stop takes 5 minutes
* API
  * Getting all route lists
  * Getting all stop lists
  * Adding a stop before an old stop
  * Searching the serving route list given a stop
  * Searching the serving route list given a stop and a certain time
  * Showing the serving time given a stop