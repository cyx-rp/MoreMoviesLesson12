package sg.edu.rp.c346.id22022868.moremovieslesson12;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText etID;
    EditText etTitle;
    EditText etGenre;
    EditText etYear;
    Spinner spinnerRating;
    Button btnUpdate, btnDelete, btnCancel;
    Movie data;
    int id;
    String title;
    String genre;
    int year;
    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinnerRating = findViewById(R.id.spinner);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        //prevent edits to movie ID
        etID.setEnabled(false);

        //receiving Intent from 2nd activity, passing data of lv item selected
        Intent third = getIntent();
        data = (Movie) third.getSerializableExtra("data");
        id = data.getId(); //third.getIntExtra("id", 0);
        title = data.getTitle();//third.getStringExtra("title");
        genre = data.getGenre();//third.getStringExtra("singers");
        year = data.getYear();//third.getIntExtra("year", 0);
        rating = data.getRating();//third.getIntExtra("stars", 0);

        //setting the et such that it shows the data of the lv item selected
        etID.setText(Integer.toString(id));
        etTitle.setText(title);
        etGenre.setText(genre);
        etYear.setText(Integer.toString(year));
        spinnerRating.setSelection(getIndexByString(spinnerRating, rating));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper db = new DBHelper(EditActivity.this);

                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setRating(rating);

                db.updateMovie(data);
                db.close();
                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Create dialog builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                //Set dialog details
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie " + title);
                myBuilder.setCancelable(true);

                //Configure positive button
                myBuilder.setPositiveButton("Cancel", null);

                //Configure the negative button
                myBuilder.setNegativeButton("Delete",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        DBHelper db = new DBHelper(EditActivity.this);
                        db.deleteMovie(id);

                        db.close();
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create dialog builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                //Set dialog details
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(true);

                //Configure positive button
                myBuilder.setPositiveButton("Do Not Discard", null);

                //Configure the negative button
                myBuilder.setNegativeButton("Discard",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){

                        finish();

                    }
                });

                //without this dialogbox wont show
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });











    }



    private int getIndexByString(Spinner spinnerRating, String rating) {
        int index = 0;

        for (int i = 0; i < spinnerRating.getCount(); i++) {
            if (spinnerRating.getItemAtPosition(i).toString().equalsIgnoreCase(rating)) {
                index = i;
                break;
            }
        }
        return index;
    }








}
