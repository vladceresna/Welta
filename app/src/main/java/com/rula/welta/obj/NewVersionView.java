package com.rula.welta.obj;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NewVersionView {
    public String version, url, whatsnew;
    public NewVersionView(){}
    public NewVersionView(String version, String url, String whatsnew) {
        this.version = version;
        this.url = url;
        this.whatsnew = whatsnew;
    }
    public String getVersion() {
        return version;
    }
    public String getUrl() {
        return url;
    }
    public String getWhatsnew() {
        return whatsnew;
    }
}
