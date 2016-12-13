package sv.devla.gupper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.explosionfield.ExplosionField;

import static android.R.attr.scaleHeight;
import static android.R.attr.scaleWidth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final float BLUR_RADIUS = 25f;
    //private SliderLayout mDemoSlider;
    //private SliderLayout mDemoSlider2;
//    private SliderLayout mDemoSlider3;
//    private SliderLayout mDemoSlider4;

    private ExplosionField mExplosionField;
    private AdView mAdView;


    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,
//                drawer,
//                toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                try {
                    ImageView imageView = (ImageView) findViewById(R.id.imageViewHeader);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bk4);
                    Bitmap blurredBitmap = blur(bitmap);
                    imageView.setImageBitmap(blurredBitmap);
                }
                catch(Exception s){
                    Log.d("Error",s.toString());
                }

            }
        };


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getSupportActionBar().setTitle("");

       // mDemoSlider = (SliderLayout)this.findViewById(R.id.slider);

        //mDemoSlider2 = (SliderLayout)this.findViewById(R.id.slider2);
//        mDemoSlider3 = (SliderLayout)this.findViewById(R.id.slider3);
//        mDemoSlider4 = (SliderLayout)this.findViewById(R.id.slider4);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();

        HashMap<String,Integer> file_maps2 = new HashMap<String, Integer>();
        HashMap<String,Integer> file_maps3 = new HashMap<String, Integer>();
        HashMap<String,Integer> file_maps4 = new HashMap<String, Integer>();


        file_maps.put("Promoción 1",R.drawable.i_login);
        file_maps.put("Promoción 2",R.drawable.layout_bg_name);
        file_maps.put("Promoción 3", R.drawable.i_login);
        file_maps.put("Promoción 4", R.drawable.i_login);

        file_maps2.put("",R.drawable.i_login);
        file_maps2.put("",R.drawable.layout_bg_name);
        file_maps2.put("", R.drawable.i_login);
        file_maps2.put("", R.drawable.i_login);

        file_maps3.put("Promoción 1",R.drawable.i_login);
        file_maps3.put("Promoción 2",R.drawable.layout_bg_name);
        file_maps3.put("Promoción 3", R.drawable.i_login);
        file_maps3.put("Promoción 4", R.drawable.i_login);

        file_maps4.put("Promoción 1",R.drawable.i_login);
        file_maps4.put("Promoción 2",R.drawable.layout_bg_name);
        file_maps4.put("Promoción 3", R.drawable.i_login);
        file_maps4.put("Promoción 4", R.drawable.        i_login
        );

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
            ;

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            //mDemoSlider.addSlider(textSliderView);


//            mDemoSlider3.addSlider(textSliderView);
//            mDemoSlider4.addSlider(textSliderView);
        }


        //slider 2
        for(String name : file_maps2.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView
                    .description(name)
                    .image(file_maps2.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
            ;

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);



            //mDemoSlider2.addSlider(textSliderView);

        }

        //end

        //mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        //mDemoSlider.setDuration(8000);



//        mDemoSlider2.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
//        mDemoSlider2.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider2.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider2.setDuration(5000);
//        mDemoSlider2.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));


//        mDemoSlider3.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
//        mDemoSlider3.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider3.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider3.setDuration(3000);
//
//        mDemoSlider4.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
//        mDemoSlider4.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider4.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider4.setDuration(1000);



        Drawable icon=this.getResources(). getDrawable( R.drawable.i_report
);
        Drawable icon2=this.getResources(). getDrawable( R.drawable.        i_login
        );
        Drawable icon3=this.getResources(). getDrawable( R.drawable.        i_login
        );


        Button slider2 =null;
        Button slider3 =null;
        Button slider4 =null;

       // slider2=(Button)this.findViewById(R.id.slider2);
       // slider3=(Button)this.findViewById(R.id.slider3);
       // slider4=(Button)this.findViewById(R.id.slider4);

        //slider2.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        //slider3.setCompoundDrawablesWithIntrinsicBounds(null, icon3, null, null);
        //slider4.setCompoundDrawablesWithIntrinsicBounds(null, icon2, null, null);



        Drawable drawable = getResources().getDrawable(R.drawable.i_report);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.8),
                (int)(drawable.getIntrinsicHeight()*0.8));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);

//        slider2.setCompoundDrawables(null, sd.getDrawable(), null, null);



        mExplosionField = ExplosionField.attach2Window(this);
        addListener(findViewById(R.id.root));


         String android_id = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("CD9BF0A478AF16984B67A0EC13B45623")
                .build();
        mAdView.loadAd(adRequest);


      //  carouselView = (CarouselView) findViewById(R.id.carouselView);
       // carouselView.setPageCount(sampleImages.length);

//        carouselView.setImageListener(imageListener);
//        lv = (ListView) findViewById(R.id.lvItems);
        List<String> your_array_list = new ArrayList<>();
        your_array_list.add("foo 1");
        your_array_list.add("bar 1");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );

      //  lv.setAdapter(arrayAdapter);
        ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
//https://guides.codepath.com/android/implementing-a-horizontal-listview-guide

        ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, your_array_list);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);




    }

    public Bitmap makeTransparent(Bitmap bitmap, int opacity) {
        Bitmap mutableBitmap = bitmap.isMutable()
                ? bitmap
                : bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        int colour = (opacity & 0xFF) << 24;
        canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }



    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);

        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        outputBitmap=makeTransparent(outputBitmap,190);
        return outputBitmap;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addListener(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));

            }
        } else {
            root.setClickable(true);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExplosionField.explode(v);

                    v.setOnClickListener(null);


                    //start
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent = null;
                                    intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    //MainActivity.this.finish();


                                }
                            });
                        }
                    }, 1000); // End of your timer code.
                    //end
//                    View root = findViewById(R.id.root);
//                    reset(root);
//                    addListener(root);
//                    mExplosionField.clear();
                }
            });
        }
    }

    private void reset(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }
}
