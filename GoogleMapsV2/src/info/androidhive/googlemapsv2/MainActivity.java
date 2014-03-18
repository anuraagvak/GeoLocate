package info.androidhive.googlemapsv2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity implements OnMapClickListener,OnCameraChangeListener{
 
    // Google Map
	Context mainactivity_context = this;
	String selectedLocAddress;
    private GoogleMap googleMap;
    double latitude = 28.6100;
    double longitude = 77.2300;
    double lat_hyd = 28.6100;//17.3660;
    double lon_hyd = 77.2300;//78.4760;
    MarkerOptions marker;
    LatLng latlong = new LatLng(latitude, longitude);
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Why so serios?","ye kya hai");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap.setOnMapClickListener(this);
        googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {

            private float currentZoom = -1;

            @Override
            public void onCameraChange(CameraPosition pos) {
                if (pos.zoom != currentZoom){
                    currentZoom = pos.zoom;
                Log.w("inside camerachangelistener",Float.toString(currentZoom));	
                    // do you action here
                }
            }
        });
    }
    
    @Override
    public void onMapClick(LatLng point){
    	latlong = point;
		String out1=null;
		if(Variables_class.question_no == 2)
			new myClass().execute();

		try {
            // Loading map
        	Log.w("Thi is eating a","lot of time");
            googleMap.clear();
            initilizeMap();
            if(Variables_class.question_no == 3){
                if(( (lat_hyd-point.latitude)*(lat_hyd-point.latitude) + (lon_hyd-point.longitude)*(lon_hyd-point.longitude) ) <= 3.0){
                    Toast.makeText(getApplicationContext(),"Correct Answer", Toast.LENGTH_SHORT).show();
                    ((Activity) mainactivity_context).finish();
                }
            }
            /*
            if( selectedLocAddress.toLowerCase().contains("andhra pradesh") ){//( (lat_hyd-point.latitude)*(lat_hyd-point.latitude) + (lon_hyd-point.longitude)*(lon_hyd-point.longitude) ) <= 3.0){
                Toast.makeText(getApplicationContext(),
                        "Correct Answer", Toast.LENGTH_SHORT)
                        .show();

            }
            */
            Log.w("Loading","Map");
        } catch (Exception e) {
            e.printStackTrace();
        }    	
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        Log.w("HI maps","hello11");
//        googleMap.clear();
        googleMap = null;
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            Log.w("HI maps","hello");
         // latitude and longitude
            // create marker
            marker = new MarkerOptions().position(latlong).title("Hello Maps ");
            // adding marker
            googleMap.addMarker(marker);
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub
		
	}
	
	protected class myClass extends AsyncTask<String, Void , String>
	{
		
		private ProgressDialog simpleWaitDialog ;

		protected String doInBackground(String... params) {
			return downloadFile();			
		}
		
		String downloadFile(){
			
			URL myFileUrl =null; 			
			try {
				myFileUrl= new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="+ Double.toString(latlong.latitude) +","+Double.toString(latlong.longitude)+"&sensor=false");
				} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			try {
				HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is1 = conn.getInputStream();
			    java.util.Scanner s1 = new java.util.Scanner(is1);
			    String output_is="hmm";
			    for(;s1.hasNext();){
			    	String test1 = s1.nextLine();
			    	
			    	if(test1.contains("formatted_address")){
			    		selectedLocAddress = test1;
			    		break;
			    	}
			    	Log.w("the line","it is "+s1.nextLine());
			    }
			} catch (IOException e) {
			e.printStackTrace();
			}catch (IOError e) {
				Log.w("","this is the error hmmmmmmmmmmm "+e);
			e.printStackTrace();
			}
			String test="dash";
			return test;
		}

		

	//when user issues a cancel on the operation.
	@Override
	protected void onCancelled() {
	// Do a cleanup on operation canceled
	// ReInitialise Data
	super.onCancelled();
	}

	// on successful completion of onPostExecute method is called

	// Called before doInBackground() 
	@Override
	protected void onPreExecute() {
        Log.i("Async-Example", "onPreExecute Called");
        //simpleWaitDialog = ProgressDialog.show(ad_activity.this,
          //      "Wait", "Downloading Image");

	super.onPreExecute();
	// do any initialization
	}
	@Override
	protected void onPostExecute(String result) {
		Log.w("s","Smartwork"+selectedLocAddress);
        if( selectedLocAddress.toLowerCase().contains("andhra pradesh") ){//( (lat_hyd-point.latitude)*(lat_hyd-point.latitude) + (lon_hyd-point.longitude)*(lon_hyd-point.longitude) ) <= 3.0){
            Toast.makeText(getApplicationContext(),"Correct Answer", Toast.LENGTH_SHORT).show();
            ((Activity) mainactivity_context).finish();
//            Intent i = new Intent(mainactivity_context, .class);
//            startActivity(i); 
        }
        selectedLocAddress = "nothiNg";

	//showResults();
	//progressDialog.dismiss();
	//	simpleWaitDialog.dismiss();
	
//	super.onPostExecute(result);
	}
	
	// update the UI on complete of longrunningtask()
	protected void onProgressUpdate(Void... values) {
	super.onProgressUpdate(values);
	}
	
	}		
	

}