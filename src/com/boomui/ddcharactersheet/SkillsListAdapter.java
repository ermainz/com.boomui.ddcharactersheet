package com.boomui.ddcharactersheet;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SkillsListAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<Skill> data;
	private static LayoutInflater inflater=null;
	
	public SkillsListAdapter(Activity activity, ArrayList<Skill> data){
		this.activity = activity;
		this.data = data;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View retView = convertView;
		if(convertView == null){
			retView = inflater.inflate(R.layout.skills_tab_row_layout, parent, false);
		}
		
		TextView skillName = (TextView) retView.findViewById(R.id.skill_name_text);
		TextView keyAbility = (TextView) retView.findViewById(R.id.key_ability_text);
		TextView skillModifier = (TextView) retView.findViewById(R.id.skill_modifier_text);
		TextView ranks = (TextView) retView.findViewById(R.id.ranks_text);
		TextView abilityModifier = (TextView) retView.findViewById(R.id.ability_modifier_text);
		TextView miscModifier = (TextView) retView.findViewById(R.id.misc_modifier_text);
		TextView buffsModifier = (TextView) retView.findViewById(R.id.buffs_modifier_text);
		
		Skill skill = data.get(position);
		
		skillName.setText(skill.skillName);
		switch(skill.keyAbility){
		case STR:
			keyAbility.setText("STR");
			break;
		case INT:
			keyAbility.setText("INT");
			break;
		case WIS:
			keyAbility.setText("WIS");
			break;
		case DEX:
			keyAbility.setText("DEX");
			break;
		case CHA:
			keyAbility.setText("CHA");
			break;
		case CON:
			keyAbility.setText("CON");
			break;
		default:
			break;
		}
		skillModifier.setText(Integer.toString(skill.skillModifier));
		ranks.setText(Integer.toString(skill.ranks));
		abilityModifier.setText(Integer.toString(skill.abilityModifier));
		miscModifier.setText(Integer.toString(skill.miscModifier));
		buffsModifier.setText(Integer.toString(skill.buffsModifier));
		
		
		return retView;
	}

}
