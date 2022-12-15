package hanu.a2_1901040059.database;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "cart.db";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DbSchema.ProductTable.NAME);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DbSchema.ProductTable.NAME + "("
                    + DbSchema.ProductTable.Cols.ID + " INT, "
                    + DbSchema.ProductTable.Cols.NAME + " TEXT,"
                    + DbSchema.ProductTable.Cols.THUMNAIL + " TEXT,"
                    + DbSchema.ProductTable.Cols.PRICE + " INT,"
                    + DbSchema.ProductTable.Cols.UNITQUANTITY + " INT)");
    }


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}
