package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.climesoftt.transportmanagement.R;
import com.squareup.picasso.Picasso;

/**
 * Created by AtoZ on 3/30/2018.
 */

public class WelcomeImage {
    public static void setWelcomeImage(Context context, String userImage, ImageView imageView)
    {
        if(userImage != null && !TextUtils.isEmpty(userImage))
        {
            Picasso.with(context)
                    .load(userImage)
                    .placeholder(R.drawable.users_admin)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }else
        {

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.profile_icon);
            Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

            //ImageView circularImageView = (ImageView)findViewById(R.id.imageView);
            //circularImageView.setImageBitmap(circularBitmap);
            imageView.setImageBitmap(circularBitmap);
            imageView.setBackgroundResource(R.drawable.user_profile_image_background);
        }
    }
}
