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

    public static final String separator = "@sep@";

    private List<ImageCard> images;
    private Button backToMenuButton;
    private Button emptyRecycleBinButton;
    private ListView imagesListView;
    private displayImagesAdapter adapter;
    private String file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_images);

        file = getString(R.string.delete_file);

        images = getImages();

        backToMenuButton = (Button) findViewById(R.id.back_to_menu_btn);
        imagesListView = (ListView) findViewById(R.id.images_list_view);

        emptyRecycleBinButton = (Button) findViewById(R.id.empty_recycle_btn);

        emptyRecycleBinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyRecycleBin();
            }
        });

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMenu();
            }
        });

        adapter = new displayImagesAdapter(this, images, file);

        imagesListView.setAdapter(adapter);
    }

    private void emptyRecycleBin() {
        deleteImages();
        String buff = "";
        FilesHandler.writeToFile(this, file, buff);
        images = new ArrayList<>();
        adapter = new displayImagesAdapter(this, images, file);
        imagesListView.setAdapter(adapter);
    }

    private List<ImageCard> getImages() {
        List<ImageCard> list = new ArrayList<ImageCard>();
        String buff = FilesHandler.readFromFile(this, getString(R.string.delete_file));
        if (buff == null || buff.length() < 2) {
            return list;
        }
        for (String s: buff.split(separator)) {
            if (s != null && s.length() > 1) {
                ImageCard imageCard = new ImageCard(s);
                list.add(imageCard);
            }
        }
        return list;
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
    }
}
