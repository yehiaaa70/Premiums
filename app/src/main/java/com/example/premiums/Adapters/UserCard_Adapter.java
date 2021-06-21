package com.example.premiums.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.premiums.Models.UserCard_Model;
import com.example.premiums.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;


public class UserCard_Adapter extends RecyclerView.Adapter<UserCard_Adapter.Holder> implements Filterable {
    private List<UserCard_Model> modelList;
    private List<UserCard_Model> modelListALL;
    private Context context;
    private OnUserCardClick onUserCardClick;
    private OnPhoneIconClick onPhoneIconClick;
    private OnBookmarkIconClick onBookmarkIconClick;
    private OnUserCardLongClick onUserCardLongClick;
    private OnUserDetailsClick onUserDetailsClick;


    public interface OnUserCardClick {
        void onItemClick(View view, int position);
    }

    public interface OnUserCardLongClick {
        void onItemLongClick(View view, int position);
    }

    public interface OnUserDetailsClick {
        void onDetailsClick(View view, int position);
    }


    public interface OnPhoneIconClick {
        void onPhoneIconClick(View view, int position);
    }

    public interface OnBookmarkIconClick {
        void OnBookmarkIconClick(ImageView imageView, int position);
    }


    public UserCard_Adapter(List<UserCard_Model> modelList, Context context, OnUserCardClick onUserCardClick, OnUserCardLongClick onUserCardLongClick, OnPhoneIconClick onPhoneIconClick, OnBookmarkIconClick onBookmarkIconClick, OnUserDetailsClick onUserDetailsClick) {
        this.modelList = modelList;
        modelListALL=new ArrayList<>(modelList);
        this.context = context;
        this.onUserCardClick = onUserCardClick;
        this.onPhoneIconClick = onPhoneIconClick;
        this.onBookmarkIconClick = onBookmarkIconClick;
        this.onUserCardLongClick = onUserCardLongClick;
        this.onUserDetailsClick = onUserDetailsClick;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<UserCard_Model> list_filter = new ArrayList<>();

                if (constraint.toString().isEmpty()) {
                    list_filter.addAll(modelListALL);
                } else {
                    for (UserCard_Model model : modelListALL) {
                        if (model.getUserPhone().contains(constraint.toString())
                                || model.getUserName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            list_filter.add(model);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list_filter;


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                modelList.clear();
                modelList.addAll((Collection<? extends UserCard_Model>) results.values);
                notifyDataSetChanged();

            }
        };
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recy_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        UserCard_Model userCard_model = modelList.get(position);
        holder.user_name.setText(userCard_model.getUserName());
        holder.user_phone.setText(userCard_model.getUserPhone());
        Glide.with(context).load(userCard_model.getUrl()).apply(new RequestOptions().centerCrop()).into(holder.user_image);
        if(userCard_model.isBook()){
            holder.bookmark_icon.setImageResource(R.drawable.ic_right_bookmark);
        }else {
            holder.bookmark_icon.setImageResource(R.drawable.ic_normal_bookmark);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserCardClick.onItemClick(v,position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onUserCardLongClick.onItemLongClick(v, position);
                return false;
            }
        });

        holder.phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPhoneIconClick.onPhoneIconClick(view, position);
            }
        });

        holder.bookmark_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookmarkIconClick.OnBookmarkIconClick(holder.bookmark_icon, position);
            }
        });

        holder.user_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserDetailsClick.onDetailsClick(v,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView user_image, phone_icon, bookmark_icon;
        TextView user_name;
        TextView user_phone;
        TextView user_details;


        public Holder(@NonNull View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_image_iv);
            user_name = itemView.findViewById(R.id.user_name_tv);
            user_phone = itemView.findViewById(R.id.user_phone_tv);
            user_details = itemView.findViewById(R.id.details_tv);
            phone_icon = itemView.findViewById(R.id.callIcon_btn);
            bookmark_icon = itemView.findViewById(R.id.bookmark_icon);
        }
    }
}
