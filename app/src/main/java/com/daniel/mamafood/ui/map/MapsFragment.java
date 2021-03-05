package com.daniel.mamafood.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.daniel.mamafood.MyApplication;
import com.daniel.mamafood.R;
import com.daniel.mamafood.model.Meal;
import com.daniel.mamafood.ui.meals.MealsFragmentDirections;
import com.daniel.mamafood.ui.meals.MealsViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            MealsViewModel viewModel = new ViewModelProvider(getParentFragment()).get(MealsViewModel.class);
            LatLng location = null;
            for (Meal m : viewModel.getMealLiveData().getValue()) {
                location = getLocationFromAddress(m.getAddress());
                googleMap.addMarker(new MarkerOptions().position(location).title(m.getAddress()).snippet(m.getId()));
            }
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Log.d("TAG","Marker meal id: " + marker.getSnippet());
                    MapsFragmentDirections.ActionMapsFragmentToMealDetailsFragment direction = MapsFragmentDirections.actionMapsFragmentToMealDetailsFragment(marker.getSnippet());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(direction);
                    return true;
                }
            });
//            LatLng sydney = getLocationFromAddress("Amos Tel Aviv");
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //           googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            ;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocationFromAddress("Israel"),8));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(MyApplication.context);

        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}