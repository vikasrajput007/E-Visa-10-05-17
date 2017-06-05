package com.android.developer.e_visa.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.developer.e_visa.R;
import com.android.developer.e_visa.models.ListDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vikas on 5/29/2017.
 */

public class LanguageAdapter extends ArrayAdapter<ListDetail> {

    private final Context context;
    private ArrayList<ListDetail> listDetailArrayLsit;
    View view;

    public LanguageAdapter(Context context, ArrayList<ListDetail> objects) {
        super(context, 0, objects);


        this.context = context;
        this.listDetailArrayLsit = objects;

    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null)// if it's not recycled, initialize some attributes
             {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.flags_layout, parent, false);
        }
        else {
        view = convertView;
        }
        ImageView iv_placeholder = (ImageView) view.findViewById(R.id.iv_placeholder);
        TextView tv_language_name = (TextView) view.findViewById(R.id.tv_language_name);

        tv_language_name.setText(listDetailArrayLsit.get(position).getLanguage());
        Picasso.with(context).load(listDetailArrayLsit.get(position).getImage()).transform(new RoundedTransformation(5, 1)).into(iv_placeholder);
        return view;
    }


    public class RoundedTransformation implements com.squareup.picasso.Transformation {
        private final int radius;
        private final int margin;  // dp

        // radius is corner radii in dp
        // margin is the board in dp
        public RoundedTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }


        @Override
        public Bitmap transform(final Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }
}
