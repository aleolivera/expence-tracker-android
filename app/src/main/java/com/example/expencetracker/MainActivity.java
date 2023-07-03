package com.example.expencetracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.expencetracker.async.ExecutorCreateTransaction;
import com.example.expencetracker.async.ExecutorUpdateTransaction;
import com.example.expencetracker.data.DataTransaction;
import com.example.expencetracker.databinding.ActivityMainBinding;
import com.example.expencetracker.dialog.NewTransactionDialog;
import com.example.expencetracker.dialog.UpdateTransactionDialog;
import com.example.expencetracker.entities.Transaction;
import com.example.expencetracker.ui.home.HomeViewModel;
import com.example.expencetracker.ui.summary.MonthSummaryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity
        extends AppCompatActivity
        implements NewTransactionDialog.NewAddTransactionListener,
                    UpdateTransactionDialog.UpdateTransactionListener{

    private ActivityMainBinding binding;
    private BottomNavigationView navView;
    private HomeViewModel homeViewModel;
    private MonthSummaryViewModel monthSummaryViewModel;

    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getLiveData().observe(this, transactions -> setMenu());

        monthSummaryViewModel = new ViewModelProvider(this).get(MonthSummaryViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_summary,R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        rootView = findViewById(R.id.container).getRootView();

        setMenu();

    }

    @Override
    public void onNewDialogPositiveClick(Transaction transaction,String originFragment) {
        ExecutorCreateTransaction task;

        if(Objects.equals(originFragment, "Home"))
            task = new ExecutorCreateTransaction(this, rootView, homeViewModel, transaction);
        else
            task = new ExecutorCreateTransaction(this, rootView, monthSummaryViewModel, transaction);

        task.runAsync();
    }

    @Override
    public void onUpdateDialogPositiveClick(Transaction transaction,String originFragment) {
        ExecutorUpdateTransaction task;

        if(Objects.equals(originFragment, "Home"))
            task = new ExecutorUpdateTransaction(this,rootView,transaction,homeViewModel);
        else
            task = new ExecutorUpdateTransaction(this,rootView,transaction,monthSummaryViewModel);

        task.runAsync();
    }

    private void setMenu(){
        DataTransaction data = new DataTransaction(this);

        Menu menu = navView.getMenu();
        boolean isVisible = !data.isEmpty();
        menu.getItem(1).setVisible(isVisible);
        menu.getItem(2).setVisible(false);
    }

}