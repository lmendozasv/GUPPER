package sv.devla.gupper;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //View smallImageView = findViewById(R.id.textView);
        View editText = findViewById(R.id.etautobusqueda);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          //  smallImageView.setTransitionName(getString(R.string.activity_text_trans));
            editText.setTransitionName(getString(R.string.activity_mixed_trans));
        }

    }
}
