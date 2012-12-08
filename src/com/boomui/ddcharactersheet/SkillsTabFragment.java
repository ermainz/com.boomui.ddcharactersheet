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
import android.widget.EditText;
import android.widget.ListView;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class SkillsTabFragment extends Fragment {

	FragmentCommunicator com;
	View mainView;
	ListView skillListView;
	SkillsListAdapter skillListAdapter;
	ArrayList<Skill> skillList;
	TextView diceRollOutput;

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

		String allSkillsString = com.loadData(CharacterDataKey.SKILLS);
		if (allSkillsString == null) {

			for (int i = 0; i < skills.length; i++) {
				CharacterDataKey keyAbil = keyAbility[i];
				String abilModString = "";
				switch (keyAbil) {
				case STR:
					abilModString = com.loadData(CharacterDataKey.STR_BUFF);
					break;
				case DEX:
					abilModString = com.loadData(CharacterDataKey.DEX_BUFF);
					break;
				case CON:
					abilModString = com.loadData(CharacterDataKey.CON_BUFF);
					break;
				case INT:
					abilModString = com.loadData(CharacterDataKey.INT_BUFF);
					break;
				case WIS:
					abilModString = com.loadData(CharacterDataKey.WIS_BUFF);
					break;
				case CHA:
					abilModString = com.loadData(CharacterDataKey.CHA_BUFF);
					break;
				default:
					abilModString = null;
					break;
				}
				int abilMod;
				if(abilModString == null || abilModString.equals("")){
					abilMod = 0;
				}
				else {
					abilMod = Integer.valueOf(abilModString);
				}
				Skill newSkill = new Skill(skills[i], keyAbil, 0, abilMod, 0, 0);
				// load ranks
				// load ability modifier
				// load misc modifier
				// load buffs modifier
				skillList.add(newSkill);
			}
		} else {
			String[] skillPairs = allSkillsString.split(";");
			for (int i = 0; i < skills.length; i++) {
				String[] skillPair = skillPairs[i].split(",");
				String ranks = skillPair[0];
				String miscMod = skillPair[1];
				String skillName = skills[i];
				CharacterDataKey keyAbil = keyAbility[i];
				String abilModString;
				switch (keyAbil) {
				case STR:
					abilModString = com.loadData(CharacterDataKey.STR_BUFF);
					break;
				case DEX:
					abilModString = com.loadData(CharacterDataKey.DEX_BUFF);
					break;
				case CON:
					abilModString = com.loadData(CharacterDataKey.CON_BUFF);
					break;
				case INT:
					abilModString = com.loadData(CharacterDataKey.INT_BUFF);
					break;
				case WIS:
					abilModString = com.loadData(CharacterDataKey.WIS_BUFF);
					break;
				case CHA:
					abilModString = com.loadData(CharacterDataKey.CHA_BUFF);
					break;
				default:
					abilModString = null;
					break;
				}
				int abilMod;
				if(abilModString == null || abilModString.equals("")){
					abilMod = 0;
				}
				else {
					abilMod = Integer.valueOf(abilModString);
				}
				Skill newSkill = new Skill(skillName, keyAbil, Integer.valueOf(ranks), abilMod, Integer.valueOf(miscMod), 0);
				skillList.add(newSkill);
			}
		}

		diceRollOutput = (TextView) mainView
				.findViewById(R.id.skill_dice_roll_output);

		View.OnClickListener onClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View parent = (View) v.getParent();
				TextView skill_mod_view = (TextView) parent
						.findViewById(R.id.skill_modifier_text);
				int skill_mod = Integer.valueOf(skill_mod_view.getText()
						.toString());
				int value = (int) (Math.random() * 20) + 1;
				diceRollOutput.setText(Integer.toString(value + skill_mod));

			}
		};

		skillListView = (ListView) mainView.findViewById(R.id.skill_list);
		skillListAdapter = new SkillsListAdapter(getActivity(), skillList,
				onClickListener, skillListView);

		skillListView.setAdapter(skillListAdapter);
		skillListAdapter.notifyDataSetChanged();

		return mainView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		com = (FragmentCommunicator) activity;

		// This code hides the keyboard
		InputMethodManager inputManager = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow((null == activity
				.getCurrentFocus()) ? null : activity.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void onPause() {
		super.onPause();
		int count = skillListAdapter.getCount();
		String data = "";
		for (int i = 0; i < count; i++) {
			Skill curr = (Skill) skillListAdapter.getItem(i);
			data = data + curr.ranks + "," + curr.miscModifier;
			data = data + ";";
		}
		com.saveData(CharacterDataKey.SKILLS, data);
	}

}
