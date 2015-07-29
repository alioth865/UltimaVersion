package com.example.alioth.ultimaversion.Todo.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Alioth on 24/06/2015.
 */
public class MySingletonClass {
    private static MySingletonClass singleton;
    private RequestQueue requestQueue;
    private static Context context;
    private ImageLoader imageLoader;

    private MySingletonClass(Context context, final String usuario, final String contraseña){
        MySingletonClass.context=context;
        requestQueue=getRequestQueue();
        imageLoader = new ImageLoader(requestQueue,
        new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }


        }){
            @Override
            protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, final String cacheKey) {
                return new ImageRequest(requestUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        onGetImageSuccess(cacheKey, response);
                    }
                }, maxWidth, maxHeight,
                        Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onGetImageError(cacheKey, error);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        String userpass= usuario + ":" + contraseña;
                        String basicAuth = "Basic " + new String(Base64.encodeBase64(userpass.getBytes()));
                        params.put("Authorization", basicAuth);
                        return params;
                    }
                };
            }
        };
    }

    public static synchronized MySingletonClass getInstance(Context context, String usuario, String contraseña){
        if (singleton == null){
            singleton=new MySingletonClass(context, usuario, contraseña);
        }
        return singleton;
    }


    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }


    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
