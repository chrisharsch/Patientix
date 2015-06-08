package de.teambluebaer.patientix.xmlParser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pen.Spen;
import com.samsung.android.sdk.pen.SpenSettingEraserInfo;
import com.samsung.android.sdk.pen.SpenSettingPenInfo;
import com.samsung.android.sdk.pen.document.SpenNoteDoc;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
import com.samsung.android.sdk.pen.settingui.SpenSettingEraserLayout;
import com.samsung.android.sdk.pen.settingui.SpenSettingPenLayout;

import java.io.IOException;
import java.io.OutputStream;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.activities.SignatureActivity;
import de.teambluebaer.patientix.helper.Constants;


/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Image that could insert into a <code>Row</code>
 * @see
 * @see Element
 */
public class Image implements Element {
    private String imageSource;

    private SpenNoteDoc mSpenNoteDoc;
    private SpenPageDoc mSpenPageDoc;
    private SpenSurfaceView mSpenSurfaceView;
    private SpenSettingPenLayout mPenSettingView;
    private SpenSettingEraserLayout mEraserSettingView;
    private ImageView mPenBtn;
    private ImageView mEraserBtn;
    private int mToolType = SpenSurfaceView.TOOL_SPEN;

    /**
     * Constructor
     *
     * @param src URL-String represents Location of the Image you want to add
     */
    public Image(String src) {

        if(src != null && !src.isEmpty()){
            imageSource = src;
        }else{
            imageSource = "";
        }
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {

        WebView image = new WebView(context);
        LinearLayout wrapper = new LinearLayout(context);
        RelativeLayout buttonsLayout = new RelativeLayout(context);

        if (!imageSource.isEmpty()) {
            image.setBackgroundColor(0);
            image.loadDataWithBaseURL("", "<img src='" + imageSource + "'/>", "text/html", "UTF-8", "");

            if(Constants.resign) {
                wrapper.addView(buttonsLayout);
                sPenGenerate(context, layout, buttonsLayout);
            }
            wrapper.addView(image);
            layout.addView(wrapper);

        } else {
            if(Constants.resign){
                wrapper.setBackgroundColor(0xFFD6E6F5);
                wrapper.addView(buttonsLayout);
                wrapper.addView(image);
                sPenGenerate(context, layout, buttonsLayout);
                layout.addView(wrapper);
            }

        }


    }

    public String toXMLString() {
        String xmlString = new String();
        xmlString = xmlString + "<picture ";
        xmlString = xmlString + "src=\"" + this.imageSource + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    public void sPenGenerate(final Context context, final LinearLayout layout, RelativeLayout buttonsLayout) {

        // Initialize Spen
        boolean isSpenFeatureEnabled = false;
        Spen spenPackage = new Spen();
        try {
            spenPackage.initialize(context);
            isSpenFeatureEnabled = spenPackage.isFeatureEnabled(Spen.DEVICE_PEN);
        } catch (SsdkUnsupportedException e) {
            if (processUnsupportedException(e, context) == true) {
                return;
            }
        } catch (Exception e1) {
            Toast.makeText(context, "Cannot initialize Spen.",
                    Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        }

        // Create Spen View
        mSpenSurfaceView = new SpenSurfaceView(context);
        if (mSpenSurfaceView == null) {
            Toast.makeText(context, "Cannot create new SpenView.",
                    Toast.LENGTH_SHORT).show();
        }
        mSpenSurfaceView.setZOrderOnTop(true);
        mSpenSurfaceView.setZoomable(false);

        // Create PenSettingView
        mPenSettingView = new SpenSettingPenLayout(context, new String(), buttonsLayout);

        if (mPenSettingView == null) {
            Toast.makeText(context, "Cannot create new PenSettingView.", Toast.LENGTH_SHORT).show();
        }
        // Create EraserSettingView
        mEraserSettingView = new SpenSettingEraserLayout(context, new String(), buttonsLayout);

        if (mEraserSettingView == null) {
            Toast.makeText(context, "Cannot create new EraserSettingView.", Toast.LENGTH_SHORT).show();
        }
        buttonsLayout.addView(mPenSettingView);
        buttonsLayout.addView(mEraserSettingView);

        layout.addView(mSpenSurfaceView);

        mPenSettingView.setCanvasView(mSpenSurfaceView);
        mEraserSettingView.setCanvasView(mSpenSurfaceView);

        // Create SpenNoteDoc
        final ViewTreeObserver observer = layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                createSpenNoteDoc(layout, context);
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     *  Erstellt das Layout
     * @param layout
     */
    private void createSpenNoteDoc(View layout, Context context) {
        try {
            mSpenNoteDoc =
                    new SpenNoteDoc(context, layout.getWidth(), layout.getHeight());
        } catch (IOException e) {
            Toast.makeText(context, "Cannot create new NoteDoc.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add a Page to NoteDoc, get an instance, and set it to the member variable.
        mSpenPageDoc = mSpenNoteDoc.appendPage();
        mSpenPageDoc.setBackgroundColor(0xFFD6E6F5);
        mSpenPageDoc.clearHistory();
        // Set PageDoc to View.
        mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);

        initSettingInfo();

        // Set a button
        mPenBtn = (ImageView) layout.findViewById(R.id.penBtn);
        mPenBtn.setOnClickListener(mPenBtnClickListener);

        mEraserBtn = (ImageView) layout.findViewById(R.id.eraserBtn);
        mEraserBtn.setOnClickListener(mEraserBtnClickListener);

        selectButton(mPenBtn);

        mSpenPageDoc.startRecord();
    }

    private void initSettingInfo() {
        // Initialize Pen settings
        SpenSettingPenInfo penInfo = new SpenSettingPenInfo();
        penInfo.color = Color.BLUE;
        penInfo.size = 10;

        // Initialize Eraser settings
        SpenSettingEraserInfo eraserInfo = new SpenSettingEraserInfo();
        eraserInfo.size = 100;
        mSpenSurfaceView.setEraserSettingInfo(eraserInfo);
        mEraserSettingView.setInfo(eraserInfo);
    }

    /**
     * Use the pen when click the symbol
     */
    private final View.OnClickListener mPenBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // When Spen is in stroke (pen) mode
            if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_STROKE) {
                // If PenSettingView is open, close it.
                if (mPenSettingView.isShown()) {
                    mPenSettingView.setVisibility(View.GONE);
                    // If PenSettingView is not open, open it.
                } else {
                    mPenSettingView.setViewMode(SpenSettingPenLayout.VIEW_MODE_EXTENSION);
                    mPenSettingView.setVisibility(View.VISIBLE);
                }
                // If Spen is not in stroke (pen) mode, change it to stroke mode.
            } else {
                selectButton(mPenBtn);
                mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_STROKE);
            }
        }
    };

    /**
     * Use the eraser when click the symbol
     */
    private final View.OnClickListener mEraserBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // When Spen is in eraser mode
            if (mSpenSurfaceView.getToolTypeAction(mToolType) == SpenSurfaceView.ACTION_ERASER) {
                // If EraserSettingView is open, close it.
                if (mEraserSettingView.isShown()) {
                    mEraserSettingView.setVisibility(View.GONE);
                    // If EraserSettingView is not open, open it.
                } else {
                    mEraserSettingView.setViewMode(SpenSettingEraserLayout.VIEW_MODE_NORMAL);
                    mEraserSettingView.setVisibility(View.VISIBLE);
                }
                // If Spen is not in eraser mode, change it to eraser mode.
            } else {
                selectButton(mEraserBtn);
                mSpenSurfaceView.setToolTypeAction(mToolType, SpenSurfaceView.ACTION_ERASER);
            }
        }
    };

    public void saveImage(Context context){


        Bitmap imgBitmap = mSpenSurfaceView.captureCurrentView(true);

        Toast.makeText(context, "Bild wurde gespeichert", Toast.LENGTH_SHORT).show();

        OutputStream out = null;
            try {
                // Save signature to a Base64 encode String
                Constants.globalMetaandForm.setSignature(SignatureActivity.encodeTobase64(imgBitmap));
            } catch (Exception e) {
                Toast.makeText(context, "Speicherung fehlgeschlagen", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        imgBitmap.recycle();
    }


    /**
     * select the pen or the eraser
     * @param v
     */
    private void selectButton(View v) {
        // Enable or disable the button according to the current mode.
        mPenBtn.setSelected(false);
        mEraserBtn.setSelected(false);

        v.setSelected(true);

        closeSettingView();
    }

    /**
     * close the view from the settings (pen or eraser)
     */
    private void closeSettingView() {
        // Close all the setting views.
        mEraserSettingView.setVisibility(SpenSurfaceView.GONE);
        mPenSettingView.setVisibility(SpenSurfaceView.GONE);
    }

    /**
     * Handle the Exceptions
     *
     * @param e
     * @return
     */
    private boolean processUnsupportedException(SsdkUnsupportedException e, Context context) {

        e.printStackTrace();
        int errType = e.getType();
        // If the device is not a Samsung device or if the device does not support Pen.
        if (errType == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED
                || errType == SsdkUnsupportedException.DEVICE_NOT_SUPPORTED) {
            Toast.makeText(context, "This device does not support Spen.",
                    Toast.LENGTH_SHORT).show();
        } else if (errType == SsdkUnsupportedException.LIBRARY_NOT_INSTALLED) {
            // If SpenSDK APK is not installed.
            showAlertDialog("You need to install additional Spen software"
                    + " to use this application."
                    + "You will be taken to the installation screen."
                    + "Restart this application after the software has been installed."
                    , true, context);
        } else if (errType
                == SsdkUnsupportedException.LIBRARY_UPDATE_IS_REQUIRED) {
            // SpenSDK APK must be updated.
            showAlertDialog("You need to update your Spen software "
                    + "to use this application."
                    + " You will be taken to the installation screen."
                    + " Restart this application after the software has been updated."
                    , true, context);
        } else if (errType
                == SsdkUnsupportedException.LIBRARY_UPDATE_IS_RECOMMENDED) {
            // Update of SpenSDK APK to an available new version is recommended.
            showAlertDialog("We recommend that you update your Spen software"
                    + " before using this application."
                    + " You will be taken to the installation screen."
                    + " Restart this application after the software has been updated."
                    , false, context);
            return false;
        }
        return true;
    }

    /**
     * Show alert for the exceptions
     *
     * @param msg
     * @param closeActivity
     */
    private void showAlertDialog(String msg, final boolean closeActivity, Context context) {

        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setTitle("Upgrade Notification")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog, int which) {
                                // Go to the market website and install/update APK.
                                Uri uri = Uri.parse("market://details?id="
                                        + Spen.SPEN_NATIVE_PACKAGE_NAME);

                                dialog.dismiss();
                            }
                        });
        dlg = null;
    }

    /**
     * Release the memory allocations
     */
    protected void onDestroy() {
        //mToast.cancel();
        if (mSpenNoteDoc != null && mSpenPageDoc.isRecording()) {
            mSpenPageDoc.stopRecord();
        }

        if (mPenSettingView != null) {
            mPenSettingView.close();
        }
        if (mEraserSettingView != null) {
            mEraserSettingView.close();
        }
        if (mSpenSurfaceView != null) {
            if (mSpenSurfaceView.getReplayState() == SpenSurfaceView.REPLAY_STATE_PLAYING) {
                mSpenSurfaceView.stopReplay();
            }
            mSpenSurfaceView.close();
            mSpenSurfaceView = null;
        }

        if (mSpenNoteDoc != null) {
            try {
                mSpenNoteDoc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSpenNoteDoc = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return !(imageSource != null ? !imageSource.equals(image.imageSource) : image.imageSource != null);

    }

    @Override
    public int hashCode() {
        return imageSource != null ? imageSource.hashCode() : 0;
    }
}

