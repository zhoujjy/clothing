package com.zj.common;

import lombok.Data;

/**
 * 统一返回格式
 */
@Data
public class R {
    private Integer code;
    private String msg;
    private Object data;
    public R() {
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code,Object data){
        this.data = data;
        this.code = code;
    }

    public R(Integer code,Object data, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }


}
