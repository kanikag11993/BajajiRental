package com.balaji.rental.adapter;

import android.view.ContextMenu;
import android.view.View;

interface PropertyViewHolder {
    void onCreateContextMenu(ContextMenu menu, View v,
                             ContextMenu.ContextMenuInfo menuInfo);
}
