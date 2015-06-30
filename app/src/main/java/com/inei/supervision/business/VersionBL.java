package com.inei.supervision.business;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.inei.supervision.DAO.VersionDAO;
import com.inei.supervision.Response.VersionResponse;
import com.inei.supervision.entity.VersionEntity;
import com.inei.supervision.request.VersionRequest;
import org.json.JSONObject;

public class VersionBL {

    private Context context;
    private VersionRequest versionRequest;
    private VersionEntity versionEntity, version;
    private VersionDAO versionDAO;

    public VersionBL(Context context) {
        this.context = context;
    }

    public boolean getVersion(){
        versionRequest = new VersionRequest(context);
        versionDAO = new VersionDAO(context);
        versionEntity = versionDAO.getVersion();
        versionRequest.getVersion();

        if( versionRequest.getJsonObject() != null){
            Log.e("Version", versionRequest.getJsonObject().toString());
            version = getVersion( versionRequest.getJsonObject().toString());
            Log.e("Version", versionEntity.getNro_version() + ", " + version.getNro_version());
            if(Integer.valueOf(versionEntity.getNro_version()) < Integer.valueOf(version.getNro_version())){
                versionDAO.addVersion(version);
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }

    }

    public VersionEntity getVersion(String response){
        Gson gson = new Gson();
        Log.v("Version", response);
        VersionResponse versionResponse = gson.fromJson(response, VersionResponse.class);
        VersionEntity version = versionResponse.getVersion().get(0);
        return version;
    }
}
