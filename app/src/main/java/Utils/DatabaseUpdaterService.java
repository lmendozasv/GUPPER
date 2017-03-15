package Utils;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;

import sv.devla.multiprecios.DatabaseHandler;
import sv.devla.multiprecios.ProductSearchObject;


public class DatabaseUpdaterService extends Service {
        public Runnable mRunnable = null;
        DatabaseHandler databaseH;
    String myArticleUrl = "http://www.olfaelsalvador.com/Mobile/gensvc.php";
    private getTodayTask mAuthTask = null;
        public DatabaseUpdaterService() {

        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            final Handler mHandler = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    DatabaseHandler myDBHelper = new DatabaseHandler(getApplicationContext());
                    databaseH = new DatabaseHandler(DatabaseUpdaterService.this);
                    //boolean isInfoAvailable = myDBHelper.isAnyInfoAvailable(getApplicationContext());

                    //Toast.makeText(getApplicationContext(), String.valueOf(isInfoAvailable), Toast.LENGTH_LONG).show();

                    //do something

                    Log.d("SERViCE","LOGGING and sqlite");
                    mAuthTask = new getTodayTask("","");
                    mAuthTask.execute((Void) null);
                    mHandler.postDelayed(mRunnable, 10 * 5000);



                }
            };
            mHandler.postDelayed(mRunnable, 10 * 1000);

            return super.onStartCommand(intent, flags, startId);
        }
//server methods



    public class getTodayTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        getTodayTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("cuentaback",mEmail);

            String url_select = myArticleUrl;


            String lstidp= "0";
            try {
                lstidp = databaseH.getLastProductID().toString();

            }catch(Exception s){
                lstidp="0";
            }

            if(lstidp.equals("")){
                lstidp="0";
            }

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("formid", "c2.0"));
            param.add(new BasicNameValuePair("lid", lstidp));

            try {
                String resulting_json = null;
                ServiceHandler jsonParser = new ServiceHandler();
                resulting_json = jsonParser.makeServiceCall(url_select,ServiceHandler.GET, param);

                JSONArray ja;
                ja = new JSONArray(resulting_json);
                Log.d("rpt",resulting_json);

                if (ja != null) {
                    for (int i = 0; i < ja.length(); i++) {
                        String name =ja.getJSONObject(i).getString("prd_name");
                        String id=ja.getJSONObject(i).getString("prd_id");
                        Log.d("LOOP:",name.toString()+id.toString());
                        databaseH.create(new ProductSearchObject(name,id));
                    }
                }
            } catch (Exception jds) {
                Log.d("Prueba",jds.toString());
            }


            //brand

            String lstid= "0";
            try {
                lstid = databaseH.getLastBrandID().toString();

            }catch(Exception s){
                lstid="0";
            }

            if(lstid.equals("")){
                lstid="0";
            }

            ArrayList<NameValuePair> paramBrand = new ArrayList<NameValuePair>();
            paramBrand.add(new BasicNameValuePair("formid", "c3.0"));
            paramBrand.add(new BasicNameValuePair("lid", lstid));

            try {
                String resulting_json = null;
                ServiceHandler jsonParser = new ServiceHandler();
                resulting_json = jsonParser.makeServiceCall(url_select,ServiceHandler.GET, paramBrand);

                JSONArray ja;
                ja = new JSONArray(resulting_json);
                Log.d("rpt",resulting_json);

                if (ja != null) {
                    for (int i = 0; i < ja.length(); i++) {
                        String name =ja.getJSONObject(i).getString("nombre_marca");
                        String id=ja.getJSONObject(i).getString("id_marca");
                        Log.d("LOOP:",name.toString()+id.toString());
                        databaseH.createBrand(new ProductSearchObject(name,id));
                    }
                }
            } catch (Exception jds) {
                Log.d("Prueba",jds.toString());
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            mAuthTask = null;

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }


}
