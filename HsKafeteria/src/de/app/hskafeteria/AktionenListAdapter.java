package de.app.hskafeteria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import java.util.ArrayList;

import de.app.hskafeteria.httpclient.domain.Aktion;
 
public class AktionenListAdapter extends BaseExpandableListAdapter {
	 
	 
    private LayoutInflater inflater;
    private ArrayList<Aktion> mParent;
    private Context ctx;
 
    public AktionenListAdapter(Context context, ArrayList<Aktion> parent){
        mParent = parent;
        inflater = LayoutInflater.from(context);
        ctx = context;
    }
 
 
    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }
 
    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return 1;
    }
 
    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).getTitel();
    }
    
    //gets the title of each parent/group
    public Object getGroupDate(int i) {
        return mParent.get(i).getTag();
    }
 
    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).getInhalt().toString();
    }
 
    @Override
    public long getGroupId(int i) {
        return i;
    }
 
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }
 
    @Override
    public boolean hasStableIds() {
        return true;
    }
 
    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
 
        if (view == null) {
            view = inflater.inflate(R.layout.aktionen_list_item_parent, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.list_item_text_view_aktionen);
        TextView dayView = (TextView) view.findViewById(R.id.list_item_text_view_aktionen_date);
        //"i" is the position of the parent/group in the list
        textView.setText(getGroup(i).toString());
        
        dayView.setText(getGroupDate(i).toString());

        return view;
    }
 
    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int i, int i1, boolean b, View view, final ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.aktionen_list_item_child, viewGroup,false);
        }
 
        TextView textView = (TextView) view.findViewById(R.id.list_item_text_child_aktionen);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageViewAktionen);
        
        //"i" is the position of the parent/group in the list and
        //"i1" is the position of the child
        textView.setText(mParent.get(i).getInhalt());
        
        if (mParent.get(i).getTitel().startsWith("Burgertag")) {
            imageView.setImageResource(R.drawable.burger);
          } 
        
        else if (mParent.get(i).getTitel().startsWith("Pastatag")){
            imageView.setImageResource(R.drawable.pasta);
          }
        
        else if (mParent.get(i).getTitel().startsWith("Pizzatag")){
            imageView.setImageResource(R.drawable.pizza);
          }
        
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder imageDialog = new AlertDialog.Builder(ctx);
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.custom_fullimage_dialog, viewGroup, false);
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                image.setImageDrawable(imageView.getDrawable());
                imageDialog.setView(layout);
                imageDialog.setPositiveButton("Schlieﬂen", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

                imageDialog.create();
                imageDialog.show(); 
            }
        });
          
 
        //return the entire view
        return view;
    }
 
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
 
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        /* used to make the notifyDataSetChanged() method work */
        super.registerDataSetObserver(observer);
    }
}