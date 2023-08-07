package sg.edu.rp.c346.id22022868.moremovieslesson12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etGenre;
    EditText etYear;
    Spinner spinnerRating;
    Button btnInsert;
    Button btnShowList;
    ListView lvMovies;
    String title;
    String genre;
    int year;
    String yearCheck;
    String rating;
    ArrayList<Movie> alMovies;
    ArrayAdapter adapter;

    //used for testing
    //TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinnerRating = findViewById(R.id.spinner);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);
        lvMovies = findViewById(R.id.lvMovies);
        //test = findViewById(R.id.textViewTest);

        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //G
                        rating = "G";
                        //test.setText(rating);
                        break;
                    case 1: //PG
                        rating = "PG";
                        //test.setText(rating);
                        break;
                    case 2: //PG13
                        rating = "PG13";
                        //test.setText(rating);
                        break;
                    case 3: //NC16
                        rating = "NC16";
                        //test.setText(rating);
                        break;
                    case 4: //M18
                        rating = "M18";
                        //test.setText(rating);
                        break;
                    case 5: //R21
                        rating = "R21";
                        //test.setText(rating);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create DBHelper object, pass it in this activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                //Converting inputs to string
                title = etTitle.getText().toString();
                genre = etGenre.getText().toString();
                yearCheck = etYear.getText().toString();
                //condition to prevent nullpointerexception
                if (!yearCheck.isEmpty()) {
                    year = Integer.parseInt(yearCheck);
                }

                if (!title.isEmpty() && !genre.isEmpty() && !yearCheck.isEmpty()) {
                    //Insert a task
                    db.insertMovie(title, genre, year, rating);
                    Toast.makeText(MainActivity.this, "Movie inserted!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListViewActivity.class);
                //i.putExtra("data", );
                startActivity(i);
            }
        });
    }
}