package com.climesoftt.transportmanagement.utils;

import android.content.Context;
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
            imageView.setImageResource(R.drawable.profile_icon);
        }
    }
}
