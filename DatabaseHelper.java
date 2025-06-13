package com.example.studentelectives; 
import android.content.ContentValues; 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper { 
  private static final String DATABASE_NAME = "StudentElectives.db";
private static final int DATABASE_VERSION = 1;
private static final String TABLE_NAME = "electives"; private static final String COLUMN_ID = "id";
private static final String COLUMN_NAME = "name"; private static final String COLUMN_ENROLLMENT =
"enrollment_no";
private static final String COLUMN_EMAIL = "email";
private static final String COLUMN_ELECTIVE = "elective"; public DatabaseHelper(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
@Override
public void onCreate(SQLiteDatabase db) {
String createTable = "CREATE TABLE " + TABLE_NAME + "
(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
COLUMN_NAME + " TEXT, " +
COLUMN_ENROLLMENT + " TEXT UNIQUE, " +
COLUMN_EMAIL + " TEXT, " +
COLUMN_ELECTIVE + " TEXT)";
db.execSQL(createTable);
}
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
onCreate(db);
}
public boolean insertData(String name, String enrollment, String email, String elective) {
SQLiteDatabase db = this.getWritableDatabase(); ContentValues values = new ContentValues(); values.put(COLUMN_NAME, name); values.put(COLUMN_ENROLLMENT, enrollment); values.put(COLUMN_EMAIL, email); values.put(COLUMN_ELECTIVE, elective);
long result = db.insert(TABLE_NAME, null, values); return result != -1;
}
public boolean updateData(String enrollment, String name, String email, String elective) {
SQLiteDatabase db = this.getWritableDatabase(); ContentValues values = new ContentValues(); values.put(COLUMN_NAME, name); values.put(COLUMN_EMAIL, email); values.put(COLUMN_ELECTIVE, elective);
int result = db.update(TABLE_NAME, values, COLUMN_ENROLLMENT + "=?", new String[]{enrollment});
return result > 0;
}
public boolean deleteData(String enrollment) {
SQLiteDatabase db = this.getWritableDatabase(); int result = db.delete(TABLE_NAME,
COLUMN_ENROLLMENT + "=?", new String[]{enrollment}); return result > 0;
}
public Cursor getAllData() {
SQLiteDatabase db = this.getWritableDatabase(); return db.rawQuery("SELECT * FROM " + TABLE_NAME,
null);
}
}
