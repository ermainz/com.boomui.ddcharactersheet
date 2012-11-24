package com.boomui.ddcharactersheet;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.widget.*;

public class MagicTabFragment extends Fragment{
	FragmentCommunicator com;
	Activity parent;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		parent = activity;
		com = (FragmentCommunicator)activity;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		//return inflater.inflate(R.layout.magic_tab_fragment_layout, container, false);
		return createSpellsKnown("Sorcerer");
	}
	
	public View createSpellsKnown(String characterClass){
		TextView header = getGenericView();
		header.setText("Spells Known");
		
		ExpandableListView spellsKnownView = new ExpandableListView(parent);
		SpellsKnown sk = new SpellsKnown(com, characterClass);
		SpellsKnownExpandableListAdapter adapter = new SpellsKnownExpandableListAdapter(sk, parent);
		spellsKnownView.setAdapter(adapter);
		
		LinearLayout column = new LinearLayout(parent);
		
		column.setOrientation(LinearLayout.VERTICAL);
		column.addView(header);
		column.addView(spellsKnownView);
		
		return column;
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
}

class SpellsKnownExpandableListAdapter extends BaseExpandableListAdapter{
    private String[] groups = {"Level 0", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};
    private SpellsKnown children;
    Activity parent;
    
    public SpellsKnownExpandableListAdapter(SpellsKnown children, Activity parent){
    	this.children = children;
    	this.parent = parent;
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
        return textView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

}