package com.clixifi.wabell.ui.Adapters;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;

public interface onRemoveImage {
    void onRemoveImage(ArrayList<Bitmap> bitmaps, ArrayList<File> files , int position);
}
