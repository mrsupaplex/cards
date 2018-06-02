package com.apps.brz.cardstack;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.wenchao.cardstack.CardStack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements CardStack.CardEventListener {

    public enum SwipeDirection {
        KEEP, DELETE, STAR, SHARE;
    }

    private static final int NUM_OF_IMAGES = 20;
    private int imagesCounter = 0;
    private List<ImageCard> images;
    private CardStack card_stack;
    private CardAdapter card_adapter;
    private CardAdapterImage card_adapter_img;
    public static final int GALLERY_CODE = 322;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImages();

        card_stack = (CardStack) findViewById(R.id.card_stack);
        card_stack.setContentResource(R.layout.card_layout);
        card_stack.setStackMargin(20);
        card_stack.setAdapter(card_adapter_img);



        card_stack.setListener(this);

       // Thread t = new Thread(new Runnable() {
     //       @Override
     //       public void run() {
                getgallery();
   //         }
   //     });
   //     t.start();
    }

    private void getgallery() {

        ArrayList<String> photos = getImagesPath(this);
        images = new ArrayList<ImageCard>();
        int added = 0, start_ind = 0, photoNum = photos.size();
        //Toast.makeText(this, "" + photos.size(), Toast.LENGTH_SHORT).show();
        // TODO: make it configurate
        // TODO: dont pick approved photos
        if (photoNum > 200) {
            Random r = new Random();
            start_ind = r.nextInt(photoNum - 20);
        }
        for (int i = start_ind; i < photoNum; i++) {
            if (added >= NUM_OF_IMAGES) {
                return;
            }
            //Toast.makeText(this, photos.get(i), Toast.LENGTH_SHORT).show();
            String path = photos.get(i);
            File file = new File(path);
            if (file.exists()) {
                added++;
                putInAdepter(path);
                images.add(new ImageCard(path));
            }

        }


    }

    private Bitmap ThumbImage;

    private void putInAdepter(String path) {
        File imgFile = new  File(path);

        if(imgFile.exists()){

           // Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path), 500, 1000);

     //       try {
     //           Thread.sleep(500);
     //       } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        }

           // runOnUiThread(new Runnable() {
         //       @Override
      //          public void run() {
                    card_adapter_img.add(ThumbImage);
   //             }
  //          });

       }
    }

    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }



    private void initImages() {
        card_adapter = new CardAdapter(getApplicationContext(),0);
        card_adapter.add(R.drawable.fartapp3);
        for (int i = 0; i < 5; i++)
            card_adapter.add(R.drawable.screenshot1);

        card_adapter_img = new CardAdapterImage(getApplicationContext(),0);

    }

    @Override
    public boolean swipeEnd(int i, float v) {

        //the direction indicate swipe direction
        //there are four directions
        //  0  |  1
        // ----------
        //  2  |  3



        if (v > 300) {

            return true;
        }
        return false;
    }

    @Override
    public boolean swipeStart(int i, float v) {
        return false;
    }

    @Override
    public boolean swipeContinue(int i, float v, float v1) {
        return false;
    }

    @Override
    public void discarded(int i, int i1) {
        int imageIndex = i - 1; // WTF why it starts with one??
        SwipeDirection direction = SwipeDirection.values()[i1];
        switch(direction) {
            case KEEP:
                // TODO: keep the file path so it wont be used next time
                break;
            case DELETE:
                images.get(imageIndex).setState(ImageCard.ImageState.PENDING_DELETION);
                break;
            case STAR:
                starImage(images.get(imageIndex));
                // TODO: support star
                break;
            case SHARE:
                shareImage(imageIndex);
                // TODO: support share
                break;
            default:
                break;
        }

        if (imageIndex + 1 >= NUM_OF_IMAGES) {
            for (ImageCard image: images) {
                if (image.getState() == ImageCard.ImageState.PENDING_DELETION) {
                    deleteImages();
                    return;
                }
            }
            backToMenu();
        }
    }

    private void starImage(ImageCard imageCard) {
        String path = imageCard.getPath();
        String buff = FilesHandler.readFromFile(this, getString(R.string.star_file));

        if (buff != null && buff.length() > 1) {
            for (String s : buff.split("\n")) {
                if (s.equals(path)) {
                    return;
                }
            }
        }
        else {
            buff = "";
        }
        FilesHandler.writeToFile(this, getString(R.string.star_file), buff + FavoriteActivity.separator + imageCard.getPath());
    }

    private void shareImage(int imageIndex) {

        String path = images.get(imageIndex).getPath();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        //Uri screenshotUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + path);
        Uri screenshotUri = Uri.parse(path);

        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
    }

    private void deleteImages() {
        Intent intent = new Intent(this, DeleteImagesActivity.class);


        ArrayList<ImageCard> pendingDeletionList = new ArrayList<ImageCard>();

        for (ImageCard imageCard: images) {
            if (imageCard.getState() == ImageCard.ImageState.PENDING_DELETION) {
                pendingDeletionList.add(imageCard);
            }
        }

        String imageStr = ImageCard.toJSONList(pendingDeletionList);

        Intent i = new Intent(MainActivity.this, DeleteImagesActivity.class);
        i.putExtra("images", imageStr);
        startActivity(i);
    }

    private void toastit(String imageStr) {
        Toast.makeText(this, imageStr, Toast.LENGTH_SHORT).show();
    }

    private void backToMenu() {
        Intent i = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(i);
    }

    @Override
    public void topCardTapped() {

    }
}
