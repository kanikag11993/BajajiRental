package com.balaji.rental.dao;

import com.amazonaws.amplify.generated.graphql.CreateElectricityMutation;
import com.amazonaws.amplify.generated.graphql.CreatePropertyMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Query;

import javax.annotation.Nonnull;

import type.CreateElectricityInput;

public class ElectricityDao extends GenericDao {

    public ElectricityDao(AWSAppSyncClient awsAppSyncClient) {
        super(awsAppSyncClient);
    }

    public void create(@Nonnull final CreateElectricityInput input) {

        super.create(CreateElectricityMutation.builder().input(input).build());

    }

    public void query(@Nonnull final Operation query) {

        super.query(query);

    }

}
