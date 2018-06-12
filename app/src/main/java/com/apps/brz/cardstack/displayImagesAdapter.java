package com.apps.brz.cardstack;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ophir on 5/4/2018.
 */

class displayImagesAdapter extends ArrayAdapter<ImageCard> {

    private Context context;
    private List<ImageCard> images;
    private String file;


    public displayImagesAdapter(@NonNull Context context, List<ImageCard> images, String file) {
        super(context, 0, images);
        this.context = context;
        this.images = images;
        this.file = file;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_image, parent, false);
        }

        ImageCard image = getItem(position);

        ImageView singleImageView = (ImageView) convertView.findViewById(R.id.single_image_view);

        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(image.getPath()), 500, 1000);

        singleImageView.setImageBitmap(ThumbImage);

        Button XButton = (Button) convertView.findViewById(R.id.x_btn);

        XButton.setOnClickListener(new IntOnClickListener(this, image, file));

        return convertView;
    }
}
