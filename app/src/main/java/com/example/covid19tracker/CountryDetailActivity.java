package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CountryDetailActivity extends AppCompatActivity {
private int positioncountry;
    private TextView cases, active, recovered, totaldeath, todaycases, todaydeath, country, critical;
    private ImageView flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        Intent intent=getIntent();
        positioncountry=intent.getIntExtra("position",0);


        getSupportActionBar().setTitle("Detail of "+AffectedCountry.countryModalslist.get(positioncountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cases = findViewById(R.id.tvcases);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.tvrecovered);
        totaldeath = findViewById(R.id.totaldeath);
        todaycases = findViewById(R.id.today_cases);
        todaydeath = findViewById(R.id.today_death);
        country = findViewById(R.id.country);
        critical = findViewById(R.id.critical);
        flag=findViewById(R.id.flagdetail);

        Glide.with(getApplicationContext()).load(AffectedCountry.countryModalslist.get(positioncountry).getFlag()).into(flag);

        country.setText(AffectedCountry.countryModalslist.get(positioncountry).getCountry());
        cases.setText(AffectedCountry.countryModalslist.get(positioncountry).getCases());
        active.setText(AffectedCountry.countryModalslist.get(positioncountry).getActive());
        recovered.setText(AffectedCountry.countryModalslist.get(positioncountry).getRecovered());
        totaldeath.setText(AffectedCountry.countryModalslist.get(positioncountry).getDeaths());
        todaycases.setText(AffectedCountry.countryModalslist.get(positioncountry).getTodaycases());
        todaydeath.setText(AffectedCountry.countryModalslist.get(positioncountry).getTodaydeaths());
        critical.setText(AffectedCountry.countryModalslist.get(positioncountry).getCritical());



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)finish();

        return super.onOptionsItemSelected(item);
    }
}