package com.example.drawmap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.drawmap.Models.Coordinate;
import com.example.drawmap.Models.Drawing;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private DataBase db;
    private Drawing drawing;

    private Button btnTop;
    private Button btnLeft;
    private Button btnRight;
    private Button btnBottom;

    private CanvasView canvasView;
    private final int MAX_STEPS = 981;
    private final int STEPS = 109;
    private int lastTouch;
    private float x;
    private float y;

    private Functions functions;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBase(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startComponents();
        startListeners();
        startCanvas();
    }

    private void startComponents(){
        btnTop = findViewById(R.id.btnTop);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        btnBottom = findViewById(R.id.btnBottom);
        canvasView = findViewById(R.id.canvas);
        functions = new Functions(this);
    }

    private void startCanvas(){
        drawing = new Drawing();
        canvasView.clearCanvas();
        lastTouch = 2;
        x = 5;
        y = 6;
        canvasView.mPath.moveTo(x, y);
    }

    private void startListeners(){
        btnTop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(lastTouch != 3) {
                    if (y > STEPS) {
                        lastTouch = 1;
                        y -= STEPS;
                        canvasView.moveTouch(x, y);
                        canvasView.invalidate();
                        drawing.addCoordinate(new Coordinate(x, y));
                    }
                }
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(lastTouch != 2){
                    if(x > STEPS){
                        lastTouch = 4;
                        x -= STEPS;
                        canvasView.moveTouch(x, y);
                        canvasView.invalidate();
                        drawing.addCoordinate(new Coordinate(x, y));
                    }
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(lastTouch != 4) {
                    if (x < MAX_STEPS) {
                        lastTouch = 2;
                        x += STEPS;
                        canvasView.moveTouch(x, y);
                        canvasView.invalidate();
                        drawing.addCoordinate(new Coordinate(x, y));
                    }
                }
            }
        });

        btnBottom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(lastTouch != 1) {
                    if (y < MAX_STEPS) {
                        lastTouch = 3;
                        y += STEPS;
                        canvasView.moveTouch(x, y);
                        canvasView.invalidate();
                        drawing.addCoordinate(new Coordinate(x, y));
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            if(drawing.getCoordinates().size() > 0){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                View viewDlg = getLayoutInflater().inflate(R.layout.alert_drawing_save, null);
                final EditText edtName = viewDlg.findViewById(R.id.edtName);
                final Button btnSave = viewDlg.findViewById(R.id.btnSave);

                dlg.setView(viewDlg);
                dialog = dlg.create();
                dialog.show();

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawing.setName(edtName.getText().toString().trim());

                        if(drawing.getName().isEmpty()){
                            functions.alertDialog("Atenção", "Nome obrigatório.", R.mipmap.ic_warning, "OK");
                        }else{
                            db.addDrawing(drawing);
                            startCanvas();
                            dialog.dismiss();
                            snackbar("Desenho Salvo!", Color.GREEN);
                        }
                    }
                });
            } else{
                snackbar("Folha em branco!", Color.RED);
            }

            return true;
        }

        if (id == R.id.action_clear) {
            startCanvas();
            return true;
        }

        if (id == R.id.action_list_drawings) {
            Intent it = new Intent(getApplicationContext(), ListDrawings.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void snackbar(String mensagem, int color){
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), mensagem, Snackbar.LENGTH_SHORT);
        TextView tv = snackbar.getView().findViewById(R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}
