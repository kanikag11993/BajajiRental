package com.balaji.rental.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balaji.rental.R;
import com.balaji.rental.main.home.HomeFragment;
import com.balaji.rental.model.PropertyModel;

import java.util.ArrayList;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder> {

    private Context mContext;
    private ArrayList<PropertyModel> propertyModel;
    private HomeFragment.PropertyCLickListener mListener;


    /**
     * Constructor for our adapter class
     */
    public PropertyListAdapter(Context context, ArrayList<PropertyModel> navigatorList, HomeFragment.PropertyCLickListener mListener) {
        this.mContext = context;
        this.propertyModel = navigatorList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.property_item, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        PropertyModel navigator = propertyModel.get(position);
        holder.title.setText(navigator.getPropertySubtitle());
        holder.subtitle.setText(navigator.getPropertyTitle());
    }

    @Override
    public int getItemCount() {
        return propertyModel.size();
    }

    public ArrayList<PropertyModel> getPropertyList() {
        return propertyModel;
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView title;
        private TextView subtitle;


        public PropertyViewHolder(final @NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.cat_card_title);
            subtitle = itemView.findViewById(R.id.cat_card_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onNavigatorSelected(propertyModel.get(getAdapterPosition()), itemView);
                }
            });
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1:
                        Log.e("Abhijeeet", "1");
                        break;

                    case 2:
                        //Do stuff
                        Log.e("Abhijeeet", "2");
                        break;
                }
                return true;
            }
        };


    }

    public interface PropertyClickListener {

        void onNavigatorSelected(PropertyModel navigator, View view);
    }
}




