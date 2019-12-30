package com.star.e_learning.bean;

public class Model {

    public static final int COURSE_IMAGE_TYPE = 0;
    public static final int COURSE_TEXT_TYPE = 1;
    public static final int SUMMARY_TYPE = 2;
    public static final int ENROLLMENT_TYPE = 3;
    public static final int LECTURE_TYPE = 4;
    public static final int VIDEO_TYPE = 5;
    public static final int IMAGE_TYPE = 6;
    public static final int AUDIO_TYPE = 7;
    public static final int PDF_TYPE = 8;

    private int type;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Model(int type, Object data)
    {
        this.type=type;
        this.data=data;
    }

}
