package com.tangzy.tzymvp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.tangzy.tzymvp.util.Utils;

public class DataBean implements Parcelable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Utils.writeParcelableBean(this, dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel in) {
            DataBean bean = new DataBean();
            Utils.readParcelableBean(bean, in);
            return bean;
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };
}
