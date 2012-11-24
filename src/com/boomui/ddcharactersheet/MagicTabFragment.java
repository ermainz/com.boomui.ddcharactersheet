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
		return createClassView("Misc.");
	}
	
	public View createClassView(String characterClass){
		LinearLayout horizColumns = new LinearLayout(parent);
		
		View sk = createSpellsKnown(characterClass);
		View sp = createSpellsPrepared(characterClass);
		
    	sk.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
    	sp.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
		
		horizColumns.addView(sk);
		horizColumns.addView(sp);
		horizColumns.setOrientation(LinearLayout.HORIZONTAL);
		
		return horizColumns;
	}
	
	public View createSpellsPrepared(String characterClass){
		TextView header = getGenericView();
		header.setText("Spells Prepared");
		
		ExpandableListView spellsPreparedView = new ExpandableListView(parent);
		SpellsPrepared sp = new SpellsPrepared(com, characterClass);
		SpellsPreparedExpandableListAdapter adapter = new SpellsPreparedExpandableListAdapter(sp, spellsPreparedView, parent);
		spellsPreparedView.setAdapter(adapter);
		//spellsPreparedView.setGroupIndicator(null);
		
		LinearLayout column = new LinearLayout(parent);
		column.setOrientation(LinearLayout.VERTICAL);
		column.addView(header);
		column.addView(spellsPreparedView);
		
		return column;
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