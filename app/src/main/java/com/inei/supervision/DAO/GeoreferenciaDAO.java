package com.inei.supervision.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.inei.supervision.entity.GeoreferenciaEntity;

import java.util.ArrayList;

public class GeoreferenciaDAO extends BaseDAO {

    private static final String TAG = GeoreferenciaDAO.class.getSimpleName();
    private static GeoreferenciaDAO georeferenciaDAO;

    public synchronized static GeoreferenciaDAO getInstance( Context paramContext ) {

        if ( georeferenciaDAO == null ){
            georeferenciaDAO = new GeoreferenciaDAO( paramContext );
        }
        return georeferenciaDAO;
    }

    public GeoreferenciaDAO(Context context) { initDBHelper(context);
    }

    public void addGeoreferencia(double latitude, double longitude, String user, String date){
        try{
            openDBHelper();
            Log.v(TAG, "Start addGeoreferencia");
            int idCaptura = 0;
            SQL = "select * from padron_ubicacion where fecha_captura > datetime('" + date + "', '-5 minutes') and fecha_captura < datetime('" + date + "', '+5 minutes')";
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            if (cursor.moveToFirst()){
                idCaptura = cursor.getInt(cursor.getColumnIndex("id_captura"));
                contentValues = new ContentValues();
                contentValues.put("id_captura", idCaptura);
                contentValues.put("latitud", latitude);
                contentValues.put("longitude", longitude);
                contentValues.put("usuario", user);
                contentValues.put("fecha_registro", date);
                contentValues.put("estado", 1);
                dbHelper.getDatabase().insert("georeferencia", null, contentValues);
                dbHelper.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "Error addGeoreferencia");
        }finally {
            Log.v(TAG, "End addGeoreferencia");
            cursor.close();
            dbHelper.close();
        }
    }

    public ArrayList<GeoreferenciaEntity> getData() {

        ArrayList <GeoreferenciaEntity> arrayGeoreferencia = null;
        try{
            openDBHelper();
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            Log.v(TAG, "Start getData");
            SQL = "select * from georeferencia where estado like 1";
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    GeoreferenciaEntity georeferenciaEntity = new GeoreferenciaEntity();
                    georeferenciaEntity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    georeferenciaEntity.setAltitud(cursor.getDouble(cursor.getColumnIndex("altitud")));
                    georeferenciaEntity.setLongitud(cursor.getDouble(cursor.getColumnIndex("longitud")));
                    georeferenciaEntity.setFecha_registro(cursor.getString(cursor.getColumnIndex("date")));
                    georeferenciaEntity.setEstado(cursor.getInt(cursor.getColumnIndex("estado")));
                    arrayGeoreferencia.add(georeferenciaEntity);
                }
                return arrayGeoreferencia;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            arrayGeoreferencia = null;
            Log.v(TAG, "Error");

        }finally {
            Log.v(TAG, "Start getData");
            cursor.close();
           closeDBHelper();
        }
        return arrayGeoreferencia;
    }
}
