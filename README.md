# android-calendar

#3rd Assignment for Mobile Cloud Computing

Juho Salmio 217259

Vivien Letonnellier

https://github.com/Dolmio/android-calendar

# Mobile application
The mobile application uses the backend developed in previous assignments available https://github.com/Dolmio/calendar-app.
The application was developed using Android studio and it targets android sdk version 23 with the minimum version 18.
The application uses 3rd party libraries for making and handling http requests to the server and parsing the json and transforming it to domain objects. Eg. http://square.github.io/okhttp/ https://github.com/FasterXML/jackson and https://github.com/ReactiveX/RxJava were used.

We also used https://github.com/evant/gradle-retrolambda to get the lambda support from Java8 to the Android platform.

The application can be developed using Android Studio (which uses gradle as a build/dep system) (other alternatives haven't been tested).

## Features

* Add events
* Edit events
* Remove events
* Sync events to hardcoded google calendar
* Sync events from hardcoded google calendar
* View events in monthly and daily views
