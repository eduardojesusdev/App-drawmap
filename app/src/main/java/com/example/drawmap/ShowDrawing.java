package com.example.drawmap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.example.drawmap.Models.Coordinate;
import com.example.drawmap.Models.Drawing;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class ShowDrawing extends AppCompatActivity {

    private DataBase db;
    private CanvasView canvasView;
    private Drawing drawing;

    public ShowDrawing() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_drawing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DataBase(this);
        canvasView = findViewById(R.id.canvas);
        drawing = new Drawing();

        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        if(bundle != null){
            drawing = (Drawing) bundle.getSerializable("Drawing");
        }

        getSupportActionBar().setTitle(drawing.getName());

        db.getCoordinates(drawing);

        for (Coordinate coordinate : drawing.getCoordinates()) {
            canvasView.moveTouch(coordinate.getX(), coordinate.getY());
            canvasView.invalidate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_drawing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg
                .setTitle("Atenção")
                .setIcon(R.mipmap.ic_warning)
                .setMessage("Deseja excluir o desenho ?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteDrawing(drawing);
                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                    .show();
            return true;
        }

        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
