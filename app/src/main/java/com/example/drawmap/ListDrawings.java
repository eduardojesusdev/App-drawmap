package com.example.drawmap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawmap.Models.Drawing;
import java.util.ArrayList;

public class ListDrawings extends AppCompatActivity {

    private DataBase db;
    private ListView listView;
    private TextView txtResult;
    private ArrayAdapter<Drawing> arrayAdapter;
    private ArrayList<Drawing> drawings;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_drawings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Desenhos");

        startComponents();
        startListeners();
    }

    private void startComponents(){
        listView = findViewById(R.id.listView);
        txtResult = findViewById(R.id.txtResult);
        db = new DataBase(this);
        it = new Intent(this, ShowDrawing.class);
    }

    private void startListeners(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Drawing", drawings.get(position));
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

    private void startListDrawing(){
        drawings = db.getDrawing();

        if(drawings.size() == 0){
            txtResult.setVisibility(View.VISIBLE);
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, drawings);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startListDrawing();
    }
}
