package de.teambluebaer.patientix.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pen.Spen;
import com.samsung.android.sdk.pen.document.SpenNoteDoc;
import com.samsung.android.sdk.pen.document.SpenPageDoc;
import com.samsung.android.sdk.pen.engine.SpenSurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.Flasher;

public class SignatureActivity extends Activity {

    private Context mContext;
    private SpenNoteDoc mSpenNoteDoc;
    private SpenPageDoc mSpenPageDoc;
    private SpenSurfaceView mSpenSurfaceView;
    private Button buttonAbsolutlyReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        mContext = this;
        buttonAbsolutlyReady = (Button) findViewById(R.id.buttonAbsolutlyReady);

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
            finish();
        }

        // Create Spen View
        RelativeLayout spenViewLayout =
                (RelativeLayout) findViewById(R.id.spenViewLayout);
        mSpenSurfaceView = new SpenSurfaceView(mContext);
        if (mSpenSurfaceView == null) {
            Toast.makeText(mContext, "Cannot create new SpenView.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        spenViewLayout.addView(mSpenSurfaceView);

        // Get the dimension of the device screen.
        Display display = getWindowManager().getDefaultDisplay();
        Rect rect = new Rect();
        display.getRectSize(rect);
        // Create SpenNoteDoc
        try {
            mSpenNoteDoc =
                    new SpenNoteDoc(mContext, rect.width(), rect.height());
        } catch (IOException e) {
            Toast.makeText(mContext, "Cannot create new NoteDoc.",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        // Add a Page to NoteDoc, get an instance, and set it to the member variable.
        mSpenPageDoc = mSpenNoteDoc.appendPage();
        mSpenPageDoc.setBackgroundColor(0xFFD6E6F5);
        mSpenPageDoc.clearHistory();
        // Set PageDoc to View.
        mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);

        if(isSpenFeatureEnabled == false) {
            mSpenSurfaceView.setToolTypeAction(SpenSurfaceView.TOOL_FINGER, SpenSurfaceView.ACTION_STROKE);
            Toast.makeText(mContext,
                    "Device does not support Spen. \n You can draw stroke by finger.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Save signature in a bitmap
    public void saveSig(View view) {
        try {
            GestureOverlayView gestureView = (GestureOverlayView) findViewById(R.id.signaturePad);
            gestureView.setDrawingCacheEnabled(true);
            Bitmap bm = Bitmap.createBitmap(gestureView.getDrawingCache());
            File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "signature.png");
            f.createNewFile();
            FileOutputStream os = new FileOutputStream(f);
            os = new FileOutputStream(f);
            //compress to specified format (PNG), quality - which is ignored for PNG, and out stream
            bm.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
        } catch (Exception e) {
            Log.v("Gestures", e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean processUnsupportedException(SsdkUnsupportedException e) {

        e.printStackTrace();
        int errType = e.getType();
        // If the device is not a Samsung device or if the device does not support Pen.
        if (errType == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED
                || errType == SsdkUnsupportedException.DEVICE_NOT_SUPPORTED) {
            Toast.makeText(mContext, "This device does not support Spen.",
                    Toast.LENGTH_SHORT).show();
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
                            finish();
                        }
                    }
                })
                .show();
        dlg = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSpenSurfaceView != null) {
            mSpenSurfaceView.close();
            mSpenSurfaceView = null;
        }

        if(mSpenNoteDoc != null) {
            try {
                mSpenNoteDoc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mSpenNoteDoc = null;
        }
    };
}
