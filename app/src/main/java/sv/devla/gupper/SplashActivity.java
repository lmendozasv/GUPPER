package sv.devla.gupper;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;

import utiles.DatabaseUpdaterService;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 700;
    Intent main=null;
    String zipid,postcode,cityname;
    private getDetails mAuthTask = null;

    //private getDetails mAuthTask = null;

    String myArticleUrl = "http://olfaelsalvador.com/Mobile/service.php";
    private SQLiteAdapter mySQLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        startService(new Intent(this, DatabaseUpdaterService.class));

        YoYo.with(Techniques.Wobble)
                .duration(700)
                .playOn(findViewById(R.id.textView2));


        mySQLiteAdapter = new SQLiteAdapter(SplashActivity.this);
        mySQLiteAdapter.openToWrite();
        int rp= mySQLiteAdapter.getCitiesCount();

        Log.d("conta",String.valueOf(rp));




        if(rp>=14){


            SplashActivity.this.startAct();
        }
        else{
            SplashActivity.this.startAct();// TO-DO quitar
            Log.d("GETFROMSERVER","GET FROM SERVER");
            mAuthTask = new getDetails("","");

            mAuthTask.execute((Void) null);

        }







    }



    public void startAct(){

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity




                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                int logged = preferences.getInt("Logged", 0);

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                main = new Intent(SplashActivity.this, MainActivity.class);

                    if(logged==0){
                  //  if(logged==1){
                    startActivity(main);
                }
                else{
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }





    public class getDetails extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        getDetails(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("cuentaback",mEmail);
            String url_select = myArticleUrl;
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("formid", "c19"));

            try {
                String resulting_json = null;
                ServiceHandler jsonParser = new ServiceHandler();
                resulting_json = jsonParser.makeServiceCall(url_select,
                        ServiceHandler.GET, param);

                Log.d("Parametros",param.toString());
                Log.d("CUENTA",resulting_json.toString());
                JSONArray ja;
                ja = new JSONArray(resulting_json);


                if (ja != null) {
                    for (int i = 0; i < ja.length(); i++) {
                        cityname = ja.getJSONObject(i).getString("id_localidad");
                        postcode = ja.getJSONObject(i).getString("id_localidad_padre");
                        zipid=ja.getJSONObject(i).getString("nombre_localidad");

                        mySQLiteAdapter = new SQLiteAdapter(SplashActivity.this);
                        mySQLiteAdapter.openToWrite();
                        //mySQLiteAdapter.deleteAllDeptos();
                        if(postcode.equals("0")){
                            mySQLiteAdapter.insertDepartment(cityname,zipid);
                        }
                        else{
                            mySQLiteAdapter.insertLocality(cityname,zipid,postcode);
                        }


                    }
                }


        }catch(Exception s){

        }
            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            SplashActivity.this.startAct();

            mAuthTask = null;

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }



}