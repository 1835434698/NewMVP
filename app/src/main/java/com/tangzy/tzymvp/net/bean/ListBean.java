package com.tangzy.tzymvp.net.bean;

import java.util.List;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/26
 */
public class ListBean extends BaseBean{
    public List<TestBean> data;

    @Override
    public String toString() {
        return "ResultBean{" +
//                "retCode=" + retCode +
//                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}