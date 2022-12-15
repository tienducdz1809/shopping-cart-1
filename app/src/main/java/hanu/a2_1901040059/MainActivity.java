package hanu.a2_1901040059;

import android.graphics.Point;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import hanu.a2_1901040059.database.ProductManager;
import hanu.a2_1901040059.fragment.CartFragment;
import hanu.a2_1901040059.fragment.ProductFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import hanu.a2_1901040059.models.Product;
import hanu.a2_1901040059.util.AsyncResponse;


public class MainActivity extends AppCompatActivity implements AsyncResponse {
    private List<Product> shoppingProducts = new ArrayList<>();
    public static int WIDTH;
    public static int HEIGHT;
    private ProductManager productManager;
    private final String productUrl = "https://mpr-cart-api.herokuapp.com/products";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productManager = ProductManager.getInstance(this);
        JsonTask jsonTask = new JsonTask();
        jsonTask.task = this;
        jsonTask.execute(productUrl);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.view_cart){
            List<Product> carts = productManager.all();

            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = new CartFragment(carts, productManager);

            manager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, "VIEW_CART" )
                    .addToBackStack("back")
                    .commit();

            return true;
        }

        return false;
    }
    @Override
    public void onTaskCompleted() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment productFragment = new ProductFragment(shoppingProducts, productManager);

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, productFragment)
                .commit();
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        private AsyncResponse task;


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
                return;
            }
            try {
                JSONArray products = new JSONArray(result);
                for(int i = 0; i < products.length(); i++){
                    JSONObject product = products.getJSONObject(i);
                    Product shoppingProduct = new Product();
                    shoppingProduct.setId(product.getInt("id"));
                    shoppingProduct.setName(product.getString("name"));
                    shoppingProduct.setImgUrl(product.getString("thumbnail"));
                    shoppingProduct.setPrice(product.getInt("unitPrice"));
                    shoppingProducts.add(shoppingProduct);
                }
                task.onTaskCompleted();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner sc = new Scanner(inputStream);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    stringBuilder.append(line);
                }
                Log.i("RESULT", "" + stringBuilder);
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}