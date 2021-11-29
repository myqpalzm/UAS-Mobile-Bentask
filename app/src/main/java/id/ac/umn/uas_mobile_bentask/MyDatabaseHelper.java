package id.ac.umn.uas_mobile_bentask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ToDo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CATEGORY = "my_category";
    private static final String TABLE_TASK = "task";
    private static final String CATEGORY_ID = "_id";
    private static final String CATEGORY_NAME = "category_name";
    private static final String TASK_ID = "taskId";
    private static final String TASK_NAME = "taskName";
    private static final String TASK_DESCRIPTION = "taskDesription";
    private static final String TASK_DATE = "taskDate";
    private static final String TASK_TIME = "taskTime";
    private static final String categoryId = "categoryId";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_CATEGORY +
                " (" + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CATEGORY_NAME + " TEXT);";
        db.execSQL(query1);
        String query2 = "CREATE TABLE "
                + TABLE_TASK + " ("
                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK_NAME + " TEXT, "
                + TASK_DESCRIPTION + " TEXT, "
                + TASK_DATE + " TEXT, "
                + TASK_TIME + " TEXT, "
                + categoryId + " INTEGER, "
                + " FOREIGN KEY ("+categoryId+") REFERENCES " +TABLE_CATEGORY+"("+ CATEGORY_ID +"));";
        db.execSQL(query2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    void addCategory(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_NAME, title);
        long result = db.insert(TABLE_CATEGORY,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void addTask(String title,String desc,String date,String time,String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_NAME, title);
        cv.put(TASK_DESCRIPTION,desc);
        cv.put(TASK_DATE,date);
        cv.put(TASK_TIME, time);
        cv.put(categoryId, id);

        long result = db.insert(TABLE_TASK,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readTasks(String COLUMN_ID){
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + categoryId + " = " + COLUMN_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CATEGORY_NAME, title);

        long result = db.update(TABLE_CATEGORY, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void updateDataTask(String row_id, String title,String desc,String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_NAME, title);
        cv.put(TASK_DESCRIPTION, desc);
        cv.put(TASK_DATE,date);
        cv.put(TASK_TIME,time);
        long result = db.update(TABLE_TASK, cv, "taskId=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CATEGORY, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteTask(String task_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TASK, "taskId=?", new String[]{task_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}
