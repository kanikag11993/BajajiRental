package com.balaji.rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreatePropertyMutation;
import com.amazonaws.amplify.generated.graphql.CreatePropertyTestMutation;
import com.amazonaws.amplify.generated.graphql.ListElectricitysQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.balaji.rental.dao.ElectricityDao;
import com.balaji.rental.dao.GenericDao;

import java.util.UUID;

import javax.annotation.Nonnull;

import type.CreateElectricityInput;
import type.CreatePropertyInput;
import type.CreatePropertyTestInput;
import type.ModelStringKeyConditionInput;
import type.Status;

public class MainActivity extends AppCompatActivity {

    private static AWSAppSyncClient awsAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        ElectricityDao electrityDao = new ElectricityDao(awsAppSyncClient);

        electrityDao.create(CreateElectricityInput.builder()
                .flatName("A")
                .date("2020-04-02")
                .meterReading(65.9)
                .month(4)
                .updatedAt("2020-04-12")
                .build()
        );

        electrityDao.query(ListElectricitysQuery.builder()
                .flatName("A")
                .date(ModelStringKeyConditionInput.builder()
                        .beginsWith("2020-04")
                        .build())
                .build()
        );

    }

}
