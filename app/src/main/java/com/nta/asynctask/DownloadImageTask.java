package com.nta.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class DownloadImageTask extends BaseTask {
    private String url;
    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar
    public DownloadImageTask(iOnDataFetched onDataFetchedListener, String imgUrl) {
        this.listener = onDataFetchedListener;
        this.url = imgUrl;
    }

    @Override
    public Object call() throws Exception {
        return getBitmapFromURL(url);
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(Object result) {
        listener.setDataInPageWithResult(result);
        listener.hideProgressBar();
    }
    public interface iOnDataFetched{
        void showProgressBar();
        void hideProgressBar();
        void setDataInPageWithResult(Object result);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    }
