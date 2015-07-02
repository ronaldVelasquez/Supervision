package com.inei.supervision.business;


import android.content.Context;

import com.inei.supervision.DAO.UserDAO;
import com.inei.supervision.entity.UserEntity;

public class UserBL {
    private UserDAO userDAO;

    public UserBL(Context context) {
        userDAO = UserDAO.getInstance(context);
    }

    public UserEntity getUser(String dni){
        return userDAO.searchUserByDNI(dni);
    }
}
