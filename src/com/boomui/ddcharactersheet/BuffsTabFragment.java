package com.boomui.ddcharactersheet;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class BuffsTabFragment extends Fragment implements BuffActivationListener{
	public static String CLASS_SEPARATOR = "###";
	
	FragmentCommunicator com;
	Activity parent;
	
	LinearLayout mainPane;
	
	LinearLayout[] classPanes;
	String[] classes;
	int maxHeight = -1;
	int selected = -1;
	
	List<BaseExpandableListAdapter> expandableListAdapters;
	
	//For saving
	List<BuffsSaved> allBuffsSaved;
	List<BuffsActive> allBuffsActive;
	
	BuffsSaved buffsSaved;
	BuffsActive buffsActive;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		parent = activity;
		com = (FragmentCommunicator)activity;

		//This code hides the keyboard
		InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	public void onDestroyView(){
		super.onDestroyView();
		//com.saveData(CharacterDataKey.MAGIC_TAB_CLASS_PAGE_OPEN, classes[selected]);
		/*
		String saveStr = "";
		for(BuffsActive sp : allBuffsActive){
			saveStr += sp.save() + SpellsPrepared.CHARACTER_SPLIT;
		}*/
		com.saveData(CharacterDataKey.BUFFS_ACTIVE, buffsActive.save());
		int str=0,dex=0,con=0,intl=0,wis=0,cha=0,fort=0,ref=0,will=0,ac=0;
		
		
		for(Buff buff : buffsActive.getBuffs()){
			if( buff.str != null && !buff.str.isEmpty()) str += Integer.parseInt(buff.str);
			if( buff.dex != null && !buff.dex.isEmpty()) dex += Integer.parseInt(buff.dex);
			if( buff.con != null && !buff.con.isEmpty()) con += Integer.parseInt(buff.con);
			if( buff.intl != null && !buff.intl.isEmpty()) intl += Integer.parseInt(buff.intl);
			if( buff.wis != null && !buff.wis.isEmpty()) wis += Integer.parseInt(buff.wis);
			if( buff.cha != null && !buff.cha.isEmpty()) cha += Integer.parseInt(buff.cha);
			
			if( buff.fort != null && !buff.fort.isEmpty()) fort += Integer.parseInt(buff.fort);
			if( buff.ref != null && !buff.ref.isEmpty()) ref += Integer.parseInt(buff.ref);
			if( buff.will != null && !buff.will.isEmpty()) will += Integer.parseInt(buff.will);
			
			if( buff.ac != null && !buff.ac.isEmpty()) ac += Integer.parseInt(buff.ac);
		}
		if(str != 0) com.saveData(CharacterDataKey.STR_BUFF, str+"");
		else com.saveData(CharacterDataKey.STR_BUFF, "");
		if(dex != 0) com.saveData(CharacterDataKey.DEX_BUFF, dex+"");
		else com.saveData(CharacterDataKey.DEX_BUFF, "");
		if(con != 0) com.saveData(CharacterDataKey.CON_BUFF, con+"");
		else com.saveData(CharacterDataKey.CON_BUFF, "");
		if(intl != 0) com.saveData(CharacterDataKey.INT_BUFF, intl+"");
		else com.saveData(CharacterDataKey.INT_BUFF, "");
		if(wis != 0) com.saveData(CharacterDataKey.WIS_BUFF, wis+"");
		else com.saveData(CharacterDataKey.WIS_BUFF, "");
		if(cha != 0) com.saveData(CharacterDataKey.CHA_BUFF, cha+"");
		else com.saveData(CharacterDataKey.CHA_BUFF, "");
		
		com.saveData(CharacterDataKey.FORT_BUFF, fort+"");
		com.saveData(CharacterDataKey.REF_BUFF, ref+"");
		com.saveData(CharacterDataKey.WILL_BUFF, will+"");
		
		com.saveData(CharacterDataKey.AC_BUFF, ac+"");
		
		/*
		saveStr = "";
		for(BuffsSaved sk : allBuffsSaved){
			saveStr += sk.save() + SpellsKnown.CHARACTER_SPLIT;
		}*/
		com.saveData(CharacterDataKey.BUFFS_SAVED, buffsSaved.save());
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		//return inflater.inflate(R.layout.magic_tab_fragment_layout, container, false);
		//return createClassView("Misc.");
		
		allBuffsSaved = new LinkedList<BuffsSaved>();
		allBuffsActive = new LinkedList<BuffsActive>();
		expandableListAdapters = new LinkedList<BaseExpandableListAdapter>();
		
		LinearLayout retVal = new LinearLayout(parent);
		retVal.setOrientation(LinearLayout.VERTICAL);
		retVal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

		//This will load the set of classes later
		/*
		String allClasses = "Misc."+CLASS_SEPARATOR+"Sorcerer"+CLASS_SEPARATOR+"Wizard";
		classes = allClasses.split(CLASS_SEPARATOR);
		*/
		mainPane = createBaseView();
		
		/*
		for(int i = 0; i < classes.length; i++){
			
			classPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 0));
			
		}
		*/
		//String classOpen = com.loadData(CharacterDataKey.MAGIC_TAB_CLASS_PAGE_OPEN);
		//Default spell page that will always be there
		//if(classOpen == null || allClasses.indexOf(classOpen) == -1){
		//	String classOpen = "Misc.";
		//}
		
		final BuffsTabFragment thisFrag = this;
		boolean addToTop = true;
		mainPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
		retVal.addView(mainPane);
		//selected = 0;
		
		/*
		for(int i = 0; i < classes.length; i++){
			Button b = new Button(parent);
			b.setText(classes[i]);
			
			final int index = i;
			OnClickListener buttonListener = new OnClickListener(){
				public void onClick(View v){
					thisFrag.setCurrentPane(index);
				}
			};
			
			b.setOnClickListener(buttonListener);
	    	b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
			retVal.addView(b);
			
			
			if(classes[i].equals(classOpen) ){
				classPanes[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
				selected = i;
			}
		}
		*/
		return retVal;
	}
	
	public void onActivateClick(int groupPosition){
		buffsActive.getBuffs().add(buffsSaved.getBuffs().get(groupPosition));
		
		for(BaseExpandableListAdapter bela : expandableListAdapters){
			bela.notifyDataSetChanged();
		}
	}
	
	public void spellClicked(String spellName, int level){
		//allBuffsActive.get(selected).addNewSpell(spellName, level);
		
		for(BaseExpandableListAdapter bela : expandableListAdapters){
			bela.notifyDataSetChanged();
		}
	}
	
	public void setCurrentPane(int selected){
		maxHeight = classPanes[this.selected].getHeight();
		
		if(this.selected == selected){
			return;
		}
		
		/*for(int i = 0; i < classPanes.length; i++){
			if(i != selected){
				classPanes[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 0));
			}
			else{
				classPanes[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
				this.selected = selected;
			}
		}*/
		
		/*TabExpansionAnimation shrink = new TabExpansionAnimation(classPanes[this.selected], 100, 1, true);
		TabExpansionAnimation grow = new TabExpansionAnimation(classPanes[selected], 100, maxHeight, false);
		
		classPanes[this.selected].startAnimation(shrink);
		classPanes[selected].startAnimation(grow);*/
		
		TabExpansionAnimation change = new TabExpansionAnimation(classPanes[selected], classPanes[this.selected], 500);
		classPanes[selected].startAnimation(change);
		
		this.selected = selected;
	}
	
	public LinearLayout createBaseView(){
		LinearLayout horizColumns = new LinearLayout(parent);
		
		View buffs_saved = createBuffsSaved();
		View buffs_active = createBuffsActive();
		
		buffs_saved.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
		buffs_active.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
		
		horizColumns.addView(buffs_saved);
		horizColumns.addView(buffs_active);
		horizColumns.setOrientation(LinearLayout.HORIZONTAL);
		
		horizColumns.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT) );
		
		return horizColumns;
	}
	
	public View createBuffsActive(){
		TextView header = getGenericView();
		header.setText("Active Buffs");
		
		final ExpandableListView buffsActiveView = new ExpandableListView(parent);
		BuffsActive buffs_active = new BuffsActive(com);
		buffsActive = buffs_active;
		final BuffsActiveListAdapter adapter = new BuffsActiveListAdapter(buffs_active, buffsActiveView, parent);
		buffsActiveView.setAdapter(adapter);
		buffsActiveView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1) );
		expandableListAdapters.add(adapter);
		//spellsPreparedView.setGroupIndicator(null);
		/*
		for(int i = 0; i < adapter.getGroupCount(); i++){
			String result = com.loadData(CharacterDataKey.MAGIC_TAB_SPELLS_PREPARED_LEVEL_OPEN, "" + i);
			if(result == null){
				spellsPreparedView.expandGroup(i);
				com.saveData(CharacterDataKey.MAGIC_TAB_SPELLS_PREPARED_LEVEL_OPEN, "" + i, "true");
			}
			else if(result.equals("true") ){
				spellsPreparedView.expandGroup(i);
			}
		}
		
		spellsPreparedView.setOnGroupCollapseListener(new OnGroupCollapseListener(){
			public void onGroupCollapse(int groupPosition){
				com.saveData(CharacterDataKey.MAGIC_TAB_SPELLS_PREPARED_LEVEL_OPEN, "" + groupPosition, "" + spellsPreparedView.isGroupExpanded(groupPosition) );
			}
		});
		spellsPreparedView.setOnGroupExpandListener(new OnGroupExpandListener(){
			public void onGroupExpand(int groupPosition){
				com.saveData(CharacterDataKey.MAGIC_TAB_SPELLS_PREPARED_LEVEL_OPEN, "" + groupPosition, "" + spellsPreparedView.isGroupExpanded(groupPosition) );
			}
		});
		*/
		
		LinearLayout buttons = new LinearLayout(parent);
		buttons.setOrientation(LinearLayout.HORIZONTAL);
		buttons.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
		
		Button back = new Button(parent);
		back.setText("Back");
		back.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
		back.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				int size = buffsActive.getBuffs().size();
				for(int i = 0; i < size; i++){
						int new_turns = Integer.parseInt( buffsActive.getBuffs().get(i).turns);
						new_turns ++;
						buffsActive.getBuffs().get(i).turns = new_turns + "";					
				}
				adapter.notifyDataSetChanged();
			}		
		});
		
		Button nextTurn = new Button(parent);
		nextTurn.setText("Next Turn");
		nextTurn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
		nextTurn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				int size = buffsActive.getBuffs().size();
				for(int i = 0; i < size; i++){
					if(buffsActive.getBuffs().get(i).turns.equals("1")){
						buffsActive.getBuffs().remove(i);
						i--;
						size --;
					}
					else{
						try{
							int new_turns = Integer.parseInt( buffsActive.getBuffs().get(i).turns);
							new_turns --;
							buffsActive.getBuffs().get(i).turns = new_turns + "";
						}
						catch(NumberFormatException ex){}
					}
				}
				adapter.notifyDataSetChanged();
			}		
		});
		
		buttons.addView(back);
		buttons.addView(nextTurn);
		
		LinearLayout column = new LinearLayout(parent);
		column.setOrientation(LinearLayout.VERTICAL);
		column.addView(header);
		column.addView(buffsActiveView);
		column.addView(buttons);
		
		return column;
	}
	
	public View createBuffsSaved(){
		TextView header = getGenericView();
		header.setText("Saved Buffs");
		
		final ExpandableListView buffsSavedView = new ExpandableListView(parent);
		BuffsSaved buffs_saved = new BuffsSaved(com);
		buffsSaved = buffs_saved;
		final BuffsSavedListAdapter adapter = new BuffsSavedListAdapter(buffsSaved, buffsSavedView, parent, this);
		buffsSavedView.setAdapter(adapter);
		buffsSavedView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1) );
		expandableListAdapters.add(adapter);
		
		/*
		for(int i = 0; i < adapter.getGroupCount(); i++){
			String result = com.loadData(CharacterDataKey.MAGIC_TAB_SPELLS_KNOWN_LEVEL_OPEN, "" + i);
			if(result == null){
				spellsKnownView.expandGroup(i);
				com.saveData(CharacterDataKey.MAGIC_TAB_SPELLS_KNOWN_LEVEL_OPEN, "" + i, "true");
			}
			else if(result.equals("true") ){
				spellsKnownView.expandGroup(i);
			}
		}

		spellsKnownView.setOnGroupCollapseListener(new OnGroupCollapseListener(){
			public void onGroupCollapse(int groupPosition){
				com.saveData(CharacterDataKey.MAGIC_TAB_SPELLS_KNOWN_LEVEL_OPEN, "" + groupPosition, "" + spellsKnownView.isGroupExpanded(groupPosition) );
			}
		});
		spellsKnownView.setOnGroupExpandListener(new OnGroupExpandListener(){
			public void onGroupExpand(int groupPosition){
				com.saveData(CharacterDataKey.MAGIC_TAB_SPELLS_KNOWN_LEVEL_OPEN, "" + groupPosition, "" + spellsKnownView.isGroupExpanded(groupPosition) );
			}
		});
		*/
		Button addBuff = new Button(parent);
		addBuff.setText("Add New Buff");
		addBuff.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
		
		addBuff.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				buffsSaved.getBuffs().add(new Buff());
				adapter.notifyDataSetChanged();
			}		
		});
		
		LinearLayout column = new LinearLayout(parent);
		column.setOrientation(LinearLayout.VERTICAL);
		column.addView(header);
		column.addView(buffsSavedView);
		column.addView(addBuff);
		
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