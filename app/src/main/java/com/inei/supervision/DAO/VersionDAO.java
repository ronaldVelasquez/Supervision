package com.inei.supervision.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.inei.supervision.entity.VersionEntity;

public class VersionDAO extends BaseDAO {
    private static final String TAG = VersionDAO.class.getSimpleName();
    private Context context;
    private VersionEntity versionEntity;
    private static VersionDAO versionDAO;

    public synchronized static VersionDAO getInstance( Context paramContext ) {

        if ( versionDAO == null ){
            versionDAO = new VersionDAO( paramContext );
        }
        return versionDAO;
    }

    public VersionDAO(Context context) {
        initDBHelper(context);
    }

    public VersionEntity getVersion(){
        try{
            openDBHelper();
            SQL = "select * from version";
            Log.v(TAG, "Start getVersion");
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            versionEntity = new VersionEntity();
            if(cursor.moveToFirst()){
                versionEntity.setNro_version(cursor.getString(cursor.getColumnIndex("nro_version")));
                versionEntity.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
                versionEntity.setFecha_Registro(cursor.getString(cursor.getColumnIndex("fecha_registro")));
            } else {
                versionEntity = null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Log.v(TAG, "Error al obtener la version");
            versionEntity = null;
        } finally {
            cursor.close();
            closeDBHelper();
        }
        Log.v(TAG, "End getVersion");
        return versionEntity;
    }

    public void addVersion(VersionEntity version) {
        try{
            openDBHelper();
            deleteDataVersion();
            Log.v(TAG, "Start addVersion");
            contentValues = new ContentValues();
            contentValues.put("nro_version", Integer.valueOf(version.getNro_version()));
            contentValues.put("usuario", version.getUsuario());
            contentValues.put("fecha_registro", version.getFecha_Registro());
            dbHelper.getDatabase().insertWithOnConflict("version", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            dbHelper.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.v(TAG, "Error al escribir la version");
            versionEntity = null;
        } finally {
            cursor.close();
            closeDBHelper();
        }
        Log.v(TAG, "End getVersion");
    }
    public void deleteDataVersion(){
        try{
            dbHelper.getDatabase().delete( "version", null, null );
        }catch (Exception e){
            e.printStackTrace();
            cursor.close();
            closeDBHelper();
        }
    }
}
