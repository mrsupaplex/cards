package com.apps.brz.cardstack;

import android.view.View;

/**
 * Created by ophir on 6/4/2018.
 */

public class IntOnClickListener implements View.OnClickListener {

    private ImageCard imageCard;
    private displayImagesAdapter adapter;
    private String file;

    public IntOnClickListener(displayImagesAdapter adapter, ImageCard imageCard, String file) {
        this.adapter = adapter;
        this.imageCard = imageCard;
        this.file = file;
    }

    @Override
    public void onClick(View view) {
        adapter.remove(imageCard);
        adapter.notifyDataSetChanged();
        FilesHandler.removeFromFile(view.getContext(), file, FavoriteActivity.separator + imageCard.getPath());
    }
}