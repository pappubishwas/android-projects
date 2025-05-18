package com.example.flappygame;

import android.graphics.Canvas;

public class GameEngine {
    BackgroundImage backgroundImage;
    public GameEngine() {
        backgroundImage=new BackgroundImage();

    }

    public void updateAndDrawBackgroundImage(Canvas canvas){
        backgroundImage.setX(backgroundImage.getX()-backgroundImage.getVelocity());
        if(backgroundImage.getX()< -AppConstants.getBitmapBank().getBackgroundWidth()){
            backgroundImage.setX(0);
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(),backgroundImage.getX(),backgroundImage.getY(),null);
        if(backgroundImage.getX()<-(AppConstants.getBitmapBank().getBackgroundWidth()- AppConstants.SCREEN_WIDTH)){
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(),backgroundImage.getX()+AppConstants.getBitmapBank().getBackgroundWidth(),backgroundImage.getY(),null);
        }
    }
}
