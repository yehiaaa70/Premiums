package com.example.premiums;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.premiums.Adapters.UserCard_Adapter;
import com.example.premiums.Fragments.HomePageFragment;
import com.example.premiums.Models.AdminUsers_model;
import com.example.premiums.Models.UserCard_Model;
import com.example.premiums.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private CircleImageView proImage;
    private TextView Username, UserEmail;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private AdminUsers_model adminModel;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        component();
//        getData();
        View view = getLayoutInflater().inflate(R.layout.fragment_login, null);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Home Page");

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homePageFragment, R.id.loginFragment2, R.id.registerFragment, R.id.signUp2Fragment)
                .setDrawerLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        listener = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                navigationView.getMenu().getItem(0).setChecked(true);
                Toast.makeText(MainActivity.this, "null user", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "success user", Toast.LENGTH_SHORT).show();
            }
        };
//        Menu menu = navigationView.getMenu();
//        MenuItem menuItem = menu.findItem(R.id.log_out);
//        View actionView = MenuItemCompat.getActionView(menuItem);
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Navigation.findNavController(v).popBackStack(R.id.loginFragment2, false);
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nav, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.loginFragment2) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logout Done", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void component() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);

        toolbar = findViewById(R.id.toolbar);
        proImage = header.findViewById(R.id.header_profile_iv);
        Username = header.findViewById(R.id.header_name_tv);
        UserEmail = header.findViewById(R.id.header_mail_tv);
    }

    private void getData() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adminModel = dataSnapshot.getValue(AdminUsers_model.class);

                Username.setText(adminModel.getName());
                UserEmail.setText(mAuth.getCurrentUser().getEmail());
                Glide.with(MainActivity.this).load(adminModel.getUrl()).apply(new RequestOptions().centerCrop()).into(proImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getParent(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}