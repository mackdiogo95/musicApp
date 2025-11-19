package pt.ua.ihc1718.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private ImageSliderAdapter imageSliderAdapter;
    private Timer timer;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager = (ViewPager) findViewById(R.id.newsSlider);
        imageSliderAdapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(imageSliderAdapter);
        pageSwitcher(4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay in milliseconds
    }

    // this is an inner class...
    class RemindTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (page > 2) {
                        page = 0;
                        viewPager.setCurrentItem(page);
                    } else {
                        viewPager.setCurrentItem(page++);
                    }
                }
            });
        }
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
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Log.d(TAG, "onQueryTextSubmit: query: " + query);

                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                LinearLayout searchResults = (LinearLayout) findViewById(R.id.searchResults);

                if(!query.isEmpty()) {
                    mainLayout.setVisibility(View.GONE);
                    searchResults.setVisibility(View.VISIBLE);
                } else {
                    mainLayout.setVisibility(View.VISIBLE);
                    searchResults.setVisibility(View.GONE);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: newText: " + newText);
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                LinearLayout searchResults = (LinearLayout) findViewById(R.id.searchResults);

                if(!newText.isEmpty()) {
                    mainLayout.setVisibility(View.GONE);
                    searchResults.setVisibility(View.VISIBLE);
                } else {
                    mainLayout.setVisibility(View.VISIBLE);
                    searchResults.setVisibility(View.GONE);
                }

                return false;
            }
        });


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_albuns:
            case R.id.nav_meus_albuns:
                startActivity(new Intent(MainActivity.this, AlbumsActivity.class));
                break;
            case R.id.nav_artistas:
            case R.id.nav_meus_artistas:
                startActivity(new Intent(MainActivity.this, ArtistsActivity.class));
                break;
            case R.id.nav_musicas:
            case R.id.nav_minhas_musicas:
                startActivity(new Intent(MainActivity.this, TracksActivity.class));
                break;
            case R.id.nav_signout:
                finishAffinity();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onArtistClick(View view) {
        startActivity(new Intent(MainActivity.this, ArtistActivity.class));
    }

    public void onMoreArtistsClick(View view) {
        startActivity(new Intent(MainActivity.this, ArtistsActivity.class));
    }

    public void onMoreTracksClick(View view) {
        startActivity(new Intent(MainActivity.this, TracksActivity.class));
    }

    public void onMoreAlbumsClick(View view) {
        startActivity(new Intent(MainActivity.this, AlbumsActivity.class));
    }

    public void onTrackClick(View view) {
        startActivity(new Intent(MainActivity.this, TrackActivity.class));
    }

    public void onAlbumClick(View view) {
        startActivity(new Intent(MainActivity.this, AlbumActivity.class));
    }
}
