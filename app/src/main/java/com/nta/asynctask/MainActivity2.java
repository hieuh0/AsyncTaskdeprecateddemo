package com.nta.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements DownloadImageTask.iOnDataFetched {
    private ImageView imageView;
    private int i = 0;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        TextView textView = findViewById(R.id.textView);
        TaskRunner runner = new TaskRunner();
        runner.executeAsync(new DownloadImageTask(this,"https://7132.com/en/ajax/downloadmedia?media=3767"));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(String.valueOf(i));
                i++;
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDataInPageWithResult(Object result) {
        Bitmap bmp = (Bitmap) result;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        imageView.setImageBitmap(getResizedBitmap(bmp,dm.widthPixels,dm.heightPixels));
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }
}