package com.example.sauld.pesoideal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sauld.pesoideal.Fragment.InformacionFragment;
import com.example.sauld.pesoideal.Fragment.PesoIdealFragment;

public class MainActivity extends AppCompatActivity {
    private Menu menu;
    private PesoIdealFragment pesoIdealFragment;
    private InformacionFragment informacionFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom_bar);
        menu = bottomNavigationView.getMenu();
        pesoIdealFragment = new PesoIdealFragment();
        informacionFragment = new InformacionFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenFrag, pesoIdealFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.calcul:
                        checked(0);
                        fragmentTransaction.replace(R.id.contenFrag, pesoIdealFragment);
                        break;
                    case R.id.infor:
                        checked(1);
                        fragmentTransaction.replace(R.id.contenFrag, informacionFragment);
                        break;
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return false;
            }
        });

    }

    private void checked(int op) {
        MenuItem menuItem;
        menuItem = menu.getItem(op);
        menuItem.setChecked(true);
    }


}
