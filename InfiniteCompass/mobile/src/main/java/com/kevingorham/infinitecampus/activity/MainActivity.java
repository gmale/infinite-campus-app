package com.kevingorham.infinitecampus.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.kevingorham.infinitecampus.R;
import com.kevingorham.infinitecampus.factory.ApiFactory;
import com.kevingorham.infinitecampus.model.District;
import com.kevingorham.infinitecampus.service.InfiniteCampusJsonApi;
import com.kevingorham.infinitecampus.util.Toaster;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	@Bind(R.id.search_district)
	EditText searchDistrictEditText;

	@Bind(R.id.district_results)
	TextView districtResults;

	@Bind(R.id.fab)
	FloatingActionButton fab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

		drawer.setDrawerListener(toggle);
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
		getMenuInflater().inflate(R.menu.main, menu);
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

	@OnTouch(R.id.search_district)
	public boolean onSearchDistrictTouch(EditText searchEditText, MotionEvent motionEvent) {
		final int DRAWABLE_RIGHT = 2;
		if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
			if(motionEvent.getX() >=
					(searchEditText.getRight() - searchEditText.getPaddingRight() - searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//				Toaster.show(this, "eventX: %d\ntextRight: %d\npadRight: %d\nwidth: %d", Math.round(motionEvent.getX()), searchEditText.getRight(), searchEditText.getPaddingRight(), searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width());
				onSearchDistrict();
				return true;
			}
		}
		return false;
	}

	@OnEditorAction(R.id.search_district)
	public boolean onSearchDistrictEditorAction(int actionId) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			onSearchDistrict();
			return true;
		}
		return false;
	}

	@OnClick(R.id.fab)
	public void onFabClick(View view) {
		Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		fab.requestFocus();
	}

	private void onSearchDistrict() {
		ApiFactory factory = new ApiFactory();
		InfiniteCampusJsonApi infiniteCampusJsonApi = factory.createInfiniteCampusJsonApi();
		infiniteCampusJsonApi.getDistrict(searchDistrictEditText.getText().toString()).enqueue(new Callback<District>() {

			@Override
			public void onResponse(Call<District> call, Response<District> response) {
				District district = response.body();
				String prettyDistrict = new GsonBuilder().setPrettyPrinting().create().toJson
						(district);
				districtResults.setText(prettyDistrict);
			}

			@Override
			public void onFailure(Call<District> call, Throwable t) {
				Toaster.show(MainActivity.this, "Request failed");
			}
		});
	}
}
