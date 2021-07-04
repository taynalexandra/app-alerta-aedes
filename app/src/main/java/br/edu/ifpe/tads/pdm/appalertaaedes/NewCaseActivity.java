package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class NewCaseActivity extends AppCompatActivity {

    Spinner typeOfCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);

        TextView view = (TextView) findViewById(R.id.text_view_toolbar);
        view.setText("Novo Caso");

        typeOfCase = findViewById(R.id.type_of_case);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_febre_alta:

                break;
            case R.id.checkbox_febre_baixa_media:

                break;

        }
    }
}