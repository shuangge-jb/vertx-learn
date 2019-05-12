package com.guojb.error;

public class Error {
    public int code;
    public String msg;

    public Error() {
    }

    public Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
