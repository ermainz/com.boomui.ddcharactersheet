package com.boomui.ddcharactersheet;

import java.util.LinkedList;
import java.util.List;

import com.boomui.ddcharactersheet.CharacterSheetActivity.EditFortitudeFragment;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

/*
 * Spells will be stored in two ways:
 * 		One long file with spell names/levels
 * 		Numerous individual files, one per spell
 */

public class SpellLookupTabFragment extends Fragment implements SearchedSpellInteractionListener, SpellInteractionListener, SpellSearcher{
	FragmentCommunicator com;
	Activity parent;
	SpellLoader spellLoader;
	List<SpellsKnown> allSpellsKnown;
	List allAdapters;
	int selected = -1; //The currently selected accordion
	String[] classes;
	LinearLayout[] classPanes;
	
	SearchView searcher;
	ListView spellList;
	TextView failureField;
	SpellSearchListAdapter spellListAdapter;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		parent = activity;
		com = (FragmentCommunicator)activity;
		spellLoader = (SpellLoader)activity;

		//This code hides the keyboard
		InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	public void onDestroyView(){
		super.onDestroyView();
		com.saveData(CharacterDataKey.MAGIC_TAB_CLASS_PAGE_OPEN, classes[selected]);
		
		String saveStr = "";
		for(SpellsKnown sk : allSpellsKnown){
			saveStr += sk.save() + SpellsKnown.CHARACTER_SPLIT;
		}
		com.saveData(CharacterDataKey.SPELLS_KNOWN, saveStr);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		allSpellsKnown = new LinkedList<SpellsKnown>();
		allAdapters = new LinkedList();
		
		String allClasses = Constants.defaultClasses;
		classes = allClasses.split(Constants.CHARACTER_CLASS_SEPARATOR);
		String classOpen = com.loadData(CharacterDataKey.MAGIC_TAB_CLASS_PAGE_OPEN);
		
		//Default spell page that will always be there
		if(classOpen == null || allClasses.indexOf(classOpen) == -1){
			classOpen = "Misc.";
		}
		
		return createFullView(classOpen);
		//return createSpellsKnownColumn(classOpen);
		//return inflater.inflate(R.layout.spell_lookup_tab_fragment, container, false);
	}
	
