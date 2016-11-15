package sv.devla.gupper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class getBirthActivity extends AppCompatActivity {


    String formatedDateMYSQL="";

    EditText birth =null;

    String myArticleUrl = "http://olfaelsalvador.com/Mobile/service.php";
    private saveTasktoDB mAuthTask = null;
    String fbid="";
    String birthdt="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_birth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        birth=(EditText) this.findViewById(R.id.etbirth);


        birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(getBirthActivity.this,new mDateSetListener(), mYear, mMonth, mDay);
                    dialog.show();
                }else {
                    //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        birth.setInputType(InputType.TYPE_NULL);
        birth.setTextIsSelectable(true);

        this.setTitle("Fecha de nacimiento");



        Button save = (Button) this.findViewById(R.id.btnsavebirth);
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

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formatedDate = sdf.format(new Date(year-1900, mMonth, mDay));

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            formatedDateMYSQL = sdf2.format(new Date(year-1900, mMonth, mDay));

            birth.setText(""+formatedDate+"");
            birth.setTag(formatedDateMYSQL);
            birthdt=formatedDateMYSQL;
        }
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
            param.add(new BasicNameValuePair("formid", "u3w"));

            param.add(new BasicNameValuePair("fld","birth" ));
            param.add(new BasicNameValuePair("fldvalue", birthdt));



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

            Intent i = new Intent(getBirthActivity.this, TermsActivity.class);
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
