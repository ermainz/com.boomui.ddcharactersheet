package com.boomui.ddcharactersheet;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.boomui.ddcharactersheet.SpellsPreparedExpandableListAdapter.SpellsPreparedOnCheckedChangeListener;

public class BuffsActiveListAdapter extends BaseExpandableListAdapter{
	final private BuffsActive data;
	Activity parent;
	final ExpandableListView controlledView;
	
	public BuffsActiveListAdapter(BuffsActive data,
			ExpandableListView controlledView, Activity parent){
		this.data = data;
		this.parent = parent;
		this.controlledView = controlledView;
	}
	
	public Buff getChild(int groupPosition, int childPosition){
		return data.getBuffs().get(groupPosition);
	}
	
	public long getChildId(int groupPosition, int childPosition){
		return groupPosition;
	}
	
	public int getChildrenCount(int groupPosition){
		return 1;
	}
	
	public TextView getGenericView(){
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 64);
		
		TextView textView = new TextView(parent);
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setTextAppearance(parent, android.R.style.TextAppearance_Medium);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(36, 0, 0, 0);
		return textView;
	}
	
	public View getLevelView(int level){
		LinearLayout lView = new LinearLayout(parent);
		
		TextView turns = getGenericView();
		turns.setText(data.getBuffs().get(level).turns);
		
		TextView name = getGenericView();
		name.setText("  " + data.getBuffs().get(level).name + "  ");
		
		Button remove = new Button(parent);
		remove.setText("X");
		
		final Buff this_buff = data.getBuffs().get(level);
		
		remove.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				data.getBuffs().remove(this_buff);
				notifyDataSetChanged();
			}		
		});
		
		lView.setOrientation(LinearLayout.HORIZONTAL);
		
		lView.addView(name);
		lView.addView(turns);
		lView.addView(remove);
		
		
		lView.setFocusable(false);
		lView.setClickable(false);
		
		name.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		final BuffsActiveListAdapter spela = this;
		final int listenerLevel = level;
		/*
		add_to_active.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				spela.data.removeLastSpell(listenerLevel);
				spela.notifyDataSetChanged();
			}
		});
		*/
		return lView;
	}
	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup p){
		LinearLayout lView = new LinearLayout(parent);
				
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 64);
		
		
		Buff listenerSpell = getChild(groupPosition, childPosition);
		//cbox.setOnCheckedChangeListener(new SpellsPreparedOnCheckedChangeListener(lView, name, listenerSpell, this));
		
		TextView editBuffs = new TextView(parent);
		String description = getChild(groupPosition, childPosition).getDescription();
		if (description.equals(""))
			editBuffs.setText("");
		else
			editBuffs.setText(description);
		editBuffs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
		editBuffs.setPadding(0, 0, 0, 0);
		editBuffs.setTextAppearance(parent, android.R.style.TextAppearance_Medium);
		
		TextView editSkills = new TextView(parent);
		String descriptionSkills = getChild(groupPosition, childPosition).getSkillDescription();
		if (descriptionSkills.equals(""))
			editSkills.setText("");
		else
			editSkills.setText(descriptionSkills);
		editSkills.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
		editSkills.setPadding(0, 0, 0, 0);
		editSkills.setTextAppearance(parent, android.R.style.TextAppearance_Medium);
		
		final int gPos = groupPosition;
		final int cPos = childPosition;
		final BuffsActiveListAdapter spela = this;
		/*
		editBuffs.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				// TODO Auto-generated method stub
				data.removeSpellByName(gPos, data.getSpellName(gPos, cPos) );
				spela.notifyDataSetChanged();
			}
		});
	*/
		
	
		lView.addView(editBuffs);
		lView.addView(editSkills);
		lView.setOrientation(LinearLayout.VERTICAL);
		
		return lView;
	}
	
	public String getGroup(int groupPosition){
		return data.getBuffs().get(groupPosition).name;
	}
	
	public int getGroupCount(){
		return data.getBuffs().size();
	}
	
	public long getGroupId(int groupPosition){
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
		View lView = getLevelView(groupPosition);
		final int gPos = groupPosition;
		
		lView.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				if(controlledView.isGroupExpanded(gPos)){
					controlledView.collapseGroup(gPos);
				}
				else{
					controlledView.expandGroup(gPos);
				}
			}
		});
		return lView;
	}
	
	public boolean isChildSelectable(int groupPosition, int childPosition){
		return true;
	}
	
	public boolean hasStableIds(){
		return true;
	}
	
	
	
}