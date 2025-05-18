package com.example.flappygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapBank {
    Bitmap background;
    public BitmapBank(Resources res) {
        background= BitmapFactory.decodeResource(res,R.drawable.backgroundimage);
        background=scaleImage(background);
    }

    // return background bitmap
    public Bitmap getBackground() {
        return background;
    }

    // return background width
    public int getBackgroundWidth(){
        return background.getWidth();
    }

    // return background Height
    public int getBackgroundHeight(){
        return background.getHeight();
    }

    public Bitmap scaleImage(Bitmap bitmap){
        float widthHeightRatio=getBackgroundWidth()/getBackgroundHeight();
        int backgroundScaleWidth=(int) widthHeightRatio*AppConstants.SCREEN_HEIGHT;
        return Bitmap.createScaledBitmap(bitmap,backgroundScaleWidth,AppConstants.SCREEN_HEIGHT,false);
    }
}
