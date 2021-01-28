package com.megednan.molxforsale.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;
import com.megednan.molxforsale.R;
import com.megednan.molxforsale.databinding.ActivityMainBinding;
import com.megednan.molxforsale.firebase.FireBaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private AppBarConfiguration appBarConfiguration;
    private FireBaseAuth manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolBar);

        manager=new FireBaseAuth();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainNavHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.categoriesFragment, R.id.allAdsFragment,R.id.createAdFragment,
                        R.id.profileFragment,R.id.myAdsFragment).setDrawerLayout(binding.drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.op_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Bundle bundle= new Bundle();
                bundle.putString("Search",s);
                navHostFragment.getNavController().navigate(R.id.searchFragment,bundle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.op_menu_signout){
            manager.signOut(this);
            startActivity(new Intent(this,AuthActivity.class));
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
}