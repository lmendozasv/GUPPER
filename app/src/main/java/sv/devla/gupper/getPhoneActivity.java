package sv.devla.gupper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class getPhoneActivity extends AppCompatActivity {
    private saveTasktoDB mAuthTask = null;
    String myArticleUrl = "http://olfaelsalvador.com/Mobile/service.php";
    String tel_ = "";
    String fbid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences prefs = this.getSharedPreferences("sp", MODE_PRIVATE);
        fbid = prefs.getString("fbid", "9999");
        Log.d("FBID:",fbid);

        this.setTitle("Número móvil");

        Button save = (Button) this.findViewById(R.id.btnenviarphone);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etphone = (EditText)findViewById(R.id.etphone);
                tel_=etphone.getText().toString();
                 mAuthTask = new saveTasktoDB("","");
                 mAuthTask.execute((Void) null);




            }
        });
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
            Log.d("FACEBOOK ACCOUNT II",tel_);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("formid", "u3w"));
            param.add(new BasicNameValuePair("fld","phone" ));
            param.add(new BasicNameValuePair("fldvalue", tel_));
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

            Intent i = new Intent(getPhoneActivity.this, getLocationActivity.class);
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
