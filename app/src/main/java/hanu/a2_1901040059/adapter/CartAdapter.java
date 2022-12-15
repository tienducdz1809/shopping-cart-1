package hanu.a2_1901040059.adapter;

import android.content.Context;
import hanu.a2_1901040059.R;
import hanu.a2_1901040059.database.ProductManager;
import hanu.a2_1901040059.models.Product;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import hanu.a2_1901040059.MainActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import hanu.a2_1901040059.util.CurrencyFormatter;
import hanu.a2_1901040059.util.RecyclerViewItemClick;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{
    private ProductManager productManager;
    private List<Product> products;
    private RecyclerViewItemClick myClickListener;

    public CartAdapter(List<Product> products, ProductManager productManager) {
        this.productManager = productManager;
        this.products = products;
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Product product = products.get(position);

        holder.bind(product);
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_activity, parent, false );
        return new CartHolder(itemView);
    }


    public void updateList(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return products.size();
    }
    public void onItemClick(RecyclerViewItemClick mclick){
        this.myClickListener = mclick;
    }



    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }



        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    public class CartHolder extends RecyclerView.ViewHolder{
        private TextView productQuantity;
        private ImageButton increase;
        private TextView productPrice;
        private ImageButton decrease;
        private TextView sumPrice;
        private ImageView productImg;
        private TextView productName;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.product_img);
            productName = itemView.findViewById(R.id.product_name);
            productName.setWidth(MainActivity.WIDTH /3);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.quantity);
            increase = itemView.findViewById(R.id.increase_quantity);
            decrease = itemView.findViewById(R.id.reduce_quantity);
            sumPrice = itemView.findViewById(R.id.sum_price);
        }

        public void bind(Product product){
            new DownloadImageTask(productImg).execute(product.getImgUrl());
            productPrice.setText(CurrencyFormatter.format((long) product.getPrice()));
            productQuantity.setText(String.valueOf(product.getQuantity()));
            productName.setText(product.getName());
            sumPrice.setText(CurrencyFormatter.format((long) product.getPrice() * product.getQuantity()));

            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = product.getQuantity();
                    if (currentQuantity - 1 == 0){
                        productManager.delete((long) product.getId());
                        products.remove(product);

                    }else {
                        product.setQuantity(currentQuantity - 1);
                        productManager.update(product);
                    }

                    myClickListener.onItemClick(getAdapterPosition(), v);
                    notifyDataSetChanged();
                }
            });
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuantity = product.getQuantity();
                    product.setQuantity(currentQuantity + 1);
                    productManager.update(product);
                    myClickListener.onItemClick(getAdapterPosition(), v);
                    notifyDataSetChanged();
                }
            });
        }
    }

}
