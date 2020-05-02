package com.titvt.yinle.util.edilg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.titvt.yinle.util.httpss.Httpss;
import com.titvt.yinle.util.httpss.HttpssCallback;

public class Edilg {
    private Context context;
    private String url;
    private Bitmap bitmap, placeholder, error;
    private int bitmapId, placeholderId, errorId;
    private int cornerRadius;

    private Edilg(Context context) {
        this.context = context;
    }

    public static Edilg with(Context context) {
        return new Edilg(context);
    }

    public Edilg load(String url) {
        this.url = url;
        bitmap = null;
        bitmapId = 0;
        return this;
    }

    public Edilg load(Bitmap bitmap) {
        url = null;
        this.bitmap = bitmap;
        bitmapId = 0;
        return this;
    }

    public Edilg load(int resource) {
        url = null;
        bitmap = null;
        bitmapId = resource;
        return this;
    }

    public Edilg placeholder(Bitmap placeholder) {
        this.placeholder = placeholder;
        placeholderId = 0;
        return this;
    }

    public Edilg placeholder(int resource) {
        placeholder = null;
        placeholderId = resource;
        return this;
    }

    public Edilg error(Bitmap error) {
        this.error = error;
        errorId = 0;
        return this;
    }

    public Edilg error(int resource) {
        error = null;
        errorId = resource;
        return this;
    }

    public Edilg cornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public void into(ImageView imageView) {
        if (imageView != null) {
            int width = imageView.getWidth(), height = imageView.getHeight();
            if (url != null) {
                if (placeholderId != 0)
                    placeholder = BitmapFactory.decodeResource(context.getResources(), placeholderId);
                if (errorId != 0)
                    error = BitmapFactory.decodeResource(context.getResources(), errorId);
                if (placeholder != null)
                    new Handler(Looper.getMainLooper()).post(() -> imageView.setImageBitmap(getCornerRadiusBitmap(Bitmap.createScaledBitmap(placeholder, width == 0 ? placeholder.getWidth() : width, height == 0 ? placeholder.getHeight() : height, true))));
                Edilg self = this;
                new Httpss().setUrl(url).setCallback(new HttpssCallback() {
                    @Override
                    public void onHttpssOK(byte[] data) {
                        if (imageView.getTag() == null || imageView.getTag().equals(url)) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            imageView.setImageBitmap(getCornerRadiusBitmap(Bitmap.createScaledBitmap(bitmap, width == 0 ? bitmap.getWidth() : width, height == 0 ? bitmap.getHeight() : height, true)));
                        } else
                            self.load(imageView.getTag().toString()).into(imageView);
                    }

                    @Override
                    public void onHttpssFail(int responseCode) {
                        if (error != null)
                            imageView.setImageBitmap(getCornerRadiusBitmap(Bitmap.createScaledBitmap(error, width == 0 ? error.getWidth() : width, height == 0 ? error.getHeight() : height, true)));
                    }

                    @Override
                    public void onHttpssError() {
                        if (error != null)
                            imageView.setImageBitmap(getCornerRadiusBitmap(Bitmap.createScaledBitmap(error, width == 0 ? error.getWidth() : width, height == 0 ? error.getHeight() : height, true)));
                    }
                }).request();
            } else {
                if (bitmapId != 0)
                    bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
                if (bitmap != null)
                    new Handler(Looper.getMainLooper()).post(() -> imageView.setImageBitmap(getCornerRadiusBitmap(Bitmap.createScaledBitmap(bitmap, width == 0 ? bitmap.getWidth() : width, height == 0 ? bitmap.getHeight() : height, true))));
            }
        }
    }

    private Bitmap getCornerRadiusBitmap(Bitmap bitmap) {
        if (cornerRadius == 0)
            return bitmap;
        Bitmap thumb = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(thumb);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xFF000000);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(new RectF(rect), cornerRadius, cornerRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return thumb;
    }
}
