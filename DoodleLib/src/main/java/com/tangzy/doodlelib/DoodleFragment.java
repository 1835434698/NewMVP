package com.tangzy.doodlelib;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tangzy.doodlelib.view.DoodleView;
import com.tangzy.doodlelib.view.IMGColorGroup;

public class DoodleFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String PATH = "path";
    private static final String TAG = "DoodleFragment";
    private DoodleView doodleview;

    protected RadioGroup mModeGroup;
    protected RadioGroup mGraffitiGroup;
    protected RadioGroup mShapeGroup;
    protected IMGColorGroup mRgColorGroup;
    protected RadioGroup mSizeGroup,mosaicSizeGroup;

    protected ImageView rb_x_small, rb_small, rb_large, rb_x_large, rb_xx_large, selected;
    protected ImageView rb_x_small_m, rb_small_m, rb_large_m, rb_x_large_m, rb_xx_large_m, selected_m;

    private final int xSmall = 3;
    private final int small = 6;
    private final int large = 12;
    private final int xLarge = 25;
    private final int xxLarge = 50;



    /**
     * 创建新实例
     *
     * @param url
     * @return
     */
    public static DoodleFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(PATH, url);
        DoodleFragment fragment = new DoodleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflate = inflater.inflate(R.layout.fragment_doodle, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        doodleview = inflate.findViewById(R.id.doodleview);
        doodleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click");
            }
        });
        mGraffitiGroup = inflate.findViewById(R.id.rg_graffiti);
        mShapeGroup = inflate.findViewById(R.id.rg_shape);
        mRgColorGroup = inflate.findViewById(R.id.rg_colcr);
        mSizeGroup = inflate.findViewById(R.id.rg_size);
        rb_x_small = inflate.findViewById(R.id.rb_x_small);
        rb_x_small.setOnClickListener(this);
        rb_small = inflate.findViewById(R.id.rb_small);
        rb_small.setOnClickListener(this);
        selected = rb_large = inflate.findViewById(R.id.rb_large);
        selected.setImageResource(R.drawable.image_bg_blue);
        rb_large.setOnClickListener(this);
        rb_x_large = inflate.findViewById(R.id.rb_x_large);
        rb_x_large.setOnClickListener(this);
        rb_xx_large = inflate.findViewById(R.id.rb_xx_large);
        rb_xx_large.setOnClickListener(this);

        inflate.findViewById(R.id.rb_graffiti).setOnClickListener(this);
        inflate.findViewById(R.id.rb_color).setOnClickListener(this);
        inflate.findViewById(R.id.rb_size).setOnClickListener(this);

        inflate.findViewById(R.id.rb_rectangle).setOnClickListener(this);
        inflate.findViewById(R.id.rb_circular).setOnClickListener(this);
        inflate.findViewById(R.id.rb_arrow).setOnClickListener(this);
        inflate.findViewById(R.id.rb_curve).setOnClickListener(this);
        inflate.findViewById(R.id.rb_straight_line).setOnClickListener(this);

        mRgColorGroup.setOnCheckedChangeListener(this);
    }

    public void onClick(View v) {
        int vid = v.getId();
        if(vid == R.id.rb_graffiti) {
            showShape();
        } else if (vid == R.id.rb_color) {
            showColor();
        } else if (vid == R.id.rb_size) {
            showSize();
        } else if (vid == R.id.rb_rectangle) {
            doodleview.setShape(DoodleShape.HOLLOW_RECT);
        } else if (vid == R.id.rb_circular) {
            doodleview.setShape(DoodleShape.HOLLOW_CIRCLE);
        } else if (vid == R.id.rb_arrow) {
            doodleview.setShape(DoodleShape.ARROW);
        } else if (vid == R.id.rb_curve) {
            doodleview.setShape(DoodleShape.HAND_WRITE);
        } else if (vid == R.id.rb_straight_line) {
            doodleview.setShape(DoodleShape.LINE);
//        } else if (vid == R.id.im_close) {// TODO: 2020/8/25 关闭涂鸦
//            showDoodle(false);
//            resetDoodle();
        } else if (vid == R.id.rb_x_small) {
            setChecked(rb_x_small);
            setPenSize(xSmall);
        } else if (vid == R.id.rb_small) {
            setChecked(rb_small);
            setPenSize(small);
        } else if (vid == R.id.rb_large) {
            setChecked( rb_large);
            setPenSize(large);
        } else if (vid == R.id.rb_x_large) {
            setChecked( rb_x_large);
            setPenSize(xLarge);
        } else if (vid == R.id.rb_xx_large) {
            setChecked( rb_xx_large);
            setPenSize(xxLarge);
//        } else if (vid == R.id.imb_undo) {// TODO: 2020/8/25 撤销
//            doodleview.undo();
//        } else if (vid == R.id.imb_redo) {// TODO: 2020/8/25 重做
//            doodleview.redo(1);
//        } else if (vid == R.id.im_ok) {
//            doodleview.save();
//            showDoodle(false);
//            resetDoodle();
//        }else if (vid == R.id.tvCancelMosaic) {
//            showMosicLayout(false);
//            mImgView.setMode(IMGMode.NONE);
//            setOpDisplay(OP_NORMAL);
//        }else if (vid == R.id.tvDoneMosaic) {
//            showMosicLayout(false);
//            mImgView.setMode(IMGMode.NONE);
//            setOpDisplay(OP_NORMAL);
//        }else if (vid == R.id.rb_x_small_m) {
//            setCheckedMosaic(rb_x_small_m);
//            mImgView.setMosaicWith(1);
//        } else if (vid == R.id.rb_small_m) {
//            setCheckedMosaic(rb_small_m);
//            mImgView.setMosaicWith(2);
//        } else if (vid == R.id.rb_large_m) {
//            setCheckedMosaic( rb_large_m);
//            mImgView.setMosaicWith(3);
//        } else if (vid == R.id.rb_x_large_m) {
//            setCheckedMosaic( rb_x_large_m);
//            mImgView.setMosaicWith(4);
//        } else if (vid == R.id.rb_xx_large_m) {
//            setCheckedMosaic( rb_xx_large_m);
//            mImgView.setMosaicWith(5);
        }
    }

    private void showShape() {
        mGraffitiGroup.check(R.id.rb_graffiti);
        mShapeGroup.setVisibility(View.VISIBLE);
        mRgColorGroup.setVisibility(View.GONE);
        mSizeGroup.setVisibility(View.GONE);
    }

    private void showColor() {
        mGraffitiGroup.check(R.id.rb_color);
        mShapeGroup.setVisibility(View.GONE);
        mRgColorGroup.setVisibility(View.VISIBLE);
        mSizeGroup.setVisibility(View.GONE);
    }

    private void showSize() {
        mGraffitiGroup.check(R.id.rb_size);
        mShapeGroup.setVisibility(View.GONE);
        mRgColorGroup.setVisibility(View.GONE);
        mSizeGroup.setVisibility(View.VISIBLE);
    }



    protected void setChecked(ImageView rb_x_small) {
        selected.setImageResource(R.drawable.image_bg_whrit);
        rb_x_small.setImageResource(R.drawable.image_bg_blue);
        selected = rb_x_small;
    }

    private void setPenSize(int size) {
        doodleview.setPaintStrokeWidth(size);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.rg_colcr){
            Log.d(TAG, "getCheckColor = "+mRgColorGroup.getCheckColor());
            doodleview.setPaintColor(mRgColorGroup.getCheckColor());
        }

    }
}
