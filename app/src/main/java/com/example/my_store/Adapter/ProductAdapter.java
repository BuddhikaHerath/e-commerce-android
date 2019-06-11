package com.example.my_store.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my_store.Model.Product;
import com.example.my_store.R;
import com.example.my_store.UI.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;


    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Product product = products.get(position);
        System.err.println("product "+product);
        holder.textViewHead.setText(String.valueOf(product.getTitle()));
        holder.textDescription.setText(String.valueOf(product.getCompany()));
        holder.textPrice.setText(String.valueOf(product.getPrice()));
        byte[] decodedString = product.getImage();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id",product.getId());
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView textDescription;
        public TextView textPrice;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public View container;



        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHeading);
            textDescription = (TextView) itemView.findViewById(R.id.textDescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewList);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_row_id);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            container = itemView;

        }


    }
}
