package hanu.a2_1901040059.database;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.CursorWrapper;



import hanu.a2_1901040059.models.Product;


public class ProductCursorWrapper extends CursorWrapper {

    public Product getProduct(){
        Product product = null;
        String imgUrl = getString(getColumnIndex(DbSchema.ProductTable.Cols.THUMNAIL));
        int price = getInt(getColumnIndex(DbSchema.ProductTable.Cols.PRICE));
        int id = getInt(getColumnIndex(DbSchema.ProductTable.Cols.ID));
        String name = getString(getColumnIndex(DbSchema.ProductTable.Cols.NAME));
        int quantity = getInt(getColumnIndex(DbSchema.ProductTable.Cols.UNITQUANTITY));

        if(id != 0 && valid(name) && valid(imgUrl) && price > 0 && quantity > 0)
            product = new Product(id, imgUrl, name, price, quantity);
        return product;
    }
    public ProductCursorWrapper(Cursor cursor) {

        super(cursor);
    }



    private boolean valid(String string){
        return string != null && string.length() > 0;
    }
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        moveToFirst();
        while(!isAfterLast()){
            Product product = getProduct();
            products.add(product);
            moveToNext();
        }
        return products;
    }
    public Product getAProduct(){
        moveToFirst();

        if(getCount()==0){
            return null;
        }

        Product product = null;

        int id = getInt(getColumnIndex(DbSchema.ProductTable.Cols.ID));
        String name = getString(getColumnIndex(DbSchema.ProductTable.Cols.NAME));
        String imgUrl = getString(getColumnIndex(DbSchema.ProductTable.Cols.THUMNAIL));
        int price = getInt(getColumnIndex(DbSchema.ProductTable.Cols.PRICE));
        int quantity = getInt(getColumnIndex(DbSchema.ProductTable.Cols.UNITQUANTITY));

        if(id != 0 && valid(name) && valid(imgUrl) && price > 0 && quantity > 0)
            product = new Product(id, imgUrl, name, price, quantity);
        return product;
    }

}