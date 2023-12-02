package Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Model.Job;


public class DatabaseQuery extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DatabaseQuery(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo lại bảng Job với cấu trúc mới
        String createJobTable = "CREATE TABLE Job (id INTEGER PRIMARY KEY, title TEXT, description TEXT, date_create TEXT, deadline TEXT, status INTEGER, rank INTEGER, type_id INTEGER, id_user INTEGER)";
        sqLiteDatabase.execSQL(createJobTable);

        // Tạo lại bảng Type_Job với cấu trúc mới
        String createTypeJobTable = "CREATE TABLE Type_Job (id INTEGER PRIMARY KEY, title TEXT)";
        sqLiteDatabase.execSQL(createTypeJobTable);

        // Tạo lại bảng User với cấu trúc mới
        String createUserTable = "CREATE TABLE User (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
        sqLiteDatabase.execSQL(createUserTable);

        // Chèn dữ liệu giả vào các bảng
        sqLiteDatabase.execSQL("INSERT INTO Type_Job (title) VALUES ('Type 1')");
        sqLiteDatabase.execSQL("INSERT INTO Type_Job (title) VALUES ('Type 2')");
        sqLiteDatabase.execSQL("INSERT INTO Type_Job (title) VALUES ('Type 3')");

        sqLiteDatabase.execSQL("INSERT INTO User (username, password) VALUES ('user1', 'password1')");
        sqLiteDatabase.execSQL("INSERT INTO User (username, password) VALUES ('user2', 'password2')");
        sqLiteDatabase.execSQL("INSERT INTO User (username, password) VALUES ('user3', 'password3')");

        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create, deadline, status, rank, type_id, id_user) VALUES ('Job 1', 'Description for Job 1', '2023-01-01', '2023-02-01', 0, 1, 1, 1)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create, deadline, status, rank, type_id, id_user) VALUES ('Job 2', 'Description for Job 2', '2023-02-01', '2023-03-01', 1, 2, 2, 2)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create, deadline, status, rank, type_id, id_user) VALUES ('Job 3', 'Description for Job 3', '2023-03-01', '2023-04-01', 2, 3, 1, 3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        id
//        title
//        description
//        date_create
//        deadline
//        status
//        rank
//        type_id
//        id_user
        values.put("title", job.getTitle());
        values.put("description", job.getDecription());
        values.put("date_create", job.getDate_create());
        values.put("deadline", job.getDeadline());
        values.put("status", job.getStatus());
        values.put("rank", job.getRank());
        values.put("type_id", job.getType_id());
        values.put("id_user", job.getId_user());

        db.insert("Job", null, values);
    }

    public ArrayList<Job> getAllJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Job", null);
        if (cursor.moveToFirst()) {
            do {
//      0  id
//      1  title
//      2  description
//      3  date_create
//      4  deadline
//      5  status
//      6  rank
//      7  type_id
//      8  id_user
                Job job = new Job();
                job.setId(cursor.getInt(0));
                job.setTitle(cursor.getString(1));
                job.setDecription(cursor.getString(2));
                job.setDate_create(cursor.getString(3));
                job.setDeadline(cursor.getString(4));
                job.setStatus(cursor.getInt(5));
                job.setRank(cursor.getInt(6));
                job.setType_id(cursor.getInt(7));
                job.setId_user(cursor.getInt(8));
                jobs.add(job);
            } while (cursor.moveToNext());
        }

        return jobs;
    }

    public void deleteJob(int jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Job", "id=?", new String[]{String.valueOf(jobId)});
    }

    // Tương tự, thêm các phương thức CRUD cho Type_Job

    // Phương thức đăng nhập cho User
    @SuppressLint("Range")
    public int login(String username, String password) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT *  FROM User WHERE username=? AND password=?", new String[]{username, password});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return userId;
    }
}
