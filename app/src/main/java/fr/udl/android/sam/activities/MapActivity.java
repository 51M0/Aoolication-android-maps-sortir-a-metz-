package fr.udl.android.sam.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import fr.udl.android.sam.adapter.MyInfoWindowAdapter;
import fr.udl.android.sam.listener.MyLocationListener;
import fr.udl.android.sam.PermissionManager;
import fr.udl.android.sam.R;
import fr.udl.android.sam.listener.onClick.MyOnCameraMoveListener;
import fr.udl.android.sam.listener.onClick.MyOnMapClickListener;
import fr.udl.android.sam.listener.onClick.MyOnMarkerClickListener;
import fr.udl.android.sam.listener.onClick.button.AdvancedOptionsButtonListener;
import fr.udl.android.sam.listener.onClick.button.LockButtonListener;
import fr.udl.android.sam.listener.onClick.MyInfoWindowClickListener;
import fr.udl.android.sam.listener.onClick.MyPOIClickListener;
import fr.udl.android.sam.listener.onClick.button.SaveCurrentPosButtonListener;
import fr.udl.android.sam.persistence.Entities.Site;
import fr.udl.android.sam.persistence.dao.DaoCategorie;
import fr.udl.android.sam.persistence.dao.DaoSite;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback{

    public static List<String> checkedList = new ArrayList<>();
    private GoogleMap mMap;
    static Double lat = null;
    static Double lng = null;
    private Marker lastMarker;
    private Location currentLoc;
    private Circle mCircle;
    private boolean locked = true;
    private CircleOptions circleOptions;
    private LatLng currentPos;
    private Marker newMarker;
    private double radiusInMeters = 200.0;
    private int advancedOptionsRequestCode = 1;
    private int saisieMarkerRequestCode = 3;
    private Location lastValidPos;
    private ContentResolver content;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PermissionManager(this).checkLocationPermission();
        setContentView(R.layout.activity_map);
        getContentResolver();
        //daoCategorie = new DaoCategorie(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkedList = new ArrayList<>();
        Button detailsButton = (Button) findViewById(R.id.addCurrentLocation);
        detailsButton.setOnClickListener(new SaveCurrentPosButtonListener(this));

        Button advancedOptionsButton = (Button) findViewById(R.id.advancedOptionsButton);
        advancedOptionsButton.setOnClickListener(new AdvancedOptionsButtonListener(this));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setStartLocations();

        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this, this));
        mMap.setOnInfoWindowClickListener(new MyInfoWindowClickListener(this, newMarker, saisieMarkerRequestCode));

        initCamera();

        initCircle();

        setMarker();

        final Button lockButton = (Button) findViewById(R.id.lockPosButton);
        lockButton.setOnClickListener(new LockButtonListener(this));

        mMap.setOnPoiClickListener(new MyPOIClickListener(this));

        mMap.setOnMapClickListener(new MyOnMapClickListener(this));

        mMap.setOnMarkerClickListener(new MyOnMarkerClickListener(this));

        googleMap.setOnCameraMoveListener(new MyOnCameraMoveListener(this));
    }

    public void putMarkerOnMapClick (LatLng latLng, String title){

        if(newMarker!= null && !newMarker.getPosition().equals(latLng)){
            newMarker.remove();
        }

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        if(title != null){
            markerOptions.title(title);
        }else{
            lat = latLng.latitude;
            lng = latLng.longitude;
            markerOptions.title(lat + " : " + lng);
        }

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // Placing a marker on the touched position
        newMarker = mMap.addMarker(markerOptions);
        lastMarker = newMarker;
        newMarker.showInfoWindow();
    }

    public void putMarkerOnMap (Site site){

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng sitePos = new LatLng(site.getLattitude(), site.getLongitude());

        // Setting the position for the marker
        markerOptions.position(sitePos);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(site.getNom());

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == advancedOptionsRequestCode && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                radiusInMeters = extras.getDouble("RADIUS_METER");
                if(radiusInMeters > 0){
                    mCircle.setRadius(radiusInMeters);
                    setMarker();
                }
            }
        }

        if (requestCode == saisieMarkerRequestCode && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            setMarker();
            if (extras != null) {
                //TODO: addMarker to list
            }
        }
    }

    private void setStartLocations(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new MyLocationListener(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, locationListener);
            currentLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lastValidPos = currentLoc;
            //Location Permission already granted
            mMap.setMyLocationEnabled(true);

        } else {
            //Request Location Permission
            new PermissionManager(this).checkLocationPermission();
        }
    }

    public void setMarker(){
        mMap.clear();
        initCircle();
        List<Site> sites;
        if(checkedList != null && checkedList.isEmpty()){
            sites = new DaoSite(this).toutSites();
            displayMarkers(sites);
        }else{
            for(String categ:checkedList){
                long categId = new DaoCategorie(this).getCategorie(categ).getId();
                sites = new DaoSite(this).getSitesByCategory(categId);
                displayMarkers(sites);
            }
        }

    }

    public void displayMarkers(List<Site> sites){
        if(!sites.isEmpty()) {
            for(Site site : sites){

                if(locked){
                    Location loc = new Location("");
                    loc.setLatitude(site.getLattitude());
                    loc.setLongitude(site.getLongitude());
                    float dist = currentLoc.distanceTo(loc);
                    if(Float.compare(dist, (float) getRadiusInMeters()) <= 0 ){
                        putMarkerOnMap(site);
                    }
                }else{
                    putMarkerOnMap(site);
                }
            }
        }
    }

    private void initCamera(){
        // Add a marker in Sydney and move the camera
        currentPos = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPos));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 17));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void initCircle(){
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        circleOptions = new CircleOptions().center(currentPos).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public Marker getLastMarker() {
        return lastMarker;
    }

    public void setLastMarker(Marker lastMarker) {
        this.lastMarker = lastMarker;
    }

    public Location getCurrentLoc() {
        return currentLoc;
    }

    public void setCurrentLoc(Location currentLoc) {
        this.currentLoc = currentLoc;
    }

    public Circle getmCircle() {
        return mCircle;
    }

    public void setmCircle(Circle mCircle) {
        this.mCircle = mCircle;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public CircleOptions getCircleOptions() {
        return circleOptions;
    }

    public LatLng getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(LatLng currentPos) {
        this.currentPos = currentPos;
    }

    public double getRadiusInMeters() {
        return radiusInMeters;
    }

    public Location getLastValidPos() {
        return lastValidPos;
    }

    public void setLastValidPos(Location lastValidPos) {
        this.lastValidPos = lastValidPos;
    }

    public ContentResolver getContent() {
        return content;
    }

    public void setContent(ContentResolver content) {
        this.content = content;
    }
}
