package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.fragments.HallTestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavigation extends AppCompatActivity {


    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        ButterKnife.bind(this);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        Toast.makeText(BottomNavigation.this, "navigation_shop", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_gifts:
                        Toast.makeText(BottomNavigation.this, "navigation_gifts", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_cart:
                        Toast.makeText(BottomNavigation.this, "navigation_cart", Toast.LENGTH_SHORT).show();

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
                        ft.replace(R.id.testFragment, new HallTestFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
                        ft.commit();


                        break;
                    case R.id.navigation_profile:
                        Toast.makeText(BottomNavigation.this, "navigation_profile", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

        });


    }


}

