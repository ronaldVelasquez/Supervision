package com.inei.supervision.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inei.supervision.entity.GeoreferenciaEntity;
import com.inei.supervision.request.GeorefenciaResquest;

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

    public GeoreferenciaDAO(Context context) { initDBHelper(context); }

    public boolean addGeoreferencia(double latitude, double longitude, String user, String date, int id){
        boolean flag = false;
        try{
            openDBHelper();
            Log.v(TAG, "Start addGeoreferencia");
            int idCaptura;
            SQL = "select * from padron_ubicacion where (fecha_captura > datetime('" + date + "', '-5 minutes') and fecha_captura < datetime('" + date + "', '+5 minutes')) and estado like 1";
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            if (cursor.moveToFirst()){
                Log.v(TAG, "Padron found");
                idCaptura = cursor.getInt(cursor.getColumnIndex("id_captura"));
                contentValues = new ContentValues();
                contentValues.put("id_captura", idCaptura);
                contentValues.put("id", id);
                contentValues.put("latitud", latitude);
                contentValues.put("longitud", longitude);
                contentValues.put("usuario", user);
                contentValues.put("fecha_registro", date);
                contentValues.put("estado", 1);
                dbHelper.getDatabase().insert("georeferencia", null, contentValues);
                String sql = "id_captura like " + idCaptura +" and estado like 1";
                contentValues = new ContentValues();
                contentValues.put("estado", 0);
                dbHelper.getDatabase().updateWithOnConflict("padron_ubicacion", contentValues, sql, null, SQLiteDatabase.CONFLICT_IGNORE);
                dbHelper.setTransactionSuccessful();
                flag = true;
                Log.v(TAG, "latitud: " + String.valueOf(latitude));
            } else {
                Log.v(TAG, "Padron found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v(TAG, "Error addGeoreferencia");
        }finally {
            Log.v(TAG, "End addGeoreferencia");
            cursor.close();
            closeDBHelper();
        }
        return flag;
    }

    public ArrayList<GeoreferenciaEntity> getData() {

        ArrayList <GeoreferenciaEntity> arrayGeoreferencia = new ArrayList<GeoreferenciaEntity>();
        try{
            openDBHelper();
            Log.v(TAG, "Start getData");
            SQL = "select * from georeferencia where estado like 1";
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    GeoreferenciaEntity georeferenciaEntity = new GeoreferenciaEntity();
                    georeferenciaEntity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    georeferenciaEntity.setId_captura(cursor.getInt(cursor.getColumnIndex("id_captura")));
                    georeferenciaEntity.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
                    georeferenciaEntity.setLatitud(cursor.getDouble(cursor.getColumnIndex("latitud")));
                    georeferenciaEntity.setLongitud(cursor.getDouble(cursor.getColumnIndex("longitud")));
                    georeferenciaEntity.setFecha_registro(cursor.getString(cursor.getColumnIndex("fecha_registro")));
                    georeferenciaEntity.setEstado(cursor.getInt(cursor.getColumnIndex("estado")));
                    cursor.moveToNext();
                    arrayGeoreferencia.add(georeferenciaEntity);
                }
            }else{
                arrayGeoreferencia = null;
            }
            return arrayGeoreferencia;
        } catch (Exception ex){
            ex.printStackTrace();
            arrayGeoreferencia = null;
            Log.v(TAG, "Error");

        }finally {
            Log.v(TAG, "End getData");
            cursor.close();
            closeDBHelper();
        }
        return arrayGeoreferencia;
    }

    public void updateGeoreferencia(ArrayList<GeoreferenciaEntity> data) {
        try{
            Log.v(TAG, "Start updateGeoreferencia");
            openDBHelper();
            for(GeoreferenciaEntity entity : data){
                contentValues = new ContentValues();
                contentValues.put("estado", entity.getEstado());
                SQL = "fecha_registro like '" + entity.getFecha_registro() + "'";
                dbHelper.getDatabase().updateWithOnConflict("georeferencia", contentValues, SQL, null,SQLiteDatabase.CONFLICT_IGNORE);
            }
            dbHelper.setTransactionSuccessful();


        }catch (Exception ex){
            ex.printStackTrace();
            Log.v(TAG, "Error");
        }finally {
            Log.v(TAG, "End updateGeoreferencia");
            closeDBHelper();
        }

    }
}
