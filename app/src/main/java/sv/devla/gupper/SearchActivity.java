package sv.devla.gupper;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AdView mAdView;
    private AdView mAdView2;
    DatabaseHandler databaseH;
    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> myAdapter;
    String[] item = new String[] {""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //       WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().hide();
        this.setTitle("Búsqueda de producto");
        setContentView(R.layout.activity_search);



        //View smallImageView = findViewById(R.id.textView);
        View editText = findViewById(R.id.etautobusqueda);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          //  smallImageView.setTransitionName(getString(R.string.activity_text_trans));
            editText.setTransitionName(getString(R.string.activity_mixed_trans));
        }
//autocomplete inicio
        try{

            databaseH = new DatabaseHandler(SearchActivity.this);
            myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.etautobusqueda);
            myAutoComplete.addTextChangedListener(new SearchByNameListener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //autocomplete fin


        mAdView = (AdView) findViewById(R.id.adView);
        mAdView2 = (AdView) findViewById(R.id.adViewSearch1);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("CD9BF0A478AF16984B67A0EC13B45623")
                .build();

        AdRequest adRequest2 = new AdRequest.Builder()
                .addTestDevice("CD9BF0A478AF16984B67A0EC13B45623")
                .build();

        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest);

    }


    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<ProductSearchObject> products = databaseH.readBrand(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (ProductSearchObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }
}
