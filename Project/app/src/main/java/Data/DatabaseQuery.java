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
import java.util.List;
import java.util.Locale;

import Model.Job;
import Model.Type_Job;


public class DatabaseQuery extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DatabaseQuery(Context context) {
        super(context, "db.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo lại bảng Job với cấu trúc mới
        String createJobTable = "CREATE TABLE Job (id INTEGER PRIMARY KEY, title TEXT, description TEXT, date_create TEXT,date_finish TEXT, deadline TEXT, status INTEGER, rank INTEGER, type_id INTEGER, id_user INTEGER)";
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

        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 1', 'Description for Job 1', '2023-01-01 00:00:00',null, '2023-02-01 00:00:00', 0, 1, 1, 1)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 2', 'Description for Job 2', '2023-02-01 00:00:00',null, '2023-03-01 00:00:00', 0, 0, 2, 2)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 3', 'Description for Job 3', '2023-03-01 00:00:00',null, '2023-04-01 00:00:00', 0, 0, 3, 1)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 4', 'Description for Job 4', '2023-03-01 00:00:00',null, '2023-04-01 00:00:00', 0, 0, 1, 3)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 5', 'Description for Job 5', '2023-03-01 00:00:00',null, '2023-04-01 00:00:00', 0, 0, 2, 1)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 6', 'Description for Job 6', '2023-03-01 00:00:00',null, '2023-04-01 00:00:00', 0, 0, 4, 1)");
        sqLiteDatabase.execSQL("INSERT INTO Job (title, description, date_create,date_finish, deadline, status, rank, type_id, id_user) VALUES ('Job 7', 'Description for Job 7', '2023-03-01 00:00:00',null, '2023-04-01 00:00:00', 0, 0, 1, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addJob(Job job) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", job.getTitle());
        values.put("description", job.getDecription());
        values.put("date_create", job.getDate_create());
        values.put("date_finish", job.getDate_finish());
        values.put("deadline", job.getDeadline());
        values.put("status", job.getStatus());
        values.put("rank", job.getRank());
        values.put("type_id", job.getType_id());
        values.put("id_user", job.getId_user());

        db.insert("Job", null, values);
    }

    //    id INTEGER PRIMARY KEY,
//    title TEXT,
//    description TEXT,
//    date_create TEXT,
//    date_finish TEXT,
//    deadline TEXT,
//    status INTEGER,
//    rank INTEGER,
//    type_id INTEGER,
//    id_user INTEGER
    @SuppressLint("Range")
    public ArrayList<Job> getAllJobs(int id_u) {
        ArrayList<Job> jobs = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Job WHERE id_user=?", new String[]{String.valueOf(id_u)});
        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ Cursor như trước
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String dateCreate = cursor.getString(cursor.getColumnIndex("date_create"));
                String dateFinish = cursor.getString(cursor.getColumnIndex("date_finish"));
                String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                int rank = cursor.getInt(cursor.getColumnIndex("rank"));
                int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));
                int userId = cursor.getInt(cursor.getColumnIndex("id_user"));
                // Tạo đối tượng Job từ dữ liệu từ cơ sở dữ liệu
                Job job = new Job(id, title, typeId, description, dateCreate, dateFinish, deadline, rank, status, userId);
                jobs.add(job);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobs;
    }

    public void deleteJob(int jobId) {
        db = this.getWritableDatabase();
        db.delete("Job", "id=?", new String[]{String.valueOf(jobId)});
    }


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


    public void addTypeJob(String title) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", title);

        db.insert("Type_Job", null, values);

    }

    @SuppressLint("Range")
    public ArrayList<Type_Job> getAllTypeJobs() {
        ArrayList<Type_Job> typeJobs = new ArrayList<>();
        db = this.getReadableDatabase();

        String[] columns = {"id", "title"};
        Cursor cursor = db.query("Type_Job", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));

                Type_Job typeJob = new Type_Job(id, title);
                typeJobs.add(typeJob);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return typeJobs;
    }

    public void updateTypeJob(int id, String newTitle) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("title", newTitle);

        db.update("Type_Job", values, "id=?", new String[]{String.valueOf(id)});

    }

    public void deleteTypeJob(int id) {
        db = this.getWritableDatabase();
        db.delete("Type_Job", "id=?", new String[]{String.valueOf(id)});

    }

    @SuppressLint("Range")
    public ArrayList<Job> getAllJobsSortedByStatusDescending(int user_Id) {
        ArrayList<Job> jobs = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Job WHERE id_user = ? ORDER BY rank DESC", new String[]{String.valueOf(user_Id)});

        if (cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ Cursor như trước
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String dateCreate = cursor.getString(cursor.getColumnIndex("date_create"));
                String dateFinish = cursor.getString(cursor.getColumnIndex("date_finish"));
                String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                int rank = cursor.getInt(cursor.getColumnIndex("rank"));
                int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));
                int userId = cursor.getInt(cursor.getColumnIndex("id_user"));
                // Tạo đối tượng Job từ dữ liệu từ cơ sở dữ liệu
                Job job = new Job(id, title, typeId, description, dateCreate, dateFinish, deadline, rank, status, userId);
                jobs.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobs;
    }

    // Cập nhật công việc
    public void updateJob(Job job) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", job.getTitle());
        values.put("description", job.getDecription());
        values.put("date_create", job.getDate_create());
        values.put("date_finish", job.getDate_finish());
        values.put("deadline", job.getDeadline());
        values.put("status", job.getStatus());
        values.put("rank", job.getRank());
        values.put("type_id", job.getType_id());
        values.put("id_user", job.getId_user());
        db.update("Job", values, "id=?", new String[]{String.valueOf(job.getId())});
    }

    @SuppressLint("Range")
    public Job getJobById(int jobId) {
        db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(jobId)};
        Cursor cursor = db.rawQuery("SELECT * FROM Job WHERE id = ?", selectionArgs);

        Job job = null;
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String dateCreate = cursor.getString(cursor.getColumnIndex("date_create"));
            String dateFinish = cursor.getString(cursor.getColumnIndex("date_finish"));
            String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            int rank = cursor.getInt(cursor.getColumnIndex("rank"));
            int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));
            int userId = cursor.getInt(cursor.getColumnIndex("id_user"));

            // Tạo đối tượng Job từ dữ liệu từ cơ sở dữ liệu
            job = new Job(jobId, title, typeId, description, dateCreate, dateFinish, deadline, rank, status, userId);

        }

        cursor.close();
        return job;
    }

    @SuppressLint("Range")
    public String getTypeById(int typeId) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title FROM Type_Job WHERE id = ?", new String[]{String.valueOf(typeId)});
        String x = null;
        if (cursor.moveToFirst()) {
            x = cursor.getString(cursor.getColumnIndex("title"));
        }
        return x;
    }
}
