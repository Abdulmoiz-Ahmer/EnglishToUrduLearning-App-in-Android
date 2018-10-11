package com.example.aceahmer.languagedictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<DataModelClass> arrayList;
    ImageView image_view;
    TextView englishVersion_txt, urduVersion_txt;
    Boolean visible;


    public CustomAdapter(Context context, ArrayList<DataModelClass> arrayList,boolean visible) {
        this.context = context;
        this.arrayList = arrayList;
        this.visible=visible;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.custom_listview,null,false);

        image_view = view.findViewById(R.id.image_view);
        englishVersion_txt = view.findViewById(R.id.englishVersion_txt);
        urduVersion_txt = view.findViewById(R.id.urduVersion_txt);

        DataModelClass dataModelClass=arrayList.get(i);

        if(visible){
            image_view.setImageResource(dataModelClass.getImageId());
        }
        else{
            image_view.setVisibility(View.GONE);
        }
        englishVersion_txt.setText(dataModelClass.getEnglishVersion());
        urduVersion_txt.setText(dataModelClass.getUrduVersion());
        return view;
    }
}
