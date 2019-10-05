package com.yuniss.remotecarcontrol.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.widget.Toast;


public final class Methods {

    public static void openLink(String link, Context ctx){
        Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ctx.startActivity(intent);
    }


    public static int dipToPixels(int dp,Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.getResources().getDisplayMetrics());
    }

    public static void myToast(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
