package com.inei.supervision.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.inei.supervision.entity.UserEntity;

public class UserDAO extends BaseDAO {
    private static final String TAG = UserDAO.class.getSimpleName();
    private Context context;
    private UserEntity userEntity;
    private static UserDAO userDAO;

    public synchronized static UserDAO getInstance( Context paramContext ) {

        if ( userDAO == null ){
            userDAO = new UserDAO( paramContext );
        }
        return userDAO;
    }

    public UserDAO(Context context) {
        initDBHelper(context);
    }

    public UserEntity searchUserByDNI(String dni){
        try{
            openDBHelper();
            SQL = "select * from user where username like " + dni;
            Log.v(TAG, "Start searchUserByDNI");
            cursor = dbHelper.getDatabase().rawQuery(SQL, null);
            userEntity = new UserEntity();
            if(cursor.moveToFirst()){
                userEntity.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                userEntity.setUsername(cursor.getString(cursor.getColumnIndex("first_name")));
                userEntity.setUsername(cursor.getString(cursor.getColumnIndex("last_name")));
                userEntity.setUsername(cursor.getString(cursor.getColumnIndex("phone")));
            } else {
                userEntity = null;
            }

        }catch (Exception e){
            e.printStackTrace();
            userEntity = null;
        } finally {
            cursor.close();
            closeDBHelper();
        }
        Log.v(TAG, "End searchUserByDNI");
        return userEntity;
    }
}
