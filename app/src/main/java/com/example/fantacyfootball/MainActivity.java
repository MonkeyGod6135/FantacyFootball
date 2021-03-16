package com.example.fantacyfootball;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare DBHandler
    DBHandler dbHandler;

    // declare EditText
    EditText nameEditText;

    // declare Spinners
    Spinner positionSpinner;
    Spinner teamSpinner;

    // declare Strings to store year and major selected in Spinners
    String Position;
    String team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize EditText
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        // initialize Spinners
        positionSpinner = (Spinner) findViewById(R.id.positionSpinner);
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);

        // initialize ArrayAdapters with values in year and major string arrays
        // and stylize them with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.position, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this,
                R.array.team, android.R.layout.simple_spinner_item);

        // further stylize ArrayAdapters with style defined by simple_spinner_dropdown_item
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        majorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set ArrayAdapters on Spinners
        positionSpinner.setAdapter(yearAdapter);
        teamSpinner.setAdapter(majorAdapter);

        // register On Item Selected Listener to Spinners
        positionSpinner.setOnItemSelectedListener(this);
        teamSpinner.setOnItemSelectedListener(this);
    }

    /**
     * This method further initializes the Action Bar of the MainActivity.
     * It gets the code in the menu main resource file and incorporates it
     * into the Action Bar.
     * @param menu menu main resource file
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected.
     * @param item selected menu item in overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_get_count_eagles :
                // Display toast that has count of CIS students
                Toast.makeText(this, getMessage("EAGLES"), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_get_count_cowboys :
                // Display toast that has count of CIT students
                Toast.makeText(this, getMessage("COWBOYS"), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_get_count_steelers :
                // Display toast that has count of CIT students
                Toast.makeText(this, getMessage("STEELERS"), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when a menu-item in the overflow menu is selected.
     * @param team selected team
     * @return
     */
    public String getMessage (String team) {
        int count = dbHandler.getCount(team);
        return (count == 1 ? count + " player." : count + " players.");
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add student menu item
     */
    public void addplayer(MenuItem menuItem) {
        // get data input in EditText and store it in String
        String name = nameEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || Position.trim().equals("") || team.trim().equals("")){
            // display "Please enter a name, store, and date!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a name, year, and major!", Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.addplayer(name, Position, team);

            // display "Student added!" Toast of none of the Strings are empty
            Toast.makeText(this, "Player added!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method gets called when an item in one of the Spinners is selected.
     * @param parent Spinner AdapterView
     * @param view MainActivity view
     * @param position position of item in Spinner that was selected
     * @param id database id of item in Spinner that was selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // get the id of the Spinner that called method
        switch (parent.getId()) {
            case R.id.teamSpinner:
                // get the item selected in the Spinner and store it in String
                team = parent.getItemAtPosition(position).toString();
                break;
            case R.id.positionSpinner:
                // get the item selected in the Spinner and store it in String
                Position = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}