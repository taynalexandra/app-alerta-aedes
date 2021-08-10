package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.tads.pdm.appalertaaedes.model.Case;
import br.edu.ifpe.tads.pdm.appalertaaedes.model.Geolocation;

public class NewCaseActivity extends AppCompatActivity {

    private Spinner typeOfCase;
    private Button btAdd;
    private EditText etPlace;
    private DrawerLayout drawerLayout;
    private Case newCase;
    private List<String> symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);

        TextView view = (TextView) findViewById(R.id.toolbarTitle);
        view.setText("Novo Caso");

        newCase = new Case();
        symptoms = new ArrayList<>();

        typeOfCase = findViewById(R.id.type_of_case);

        btAdd = findViewById(R.id.button_add);
        etPlace = findViewById(R.id.et_place);

        btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addCase();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void home(View v) {
        Intent intent = new Intent(this, HomeMapActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, popup.getMenu());
        popup.show();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_febre_alta:
                if (checked)
                    symptoms.add("Febre Alta");
                else
                    symptoms.remove("Febre Alta");
                break;
            case R.id.checkbox_febre_baixa_media:
                if (checked)
                    symptoms.add("Febre Média/Baixa");
                else
                    symptoms.remove("Febre Média/Baixa");
                break;
            case R.id.checkbox_dores_articulacoes:
                if (checked)
                    symptoms.add("Dores nas Articulações");
                else
                    symptoms.remove("Dores nas Articulações");
                break;
            case R.id.checkbox_coceira:
                if (checked)
                    symptoms.add("Coceira");
                else
                    symptoms.remove("Coceira");
                break;
            case R.id.checkbox_vermelhidao_olhos:
                if (checked)
                    symptoms.add("Vermelhidão nos Olhos");
                else
                    symptoms.remove("Vermelhidão nos Olhosa");
                break;
            case R.id.checkbox_manchas_pele:
                if (checked)
                    symptoms.add("Manchas Vermelhas na Pele");
                else
                    symptoms.remove("Manchas Vermelhas na Pele");
                break;
            case R.id.checkbox_dor_cabeca:
                if (checked)
                    symptoms.add("Dor de Cabeça");
                else
                    symptoms.remove("Dor de Cabeça");
                break;
            case R.id.checkbox_dor_muscular:
                if (checked)
                    symptoms.add("Dor Muscular");
                else
                    symptoms.remove("Dor Muscular");
                break;
            case R.id.checkbox_fraqueza:
                if (checked)
                    symptoms.add("Fraqueza");
                else
                    symptoms.remove("Fraqueza");
                break;
            case R.id.checkbox_nauseas_vomito:
                if (checked)
                    symptoms.add("Náuseas/Vômito");
                else
                    symptoms.remove("Náuseas/Vômit");
                break;
        }
    }

    public void ClickMenu(View view) {
        HomeMapActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        HomeMapActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        HomeMapActivity.redirectActivity(this, HomeMapActivity.class);
    }

    public void ClickNewCase(View view) {
        recreate();
    }

    public void ClickNewFocus(View view) {
        HomeMapActivity.redirectActivity(this, FocusActivity.class);
    }

    public void ClickInfo(View view) {
        HomeMapActivity.redirectActivity(this, InfoActivity.class);
    }

    public void ClickEstatistics(View view) {
        HomeMapActivity.redirectActivity(this, EstatisticsActivity.class);
    }

    public void ClickLogOut(View view) {
        HomeMapActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        HomeMapActivity.closeDrawer(drawerLayout);
    }

    public void addCase() {

        String type = (String) typeOfCase.getSelectedItem();
        newCase.setType(type);
        newCase.setSymptoms(this.symptoms);

        String address = etPlace.getText().toString();
        Geolocation geolocation = new Geolocation();
        geolocation.getAddress(address, getApplicationContext(), new GeoHandler());
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String address;
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();

            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    address = "Latitude: " + bundle.getString("latitude") + "\nLongitude: " + bundle.getString("longitude");
                    newCase.setLat(bundle.getString("latitude"));
                    newCase.setLon(bundle.getString("longitude"));

                    DatabaseReference drCases = FirebaseDatabase.getInstance("https://alerta-aedes-8b4e0-default-rtdb.firebaseio.com/").getReference("cases");
                    drCases.push().setValue(newCase);

                    Intent intent = new Intent(NewCaseActivity.this, HomeMapActivity.class);
                    startActivity(intent);
                    break;
                default:
                    address = null;
            }
        }
    }

}