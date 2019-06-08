package com.example.my_store.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.my_store.Model.Product;
import com.example.my_store.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemCLick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

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
        holder.textViewHead.setText(String.valueOf(product.getTitle()));
        holder.textDescription.setText(String.valueOf(product.getCompany()));
        holder.textPrice.setText(String.valueOf(product.getPrice()));
        Picasso.with(context)
                .load(product.getImage())
                .into(holder.imageView);


//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Clicked" + product.getTitle(), Toast.LENGTH_LONG).show();
////
//                Intent intent = new Intent(context, View_Product.class);
//                Gson gson = new Gson();
//                String productObject = gson.toJson(product);
//                intent.putExtra("product", productObject);
//                context.startActivity(intent);


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



        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHeading);
            textDescription = (TextView) itemView.findViewById(R.id.textDescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewList);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_row_id);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);

      itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(mListener !=null){
                  int position = getAdapterPosition();
                  if(position != RecyclerView.NO_POSITION){
                      mListener.onItemCLick(position);
                  }
              }
          }
      });
        }


    }
}
