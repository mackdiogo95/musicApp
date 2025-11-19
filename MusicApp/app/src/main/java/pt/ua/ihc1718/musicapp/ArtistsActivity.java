package pt.ua.ihc1718.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class ArtistsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ArtistsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

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

        switch (id) {
            case R.id.nav_home:
                startActivity(new Intent(ArtistsActivity.this, MainActivity.class));
                break;
            case R.id.nav_albuns:
            case R.id.nav_meus_albuns:
                startActivity(new Intent(ArtistsActivity.this, AlbumsActivity.class));
                break;
            case R.id.nav_musicas:
            case R.id.nav_minhas_musicas:
                startActivity(new Intent(ArtistsActivity.this, TracksActivity.class));
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
        startActivity(new Intent(ArtistsActivity.this, ArtistActivity.class));
    }
}