	public LinearLayout createSpellsKnownColumn(String classOpen){
		LinearLayout retVal = new LinearLayout(parent);
		retVal.setOrientation(LinearLayout.VERTICAL);
		retVal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		
		final SpellLookupTabFragment thisFrag = this;
		boolean addToTop = true;

		classPanes = new LinearLayout[classes.length];
		for(int i = 0; i < classes.length; i++){
			LinearLayout classPane = createSpellsKnown(classes[i]);
			classPane.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 0));
			classPanes[i] = classPane;
		}
		
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
			retVal.addView(classPanes[i]);
			
			if(classes[i].equals(classOpen) ){
				classPanes[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
				selected = i;
			}
		}

		return retVal;
	}

	public void setCurrentPane(int selected){
		int maxHeight = classPanes[this.selected].getHeight();
		
		if(this.selected == selected){
			return;
		}
		
		TabExpansionAnimation change = new TabExpansionAnimation(classPanes[selected], classPanes[this.selected], Constants.MAGIC_TAB_ANIMATION_LENGTH);
		classPanes[selected].startAnimation(change);
		
		this.selected = selected;
	}
	
	public LinearLayout createFullView(String characterClass){
		LinearLayout twoColumns = new LinearLayout(parent);
		twoColumns.setOrientation(LinearLayout.HORIZONTAL);
		
		View v1 = createSearchView();
		View v2 = createSpellsKnownColumn(characterClass);
		
		v1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
		v2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
		
		twoColumns.addView(v1);
		twoColumns.addView(v2);
		
		twoColumns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT) );
		
		return twoColumns;
	}
	
	public LinearLayout createSearchView(){
		LinearLayout column = new LinearLayout(parent);
		column.setOrientation(LinearLayout.VERTICAL);
		
		searcher = new SearchView(parent);
		spellList = new ListView(parent);
		spellListAdapter = new SpellSearchListAdapter(parent, this);
		spellList.setAdapter(spellListAdapter);
		allAdapters.add(spellListAdapter);
		
		searcher.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT) );
		searcher.setIconifiedByDefault(false);
		
		failureField = new TextView(parent);
		failureField.setTextColor(Constants.DISABLED);
		failureField.setVisibility(TextView.GONE);
		
		final SpellSearcher spellSearch = this;
		final Activity finalParent = parent;
		searcher.setOnQueryTextListener(new OnQueryTextListener(){
			public boolean onQueryTextChange(String newText){
				return false;
			}

			public boolean onQueryTextSubmit(String query){
				spellSearch.doSpellSearch(query);
				InputMethodManager inputManager = (InputMethodManager)finalParent.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow((null == finalParent.getCurrentFocus()) ? null : finalParent.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				return true;
			}
		});
		
		column.addView(searcher);
		column.addView(failureField);
		column.addView(spellList);
		
		column.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );

		return column;
	}

	public LinearLayout createSpellsKnown(String characterClass){
		TextView header = getGenericView();
		header.setText("Spells Known");
		
		final ExpandableListView spellsKnownView = new ExpandableListView(parent);
		SpellsKnown sk = new SpellsKnown(com, characterClass);
		allSpellsKnown.add(sk);
		SpellsKnownExpandableListAdapter adapter = new SpellsKnownExpandableListAdapter(sk, parent, this);
		spellsKnownView.setAdapter(adapter);
		spellsKnownView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1) );
		allAdapters.add(adapter);
		
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
		
		Button goBack = new Button(parent);
		goBack.setText("Prepare Spells");
		goBack.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
		
		LinearLayout column = new LinearLayout(parent);
		column.setOrientation(LinearLayout.VERTICAL);
		column.addView(header);
		column.addView(spellsKnownView);
		column.addView(goBack);
		
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

	public void spellClicked(String spellName, int level){
		
	}
	
	public void spellClicked(SpellListSpell spell){
		String currentClass = classes[selected];
		if(spell.castBy(currentClass) ){
			String[] castingClasses = spell.levels.split(", ");
			for(String s : castingClasses){
				if(s.startsWith(currentClass) ){
					String[] parts = s.split(" ");
					int level = Integer.parseInt(parts[1]);
					
					for(SpellsKnown sk : allSpellsKnown){
						if(sk.characterClass.equals(currentClass) ){
							sk.addSpell(spell.name, level);
							break;
						}
					}
					break;
				}
			}
		}
		else{
			SpellsKnown addTo = null;
			for(SpellsKnown sk : allSpellsKnown){
				if(sk.characterClass.equals(currentClass) ){
					addTo = sk;
					break;
				}
			}
			if(addTo != null){
				DialogFragment newFragment = new SpellChosenNoLevelFragment(addTo, spell.name, this);
				newFragment.show(getFragmentManager(), "SpellChosenWithNoLevel");
			}
		}
		
		updateLists();
	}
	
	public void updateLists(){
		for(Object o : allAdapters){
			if(o instanceof BaseAdapter){
				( (BaseAdapter) o).notifyDataSetChanged();
			}
			else if(o instanceof BaseExpandableListAdapter){
				( (BaseExpandableListAdapter) o).notifyDataSetChanged();
			}
		}
	}
	
	/**
	 * Maybe this should happen in a separate thread?
	 */
	public void doSpellSearch(String searchString){
		List<SpellListSpell> allSpells = spellLoader.getAllSpells();
		
		spellListAdapter.clear();
		
		for(SpellListSpell spell : allSpells){
			if(spell.name.toLowerCase().indexOf(searchString.toLowerCase() ) != -1){
				//spellsFoundByName.add(spell);
				spellListAdapter.addSpell(spell);
			}
		}
		
		String[] parts = searchString.split(" ");
		int level = -1;
		if(parts.length == 2){
			try{
				level = Integer.parseInt(parts[1]);
				searchString = parts[0];
			}
			catch(NumberFormatException ex){
				level = -1;
			}
		}
		
		if(spellLoader.isNameOfClass(searchString) ){
			for(SpellListSpell spell : allSpells){
				if(spell.castBy(searchString, level) ){
					//spellsFoundByName.add(spell);
					spellListAdapter.addSpell(spell);
				}
			}
		}
		
		if(spellListAdapter.getCount() == 0){
			failureField.setText("        No spells found for search \'" + searchString + "\'");
			failureField.setVisibility(View.VISIBLE);
		}
		else{
			failureField.setVisibility(View.GONE);
		}
		
		searcher.clearFocus();
		
		spellListAdapter.notifyDataSetChanged();
	}
}

