package com.tangzy.pdfviewpage.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import es.voghdev.pdfviewpager.library.R;
import es.voghdev.pdfviewpager.library.adapter.BitmapContainer;
import es.voghdev.pdfviewpager.library.adapter.NullPdfErrorHandler;
import es.voghdev.pdfviewpager.library.adapter.PdfErrorHandler;
import es.voghdev.pdfviewpager.library.adapter.PdfRendererParams;
import es.voghdev.pdfviewpager.library.adapter.SimpleBitmapPool;
import es.voghdev.pdfviewpager.library.subscaleview.ImageSource;
import es.voghdev.pdfviewpager.library.subscaleview.SubsamplingScaleImageView;


/**
 * class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class PDFRecycleAdapter extends RecyclerView.Adapter<PDFRecycleAdapter.Holder> {

    protected static final int FIRST_PAGE = 0;
    protected static final float DEFAULT_QUALITY = 2.0f;
    protected static final int DEFAULT_OFFSCREENSIZE = 1;

    private Context mContext;
    private TextView tvNum;
    protected String path;


    /**
     * File descriptor of the PDF.
     */
    private ParcelFileDescriptor mFileDescriptor;

    /**
     * {@link android.graphics.pdf.PdfRenderer} to render the PDF.
     */
    private PdfRenderer mPdfRenderer;

    /**
     * Page that is currently shown on the screen.
     */
    private PdfRenderer.Page mCurrentPage;
    public PDFRecycleAdapter(Context context, String pdfPath, TextView tvNum) {
        this.path = pdfPath;
        this.mContext = context;
        this.tvNum = tvNum;
        init();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PDFRecycleAdapter.Holder holder = new PDFRecycleAdapter.Holder(LayoutInflater.from(mContext).inflate(R.layout.item_recycle_pdf, parent, false));
        return holder;
    }

    protected void init() {
        try {
            openRenderer(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
     */
    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.

        File file = new File(path);
//        File file = new File(context.getCacheDir(), FILENAME);
//        if (!file.exists()) {
//            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
//            // the cache directory.
//            InputStream asset = context.getAssets().open(FILENAME);
//            FileOutputStream output = new FileOutputStream(file);
//            final byte[] buffer = new byte[1024];
//            int size;
//            while ((size = asset.read(buffer)) != -1) {
//                output.write(buffer, 0, size);
//            }
//            asset.close();
//            output.close();
//        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }
    }




    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.d("tagnzy", "onBindViewHolder position = "+position);
        Bitmap bitmap = getBitmap(position);
        if (bitmap != null){
            holder.image.setImage(ImageSource.bitmap(bitmap));
        }
        tvNum.setText((position+1)+"/"+getItemCount());
    }


    private Bitmap getBitmap(int index) {

        if (mPdfRenderer.getPageCount() <= index) {
            return null;
        }
        // Make sure to close the current page before opening another one.
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        // Use `openPage` to open a specific page in PDF.
        mCurrentPage = mPdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap((int) (mCurrentPage.getWidth()*DEFAULT_QUALITY), (int)(mCurrentPage.getHeight()*DEFAULT_QUALITY),
                Bitmap.Config.ARGB_8888);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return mPdfRenderer != null ? mPdfRenderer.getPageCount() : 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        private SubsamplingScaleImageView image;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
