package com.apps.brz.cardstack;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by ophir on 4/18/2018.
 */

public class CardAdapterImage extends ArrayAdapter<Bitmap>{

    public CardAdapterImage(Context context, int resource, MainActivity activity) {
        super(context, resource);
        mainActivity = activity;
    }


    public class BitmapOnClickListener implements View.OnClickListener {

        private Bitmap bitmap;

        public BitmapOnClickListener(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void onClick(View view) {
            ImageFrament imageFrament = ImageFrament.newInstance(null, null, this.bitmap);
            FragmentManager manager = mainActivity.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.dd, imageFrament);
            transaction.commit();
        }
    }

    private MainActivity mainActivity;
    private Bitmap currentImage;

    @NonNull
    @Override
    public View getView(int position, View conertView, final ViewGroup parent) {
        ImageView imgView = (ImageView) conertView.findViewById(R.id.image_content);
        imgView.setImageBitmap(getItem(position));

        ImageView imgZoom = (ImageView) conertView.findViewById(R.id.zoom_image);

        currentImage = getItem(position);

        imgZoom.setOnClickListener(new BitmapOnClickListener(currentImage));
        /*\
        imgZoom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ImageFrament imageFrament = new ImageFrament();
                FragmentManager manager = mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.dd, imageFrament);
                transaction.commit();
                return false;
            }
        });
         */

        return conertView;
    }


}
