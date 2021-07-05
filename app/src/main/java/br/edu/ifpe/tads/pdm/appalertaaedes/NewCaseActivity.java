package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

public class NewCaseActivity extends AppCompatActivity {

    Spinner typeOfCase;
    Button btCancel;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);

        TextView view = (TextView) findViewById(R.id.text_view_toolbar);
        view.setText("Novo Caso");

        typeOfCase = findViewById(R.id.type_of_case);

        btCancel = findViewById(R.id.button_cancel);
        ivBack = findViewById(R.id.icon_arrow_back);

        btCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                home(v);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                home(v);
            }
        });
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

                break;
            case R.id.checkbox_febre_baixa_media:

                break;

        }
    }
}