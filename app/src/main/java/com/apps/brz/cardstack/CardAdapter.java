package com.apps.brz.cardstack;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.apps.brz.cardstack.R;

/**
 * Created by ophir on 4/18/2018.
 */

public class CardAdapter extends ArrayAdapter<Integer>{

    public CardAdapter(Context context, int resource) {
        super(context, resource);
    }


    @NonNull
    @Override
    public View getView(int position, View conertView, ViewGroup parent) {
        ImageView imgView = (ImageView) conertView.findViewById(R.id.image_content);
        imgView.setImageResource(getItem(position));
        return conertView;
    }


}
