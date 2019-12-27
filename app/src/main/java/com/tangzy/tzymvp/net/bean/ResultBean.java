package com.tangzy.tzymvp.net.bean;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/26
 */
public class ResultBean extends BaseBean{
    private String data;

    @Override
    public String toString() {
        return "ResultBean{" +
                "retCode=" + retCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}