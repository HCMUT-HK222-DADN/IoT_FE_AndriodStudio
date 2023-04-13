package Demo.Android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;

    public DBHelper(Context context) {
        super(context, "demo", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE light(xValues LONG,yValues DOUBLE);";
        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS light";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
    public void InsertData(Long x,double y){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("xValues",x);
        contentValues.put("yValues",y);
        db.insert("light",null,contentValues);
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("light", null, null);
        db.close();
    }

    public String getLastYValue() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("light", new String[]{"yValues"}, null, null, null, null, "xValues DESC", "1");
        String result = "Light";
        if (cursor != null && cursor.moveToFirst()) {
            result = String.valueOf(cursor.getColumnIndex("yValues"));
            cursor.close();
        }
        db.close();
        return result;
    }
}