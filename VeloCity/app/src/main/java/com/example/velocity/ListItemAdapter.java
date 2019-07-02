package com.example.velocity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ListItemAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] id;
    private final Boolean[] available;
    private final String[] imgID;

    public ListItemAdapter(Activity context, String[] id, Boolean[] available, String[] imgID) {
        super(context, R.layout.list_item, id);

        this.context = context;
        this.id = id;
        this.available = available;
        this.imgID = imgID;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, null,true);

        TextView isAvailable = (TextView) rowView.findViewById(R.id.is_available);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.picture);

        if (!available[position]) {
            isAvailable.setText(context.getString(R.string.unavailable));
            isAvailable.setBackgroundColor(context.getResources().getColor(R.color.colorUnavailable));
        }

        new DownloadImageFromInternet(imageView).execute(imgID[position]);

        return rowView;
    };

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = "http://192.168.77.57:3000/images?img=" + urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


}
