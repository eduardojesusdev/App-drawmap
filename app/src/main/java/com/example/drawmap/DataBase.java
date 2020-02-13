package com.example.drawmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.drawmap.Models.Coordinate;
import com.example.drawmap.Models.Drawing;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "DRAW_MAP";

    public DataBase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlDrawing = "CREATE TABLE DRAWING (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR(128) NOT NULL" +
                ")";

        String sqlCoordinate = "CREATE TABLE COORDINATE (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ID_DRAWING INTEGER NOT NULL," +
                "X REAL NOT NULL," +
                "Y REAL NOT NULL," +
                "CONSTRAINT FK_COORDINATE_DRAWING FOREIGN KEY (ID_DRAWING) REFERENCES DRAWING (ID) ON DELETE CASCADE" +
                ")";

        db.execSQL(sqlDrawing);
        db.execSQL(sqlCoordinate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addDrawing(Drawing drawing){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("NAME", drawing.getName());

        drawing.setId(db.insert("DRAWING", null, content));

        if(drawing.getId() > 0){
            for (Coordinate coordinate: drawing.getCoordinates()) {
                content.clear();
                content.put("ID_DRAWING", drawing.getId());
                content.put("X", coordinate.getX());
                content.put("Y", coordinate.getY());
                db.insert("COORDINATE", null, content);
            }
        }

        db.close();
    }

    public ArrayList<Drawing> getDrawing(){
        ArrayList<Drawing> drawings = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM DRAWING";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Drawing drawing = new Drawing();
                drawing.setId(cursor.getInt(0));
                drawing.setName(cursor.getString(1));
                drawings.add(drawing);

            }while(cursor.moveToNext());
        }

        db.close();
        return drawings;
    }

    public void getCoordinates(Drawing drawing){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM COORDINATE WHERE ID_DRAWING = "+drawing.getId();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Coordinate coordinate = new Coordinate();
                coordinate.setId(cursor.getLong(0));
                coordinate.setIdDrawing(cursor.getLong(1));
                coordinate.setX(cursor.getFloat(2));
                coordinate.setY(cursor.getFloat(3));
                drawing.addCoordinate(coordinate);

            }while(cursor.moveToNext());
        }

        db.close();
    }

    public void deleteDrawing(Drawing drawing){
        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM DRAWING WHERE id = " + drawing.getId();
        db.execSQL(query);
        db.close();
    }
}
