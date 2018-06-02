package com.apps.brz.cardstack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    public static final String separator = "@sep@";

    private List<ImageCard> images;
    private Button backToMenuButton;
    private ListView imagesListView;
    private displayImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        images = getImages();

        backToMenuButton = (Button) findViewById(R.id.back_to_menu_btn);
        imagesListView = (ListView) findViewById(R.id.images_list_view);

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenu();
            }
        });

        adapter = new displayImagesAdapter(this, images);

        imagesListView.setAdapter(adapter);
    }

    private List<ImageCard> getImages() {
        List<ImageCard> list = new ArrayList<ImageCard>();
        String buff = FilesHandler.readFromFile(this, getString(R.string.star_file));
        if (buff == null || buff.length() < 2) {
            return list;
        }
        for (String s: buff.split(separator)) {
            ImageCard imageCard = new ImageCard(s);
            list.add(imageCard);
        }
        return list;
    }

    private void backToMenu() {
        Intent i = new Intent(FavoriteActivity.this, MenuActivity.class);
        startActivity(i);
    }
}
