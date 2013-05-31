package de.app.hskafeteria;

import java.util.ArrayList;

import de.app.hskafeteria.httpclient.domain.Angebot;
import de.app.hskafeteria.httpclient.domain.Kategorie;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
public class AngeboteListAdapter extends BaseExpandableListAdapter {
 
 private Context context;
 private ArrayList<Kategorie> katList;
  
 public AngeboteListAdapter(Context context, ArrayList<Kategorie> katList) {
  this.context = context;
  this.katList = katList;
 }
  
 @Override
 public Object getChild(int groupPosition, int childPosition) {
  ArrayList<Angebot> angebotList = katList.get(groupPosition).getAngebote();
  return angebotList.get(childPosition);
 }
 
 @Override
 public long getChildId(int groupPosition, int childPosition) {
  return childPosition;
 }
 
 @Override
 public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
   View view, ViewGroup parent) {
   
  Angebot angebot = (Angebot) getChild(groupPosition, childPosition);
  if (view == null) {
   LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   view = infalInflater.inflate(R.layout.angebot_list_item_child, null);
  }
   
  TextView childItem = (TextView) view.findViewById(R.id.angebot_childItem);
  childItem.setText(angebot.getTitel().trim());
  
  TextView childItemPrice = (TextView) view.findViewById(R.id.angebot_childItem_price);
  Integer price = angebot.getPreis();
  childItemPrice.setText(price.toString() + "€");
  
   
  return view;
 }
 
 @Override
 public int getChildrenCount(int groupPosition) {
   
  ArrayList<Angebot> angebotList = katList.get(groupPosition).getAngebote();
  return angebotList.size();
  
 }
 
 @Override
 public Object getGroup(int groupPosition) {
  return katList.get(groupPosition);
 }
 
 @Override
 public int getGroupCount() {
  return katList.size();
 }
 
 @Override
 public long getGroupId(int groupPosition) {
  return groupPosition;
 }
 
 @Override
 public View getGroupView(int groupPosition, boolean isLastChild, View view,
   ViewGroup parent) {
   
  Kategorie kategorie = (Kategorie) getGroup(groupPosition);
  if (view == null) {
   LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   view = inf.inflate(R.layout.angebot_list_item_parent, null);
  }
   
  TextView heading = (TextView) view.findViewById(R.id.angebot_kategorie);
  heading.setText(kategorie.getTitel().trim());
   
  return view;
 }
 
 @Override
 public boolean hasStableIds() {
  return true;
 }
 
 @Override
 public boolean isChildSelectable(int groupPosition, int childPosition) {
  return true;
 }

}