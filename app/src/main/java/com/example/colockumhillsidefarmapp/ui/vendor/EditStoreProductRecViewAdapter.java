package com.example.colockumhillsidefarmapp.ui.vendor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.Product;
import com.example.colockumhillsidefarmapp.ProductActivity;
import com.example.colockumhillsidefarmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class EditStoreProductRecViewAdapter extends RecyclerView.Adapter<EditStoreProductRecViewAdapter.ViewHolder> {

    private static final String PRODUCT_ID = "productId";

    private ArrayList<Product> products = new ArrayList<>();
    private Context mContext;

    public EditStoreProductRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_editstore_product, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(holder.getAdapterPosition());
        holder.txtProductNameEditStoreProduct.setText(product.getName());
        String price;
        DecimalFormat df = new DecimalFormat("0.00");
        price = df.format(product.getPrice());
        holder.txtPriceEditStoreProduct.setText("$" + price + "/" + product.getPackageQuantity());
        Glide.with(mContext)
                .asBitmap()
                .load(product.getImageUrl())
                .into(holder.imgProductEditStoreProduct);



        holder.parentEditStoreProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, product.getName() + "selected", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, ProductActivity.class);
//                intent.putExtra(PRODUCT_ID, product.getId());
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parentEditStoreProduct;
        private ImageView imgProductEditStoreProduct, imgDeleteStoreItem;
        private TextView txtProductNameEditStoreProduct, txtPriceEditStoreProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentEditStoreProduct = itemView.findViewById(R.id.parentEditStoreItem);
            imgProductEditStoreProduct = itemView.findViewById(R.id.imgProductEditStoreProduct);
            imgDeleteStoreItem = itemView.findViewById(R.id.imgDeleteStoreItem);
            txtProductNameEditStoreProduct = itemView.findViewById(R.id.txtProductNameEditStoreProduct);
            txtPriceEditStoreProduct = itemView.findViewById(R.id.txtPriceEditStoreProduct);
        }
    }
}