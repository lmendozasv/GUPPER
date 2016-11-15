package sv.devla.gupper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class getLocationActivity extends AppCompatActivity {
    private SQLiteAdapter mySQLiteAdapter;
    Spinner spinner =null;
    Spinner spinnermuni =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner=(Spinner)this.findViewById(R.id.spdepto);
        spinnermuni=(Spinner)this.findViewById(R.id.spmuni);


        this.loadSpinnerData();
        this.setTitle("Ubicación");
    }


    private void loadSpinnerData() {
        //Departamento - San Salvador
        // database handler
        SQLiteAdapter db = new SQLiteAdapter(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getCities();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


//MUNICIPIO SAN SALVADOPR



        // Spinner Drop down elements
        List<String> munis = db.getLocationsByCity("6");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterMunis = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, munis);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnermuni.setAdapter(dataAdapterMunis);
    }



}
