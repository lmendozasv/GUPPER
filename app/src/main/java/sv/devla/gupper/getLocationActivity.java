package sv.devla.gupper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class getLocationActivity extends AppCompatActivity {
    private SQLiteAdapter mySQLiteAdapter;
    Spinner spinner =null;
    Spinner spinnermuni =null;
    String Deptoselected ="";
    String IDDepto="";
    String IDMuni="";
    String myArticleUrl = "http://olfaelsalvador.com/Mobile/service.php";
    private saveTasktoDB mAuthTask = null;
    String fbid="";

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



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                Deptoselected=spinner.getSelectedItem().toString();
                loadByDeparment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        spinnermuni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                SQLiteAdapter db = new SQLiteAdapter(getApplicationContext());

                IDMuni=db.getIDfromNameMuni(spinnermuni.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button save = (Button) this.findViewById(R.id.btnSaveLocation);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etphone = (EditText)findViewById(R.id.etphone);

                mAuthTask = new saveTasktoDB("","");
                mAuthTask.execute((Void) null);




            }
        });

        SharedPreferences prefs = this.getSharedPreferences("sp", MODE_PRIVATE);
        fbid = prefs.getString("fbid", "9999");
        Log.d("FBID:",fbid);

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

    }
private void loadByDeparment(){

    SQLiteAdapter db = new SQLiteAdapter(getApplicationContext());

//MUNICIPIO SAN SALVADOPR

//get id from department
    String iddepto=db.getIDfromName(Deptoselected);
    IDDepto=iddepto;
    // Spinner Drop down elements
    List<String> munis = db.getLocationsByCity(iddepto);

    // Creating adapter for spinner
    ArrayAdapter<String> dataAdapterMunis = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, munis);

    // Drop down layout style - list view with radio button
    dataAdapterMunis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // attaching data adapter to spinner
    spinnermuni.setAdapter(dataAdapterMunis);
}




    public class saveTasktoDB extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        saveTasktoDB(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("cuentaback",mEmail);

            String url_select = myArticleUrl;

            Log.d("FACEBOOK ACCOUNT",fbid);
            //Log.d("FACEBOOK ACCOUNT II",tel_);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("formid", "u4w"));

            param.add(new BasicNameValuePair("fld","id_city" ));
            param.add(new BasicNameValuePair("fldvalue", IDDepto));

            param.add(new BasicNameValuePair("flda","id_province" ));
            param.add(new BasicNameValuePair("fldvaluea", IDMuni));

            param.add(new BasicNameValuePair("fbid", fbid));

            //filename
            Log.d("Parametros",param.toString());
            try {
                String resulting_json = null;
                ServiceHandler jsonParser = new ServiceHandler();
                resulting_json = jsonParser.makeServiceCall(url_select,
                        ServiceHandler.GET, param);
                Log.d("Respuesta:",resulting_json.toString());
            } catch (Exception jds) {
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //telefono
            //depto muni
            //terminos

            Intent i = new Intent(getLocationActivity.this, TermsActivity.class);
            startActivity(i);
            finish();

            mAuthTask = null;

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }
}
