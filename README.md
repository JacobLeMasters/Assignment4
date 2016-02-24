# Assignment4
Working with Services, Mobile Programming with Java.

Basically, I changed the MainActivity from your project into my MainService, changing it from an Activity to a Service. 
The MainActivity now has an EditText, 3 Buttons and a TextView layout. 
The EditText stores and passes the FileName the user chooses. 
The first button Starts the Service, the second button calls StopService(). 
The third button outputs the content of the file created by the Service that stores the location information. 
The Service writes to this file when the first location is created and then periodically when the onLocationChanged() function is called,
which is set roughly to every 300000 millisecond interval, or 5min.
