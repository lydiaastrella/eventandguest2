package com.example.eventandguest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListEventAdapter extends RecyclerView.Adapter<ListEventAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<Event> listEvent;
    private OnItemClickCallback onItemClickCallback;

    ListEventAdapter(Context context){
        this.context = context;
    }

    private ArrayList<Event> getListEvent(){
        return listEvent;
    }

    void setListPresident(ArrayList<Event> listEvent) {
        this.listEvent = listEvent;
    }

    @NonNull
    @Override
    public ListEventAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_event,parent,false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListEventAdapter.CategoryViewHolder holder, int position) {
        holder.tvName.setText(getListEvent().get(position).getName());
        holder.tvDate.setText(getListEvent().get(position).getDate());

        Glide.with(context)
                .load(getListEvent().get(position).getImage())
                .apply(new RequestOptions().override(55,55))
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listEvent.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return  getListEvent().size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvDate;
        ImageView imgPhoto;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }

    void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Event event);
    }
}
