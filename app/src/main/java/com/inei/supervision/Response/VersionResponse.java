package com.inei.supervision.Response;


import com.inei.supervision.entity.VersionEntity;

import java.util.ArrayList;

public class VersionResponse {
    private ArrayList<VersionEntity> version;

    public VersionResponse() {
    }

    public VersionResponse(ArrayList<VersionEntity> version) {
        this.version = version;
    }

    public ArrayList<VersionEntity> getVersion() {
        return version;
    }

    public void setVersion(ArrayList<VersionEntity> version) {
        this.version = version;
    }
}
