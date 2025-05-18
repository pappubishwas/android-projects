package com.example.flappygame;

public class BackgroundImage {
    private int backGroundImageX,backGroundImageY,backGroundImageVelocity;
    public BackgroundImage() {
        backGroundImageX=0;
        backGroundImageY=0;
        backGroundImageVelocity=3;
    }
    public int getX(){
        return backGroundImageX;
    }
    public int getY(){
        return backGroundImageY;
    }
    public void setX(int backGroundImageX){
        this.backGroundImageX=backGroundImageX;
    }
    public void setY(int backGroundImageY){
        this.backGroundImageY=backGroundImageY;
    }
    public int getVelocity(){
        return backGroundImageVelocity;
    }
}
