package com.boomui.ddcharactersheet;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SkillsListAdapter extends BaseAdapter {

	private Activity thisActivity;
	private InputMethodManager imm;

	private ArrayList<Skill> data;
	private static LayoutInflater inflater = null;
	private View.OnClickListener onClickListener;
	private ListView thisListView;
	TextView.OnEditorActionListener editTextListener;

	public SkillsListAdapter(Activity activity, ArrayList<Skill> data,
			View.OnClickListener onClickListener, ListView listView) {
		this.thisActivity = activity;
		imm = (InputMethodManager) thisActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		this.data = data;
		this.onClickListener = onClickListener;
		this.thisListView = listView;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		editTextListener = new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				
				
				View parent = (View) v.getParent();
				int index = thisListView.getPositionForView(parent);
				Skill skill = (Skill) thisListView.getAdapter().getItem(index);
				switch(v.getId()){
				case R.id.ranks_text:
					if(!v.getText().toString().equals(""))
						skill.ranks = Integer.valueOf(v.getText().toString());
					break;
				case R.id.misc_modifier_text:
					if(!v.getText().toString().equals(""))
						skill.miscModifier = Integer.valueOf(v.getText().toString());
					break;
				}
				skill.recalculateMod();
				((SkillsListAdapter)thisListView.getAdapter()).notifyDataSetChanged();
				return true;
			}
		};
		
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View retView = convertView;
		if (convertView == null) {
			retView = inflater.inflate(R.layout.skills_tab_row_layout, parent,
					false);
		}

		TextView skillName = (TextView) retView
				.findViewById(R.id.skill_name_text);
		TextView keyAbility = (TextView) retView
				.findViewById(R.id.key_ability_text);
		TextView skillModifier = (TextView) retView
				.findViewById(R.id.skill_modifier_text);
		EditText ranks = (EditText) retView.findViewById(R.id.ranks_text);
		TextView abilityModifier = (TextView) retView
				.findViewById(R.id.ability_modifier_text);
		EditText miscModifier = (EditText) retView
				.findViewById(R.id.misc_modifier_text);
		TextView buffsModifier = (TextView) retView
				.findViewById(R.id.buffs_modifier_text);
		ImageButton diceButton = (ImageButton) retView
				.findViewById(R.id.skill_dice_roll_button);

		diceButton.setOnClickListener(onClickListener);
		ranks.setOnEditorActionListener(editTextListener);
		miscModifier.setOnEditorActionListener(editTextListener);

		Skill skill = data.get(position);

		skillName.setText(skill.skillName);
		switch (skill.keyAbility) {
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
