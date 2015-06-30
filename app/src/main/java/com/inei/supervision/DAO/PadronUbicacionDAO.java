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
            for (PadronUbicacionEntity padron : response){
                contentValues = new ContentValues();
                contentValues.put("id_captura", Integer.valueOf(padron.getId_captura()));
                contentValues.put("fecha_captura", padron.getFecha_captura());
                dbHelper.getDatabase().insertWithOnConflict("padron", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            }
            dbHelper.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.v(TAG, "Error al escribir el padron");
        } finally {
            Log.v(TAG, "End addPadron");
            cursor.close();
            closeDBHelper();
        }
    }
}
