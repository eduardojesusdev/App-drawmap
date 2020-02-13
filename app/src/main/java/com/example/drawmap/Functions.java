package com.example.drawmap;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class Functions {

    private Context context;

    public Functions(Context context){
        this.context = context;
    }

    public void alertDialog(String title, String message, int icone, String txtButton){
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg
            .setTitle(title)
            .setIcon(icone)
            .setMessage(message)
            .setNeutralButton(txtButton, null)
            .show();
    }
}
