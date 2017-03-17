package sv.devla.multiprecios;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;
    final int PERMISSION_REQUEST_CODE=10;
    final int PERMISSION_REQUEST_CODEI=100;
    private AdView mAdView;
    private saveTasktoDB mAuthTask = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String isvalid="0";

    JSONParserAlt jParser = new JSONParserAlt();
    JSONArray articles = null;
    String myArticleUrl = "http://olfaelsalvador.com/Mobile/service.php";

    SharedPreferences sp;

    SharedPreferences.Editor spe;

    Button createaccount=null;

    String name_,lastname_,birth_,fbid_,email_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/hm.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        AppEventsLogger.activateApp(this);


        loginButton = (LoginButton) this.findViewById(R.id.login_button);
        info = (TextView)findViewById(R.id.textView2);
        loginButton.setReadPermissions(Arrays.asList("public_profile",
                "email"));

//        loginButton.setReadPermissions(Arrays.asList("public_profile",
//                "email",
//                "user_birthday"));

        callbackManager = CallbackManager.Factory.create();

this.printHashKey();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );




                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());
                                String Name,lname,FEmail,bdt,arange;
                                try {
                                    Name = object.getString("first_name");
                                    name_=Name;

                                    FEmail = object.getString("email");
                                    email_=FEmail;

                                    lname= object.getString("last_name");
                                    lastname_=lname;

                                    bdt = object.getString("age_range");
                                    birth_="";

                                    fbid_= object.getString("id");

                                   // arange= object.getString("user_birthday");

                                    Log.v("Email = ", " " + FEmail);

                                    mAuthTask = new saveTasktoDB("","");

                                    mAuthTask.execute((Void) null);

                                    //Toast.makeText(getApplicationContext(), "Name " + Name+ "all:"+ object.toString(), Toast.LENGTH_LONG).show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender, age_range");
                request.setParameters(parameters);
                request.executeAsync();


                SharedPreferences.Editor editor = LoginActivity.this.getSharedPreferences("sp", MODE_PRIVATE).edit();
                editor.putInt("logged", 1);
                editor.commit();
                editor.apply();

                editor.putString("fbid", fbid_);
                editor.putString("name",name_);
                editor.putString("lastname",lastname_);
                editor.commit();
                editor.apply();

                Profile profilek = Profile.getCurrentProfile();
                String firstName = profilek.getFirstName();
                //obtener foto de perfil
                System.out.println(profilek.getProfilePictureUri(200,200));
                System.out.println(profilek.getLinkUri());

//save image to file

                file_download(profilek.getProfilePictureUri(200,200).toString());









            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        Button bntexit =(Button)this.findViewById(R.id.btnexit);

        bntexit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(LoginActivity.this, byeActivity.class);

                startActivity(i);
                finish();
            }
        });



        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {

            } else {
                requestPermission();
            }

            if (checkPermissionI())
            {

            } else {
                requestPermissionI();
            }
        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Create a common Method for both
        }



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("CD9BF0A478AF16984B67A0EC13B45623")
                .build();
        mAdView.loadAd(adRequest);
    }

    public void file_download(String uRl) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/dhaval_files");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fbid_+".gup");

        mgr.enqueue(request);

    }
    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "devlasolutions.com.thefoodbazar",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkPermissionI() {
        int result = ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.INTERNET);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionI() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(LoginActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODEI);
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(LoginActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
            case PERMISSION_REQUEST_CODEI:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }



    /*create task */
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

            SharedPreferences.Editor editor = LoginActivity.this.getSharedPreferences("sp", MODE_PRIVATE).edit();
            editor.putString("fbid", fbid_);
            editor.putString("name",name_);
            editor.putString("lastname",lastname_);
            editor.commit();
            editor.apply();

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("formid", "u1"));
            param.add(new BasicNameValuePair("email",email_ ));
            param.add(new BasicNameValuePair("name", name_));
            param.add(new BasicNameValuePair("lastname", lastname_));
            param.add(new BasicNameValuePair("fbid", fbid_));
            param.add(new BasicNameValuePair("birth", ""));




            //filename
            Log.d("Parametros",param.toString());
            try {
                String resulting_json = null;
                ServiceHandler jsonParser = new ServiceHandler();
                resulting_json = jsonParser.makeServiceCall(url_select,
                        ServiceHandler.GET, param);
                Log.d("CUENTA",resulting_json.toString());
            } catch (Exception jds) {
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //telefono
            //depto muni
            //terminos

            Intent i = new Intent(LoginActivity.this, getPhoneActivity.class);
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

