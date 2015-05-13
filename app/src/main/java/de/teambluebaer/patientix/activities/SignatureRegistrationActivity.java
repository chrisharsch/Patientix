package de.teambluebaer.patientix.activities;

import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Rect;
        import android.os.Bundle;
        import android.view.Display;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.BaseAdapter;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.samsung.android.sdk.SsdkUnsupportedException;
        import com.samsung.android.sdk.pen.Spen;
        import com.samsung.android.sdk.pen.document.SpenNoteDoc;
        import com.samsung.android.sdk.pen.document.SpenObjectBase;
        import com.samsung.android.sdk.pen.document.SpenObjectStroke;
        import com.samsung.android.sdk.pen.document.SpenPageDoc;
        import com.samsung.android.sdk.pen.engine.SpenSurfaceView;
        import com.samsung.android.sdk.pen.recognition.SpenCreationFailureException;
        import com.samsung.android.sdk.pen.recognition.SpenSignatureVerification;
        import com.samsung.android.sdk.pen.recognition.SpenSignatureVerificationInfo;
        import com.samsung.android.sdk.pen.recognition.SpenSignatureVerificationManager;

import de.teambluebaer.patientix.R;


public class SignatureRegistrationActivity extends Activity {

    private Context mContext = null;
    private SpenNoteDoc mSpenNoteDoc;
    private SpenPageDoc mSpenPageDoc;
    private SpenSurfaceView mSpenSurfaceView;

    public ListAdapter mSignatureAdapter;
    public ArrayList<ListItem> mSignatureListItem;

    public int mSignatureRegistrationNum = 0;
    public int mSignatureRegistrationNumMax;
    public ListView mSignatureList;

    //private int mResult = 0;

    private SpenSignatureVerificationManager mSpenSignatureVerificationManager;
    private SpenSignatureVerification mSpenSignatureVerification;

