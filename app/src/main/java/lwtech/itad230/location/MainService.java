package lwtech.itad230.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class MainService extends IntentService
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private Intent intent;
    private String mLatitude;
    private String mLongitude;
    private String mLastUpdate;
    private File file;
    private FileOutputStream fileOutputStream;
    private String output;
    private String fileName;

    @Override
    protected void onHandleIntent(Intent intent) {
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();

        this.intent = intent;
    }

    public MainService(){
        super("MainService");
    }

    @Override
    public void onCreate(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient);

        if( mLastLocation == null ) {
            return;
        }

        fileName = intent.getStringExtra("fileName");

        file = new File(getFilesDir(), fileName);
        mLatitude = (String.valueOf(mLastLocation.getLatitude()));
        mLongitude = (String.valueOf(mLastLocation.getLongitude()));
        mLastUpdate = DateFormat.getTimeInstance().format(new Date());

        output = "Latitude: " + mLatitude + '\n'
                + "Longitude: " + mLongitude + '\n'
                + "Time Stamp: " + mLastUpdate + '\n';
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(output.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createLocationRequest();
        if( mLocationRequest != null){
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(300000);
        mLocationRequest.setFastestInterval(300000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        mLatitude = (String.valueOf(mLastLocation.getLatitude()));
        mLongitude = ( String.valueOf(mLastLocation.getLongitude()));
        mLastUpdate = DateFormat.getTimeInstance().format(new Date());

        output = "Latitude: " + mLatitude + '\n'
                + "Longitude: " + mLongitude + '\n'
                + "Time Stamp: " + mLastUpdate + '\n';
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(output.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
