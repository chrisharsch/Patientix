package de.teambluebaer.patientix.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by chris on 15.06.15.
 */
public class ImageManager {
    private HashMap<Integer, Bitmap> mBitmaps;
    private HashMap<Integer, Drawable> mDrawables;
    private Context mContext;

    private boolean mActive = true;

    public ImageManager(Context c) {
        mBitmaps = new HashMap<Integer, Bitmap>();
        mDrawables = new HashMap<Integer, Drawable>();
        mContext = c;
    }

    // We need to share and cache resources between objects to save on memory.
    public Bitmap getBitmap(int resource) {
        if (mActive) {
            if (!mBitmaps.containsKey(resource)) {
                mBitmaps.put(resource,
                        BitmapFactory.decodeResource(mContext.getResources(), resource));
            }
            return mBitmaps.get(resource);
        }
        return null;
    }

    public Drawable getDrawable(int resource) {
        if (mActive) {
            if (!mDrawables.containsKey(resource)) {
                mDrawables.put(resource, mContext.getResources().getDrawable(resource));
            }
            return mDrawables.get(resource);
        }
        return null;
    }

    public void recycleBitmaps() {
        Iterator itr = mBitmaps.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry e = (Map.Entry) itr.next();
            ((Bitmap) e.getValue()).recycle();
        }
        mBitmaps.clear();
    }

    public ImageManager setActive(boolean b) {
        mActive = b;
        return this;
    }

    public boolean isActive() {
        return mActive;
    }
}

