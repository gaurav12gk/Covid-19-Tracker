package com.example.covid19tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    private TextView cases, active, recovered, totaldeath, todaycases, todaydeath, affectedcountries, critical;
    public SimpleArcLoader simpleArcLoader;
    private ScrollView scrollView;
    public PieChart pieChart;
    private Button trackcountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cases = findViewById(R.id.tvcases);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.tvrecovered);
        totaldeath = findViewById(R.id.totaldeath);
        todaycases = findViewById(R.id.today_cases);
        todaydeath = findViewById(R.id.today_death);
        affectedcountries = findViewById(R.id.affectedcountries);
        critical = findViewById(R.id.critical);
        scrollView = findViewById(R.id.scroll);
        pieChart = findViewById(R.id.piechart);
        simpleArcLoader = findViewById(R.id.loader);
        trackcountry=findViewById(R.id.trackcountry);



        fetchdata();
        trackcountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AffectedCountry.class));
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


    }

    //Using rest api to get the data from the API corona.lmao.ninja  website
    private void fetchdata() {
        String Url = "https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();
//Using Volley Library for the string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Data which we retrieve is in the form of the JSON so we have to change the data
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            cases.setText(jsonObject.getString("cases"));
                            active.setText(jsonObject.getString("active"));
                            recovered.setText(jsonObject.getString("recovered"));
                            totaldeath.setText(jsonObject.getString("deaths"));
                            todaycases.setText(jsonObject.getString("todayCases"));
                            todaydeath.setText(jsonObject.getString("todayDeaths"));
                            critical.setText(jsonObject.getString("critical"));
                            affectedcountries.setText(jsonObject.getString("affectedCountries"));
                            pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(cases.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Death", Integer.parseInt(totaldeath.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(GONE);
                            scrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
