package com.dotnet.smugglercamp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dotnet.smugglercamp.Item;
import com.dotnet.smugglercamp.R;

import java.util.List;

public class SmugglerRecyclerAdapter extends RecyclerView.Adapter<ItemHolder> {

    private List<Item> itemList;

    public SmugglerRecyclerAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_layout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Item item = itemList.get(position);
        holder.name.setText(item.getName());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
