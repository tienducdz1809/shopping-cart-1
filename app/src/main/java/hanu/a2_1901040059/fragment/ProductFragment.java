package hanu.a2_1901040059.fragment;

import android.os.Bundle;
import android.text.Editable;
import androidx.annotation.NonNull;
import hanu.a2_1901040059.adapter.ProductAdapter;
import hanu.a2_1901040059.database.ProductManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hanu.a2_1901040059.R;
import hanu.a2_1901040059.models.Product;
import java.util.ArrayList;
import java.util.List;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProductFragment extends Fragment {
    private RecyclerView productRv;
    private ProductManager productManager;
    private ProductAdapter productAdapter;
    private List<Product> shoppingProducts;
    private TextView searchProducts;


    private void filter(String text){
        List<Product> temp = new ArrayList();
        for(Product product: shoppingProducts){
            if(product.getName().toLowerCase().contains(text)){
                temp.add(product);
            }
        }
        productAdapter.updateList(temp);
    }
    public ProductFragment(List<Product> shoppingProducts, ProductManager productManager){
        this.productManager = productManager;
        this.shoppingProducts = shoppingProducts;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        productRv = view.findViewById(R.id.product_rv);
        searchProducts = view.findViewById(R.id.searchView);
        productAdapter = new ProductAdapter(shoppingProducts, productManager);
        productRv.setAdapter(productAdapter);
        productRv.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        searchProducts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

        });
        return view;
    }



}
