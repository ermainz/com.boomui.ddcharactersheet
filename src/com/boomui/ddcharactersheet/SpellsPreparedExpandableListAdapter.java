package com.boomui.ddcharactersheet;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

class SpellsPreparedExpandableListAdapter extends BaseExpandableListAdapter{
    private SpellsPrepared data;
    Activity parent;
    final ExpandableListView controlledView;
    
    public SpellsPreparedExpandableListAdapter(SpellsPrepared data, ExpandableListView controlledView, Activity parent){
    	this.data = data;
    	this.parent = parent;
    	this.controlledView = controlledView;
    }
    
    public PreparedSpell getChild(int groupPosition, int childPosition) {
        return data.getSpell(groupPosition, childPosition);
    }
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    public int getChildrenCount(int groupPosition) {
        return data.getNumSpells(groupPosition);
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
    public View getLevelView(int level){
    	LinearLayout lView = new LinearLayout(parent);
    	
    	TextView name = getGenericView();
    	name.setText("  " + data.getLevelName(level) + "  ");
    	
    	if(getChildrenCount(level) == 0){
    		name.setTextColor(Constants.DISABLED);
    	}
    	
    	Button plus = new Button(parent);
    	plus.setText("+");
    	Button minus = new Button(parent);
    	minus.setText("-");
    	
    	lView.addView(name);
    	lView.addView(plus);
    	lView.addView(minus);
    	lView.setOrientation(LinearLayout.HORIZONTAL);

    	lView.setFocusable(false);
    	lView.setClickable(false);
    	
    	name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
    	
    	final SpellsPreparedExpandableListAdapter spela = this;
    	final int listenerLevel = level;
    	plus.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				spela.data.addSpell(" ", listenerLevel);
				spela.notifyDataSetChanged();
			}
    	});
    	minus.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				spela.data.removeLastSpell(listenerLevel);
				spela.notifyDataSetChanged();
			}
    	});
    	
    	return lView;
    }
    
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup p) {
    	LinearLayout lView = new LinearLayout(parent);
    	
    	CheckBox cbox = new CheckBox(parent);
    	cbox.setChecked(getChild(groupPosition, childPosition).used);
    	
    	TextView name = getGenericView();
        name.setText(data.getSpellName(groupPosition, childPosition) );
        
        System.out.println(name);
        
        //name.setBackgroundColor(Constants.SPELL_USED);
    	
    	PreparedSpell listenerSpell = getChild(groupPosition, childPosition);
    	cbox.setOnCheckedChangeListener(new SpellsPreparedOnCheckedChangeListener(lView, name, listenerSpell, this) );
    	
        lView.addView(cbox);
    	lView.addView(name);
    	lView.setOrientation(LinearLayout.HORIZONTAL);
    	
    	name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        return lView;
    }

    public String getGroup(int groupPosition) {
        return data.getLevelName(groupPosition);
    }

    public int getGroupCount() {
        return data.getNumLevels();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	View lView = getLevelView(groupPosition);
    	final int gPos = groupPosition;
    	
    	lView.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(controlledView.isGroupExpanded(gPos) ){
					controlledView.collapseGroup(gPos);
				}
				else{
					controlledView.expandGroup(gPos);
				}
			}
    	});
        return lView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

    class SpellsPreparedOnCheckedChangeListener implements OnCheckedChangeListener{
    	TextView colorChanger;
    	SpellsPreparedExpandableListAdapter parent;
    	PreparedSpell spell;
    	LinearLayout container;
    	
    	public SpellsPreparedOnCheckedChangeListener(LinearLayout container, TextView colorChanger, PreparedSpell spell, SpellsPreparedExpandableListAdapter parent){
    		this.colorChanger = colorChanger;
    		this.parent = parent;
    		this.spell = spell;
    		this.container = container;
    		
    		//colorChanger.setBackgroundColor(Constants.SPELL_USED);
    	}
    	
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
			spell.used = isChecked;
			
			colorChanger.setText("Got here");
			
			if(isChecked){
				colorChanger.setBackgroundColor(0xfff00000);
				colorChanger.setTextColor(Constants.SPELL_USED);
				System.out.println(colorChanger);
				System.out.println("Got here");
			}
			else{
				colorChanger.setBackgroundColor(Constants.BACKGROUND_COLOR);
			}
			
			//colorChanger
			container.postInvalidate();
			parent.notifyDataSetChanged();
		}
    }
}