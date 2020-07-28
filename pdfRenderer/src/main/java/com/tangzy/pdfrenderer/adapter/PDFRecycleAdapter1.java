package com.tangzy.pdfrenderer.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tangzy.pdfrenderer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


/**
 * class
 *
 * @author Administrator
 * @date 2019/12/23
 */
public class PDFRecycleAdapter1 extends RecyclerView.Adapter<PDFRecycleAdapter1.Holder> {

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


    public PDFRecycleAdapter1(Context context, String pdfPath, TextView tvNum) {
        this.path = pdfPath;
        this.mContext = context;
        this.tvNum = tvNum;
        init();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PDFRecycleAdapter1.Holder holder = new PDFRecycleAdapter1.Holder(LayoutInflater.from(mContext).inflate(R.layout.item_recycle_pdf1, parent, false));
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
            holder.image.setImageBitmap(bitmap);
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
        private ImageView image;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
