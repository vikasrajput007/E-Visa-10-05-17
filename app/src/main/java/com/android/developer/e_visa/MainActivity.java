package com.android.developer.e_visa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Target;

public class MainActivity extends AppCompatActivity {


    ImageView  england,russian,israel,german,spanies,french,arabic,protugues;
    Bitmap bitmap,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6,bitmap7,bitmap8;
    TextView select_language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        england = (ImageView)findViewById(R.id.english);
        russian = (ImageView)findViewById(R.id.russian);
        israel = (ImageView)findViewById(R.id.israel);
        german = (ImageView)findViewById(R.id.german);
        spanies = (ImageView)findViewById(R.id.spanies);
        french = (ImageView)findViewById(R.id.french);
        arabic = (ImageView)findViewById(R.id.arabic);
        protugues = (ImageView)findViewById(R.id.protugues);
        select_language = (TextView)findViewById(R.id.select_language);
        select_language.setSelected(true);


          bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.england);
          bitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.russian);
          bitmap3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.hebrow);
          bitmap4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.deutsche);
          bitmap5 = BitmapFactory.decodeResource(this.getResources(),R.drawable.spain);
          bitmap6 = BitmapFactory.decodeResource(this.getResources(),R.drawable.french);
          bitmap7 = BitmapFactory.decodeResource(this.getResources(),R.drawable.arbic);
          bitmap8 = BitmapFactory.decodeResource(this.getResources(),R.drawable.portugues);

        england.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap, 10));
        russian.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap2, 10));
        israel.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap3, 10));
        german.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap4, 10));
        spanies.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap5, 10));
        french.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap6, 10));
        arabic.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap7, 10));
        protugues.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bitmap8, 10));

//        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bitmap);
//        roundedBitmapDrawable.setCornerRadius(32);
//        roundedBitmapDrawable.setAntiAlias(true);
//        england.setImageDrawable(roundedBitmapDrawable);

        // some changes occurs so i want a commit

    }

    public static class ImageConverter {

        public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
