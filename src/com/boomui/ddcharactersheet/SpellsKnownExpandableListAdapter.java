package com.boomui.ddcharactersheet;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

class SpellsKnownExpandableListAdapter extends BaseExpandableListAdapter{
    private String[] groups = {"Level 0", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};
    private SpellsKnown children;
    Activity parent;
    final SpellInteractionListener listener;
    
    public SpellsKnownExpandableListAdapter(SpellsKnown children, Activity parent, SpellInteractionListener listener){
    	this.children = children;
    	this.parent = parent;
    	this.listener = listener;
    }
    
    public String getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition, childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return children.getNumSpells(groupPosition);
    }

    public TextView getGenericView() {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64);

        TextView textView = new TextView(parent);
        textView.setLayoutParams(lp);
        // Center the text vertically
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        textView.setPadding(36, 0, 0, 0);
        return textView;
    }
    
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        TextView textView = getGenericView();
        textView.setText(getChild(groupPosition, childPosition) );
        
        //textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        
        final int gPos = groupPosition;
        final int cPos = childPosition;
        textView.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.spellClicked(getChild(gPos, cPos), gPos);
			}
		});
        
        return textView;
    }

    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    public int getGroupCount() {
        return groups.length;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        TextView textView = getGenericView();
        textView.setText(getGroup(groupPosition).toString());
        
    	if(getChildrenCount(groupPosition) == 0){
    		textView.setTextColor(Constants.DISABLED);
    	}

        return textView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }
}
