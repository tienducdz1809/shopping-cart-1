package hanu.a2_1901040059.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hanu.a2_1901040059.R;
import hanu.a2_1901040059.adapter.CartAdapter;
import hanu.a2_1901040059.database.ProductManager;
import hanu.a2_1901040059.models.Product;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import hanu.a2_1901040059.util.CurrencyFormatter;
import hanu.a2_1901040059.util.RecyclerViewItemClick;


public class CartFragment extends Fragment {
    private RecyclerView cartRv;

    private TextView totalPrice;
    private List<Product> carts;
    private CartAdapter cartAdapter;
    private ProductManager productManager;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        TextView total = view.findViewById(R.id.total);
        cartRv = view.findViewById(R.id.cart_rv);
        totalPrice = view.findViewById(R.id.total_price);
        totalPrice.setText(CurrencyFormatter.format((long) calculateTotalPrice()));
        MaterialButton checkout = view.findViewById(R.id.checkout_btn);
        cartAdapter = new CartAdapter(carts, productManager);
        cartRv.setAdapter(cartAdapter);
        cartRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productManager.deleteAll();
                carts.clear();
                Toast toast= Toast.makeText(view.getContext(),"Thank you for the purchase!",Toast.LENGTH_SHORT);
                toast.show();
                cartAdapter.notifyDataSetChanged();
                refresh();
            }
        });

        return view;
    }
    public CartFragment(List<Product> carts, ProductManager productManager){
        this.productManager = productManager;

        this.carts = carts;
    }

    public void refresh(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.commit();
    }
    @Override
    public void onResume(){
        super.onResume();
        cartAdapter.onItemClick(new RecyclerViewItemClick() {
            @Override
            public void onItemClick(int position, View v) {
                totalPrice.setText(CurrencyFormatter.format((long) calculateTotalPrice()));
            }
        });
    }
    private int calculateTotalPrice(){
        int totalPrice = 0;

        for (Product product: carts){
            totalPrice += product.getPrice() * product.getQuantity();
        }

        return totalPrice;
    }

}
