/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tangzy.themvp.databind;

import android.os.Bundle;

import com.tangzy.themvp.model.IModel;
import com.tangzy.themvp.presenter.BaseActivityPresenter;
import com.tangzy.themvp.view.IDelegate;

/**
 * 集成数据-视图绑定的Activity基类(Presenter层)
 *
 * @param <T> View层代理类
 * @author tangzy (http://www.tangzy.com/) on 10/23/15.
 */
public abstract class BaseDataBindActivity<T extends IDelegate> extends
        BaseActivityPresenter<T> {
    protected BaseDataBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = getDataBinder();
    }

    public abstract BaseDataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (binder != null) {
            binder.viewBindModel(viewDelegate, data);
        }
    }
}