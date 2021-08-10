package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FocusActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus2);

        drawerLayout = findViewById(R.id.drawer_layout);

        TextView view = (TextView) findViewById(R.id.toolbarTitle);
        view.setText("Novo Foco");
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
        HomeMapActivity.redirectActivity(this, NewCaseActivity.class);
    }

    public void ClickNewFocus(View view) {
        recreate();
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
}