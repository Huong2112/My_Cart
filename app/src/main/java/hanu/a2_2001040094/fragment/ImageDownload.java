package hanu.a2_2001040094.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.core.os.HandlerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownload {

    public Bitmap downloadImage(String link) {
        try {
            URL url = new URL(link);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImagview(ImageView view, String link){
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

        Constants.executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(link);
                if (bitmap != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        });
    }
}
