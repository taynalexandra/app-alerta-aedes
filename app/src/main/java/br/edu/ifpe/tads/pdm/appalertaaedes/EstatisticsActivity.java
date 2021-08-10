package br.edu.ifpe.tads.pdm.appalertaaedes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EstatisticsActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistics);

        drawerLayout = findViewById(R.id.drawer_layout);

        TextView view = (TextView) findViewById(R.id.toolbarTitle);
        view.setText("Estatísticas");
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
        HomeMapActivity.redirectActivity(this, FocusActivity.class);
    }

    public void ClickInfo(View view) {
        HomeMapActivity.redirectActivity(this, InfoActivity.class);
    }

    public void ClickEstatistics(View view) {
        recreate();
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