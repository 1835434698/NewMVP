package com.tangzy.tzymvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.tangzy.tzymvp.bean.base.BaseRequest;
import com.tangzy.tzymvp.util.Utils;

public class UserBean extends BaseRequest implements Parcelable {

    public UserBean(){}

    private String name;
    private int age;

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

    protected UserBean(Parcel in) {
        Utils.readParcelableBean(this, in);
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
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
