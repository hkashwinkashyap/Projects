# TODO List ---- Front-end

# API request specification

* Getting all route lists
    * <get, "/route">
* Getting all stop lists
    * <get, "/stop">
* Adding a stop before an old stop
    * <post, "/route",
      {
      stopName: {stopName},
      routeName: {routeName},
      beforeStopName: {beforeStopName}
      }>
* Searching the serving route list given a stop
    * <get, "/route/{stopName}">
* Searching the serving route list given a stop and a certain time
    * <get, "/route/{stopName}/{time}>
* Showing the serving time given a stop
    * <get, "/time/{stopName}>

# API response specification

* Getting all route lists
  * <code, message, ArrayList of Route>
* Getting all stop lists
  * <code, message, List of Stops>
* Adding a stop before an old stop
  * <code, message, Void>
* Searching the serving route list given a stop
  * <code, message, ArrayList of Route>
* Searching the serving route list given a stop and a certain time
  * <code, message, ArrayList of Route>
* Showing the serving time given a stop
  * <code, message, ArrayList of String>