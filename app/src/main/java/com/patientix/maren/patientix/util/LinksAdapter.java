package com.patientix.maren.patientix.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.patientix.maren.patientix.R;

import java.util.ArrayList;

/**
 * Created by Chris on 04.05.2015.

public class LinksAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context adaptercontext;

    public LinksAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.adaptercontext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.lv_item_links, null);
        }

        String linkEintrag = items.get(position);

        if (linkEintrag != null) {

            String logo = linkEintrag.getLogo();

            TextView textview1 = (TextView) v.findViewById(R.id.title);
            TextView textview2 = (TextView) v.findViewById(R.id.url);
            TextView textview3 = (TextView) v.findViewById(R.id.description);

            textview1.setText( linkEintrag.getName());
            textview2.setText( linkEintrag.getUrl());
            textview3.setText( linkEintrag.getBeschreibung());
        }


        return v;
    }
}


} */