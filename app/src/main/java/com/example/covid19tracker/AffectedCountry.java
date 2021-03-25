package com.example.covid19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountry extends AppCompatActivity {
    private EditText search;
    private ListView listView;
    private SimpleArcLoader simpleArcLoader;
    public static List<CountryModal>    countryModalslist = new ArrayList<>();
    CountryModal countryModal;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_country);
        search = findViewById(R.id.search);
        simpleArcLoader = findViewById(R.id.loader);
        listView = findViewById(R.id.listview);
        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchdata();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            countryAdapter.getFilter().filter(s);
            countryAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),CountryDetailActivity.class).putExtra("position",position));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)finish();

        return super.onOptionsItemSelected(item);
    }

    private void fetchdata() {
        String Url = "https://corona.lmao.ninja/v2/countries/";
        simpleArcLoader.start();
//Using Volley Library for the string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todaycases = jsonObject.getString("todayCases");
                                String todaydeath = jsonObject.getString("todayDeaths");
                                String death = jsonObject.getString("deaths");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");
                                String recovered = jsonObject.getString("recovered");
                               //Since in the api we have the another object so we have to create another json object for flag
                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagurl = object.getString("flag");

                                //defining the countryModel variable with all the value above mention so that we can get the country model name
                                countryModal=new CountryModal(flagurl,countryName,cases,todaycases,death,todaydeath,recovered,active,critical);
                               countryModalslist.add(countryModal);
                            }
countryAdapter=new CountryAdapter(AffectedCountry.this,countryModalslist);
                            listView.setAdapter(countryAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AffectedCountry.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}