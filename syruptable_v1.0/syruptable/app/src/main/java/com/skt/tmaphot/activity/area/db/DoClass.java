package com.skt.tmaphot.activity.area.db;

public class DoClass {
    String do_code;
    String do_txt;

    public DoClass(String do_code, String do_txt) {
        this.do_code = do_code;
        this.do_txt = do_txt;
    }

    public String getDo_code() {
        return do_code;
    }

    public void setDo_code(String do_code) {
        this.do_code = do_code;
    }

    public String getDo_txt() {
        return do_txt;
    }

    public void setDo_txt(String do_txt) {
        this.do_txt = do_txt;
    }
}

