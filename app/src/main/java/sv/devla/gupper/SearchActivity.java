package sv.devla.gupper;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);



        //View smallImageView = findViewById(R.id.textView);
        View editText = findViewById(R.id.etautobusqueda);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          //  smallImageView.setTransitionName(getString(R.string.activity_text_trans));
            editText.setTransitionName(getString(R.string.activity_mixed_trans));
        }

    }
}