class SpellSearchListAdapter extends BaseAdapter{
	Activity parent;
	final SearchedSpellInteractionListener listener;
	
	List<SpellListSpell> spells;
	
	public SpellSearchListAdapter(Activity parent, SearchedSpellInteractionListener listener){
		this.parent = parent;
		this.listener = listener;
		
		spells = new LinkedList<SpellListSpell>();
	}
	
	public void addSpell(SpellListSpell sp){
		spells.add(sp);
	}
	
	public void clear(){
		spells.clear();
	}

	public int getCount(){
		return spells.size();
	}

	public SpellListSpell getItem(int index){
		return spells.get(index);
	}

	public long getItemId(int arg0){
		return 0;
	}

	public View getView(int index, View v1, ViewGroup v2){
		TextView name = getGenericView();
		name.setText(getItem(index).name);
		
		final SpellListSpell listenerSpell = getItem(index);
		name.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.spellClicked(listenerSpell);
			}
		});
		
		return name;
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

class SpellChosenNoLevelFragment extends DialogFragment{
	Activity parent;
	SpellsKnown addTo;
	String spellName;
	SpellLookupTabFragment sltf;
	Spinner editable;
	
	public SpellChosenNoLevelFragment(SpellsKnown addTo, String name, SpellLookupTabFragment sltf){
		this.addTo = addTo;
		this.spellName = name;
		this.sltf = sltf;
	}

	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		parent = activity;
	}
	
	public void onDestroy(){
		super.onDestroy();
		
		if(editable.getSelectedItemPosition() != 0){
			addTo.addSpell(spellName, editable.getSelectedItemPosition() - 1);
		}
		
		sltf.updateLists();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		LinearLayout horiz = new LinearLayout(parent);
		horiz.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView text = getGenericView();
		text.setText(spellName + " doesn't have a level associated with it for this class.  What level spell is it?");
		
		editable = new Spinner(parent);
		editable.setAdapter(new SpinnerAdapter(){
			String[] items = {"", "Level 0", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};
			
			public void unregisterDataSetObserver(DataSetObserver observer){}
			public void registerDataSetObserver(DataSetObserver observer){}
			
			public boolean isEmpty(){
				return false;
			}
			
			public boolean hasStableIds(){
				return false;
			}
			
			public int getViewTypeCount(){
				return 1;
			}
			
			public View getView(int position, View convertView, ViewGroup parent){
				TextView text = getGenericView();
				text.setText(items[position]);
				return text;
			}
			
			public int getItemViewType(int position){
				return 0;
			}
			
			public long getItemId(int position){
				return 0;
			}
			
			public String getItem(int position){
				return items[position];
			}
			
			public int getCount(){
				return items.length;
			}
			
			public View getDropDownView(int position, View convertView, ViewGroup parent){
				return getView(position, convertView, parent);
			}

		    public TextView getGenericView() {
		        // Layout parameters for the ExpandableListView
		        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 48);

		        TextView textView = new TextView(parent);
		        textView.setLayoutParams(lp);
		        // Center the text vertically
		        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		        // Set the text starting position
		        textView.setPadding(36, 0, 0, 0);
		        return textView;
		    }
		});
		
		text.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3) );
		editable.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1) );
		
		final DialogFragment df = this;
		editable.setOnItemSelectedListener(new OnItemSelectedListener(){
			boolean firstState = true;
			
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				if(firstState){
					firstState = false;
				}
				else{
					df.dismiss();
				}
			}

			public void onNothingSelected(AdapterView<?> parent){}
		});
		
		horiz.addView(text);
		horiz.addView(editable);
		
		horiz.setClickable(false);
		horiz.setFocusable(false);
		
		horiz.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT) );
		
		return horiz;
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
        textView.setPadding(36, 0, 10, 0);
        return textView;
    }
}