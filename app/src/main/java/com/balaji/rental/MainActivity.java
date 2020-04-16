package com.balaji.rental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreatePropertyMutation;
import com.amazonaws.amplify.generated.graphql.CreatePropertyTestMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.UUID;

import javax.annotation.Nonnull;

import type.CreatePropertyInput;
import type.CreatePropertyTestInput;
import type.Status;

public class MainActivity extends AppCompatActivity {

    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        // enabling action bar app icon and behaving it as toggle button

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        runMutation();
        runMutationTest();
    }

    public void runMutation(){
        CreatePropertyInput createPropertyInput = CreatePropertyInput.builder().
                name("Use AppSync").
                description("Realtime and Offline").
                build();

        mAWSAppSyncClient.mutate(CreatePropertyMutation.builder().input(createPropertyInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreatePropertyMutation.Data> mutationCallback = new GraphQLCall.Callback<CreatePropertyMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreatePropertyMutation.Data> response) {
            Log.i("Results", "Added Todo");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public void runMutationTest(){
        CreatePropertyTestInput createPropertyTestInput = CreatePropertyTestInput.builder()
                .owner("Rajender")
                .flatName(UUID.randomUUID().toString())
                .address("Dummy")
                .currentMonthElectricMeterReading(120)
                .previousMonthElectricMeterReading(111)
                .electricityPerUnitCost(9.5)
                .monthlyRent("15000")
                .status(Status.VACANT)
                .tenantName("Dummy tenant")
                .waterCost(180.7)
                .build();

        mAWSAppSyncClient.mutate(CreatePropertyTestMutation.builder().input(createPropertyTestInput).build())
                .enqueue(mutationCallbackTest);
    }

    private GraphQLCall.Callback<CreatePropertyTestMutation.Data> mutationCallbackTest = new GraphQLCall.Callback<CreatePropertyTestMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreatePropertyTestMutation.Data> response) {
            Log.i("Results", "Added Todo");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

}
