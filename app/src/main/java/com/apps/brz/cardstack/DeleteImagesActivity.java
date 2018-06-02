package com.apps.brz.cardstack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteImagesActivity extends AppCompatActivity {

    private Button deleteImageButton;
    private Button backToMenuButton;

    private ListView imagesListView;

    private displayImagesAdapter adapter;

    private List<ImageCard> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_images);


        Intent i = getIntent();

        String imagesListStr = i.getStringExtra("images");

        images = ImageCard.fromJSONList(imagesListStr);

        deleteImageButton = (Button) findViewById(R.id.delete_photos_btn);
        backToMenuButton = (Button) findViewById(R.id.back_to_menu_btn);
        imagesListView = (ListView) findViewById(R.id.images_list_view);


        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImages();
            }
        });

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenu();
            }
        });

        adapter = new displayImagesAdapter(this, images);

        imagesListView.setAdapter(adapter);



    }

    private void backToMenu() {
        Intent i = new Intent(DeleteImagesActivity.this, MenuActivity.class);
        startActivity(i);
    }

    private void deleteImages() {
        for (ImageCard imageCard: images) {
            String path = imageCard.getPath();

            File file = new File(imageCard.getPath());
            file.delete();
            if(file.exists()){
                try {
                    file.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(file.exists()){
                    boolean deleted = file.delete();
                    //.makeText(this, "image " + path + " state " + deleted, Toast.LENGTH_SHORT).show();
                }
            }
        }
        backToMenu();
    }
}
