package sg.edu.rp.c346.id22022868.moremovieslesson12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //Increases by 1 when db schema changes
    private static final int DATABASE_VER = 1;

    //Defines filename of the database
    private static final String DATABASE_NAME = "movies.db";

    //Adding the columns
    private static final String TABLE_MOVIES = "Movies";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";

    //Links instance of db helper class
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //This creates the SQL table and executes it
        String createTableSql = "CREATE TABLE " + TABLE_MOVIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_RATING + " TEXT)";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        // Create table(s) again
        onCreate(db);

    }

    public void insertMovie(String title, String genre, int year, String rating) {

        // Get an instance of the database for writing
        //this line creates the database
        SQLiteDatabase db = this.getWritableDatabase();

        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();

        // Store the column name as key and the title as value
        values.put(COLUMN_TITLE, title);

        // Store the column name as key and the singers as value
        values.put(COLUMN_GENRE, genre);

        // Store the column name as key and the year as value
        values.put(COLUMN_YEAR, year);

        // Store the column name as key and the date as value
        values.put(COLUMN_RATING, rating);

        // Insert the row into the TABLE_TASK
        db.insert(TABLE_MOVIES, null, values);

        // Close the database connection
        db.close();
    }

    public ArrayList<Movie> getMovieDetails() {

        ArrayList<Movie> movies = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};

        //Creating the object
        Cursor cursor = db.query(TABLE_MOVIES, columns, null, null, null, null, null, null);

        //moveToFirst moves to the first row of the table
        //it takes in boolean
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie obj = new Movie(id, title, singers, year, rating);
                movies.add(obj);
                //moveToNext proceeds to the next row
            } while (cursor.moveToNext());
        }

        //Closing the db connection
        cursor.close();
        db.close();
        return movies;

    }

    //Used to filter all the movies with PG13 rating
    public ArrayList<Movie> filterPG13(String keyword) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_MOVIES, columns, condition, args,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie obj = new Movie(id, title, genre, year, rating);
                movies.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movies;
    }

    //filter rating method
    public ArrayList<Movie> filterRating(String keyword) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_MOVIES, columns, condition, args,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                int year = cursor.getInt(3);
                String rating = cursor.getString(4);
                Movie obj = new Movie(id, title, genre, year, rating);
                movies.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movies;
    }

    //
/*
    public ArrayList<Song> getAccordingYear(int keyword) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_STARS + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song obj = new Song(id, title, singers, year, stars);
                songs.add(obj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }
*/

    //Update selected song
    public int updateMovie(Movie data){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_GENRE, data.getGenre());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRating());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};

        //gets a number that represents no. of affected/modified/changed rows in the table
        //typically is 1 or more updated
        int result = db.update(TABLE_MOVIES, values, condition, args);
        db.close();

        return result;


    }

    public int deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_MOVIES, condition, args);
        db.close();
        return result;
    }






}
