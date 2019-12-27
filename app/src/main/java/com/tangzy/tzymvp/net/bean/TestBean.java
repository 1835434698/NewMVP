package com.tangzy.tzymvp.net.bean;

/**
 * class
 *
 * @author Administrator
 * @date 2019/12/27
 */
public class TestBean {
    public String examid;
    public String score;
    @Override
    public String toString() {
        return "ResultBean{" +
                "examid=" + examid +
                ", score='" + score + '\'' +
                '}';
    }
}
