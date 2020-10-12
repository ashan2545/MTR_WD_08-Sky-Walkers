package com.example.alphafitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnweek,btnmonth,btnspecial,btngroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnweek = findViewById(R.id.btnWeek2);
        btnmonth = findViewById(R.id.btnMonth);
        btnspecial = findViewById(R.id.btnSpecial);
        btngroup = findViewById(R.id.btnGroup);


        btnweek.setOnClickListener(this);
        btnmonth.setOnClickListener(this);
        btnspecial.setOnClickListener(this);
        btngroup.setOnClickListener(this);



        //Intent intent =new Intent(this,AlphaFitness.class);
         //startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWeek2: goWeek();
                break;
            case R.id.btnMonth: goMonth();
                break;
            case R.id.btnSpecial: goSpecial();
                break;
            case R.id.btnGroup: goGroup();
                break;

        }

    }

    private void goGroup() {
        Intent intent =new Intent(this,GroupPackage.class);
        startActivity(intent);
    }

    private void goSpecial() {
        Intent intent =new Intent(this,SpecialPackage.class);
        startActivity(intent);
    }

    private void goMonth() {
        Intent intent =new Intent(this,monthpackage.class);
        startActivity(intent);
    }

    public void goWeek(){
        Intent intent =new Intent(this,AlphaFitness.class);
        startActivity(intent);
    }

}