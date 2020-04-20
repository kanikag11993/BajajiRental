package com.balaji.rental;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.balaji.rental.util.SharedPrefConstants.IS_USER_LOGGED_IN;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    Fragment loginFragment = new LoginFragment();
    private View contentView;
    private View loginFragmentView;
    private BottomNavigationView bottomNavView;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getSharedPreferences(
                getString(R.string.shared_pref), Context.MODE_PRIVATE);
        contentView = findViewById(R.id.nav_host_fragment);
        bottomNavView = findViewById(R.id.nav_view);
        loginFragmentView = findViewById(R.id.login_fragment);
        Boolean isUserLoggedIn = sharedPref.getBoolean(IS_USER_LOGGED_IN, false);
        if (isUserLoggedIn) {

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            // enabling action bar app icon and behaving it as toggle button

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavView, navController);
        } else {
// Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.container, loginFragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
            contentView.setVisibility(View.GONE);
            bottomNavView.setVisibility(View.GONE);
            loginFragmentView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void userLoggedIn() {
        getSupportFragmentManager().beginTransaction().remove(loginFragment).commit();
        contentView.setVisibility(View.VISIBLE);
        bottomNavView.setVisibility(View.VISIBLE);
        loginFragmentView.setVisibility(View.GONE);
        sharedPref.edit().putBoolean(IS_USER_LOGGED_IN, true).apply();
    }
}
