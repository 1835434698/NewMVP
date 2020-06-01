package com.tangzy.tzymvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tangzy.tzymvp.R;
import com.tangzy.tzymvp.util.Logger;

import java.util.ArrayList;

public class ListenerActivity extends AppCompatActivity{

    private Button btn;

    private void removeMeath(OnOffsetChangedListener listener) {
//        btn.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                removeOnOffsetChangedListener(listener);
//            }
//        }, 50);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);
        btn = findViewById(R.id.btn);
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged() {
                removeMeath(this);
            }
        });

    }

    private ArrayList<OnOffsetChangedListener> listeners = new ArrayList<>();


    public void startRemove(View view) {
        ArrayList<OnOffsetChangedListener> lists = (ArrayList<OnOffsetChangedListener>) listeners.clone();
        int length = lists.size();
        for (int i =0; i<length; i++){
            lists.get(i).onOffsetChanged();
        }
        Logger.d("tangzy", "listeners = "+listeners.size());

    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
        listeners.add(listener);
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
        if (this.listeners != null && listener != null) {
            this.listeners.remove(listener);
        }

    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged();
    }

}

