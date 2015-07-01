package com.inei.supervision.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inei.supervision.Response.PadronResponse;
import com.inei.supervision.entity.PadronUbicacionEntity;

import java.util.ArrayList;

public class PadronUbicacionDAO extends BaseDAO{

    private static final String TAG = PadronUbicacionDAO.class.getSimpleName();
    private Context context;
    private PadronUbicacionEntity padronUbicacionEntity;
    private static PadronUbicacionDAO padronUbicacionDAO;

    public synchronized static PadronUbicacionDAO getInstance( Context paramContext ) {

        if ( padronUbicacionDAO == null ){
            padronUbicacionDAO = new PadronUbicacionDAO( paramContext );
        }
        return padronUbicacionDAO;
    }

    public PadronUbicacionDAO(Context context) {
        initDBHelper(context);
    }

    public void addPadron(ArrayList<PadronUbicacionEntity> response){
        try{
            openDBHelper();
            Log.v(TAG, "Start addPadron");
            Log.e(TAG, "Padron: " + String.valueOf(response == null));
            dbHelper.beginTransaction();
            for (int i = 0; response.size() > i; i++ ){
                contentValues = new ContentValues();
                contentValues.put("id_captura", Integer.valueOf(response.get(i).getId_captura()));
                contentValues.put("fecha_captura", response.get(i).getFecha_captura());
                dbHelper.getDatabase().insert("padron_ubicacion", null, contentValues);
            }
            dbHelper.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.v(TAG, "Error al escribir el padron");
        } finally {
            Log.v(TAG, "End addPadron");
            closeDBHelper();
        }
    }
}
