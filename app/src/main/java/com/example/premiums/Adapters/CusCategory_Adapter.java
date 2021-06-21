package com.example.premiums.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.zip.Inflater;

public class CusCategory_Adapter extends RecyclerView.Adapter<CusCategory_Adapter.Holder> {
    private List<CusCategory_model> cusCategory_list;
    Context context;



    public CusCategory_Adapter(List<CusCategory_model> cusCategory_list, Context context) {
        this.cusCategory_list = cusCategory_list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.catorgis_recyclerview_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        CusCategory_model model =cusCategory_list.get(position);
        holder.CateName.setText(model.getCateName());
        holder.CateAmount.setText(model.getCateAmount());
        holder.CatePrice.setText(model.getCatePrice());
    }

    @Override
    public int getItemCount() {
        return cusCategory_list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView CateName, CateAmount, CatePrice;
        public Holder(@NonNull View itemView) {
            super(itemView);
            CateName = itemView.findViewById(R.id.cateName_ed);
            CateAmount = itemView.findViewById(R.id.cateAmount_ed);
            CatePrice = itemView.findViewById(R.id.catePrice_ed);
        }
    }
}
