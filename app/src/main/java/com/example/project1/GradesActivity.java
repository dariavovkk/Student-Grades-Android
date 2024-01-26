package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {
    //Klucze do przechowywania w obiekcie Bundle
    public static final String Oceny_Key = "";
    public static final String Average_Key = "";
    ArrayList<Grade> GradeModels;
    InteraktywnyAdapterTablicy adapter;
    int ilosc_ocen=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ilosc_ocen=Integer.parseInt(getIntent().getExtras().getString(MainActivity.AMOUNT_KEY));
        if(GradeModels==null){
            GradeModels=new ArrayList<>();
            setGradeModels();
            makeAdapter();
        }
        Button buttonBack = findViewById(R.id.backToMain);
        buttonBack.setOnClickListener(v -> BackMain());
    }
    private void setGradeModels(){
        String[] Przedmioty = getResources().getStringArray(R.array.Przedmioty);
        for(int i=0; i<ilosc_ocen; i++){
            GradeModels.add(new Grade(Przedmioty[i], 2));
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Oceny_Key, GradeModels);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        GradeModels = savedInstanceState.getParcelableArrayList(Oceny_Key);
        makeAdapter();
    }

    private void BackMain(){
        Double Average = 0.0;
        for(int i=0; i<GradeModels.size(); i++) {
            Average += GradeModels.get(i).getGrade();
        }
        Average /= GradeModels.size();

        Intent intent = new Intent();
        intent.putExtra(Average_Key, String.format("%.2f", Average));
        setResult(RESULT_OK, intent);
        finish();
    }
    private void makeAdapter(){
        RecyclerView rView = findViewById(R.id.rvOceny);
        adapter = new InteraktywnyAdapterTablicy(this, GradeModels);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }
}
