package sg.edu.rp.c346.id22022868.moremovieslesson12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    //Button btnPG13;
    Button btnRating; //enhancement
    ListView lvMovies;
    ArrayList<Movie> movies;
    CustomAdapter adapter;
    //ArrayAdapter adapter;
    Spinner spinnerRating;
    String keyword;
    String rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //btnPG13 = findViewById(R.id.btnPG13);
        btnRating = findViewById(R.id.btnPG13);
        lvMovies = findViewById(R.id.lvMovies);
        spinnerRating = findViewById(R.id.spinner2);

        //setting listview components
        movies = new ArrayList<Movie>();
        adapter = new CustomAdapter(ListViewActivity.this, R.layout.row, movies);
        //adapter = new ArrayAdapter(ListViewActivity.this, android.R.layout.simple_list_item_1, movies);
        lvMovies.setAdapter(adapter);

        // Create the DBHelper object, passing in the activity's Context
        DBHelper db = new DBHelper(ListViewActivity.this);

        movies.clear();

        //Adding Song objects to the arraylist
        movies.addAll(db.getMovieDetails());

        //Closing database connection
        db.close();

        //before enhancement
/*        btnPG13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movies.clear();

                movies.addAll(db.filterPG13("PG13"));

                //songs = rated5;

                adapter.notifyDataSetChanged();

            }
        });*/

        //Enhancement
        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //all
                        movies.clear();

                        movies.addAll(db.getMovieDetails());
                        adapter.notifyDataSetChanged();
                        break;
                    case 1: //G
                        keyword = "G";
                        movies.clear();

                        movies.addAll(db.filterRating(keyword));

                        //songs = rated5;

                        adapter.notifyDataSetChanged();
                        break;
                    case 2: //PG
                        keyword = "PG";
                        movies.clear();

                        movies.addAll(db.filterRating(keyword));

                        //songs = rated5;

                        adapter.notifyDataSetChanged();
                        break;
                    case 3: //PG13
                        keyword = "PG13";
                        movies.clear();

                        movies.addAll(db.filterRating(keyword));

                        //songs = rated5;

                        adapter.notifyDataSetChanged();
                        break;
                    case 4: //NC16
                        keyword = "NC16";
                        movies.clear();

                        movies.addAll(db.filterRating(keyword));

                        //songs = rated5;

                        adapter.notifyDataSetChanged();
                        break;
                    case 5: //M18
                        keyword = "M18";
                        movies.clear();

                        movies.addAll(db.filterRating(keyword));

                        //songs = rated5;

                        adapter.notifyDataSetChanged();
                        break;
                    case 6: //R21
                        keyword = "R21";
                        movies.clear();

                        movies.addAll(db.filterRating(keyword));

                        //songs = rated5;

                        adapter.notifyDataSetChanged();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Enhancement
        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                movies.clear();

                movies.addAll(db.filterRating("PG13"));

                //songs = rated5;

                adapter.notifyDataSetChanged();

            }
        });

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Getting the object
                Movie target = movies.get(position);

                //Retrieving the Song details by using getters
                int passId = movies.get(position).getId();
                String passTitle = movies.get(position).getTitle();
                String passGenre = movies.get(position).getGenre();
                int passYear = movies.get(position).getYear();
                String passRating = movies.get(position).getRating();

                Intent third = new Intent(ListViewActivity.this, EditActivity.class);

                third.putExtra("data", (Serializable) target);
                third.putExtra("id", passId);
                third.putExtra("title", passTitle);
                third.putExtra("genre", passGenre);
                third.putExtra("year", passYear);
                third.putExtra("rating", passRating);

                startActivity(third);
            }
        });







    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(ListViewActivity.this);

        movies.clear();

        movies.addAll(db.getMovieDetails());

        adapter.notifyDataSetChanged();

    }

}
