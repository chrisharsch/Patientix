package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.kioskMode.PrefUtils;

/**
 * This class is for saving the signature from patient.
 */
public class SignatureActivity extends Activity {

    private Context mContext;
    private SpenNoteDoc mSpenNoteDoc;
    private SpenPageDoc mSpenPageDoc;
    private SpenSurfaceView mSpenSurfaceView;
    private SpenSettingPenLayout mPenSettingView;
    private SpenSettingEraserLayout mEraserSettingView;
    private ImageView mPenBtn;
    private ImageView mEraserBtn;
    private Button buttonDone;
    private int mToolType = SpenSurfaceView.TOOL_SPEN;

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));


    /**
     * In this method is defined what happens on create of the Activity:
     * remove titlebar, set the view, initialize spen and function
     *
     * @param savedInstanceState default parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Titlebar removed
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set View
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_signature);
        Constants.LISTOFACTIVITIES.add(this);
        Constants.TORESTART = true;
        if (!isFormula()) {
            Intent intent = new Intent(SignatureActivity.this, StartActivity.class);
            startActivity(intent);
            PrefUtils.setKioskModeActive(false,this);
            finish();
        }

        Constants.CURRENTACTIVITY = this;
        PrefUtils.setKioskModeActive(true, this);
        mContext = this;


        // Initialize Spen
        boolean isSpenFeatureEnabled = false;
        Spen spenPackage = new Spen();
        try {
            spenPackage.initialize(this);
            isSpenFeatureEnabled = spenPackage.isFeatureEnabled(Spen.DEVICE_PEN);
        } catch (SsdkUnsupportedException e) {
            if( processUnsupportedException(e) == true) {
                return;
            }
        } catch (Exception e1) {
            Toast.makeText(mContext, "Cannot initialize Spen.",
                    Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }

        // Create Spen View
        mSpenSurfaceView = new SpenSurfaceView(mContext);
        if (mSpenSurfaceView == null) {
            Toast.makeText(mContext, "Cannot create new SpenView.",
                    Toast.LENGTH_SHORT).show();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }
        mSpenSurfaceView.setZOrderOnTop(true);
        mSpenSurfaceView.setZoomable(false);

        final RelativeLayout spenViewLayout = (RelativeLayout) findViewById(R.id.spenViewLayout);

        // Create PenSettingView
        if (android.os.Build.VERSION.SDK_INT > 19) {
            mPenSettingView = new SpenSettingPenLayout(mContext, new String(), spenViewLayout);
        } else {
            mPenSettingView = new SpenSettingPenLayout(getApplicationContext(), new String(), spenViewLayout);
        }
        if (mPenSettingView == null) {
            Toast.makeText(mContext, "Cannot create new PenSettingView.", Toast.LENGTH_SHORT).show();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }
        // Create EraserSettingView
        if (android.os.Build.VERSION.SDK_INT > 19) {
            mEraserSettingView = new SpenSettingEraserLayout(mContext, new String(), spenViewLayout);
        } else {
            mEraserSettingView = new SpenSettingEraserLayout(getApplicationContext(), new String(), spenViewLayout);
        }
        if (mEraserSettingView == null) {
            Toast.makeText(mContext, "Cannot create new EraserSettingView.", Toast.LENGTH_SHORT).show();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }
        spenViewLayout.addView(mPenSettingView);
        spenViewLayout.addView(mEraserSettingView);

        spenViewLayout.addView(mSpenSurfaceView);

        mPenSettingView.setCanvasView(mSpenSurfaceView);
        mEraserSettingView.setCanvasView(mSpenSurfaceView);

        // Create SpenNoteDoc
        final ViewTreeObserver observer = spenViewLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                createSpenNoteDoc(spenViewLayout);
                spenViewLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     *  Erstellt das Layout der Unterschrift
     * @param spenViewLayout
     */
    private void createSpenNoteDoc(View spenViewLayout) {
        try {
            mSpenNoteDoc =
                    new SpenNoteDoc(mContext, spenViewLayout.getWidth(), spenViewLayout.getHeight());
        } catch (IOException e) {
            Toast.makeText(mContext, "Cannot create new NoteDoc.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }

        // Add a Page to NoteDoc, get an instance, and set it to the member variable.
        mSpenPageDoc = mSpenNoteDoc.appendPage();
        mSpenPageDoc.setBackgroundColor(0xFFD6E6F5);
        mSpenPageDoc.clearHistory();
        // Set PageDoc to View.
        mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);

        Bitmap imgBitmap = mSpenSurfaceView.captureCurrentView(true);
        Constants.EMPTYSIGNATURE = (encodeTobase64(imgBitmap));

        initSettingInfo();

        // Set a button
        mPenBtn = (ImageView) findViewById(R.id.penBtn);
        mPenBtn.setOnClickListener(mPenBtnClickListener);

        mEraserBtn = (ImageView) findViewById(R.id.eraserBtn);
        mEraserBtn.setOnClickListener(mEraserBtnClickListener);

        buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(mCaptureBtnClickListener);

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

    /**
     * calls the saving methode captureSpenSurfaceView
     */
    public final View.OnClickListener mCaptureBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            closeSettingView();
            buttonDone.setEnabled(false);
            Bitmap imgBitmap = mSpenSurfaceView.captureCurrentView(true);


            if(Constants.EMPTYSIGNATURE.equals(encodeTobase64(imgBitmap))) {
                Toast.makeText(mContext, "Bitte unterschreiben Sie", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(mContext, "Unterschrift wurde gespeichert", Toast.LENGTH_SHORT).show();

                OutputStream out = null;
                try {
                    // Save signature to a Base64 encode String
                    Constants.GLOBALMETAANDFORM.setSignature(encodeTobase64(imgBitmap));
                } catch (Exception e) {
                    Toast.makeText(mContext, "Speicherung fehlgeschlagen", Toast.LENGTH_LONG).show();
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

                Intent intent = new Intent(SignatureActivity.this, EndActivity.class);
                startActivity(intent);

                PrefUtils.setKioskModeActive(false, SignatureActivity.this);
                finish();
            }

            buttonDone.setEnabled(true);
        }
    };



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
     * Gibt einen String von der Unterschrift für das XML-Formular zurück
     * @param image
     * @return String
     */
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("SignaturString", imageEncoded);
        return imageEncoded;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *  Handle the Exceptions
     * @param e
     * @return
     */
    private boolean processUnsupportedException(SsdkUnsupportedException e) {

        e.printStackTrace();
        int errType = e.getType();
        // If the device is not a Samsung device or if the device does not support Pen.
        if (errType == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED
                || errType == SsdkUnsupportedException.DEVICE_NOT_SUPPORTED) {
            Toast.makeText(mContext, "This device does not support Spen.",
                    Toast.LENGTH_SHORT).show();
            PrefUtils.setKioskModeActive(false, this);
            finish();
        }
        else if (errType == SsdkUnsupportedException.LIBRARY_NOT_INSTALLED) {
            // If SpenSDK APK is not installed.
            showAlertDialog( "You need to install additional Spen software"
                    +" to use this application."
                    + "You will be taken to the installation screen."
                    + "Restart this application after the software has been installed."
                    , true);
        } else if (errType
                == SsdkUnsupportedException.LIBRARY_UPDATE_IS_REQUIRED) {
            // SpenSDK APK must be updated.
            showAlertDialog( "You need to update your Spen software "
                    + "to use this application."
                    + " You will be taken to the installation screen."
                    + " Restart this application after the software has been updated."
                    , true);
        } else if (errType
                == SsdkUnsupportedException.LIBRARY_UPDATE_IS_RECOMMENDED) {
            // Update of SpenSDK APK to an available new version is recommended.
            showAlertDialog( "We recommend that you update your Spen software"
                    +" before using this application."
                    + " You will be taken to the installation screen."
                    + " Restart this application after the software has been updated."
                    , false);
            return false;
        }
        return true;
    }

    /**
     * Show alert for the exceptions
     * @param msg
     * @param closeActivity
     */
    private void showAlertDialog(String msg, final boolean closeActivity) {

        AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
        dlg.setIcon(getResources().getDrawable(
                android.R.drawable.ic_dialog_alert));
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
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                dialog.dismiss();
                                PrefUtils.setKioskModeActive(false, SignatureActivity.this);
                                finish();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog, int which) {
                                if(closeActivity == true) {
                                    // Terminate the activity if APK is not installed.
                                    PrefUtils.setKioskModeActive(false, SignatureActivity.this);
                                    finish();
                                }
                                dialog.dismiss();
                            }
                        })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(closeActivity == true) {
                            // Terminate the activity if APK is not installed.
                            PrefUtils.setKioskModeActive(false, SignatureActivity.this);
                            finish();
                        }
                    }
                })
                .show();
        dlg = null;
    }

    /**
     * Release the memory allocations
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    /**
     * This method checks if there is a filled formula and
     * return true if there is one else it return false
     *
     * @return Boolean true or false
     */
    private boolean isFormula() {
        try {
            if (!Constants.GLOBALMETAANDFORM.toXMLString().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
    /**
     * This method defines what happens when you press on the hardkey back on the Tablet.
     * In this case the functionality of the button is disabled.
     */
    @Override
    public void onBackPressed() {

    }


    /**
     * This method kills all system dialogs if they are shown
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    /**
     * This method disables the volumes keys
     *
     * @param event Listens on Keyinput event
     * @return Calls super class if key is allowed
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}