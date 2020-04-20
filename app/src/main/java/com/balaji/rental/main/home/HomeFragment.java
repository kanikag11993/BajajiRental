package com.balaji.rental.main.home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balaji.rental.ElectricityUpdateActivity;
import com.balaji.rental.R;
import com.balaji.rental.adapter.PropertyListAdapter;
import com.balaji.rental.model.PropertyModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PropertyListAdapter propertyListAdapter;
    ArrayList<PropertyModel> propertyModelList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        for (int i = 0; i < 10; i++) {
            propertyModelList.add(new PropertyModel("title " + i, "Like any other."));
        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = root.findViewById(R.id.property_list);
        recyclerView.setLayoutManager(layoutManager);

        propertyListAdapter = new PropertyListAdapter(getContext(), propertyModelList, new PropertyCLickListener());
        recyclerView.setAdapter(propertyListAdapter);
        registerForContextMenu(recyclerView);

        return root;
    }

    public class PropertyCLickListener implements PropertyListAdapter.PropertyClickListener {

        @Override
        public void onNavigatorSelected(PropertyModel navigator, View view) {
            Intent intent = new Intent(getContext(), ElectricityUpdateActivity.class);
            startActivity(intent);
        }
    }
}
