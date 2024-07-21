package com.example.cp;

public class Model {

    int img;
    String txt1;
    public Model(String txt1, int img)
    {
        this.txt1 = txt1;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }
}
