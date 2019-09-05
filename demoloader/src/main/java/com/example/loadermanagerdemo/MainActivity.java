package com.example.loadermanagerdemo;

//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.content.Loader;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;


@Route(path = "/demoLoader/mainActivity")
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;
    private Button addClick;

    private DataBaseAdapter dbAdapter;
    SimpleCursorAdapter adapter;

    private CursorLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo_loader);
        dbAdapter = new DataBaseAdapter(this);
        findViewById(R.id.addClick).setOnClickListener(new MyOnClick());
        listView = (ListView) findViewById(R.id.list_item);
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_item,
                dbAdapter.list(),
                new String[]{PersonMetaData.Person._ID, PersonMetaData.Person.NAME, PersonMetaData.Person.AGE},
                new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

        //初始化一个loader(第一个参数id，第二个是一个bundle对象，第三个是回调接口)
        getSupportLoaderManager().initLoader(0,null,this);
        Looper.prepare();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse("content://com.example.contentproviderdemo.personcontentprovider/person");
        //创建一个游标加载器  第一个参数是上下文  第二个是cp的uri  第二个是查询条件  第三个是查询条件的值  第四个是排序条件
        loader = new CursorLoader(this,uri,null,null,null,null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addClick) {
                addClick();
            }
        }
    }

    private void addClick() {
        dbAdapter.save(new Person("xiaobai",18));
      /*  //重启加载器
        getLoaderManager().restartLoader(0,null,this);*/
        //内容发生变化的时候，通知加载器
        loader.onContentChanged();
    }
}
