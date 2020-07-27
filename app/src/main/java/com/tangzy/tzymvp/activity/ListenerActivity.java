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

    private volatile ArrayList<OnOffsetChangedListener> listeners = new ArrayList<>();


    public void startRemove(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0; i<listeners.size(); i++){
                    Logger.d("tangzy", "i2 = "+i);
                    listeners.get(i).onOffsetChanged();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int length = listeners.size();
                for (int i =length-1; i>=0; i--){
                    Logger.d("tangzy", "i = "+i+", "+listeners.get(i).getClass().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        Logger.d("tangzy", "listeners = "+listeners.size());
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener listener) {
        listeners.add(listener);
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener listener) {
        if (this.listeners != null && listener != null) {
            Logger.d("tangzy", "removeOnOffsetChangedListener "+listener.getClass());
            this.listeners.remove(listener);
        }

    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged();
    }

}

