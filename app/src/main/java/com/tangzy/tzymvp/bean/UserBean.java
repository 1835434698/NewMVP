package com.tangzy.tzymvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.tangzy.tzymvp.bean.base.BaseRequest;
import com.tangzy.tzymvp.util.Utils;

import java.util.List;

public class UserBean extends BaseRequest implements Parcelable {

    private String name;
    private int age;
    private Integer age1;
    private boolean name1;
    private Boolean name2;
    private long age_1;
    private Long age_2;
    private double age_3;
    private Double age_4;
    private float age_5;
    private Float age_6;
    private short age_7;
    private Short age_8;
    private byte age_9;
    private Byte age_10;

    private List<DataBean> list;

    public long getAge_1() {
        return age_1;
    }

    public void setAge_1(long age_1) {
        this.age_1 = age_1;
    }

    public Long getAge_2() {
        return age_2;
    }

    public void setAge_2(Long age_2) {
        this.age_2 = age_2;
    }

    public double getAge_3() {
        return age_3;
    }

    public void setAge_3(double age_3) {
        this.age_3 = age_3;
    }

    public Double getAge_4() {
        return age_4;
    }

    public void setAge_4(Double age_4) {
        this.age_4 = age_4;
    }

    public float getAge_5() {
        return age_5;
    }

    public void setAge_5(float age_5) {
        this.age_5 = age_5;
    }

    public Float getAge_6() {
        return age_6;
    }

    public void setAge_6(Float age_6) {
        this.age_6 = age_6;
    }

    public short getAge_7() {
        return age_7;
    }

    public void setAge_7(short age_7) {
        this.age_7 = age_7;
    }

    public Short getAge_8() {
        return age_8;
    }

    public void setAge_8(Short age_8) {
        this.age_8 = age_8;
    }

    public byte getAge_9() {
        return age_9;
    }

    public void setAge_9(byte age_9) {
        this.age_9 = age_9;
    }

    public Byte getAge_10() {
        return age_10;
    }

    public void setAge_10(Byte age_10) {
        this.age_10 = age_10;
    }

    public List<DataBean> getList() {
        return list;
    }

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public Integer getAge1() {
        return age1;
    }

    public void setAge1(Integer age1) {
        this.age1 = age1;
    }

    public boolean isName1() {
        return name1;
    }

    public void setName1(boolean name1) {
        this.name1 = name1;
    }

    public Boolean getName2() {
        return name2;
    }

    public void setName2(Boolean name2) {
        this.name2 = name2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            UserBean bean = new UserBean();
            Utils.readParcelableBean(bean, in);
            return bean;
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Utils.writeParcelableBean(this, dest, flags);
    }
}