    private final int LIST_REGISRTATION = 0;
    private final int LIST_RETRY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_registration);
        mContext = this;

        // Initialize Spen
        boolean isSpenFeatureEnabled = false;
        Spen spenPackage = new Spen();
        try {
            spenPackage.initialize(this);
            isSpenFeatureEnabled = spenPackage.isFeatureEnabled(Spen.DEVICE_PEN);
        } catch (SsdkUnsupportedException e) {
            //if(processUnsupportedException(e)) {
              //  return;
            //}
        } catch (Exception e1) {
            Toast.makeText(mContext, "Cannot initialize Spen.",
                    Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
            finish();
        }

        // Create SpenView
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
        // Set PageDoc to View
        mSpenSurfaceView.setPageDoc(mSpenPageDoc, true);

        // Set Callback Listener(Interface)
        Toast.makeText(mContext, "Draw your signature to register.",
                Toast.LENGTH_SHORT).show();

        // Set the List
        mSignatureListItem = new ArrayList<ListItem>();
        mSignatureListItem.add(new ListItem("[Registration]",
                "Save your signature to register"));
        mSignatureListItem.add(new ListItem("[Retry]",
                "Clear the screen to redraw signature"));

        mSignatureAdapter = new ListAdapter(this);

        mSignatureList = (ListView) findViewById(R.id.signature_list);
        mSignatureList.setAdapter(mSignatureAdapter);

        if(isSpenFeatureEnabled == false) {
            mSpenSurfaceView.setToolTypeAction(SpenSurfaceView.TOOL_FINGER, SpenSurfaceView.ACTION_STROKE);
            Toast.makeText(mContext,
                    "Device does not support Spen. \n You can draw stroke by finger",
                    Toast.LENGTH_SHORT).show();
        }

        // Settings for Verification
        mSpenSignatureVerificationManager =
                new SpenSignatureVerificationManager(mContext);

        List<SpenSignatureVerificationInfo> signatureVerificationList =
                mSpenSignatureVerificationManager.getInfoList();
        try {
            if (signatureVerificationList.size() > 0) {
                for (SpenSignatureVerificationInfo info : signatureVerificationList) {
                    if (info.name.equalsIgnoreCase("SpenSignature")) {
                        mSpenSignatureVerification = mSpenSignatureVerificationManager
                                .createSignatureVerification(info);
                        break;
                    }
                }
            } else {
                finish();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "SpenSignatureVerificationManager class not found.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (InstantiationException e) {
            e.printStackTrace();
            Toast.makeText(mContext,
                    "Failed to access the SpenSignatureVerificationManager constructor.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Toast.makeText(mContext,
                    "Failed to access the SpenSignatureVerificationManager field or method.",
                    Toast.LENGTH_SHORT).show();
            return;
        } catch (SpenCreationFailureException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "This device does not support Recognition.",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "mSpenSignatureVerificationManager engine not loaded.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mSpenSignatureVerification.unregisterAll();
        mSignatureRegistrationNumMax =
                mSpenSignatureVerification.getMinimumRequiredCount();

        mSignatureList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == LIST_REGISRTATION) {
                    ArrayList<SpenObjectBase> strokeList =
                            mSpenPageDoc.getObjectList(SpenObjectBase.TYPE_STROKE);
                    if (strokeList.size() > 0) {
                        // List the objects on the viewport.
                        ArrayList<SpenObjectStroke> list =
                                new ArrayList<SpenObjectStroke>();
                        for (int i = 0; i < strokeList.size(); i++) {
                            list.add((SpenObjectStroke) strokeList.get(i));
                        }

                        // Register the object list as a signature.
                        try {
                            mSpenSignatureVerification.register(list);
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "SpenSignatureVerification is not loaded.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "SpenObjectStroke list is null.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext,
                                    "This signature is invalid for registration\n" +
                                            "Please try again!", Toast.LENGTH_SHORT).show();
                            return;
                        } finally {
                            mSpenPageDoc.removeAllObject();
                            mSpenSurfaceView.update();
                        }

                        int oldRegisteredSignaturesNum = mSignatureRegistrationNum;
                        mSignatureRegistrationNum = mSpenSignatureVerification.getRegisteredCount();

                        if (mSignatureRegistrationNum == oldRegisteredSignaturesNum) {
                            Toast.makeText(mContext,
                                    "This signature is invalid for registration\n" +
                                            "Please try again!", Toast.LENGTH_SHORT).show();
                        } else if (mSignatureRegistrationNum == mSignatureRegistrationNumMax) {
                            // If the number of registered signatures satisfies MinimunRequiredCount to the higher menu.
                            Toast.makeText(mContext,
                                    "Signature registration is completed, you can start verify your signature",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, "Signature has been stored.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    mSignatureAdapter.notifyDataSetChanged();
                } else if (position == LIST_RETRY) {
                    // Remove all the objects to start over.
                    mSpenPageDoc.removeAllObject();
                    mSpenSurfaceView.update();
                    Toast.makeText(mContext, "Draw your signature to register.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    static class ListItem {
        ListItem(String iTitle, String isubTitle) {
            Title = iTitle;
            subTitle = isubTitle;
        }

        String Title;
        String subTitle;
    }

    class ListAdapter extends BaseAdapter {
        LayoutInflater Inflater;

        public ListAdapter(Context context) {
            Inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mSignatureListItem.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = Inflater.inflate(
                        R.layout.signature_list_item, parent, false);
            }

            if (position == LIST_REGISRTATION) {
                TextView title = (TextView) convertView
                        .findViewById(R.id.signature_list_title);
                title.setText(mSignatureListItem.get(position).Title
                        + " - ( " + mSignatureRegistrationNum + " / "
                        + mSignatureRegistrationNumMax + " )");

                TextView subtitle = (TextView) convertView
                        .findViewById(R.id.signature_list_subtitle);
                subtitle.setText(mSignatureListItem.get(position).subTitle);
            } else {
                TextView title = (TextView) convertView
                        .findViewById(R.id.signature_list_title);
                title.setText(mSignatureListItem.get(position).Title);

                TextView subtitle = (TextView) convertView
                        .findViewById(R.id.signature_list_subtitle);
                subtitle.setText(mSignatureListItem.get(position).subTitle);
            }
            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpenSignatureVerification != null) {
            mSpenSignatureVerificationManager
                    .destroySignatureVerification(mSpenSignatureVerification);
            mSpenSignatureVerificationManager.close();
        }

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
    }
}