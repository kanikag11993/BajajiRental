package com.balaji.rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreatePropertyMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreatePropertyInput;

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

}
