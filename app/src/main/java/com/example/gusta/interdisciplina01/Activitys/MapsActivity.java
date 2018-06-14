package com.example.gusta.interdisciplina01.Activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gusta.interdisciplina01.Manifest;
import com.example.gusta.interdisciplina01.R;
import com.facebook.internal.Utility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //Atributos
    private GoogleMap mMap;
    private Marker currentLocationMarker;
    private LatLng currentLocationLatiLong;
    private Button btnVoltar;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Obtenha o SupportMapFragment e seja notificado quando o mapa estiver pronto para ser usado.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        retorno = com.example.gusta.interdisciplina01.Util.Utility.checkPermission(MapsActivity.this);

        if (retorno) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


        }
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Voltar = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(Voltar);
            }
        });

    }

    // Permissão para usar GPS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case com.example.gusta.interdisciplina01.Util.Utility.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                } else {
                    Toast.makeText(MapsActivity.this, "Problemas durante a permissão de uso do GPS.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (com.example.gusta.interdisciplina01.Util.Utility.checkPermission(MapsActivity.this)) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {

                                    //Adicionando Marcador
                                    //Pegando Latitude e Longitude, e colocando dentro do objeto
                                    currentLocationLatiLong = new LatLng(location.getLatitude(), location.getLongitude());
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    //Atualizando Posição
                                    markerOptions.position(currentLocationLatiLong);
                                    //Adicionando um titulo
                                    markerOptions.title("Sua Localização atual");
                                    //Alterando detalhes do marcador "COR"
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    currentLocationMarker = mMap.addMarker(markerOptions);
                                    // Localizacao do Madalena Sofia
                                    LatLng ColegioMadalenaSofia = new LatLng(-25.412121, -49.214714);
                                    mMap.addMarker(new MarkerOptions().position(ColegioMadalenaSofia).title("Madalena Sofia"));
                                    //Adicionando zoom
                                    CameraPosition cameraPositions = new CameraPosition.Builder().zoom(15).target(currentLocationLatiLong).build();
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositions));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }

}
