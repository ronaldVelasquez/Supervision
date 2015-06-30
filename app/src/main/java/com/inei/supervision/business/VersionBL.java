package com.inei.supervision.business;

import android.content.Context;

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
        version = this.getVersion(versionRequest.getVersion());
        if(versionEntity.getNro_version() < version.getNro_version()){
            versionDAO.addVersion(version);
            return true;
        } else {
            return false;
        }
    }

    public VersionEntity getVersion(JSONObject response){
        Gson gson = new Gson();
        VersionResponse versionResponse = gson.fromJson(response.toString(), VersionResponse.class);
        VersionEntity version = versionResponse.getVersion().get(0);
        return version;
    }
}
