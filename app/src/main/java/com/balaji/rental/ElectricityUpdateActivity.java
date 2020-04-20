package com.balaji.rental;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.amazonaws.amplify.generated.graphql.CreateElectricityMutation;
import com.amazonaws.amplify.generated.graphql.ListElectricitysQuery;
import com.amazonaws.amplify.generated.graphql.UpdateElectricityMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.ResponseFieldMapper;
import com.apollographql.apollo.exception.ApolloException;
import com.balaji.rental.adapter.ElectricityListAdapter;
import com.balaji.rental.adapter.PropertyListAdapter;
import com.balaji.rental.dao.ElectricityDao;
import com.balaji.rental.datamodel.ElectricityBO;
import com.balaji.rental.main.home.HomeFragment;
import com.balaji.rental.model.ElectricityModel;
import com.balaji.rental.model.PropertyModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.annotation.Nonnull;

import type.CreateElectricityInput;
import type.ModelStringKeyConditionInput;
import type.UpdateElectricityInput;

public class ElectricityUpdateActivity extends AppCompatActivity {
    TextInputEditText datePicker;
    final Calendar myCalendar = Calendar.getInstance();
    Context contex;
    private static AWSAppSyncClient awsAppSyncClient;
    private Boolean isDateDialogOpen = false;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ElectricityListAdapter electricityListAdapter;
    ArrayList<ElectricityModel> electricityModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contex = this;
        for (int i = 0; i < 10; i++) {
            electricityModels.add(new ElectricityModel("July ", "22/02/199" + i, 222.222));
        }

        setContentView(R.layout.activity_electricity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        datePicker = findViewById(R.id.date_picker);
        isDateDialogOpen = false;
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.electricity_list);
        recyclerView.setLayoutManager(layoutManager);

        electricityListAdapter = new ElectricityListAdapter(this, electricityModels);
        recyclerView.setAdapter(electricityListAdapter);
        registerForContextMenu(recyclerView);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        recyclerView.setAdapter(electricityListAdapter);

        //ToDo : Revisit with better strategy.
        datePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isDateDialogOpen) {

                    new DatePickerDialog(contex, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    isDateDialogOpen = true;
                }
                return true;
            }
        });

        awsAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        update(ElectricityBO.builder().flatName("A").meterReading(190).date("2020-04-02").build());

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        isDateDialogOpen = false;
        datePicker.setText(sdf.format(myCalendar.getTime()));
    }

    private void query(@Nonnull final ElectricityBO electricityBO) {
        Query query = ListElectricitysQuery.builder()
                .flatName(electricityBO.getFlatName())
                /* .date(ModelStringKeyConditionInput.builder()
                         .beginsWith("2020-04")
                         .build())*/
                .build();

        query(query);
    }

    private void query(@Nonnull final Operation query) {

        GraphQLCall.Callback<Operation.Data> queryCallback = new GraphQLCall.Callback<Operation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<Operation.Data> response) {
                Log.i("Results", response.toString());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("Error", e.toString());
            }
        };

        awsAppSyncClient.query((Query) query)
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private void create(@Nonnull final ElectricityBO electricityBO) {
        CreateElectricityInput input = CreateElectricityInput.builder()
                .flatName(electricityBO.getFlatName())
                .meterReading(electricityBO.getMeterReading())
                .date(electricityBO.getDate())
                .build();

        create(input);
    }

    public void create(@Nonnull final CreateElectricityInput input) {

        GraphQLCall.Callback<Operation.Data> mutationCallback = new GraphQLCall.Callback<Operation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<Operation.Data> response) {
                Log.i("Results", response.toString());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("Error", e.toString());
            }
        };

        Mutation mutation = CreateElectricityMutation.builder().input(input).build();
        awsAppSyncClient.mutate((Mutation) mutation)
                .enqueue(mutationCallback);

    }

    private void update(@Nonnull final ElectricityBO electricityBO) {
        UpdateElectricityInput input = UpdateElectricityInput.builder()
                .flatName(electricityBO.getFlatName())
                .meterReading(electricityBO.getMeterReading())
                .date(electricityBO.getDate())
                .build();

        update(input);
    }

    public void update(@Nonnull final UpdateElectricityInput input) {

        GraphQLCall.Callback<Operation.Data> mutationCallback = new GraphQLCall.Callback<Operation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<Operation.Data> response) {
                Log.i("Results", response.toString());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("Error", e.toString());
            }
        };

        Mutation mutation = UpdateElectricityMutation.builder().input(input).build();
        awsAppSyncClient.mutate((Mutation) mutation)
                .enqueue(mutationCallback);

    }

}
