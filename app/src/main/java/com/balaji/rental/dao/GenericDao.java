package com.balaji.rental.dao;

import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreatePropertyMutation;
import com.amazonaws.amplify.generated.graphql.GetElectricityQuery;
import com.amazonaws.amplify.generated.graphql.ListElectricitysQuery;
import com.amazonaws.amplify.generated.graphql.ListPropertysQuery;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.InputType;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreatePropertyInput;

public abstract class GenericDao {

    private final AWSAppSyncClient awsAppSyncClient;

    public GenericDao(final AWSAppSyncClient awsAppSyncClient) {

        this.awsAppSyncClient = awsAppSyncClient;

    }

    //Mutations

    private GraphQLCall.Callback<Operation.Data> mutationCallback = new GraphQLCall.Callback<Operation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<Operation.Data> response) {
            Log.i("Results", response.toString());
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public void create(@Nonnull final Operation mutation) {

        awsAppSyncClient.mutate((Mutation) mutation)
                .enqueue(mutationCallback);

    }

    public void query(@Nonnull final Operation query) {

        awsAppSyncClient.query((Query) query)
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(mutationCallback);

    }

}