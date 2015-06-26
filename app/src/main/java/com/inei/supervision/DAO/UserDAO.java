package com.inei.supervision.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.inei.supervision.entity.UserEntity;

public class UserDAO extends BaseDAO {
    private static final String TAG = UserDAO.class.getSimpleName();
    private Context context;

    public UserDAO(Context context) {
        this.context = context;
        initDBHelper(context);
    }
}
