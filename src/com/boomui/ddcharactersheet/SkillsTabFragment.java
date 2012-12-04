package com.boomui.ddcharactersheet;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class SkillsTabFragment extends Fragment {

	

	View mainView;
	ListView skillListView;
	SkillsListAdapter skillListAdapter;
	ArrayList<Skill> skillList;
	
	String skills[] = { "Appraise", "AutoHypnosis", "Balance", "Bluff",
			"Climb", "Concentration", "Craft(...)", "Decipher Script",
			"Diplomacy", "Disable Device", "Disguise", "Escape Artist",
			"Forgery", "Gather Information", "Handle Animal", "Heal", "Hide",
			"Intimidate", "Jump", "Knowledge(Arcana)", "Knowledge(Arch/Eng)",
			"Knowledge(Dungeoneering)", "Knowledge(Geography)",
			"Knowledge(History)", "Knowledge(Local)", "Knowledge(Nature)",
			"Knowledge(Nobility/Royalty)", "Knowledge(The Planes)",
			"Knowledge(Psionics)", "Knowledge(Religion)", "Knowledge(...)",
			"Listen", "Move Silently", "Open Lock", "Perform(Act)",
			"Perform(Comedy)", "Perform(Dance)", "Perform(Keyboard)",
			"Perform(Oratory)", "Perform(Percusion)",
			"Perform(String Intstrument)", "Perform(Wind Instrument)",
			"Perform(Sing)", "Perform(...)", "Profession(...)", "Psicraft",
			"Ride", "Search", "Sense Motive", "Sleight of Hand", "Spellcraft",
			"Spot", "Survival", "Swim", "Tumble", "Use Magic Device",
			"Use Psionic Device", "Use Rope" };

	CharacterDataKey keyAbility[] = { CharacterDataKey.INT,
			CharacterDataKey.WIS, CharacterDataKey.DEX, CharacterDataKey.CHA,
			CharacterDataKey.STR, CharacterDataKey.CON, CharacterDataKey.INT,
			CharacterDataKey.INT, CharacterDataKey.CHA, CharacterDataKey.INT,
			CharacterDataKey.CHA, CharacterDataKey.DEX, CharacterDataKey.INT,
			CharacterDataKey.CHA, CharacterDataKey.CHA, CharacterDataKey.WIS,
			CharacterDataKey.DEX, CharacterDataKey.CHA, CharacterDataKey.STR,
			CharacterDataKey.INT, CharacterDataKey.INT, CharacterDataKey.INT,
			CharacterDataKey.INT, CharacterDataKey.INT, CharacterDataKey.INT,
			CharacterDataKey.INT, CharacterDataKey.INT, CharacterDataKey.INT,
			CharacterDataKey.INT, CharacterDataKey.INT, CharacterDataKey.INT,
			CharacterDataKey.WIS, CharacterDataKey.DEX, CharacterDataKey.DEX,
			CharacterDataKey.CHA, CharacterDataKey.CHA, CharacterDataKey.CHA,
			CharacterDataKey.CHA, CharacterDataKey.CHA, CharacterDataKey.CHA,
			CharacterDataKey.CHA, CharacterDataKey.CHA, CharacterDataKey.CHA,
			CharacterDataKey.CHA, CharacterDataKey.WIS, CharacterDataKey.INT,
			CharacterDataKey.DEX, CharacterDataKey.INT, CharacterDataKey.WIS,
			CharacterDataKey.DEX, CharacterDataKey.INT, CharacterDataKey.WIS,
			CharacterDataKey.WIS, CharacterDataKey.STR, CharacterDataKey.DEX,
			CharacterDataKey.CHA, CharacterDataKey.CHA, CharacterDataKey.DEX };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.skills_tab_fragment_layout,
				container, false);

		skillList = new ArrayList<Skill>();
		for(int i = 0; i < skills.length; i++){
			Skill newSkill = new Skill(skills[i], keyAbility[i], 0, 0, 0, 0); 
			//load ranks
			//load ability modifier
			//load misc modifier
			//load buffs modifier
			skillList.add(newSkill);
		}

		skillListAdapter = new SkillsListAdapter(getActivity(), skillList);
		
		skillListView = (ListView) mainView.findViewById(R.id.skill_list);
		skillListView.setAdapter(skillListAdapter);
		skillListAdapter.notifyDataSetChanged();

		return mainView;
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		//This code hides the keyboard
		InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	
}
