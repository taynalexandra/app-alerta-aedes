package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.appalertaaedes.databinding.ActivityHomeMapBinding;
import br.edu.ifpe.tads.pdm.appalertaaedes.model.Focus;
import br.edu.ifpe.tads.pdm.appalertaaedes.model.Ponto;
import br.edu.ifpe.tads.pdm.appalertaaedes.model.User;

public class HomeMapActivity extends FragmentActivity implements OnMapReadyCallback, PopupMenu.OnMenuItemClickListener {

    DrawerLayout drawerLayout;
    private GoogleMap mMap;
    private ActivityHomeMapBinding binding;
    private FloatingActionButton fabHome;
    private boolean fine_location;
    final int FINE_LOCATION_REQUEST = 0;
    private FirebaseAuthListener authListener;
    private FirebaseAuth mAuth;
    private User user;
    private ArrayList<Marker> listaMarker;

    int cont = 0;
    String temp;

    DatabaseReference drPonto;

    LatLng prov = null;

    View mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = findViewById(R.id.drawer_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        requestPermissions();

        fabHome = findViewById(R.id.fab_home);

        //registerForContextMenu(fabHome);

        fabHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showMenu(v);
            }
        });

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance("https://alerta-aedes-8b4e0-default-rtdb.firebaseio.com/");

        drPonto = fbDB.getReference("cases");

        listaMarker = new ArrayList<>();

        drPonto.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Ponto ponto = dataSnapshot.getValue(Ponto.class);
                boolean existe = false;

                for(Marker m : listaMarker){
                    LatLng posicao = m.getPosition();
                    if(posicao.latitude == Double.parseDouble(ponto.getLat()) && posicao.longitude == Double.parseDouble(ponto.getLon())){
                        if(!m.getTitle().contains(ponto.getType())){
                            m.setTitle(m.getTitle() + " | "+ ponto.getType());
                        }
                        existe = true;
                        break;
                    }
                }

                if(!existe){
                    colocaPonto(ponto);
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        mapView = mapFragment.getView();
    }

    private void colocaPonto(Ponto ponto) {
        LatLng marcador = new LatLng(Double.parseDouble(ponto.getLat()), Double.parseDouble(ponto.getLon()));

        Marker marker = mMap.addMarker(new MarkerOptions().position(marcador).title("Doença(s): "+ponto.getType()));
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_decama));
        //mMap.addMarker(new MarkerOptions().position(marcador).title("Doença(s): "+ponto.getDescricao())).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_decama));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marcador));
        listaMarker.add(marker);


    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, fabHome);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.floating_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(HomeMapActivity.this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_case:
                Intent intentNewCase = new Intent(this, NewCaseActivity.class);
                startActivity(intentNewCase);
                return true;
            case R.id.menu_new_focus:
                Intent intentNewFocus = new Intent(this, FocusActivity.class);
                startActivity(intentNewFocus);
                return true;
            default:
                return false;
        }
    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        recreate();
    }

    public void ClickNewCase(View view) {
        redirectActivity(this, NewCaseActivity.class);
    }

    public void ClickNewFocus(View view) {
        redirectActivity(this, FocusActivity.class);
    }

    public void ClickInfo(View view) {
        redirectActivity(this, InfoActivity.class);
    }

    public void ClickEstatistics(View view) {
        redirectActivity(this, EstatisticsActivity.class);
    }

    public void ClickLogOut(View view) {
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Sair");
        builder.setMessage("Tem certeza que deseja sair?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    mAuth.signOut();
                } else {
                    Toast.makeText(activity, "Erro!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    private void requestPermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        this.fine_location = (permissionCheck == PackageManager.PERMISSION_GRANTED);

        if (this.fine_location) return;

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean granted = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        this.fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(this.fine_location);
        }
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


        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                return false;
            }
        });

        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {

            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(this.fine_location);

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 280);
        }

    }

    public void newCase(View view) {
        Intent intent = new Intent(this, NewCaseActivity.class);
        startActivity(intent);
    }

    public void pesquisaDoenca(View view){
        String doenca = ((EditText)findViewById(R.id.procureAqui)).getText().toString();

        mMap.clear();

        for(Marker m : listaMarker){
            String marcadorTitulo = m.getTitle().toLowerCase();
            if(marcadorTitulo.contains(doenca.toLowerCase())){
                LatLng marcador = m.getPosition();
                m = mMap.addMarker(new MarkerOptions().position(marcador).title(m.getTitle()));
                m.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_decama));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marcador));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}