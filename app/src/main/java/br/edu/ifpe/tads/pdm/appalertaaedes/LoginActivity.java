package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button)findViewById(R.id.button_redirect_sing_out );
        Drawable background = button.getBackground();
        background.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
    }

    public void cadastroTela(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void homeTela(View view) {
        Intent intent = new Intent(this, HomeMapActivity.class);
        startActivity(intent);
    }
}