package com.balaji.rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreatePropertyMutation;
import com.amazonaws.amplify.generated.graphql.CreatePropertyTestMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

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
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
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
