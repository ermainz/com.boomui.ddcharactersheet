package com.boomui.ddcharactersheet;


import com.boomui.ddcharactersheet.CharacterSheetActivity.EditFortitudeFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class BuffsSavedListAdapter extends BaseExpandableListAdapter{
	final private BuffsSaved data;
	Activity parent;
	final ExpandableListView controlledView;
	final BuffActivationListener buffActivationListener;
	
	public BuffsSavedListAdapter(BuffsSaved data,
			ExpandableListView controlledView, Activity parent, BuffActivationListener buffActivationListener){
		this.data = data;
		this.parent = parent;
		this.controlledView = controlledView;
		this.buffActivationListener = buffActivationListener;
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
		name.setText("  " + data.getBuffs().get(level).name + "  ");
		
		EditText textView = new EditText(parent);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		textView.setPadding(36, 0, 0, 0);
		textView.setText(data.getBuffs().get(level).turns);
		textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
		
		final int gPos = level;
		textView.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {			    	
		    	data.getBuffs().get(gPos).turns = s.toString();
		    }
		});
		final Buff buffEdit = data.getBuffs().get(level);
		Button add_to_active = new Button(parent);
		add_to_active.setText("->");
		add_to_active.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(!buffEdit.turns.equals("0")){
					buffActivationListener.onActivateClick(gPos);
				}
			}
		});
		
		
		lView.addView(name);
		lView.addView(add_to_active);
		lView.setOrientation(LinearLayout.HORIZONTAL);
		
		lView.setFocusable(false);
		lView.setClickable(false);
		
		name.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		final BuffsSavedListAdapter spela = this;
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
		
		EditText textView = new EditText(parent);
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(36, 0, 0, 0);
	
		textView.setText(data.getBuffs().get(groupPosition).name);
		textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
		
		final int gPos = groupPosition;
		final Buff editBuff = data.getBuffs().get(groupPosition);
		textView.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {			    	
		    	editBuff.name = s.toString();
		    	
		    }
		});
		
		LinearLayout editTurnsView = new LinearLayout(parent);
		editTurnsView.setOrientation(LinearLayout.HORIZONTAL);
		editTurnsView.setPadding(36, 0, 0, 0);
		editTurnsView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
		
		TextView editTurnsText = new TextView(parent);
		editTurnsText.setText("Turns: ");
		
		EditText editTurns = new EditText(parent);
		editTurns.setLayoutParams(lp);
		editTurns.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		
		editTurns.setText(editBuff.turns);
		editTurns.setInputType(InputType.TYPE_CLASS_NUMBER);
		editTurns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );

		editTurns.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {			    	
		    	editBuff.turns = s.toString();		    	
		    }
		});
		editTurnsView.addView(editTurnsText);
		editTurnsView.addView(editTurns);
		
		
		Buff listenerSpell = getChild(groupPosition, childPosition);
		//cbox.setOnCheckedChangeListener(new SpellsPreparedOnCheckedChangeListener(lView, name, listenerSpell, this));
		
		final Button editBuffs = new Button(parent);
		String description = getChild(groupPosition, childPosition).getDescription();
		if (description.equals(""))
			editBuffs.setText("Edit Attributes");
		else
			editBuffs.setText(description);
		editBuffs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
		//editBuffs.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		editBuffs.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				DialogFragment newFragment = new EditBuffFragment(editBuff, editBuffs);
				newFragment.show(parent.getFragmentManager(), "EditAbilityScore");
			}
		});
		
		
		final Button editSkills = new Button(parent);
		String descriptionSkills = getChild(groupPosition, childPosition).getSkillDescription();
		if (descriptionSkills.equals(""))
			editSkills.setText("Edit Skills");
		else
			editSkills.setText(descriptionSkills);
		editSkills.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
		//editBuffs.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		editSkills.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				DialogFragment newFragment = new EditSkillsFragment(editBuff, editSkills);
				newFragment.show(parent.getFragmentManager(), "EditSkills");
			}
		});
		
		//editBuffs.setPadding(36, 0, 0, 0);
				
		
				
		final BuffsSavedListAdapter spela = this;
		/*
		
	*/
		
		lView.addView(textView);
		lView.addView(editTurnsView);
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
	
	public class EditBuffFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		Buff buff;
		Button buffButton;
		
		public EditBuffFragment(Buff editBuff, Button editButton){
			buff = editBuff;
			buffButton = editButton;
		}
		
		public void onAttach(Activity activity){
			super.onAttach(activity);
			parent = activity;
			com = (FragmentCommunicator)activity;			
		}
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    
	    public EditText createDialogEdit(String value){
	    	EditText retVal = new EditText(parent);
	    	retVal.setText(value);
	    	retVal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	    	retVal.setInputType(InputType.TYPE_CLASS_NUMBER);
	    	
	    	return retVal;	
	    }
	    
	    public TextView createDialogText(String value){
	    	TextView retVal = new TextView(parent);
	    	retVal.setText(value);
	    	retVal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	    	retVal.setGravity(Gravity.RIGHT);
	    	return retVal;	
	    }
	    
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        LinearLayout view ;//= inflater.inflate(R.layout.edit_saves_fragment, container, false);
	        view = new LinearLayout(parent);
	        LinearLayout row1 = new LinearLayout(parent);
	        LinearLayout row2 = new LinearLayout(parent);
	        EditText fort = createDialogEdit(buff.fort);
	        fort.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.fort = s.toString();	
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText ref =  createDialogEdit(buff.ref);
	        ref.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.ref = s.toString();	
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText will =  createDialogEdit(buff.will);
	        will.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.will = s.toString();
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText str =  createDialogEdit(buff.str);
	        str.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.str = s.toString();		
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText dex =  createDialogEdit(buff.dex);
	        dex.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.dex = s.toString();	
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText con =  createDialogEdit(buff.con);
	        con.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.con = s.toString();	
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText intl =  createDialogEdit(buff.intl);
	        intl.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.intl = s.toString();			
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText wis =  createDialogEdit(buff.wis);
	        wis.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.wis = s.toString();		
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText cha =  createDialogEdit(buff.cha);
	        cha.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.cha = s.toString();			
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText ac =  createDialogEdit(buff.ac);
	        ac.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buff.ac = s.toString();			
			    	buffButton.setText(buff.getDescription());
			    }
			});
	        
	        TextView fort_text = createDialogText("   FORT:");
	        TextView ref_text = createDialogText("   REF:");
	        TextView will_text = createDialogText("   WILL:");
	        TextView str_text =createDialogText("   STR:");
	        TextView dex_text = createDialogText("   DEX:");
	        TextView con_text = createDialogText("   CON:");
	        TextView intl_text =createDialogText("   INT:");
	        TextView wis_text =createDialogText("   WIS:");
	        TextView cha_text = createDialogText("   CHA:");
	        TextView ac_text = createDialogText("   AC:");
	        
	        
	        view.setOrientation(LinearLayout.VERTICAL);
	        	        
	        row1.setOrientation(LinearLayout.HORIZONTAL);
	        row2.setOrientation(LinearLayout.HORIZONTAL);
	        	        
	        row1.addView(str_text);
	        row1.addView(str);
	        row1.addView(dex_text);
	        row1.addView(dex);
	        row1.addView(con_text);
	        row1.addView(con);
	        row1.addView(intl_text);
	        row1.addView(intl);
	        row1.addView(wis_text);
	        row1.addView(wis);
	        row1.addView(cha_text);
	        row1.addView(cha);
	        row2.addView(fort_text);
	        row2.addView(fort);
	        row2.addView(ref_text);
	        row2.addView(ref);
	        row2.addView(will_text);
	        row2.addView(will);
	        row2.addView(ac_text);
	        row2.addView(ac);
	        
	        view.addView(row1);
	        view.addView(row2);
	        
	        main_view = view;
	        
	        return view;
	    }
	}
	
	
	public class EditSkillsFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		Buff buff;
		Button buffButton;

		
		public EditSkillsFragment( Buff editBuff, Button editButton){
			buff = editBuff;
			buffButton = editButton;

		}
		
		public void onAttach(Activity activity){
			super.onAttach(activity);
			parent = activity;
			com = (FragmentCommunicator)activity;			
		}
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    
	    public EditText createDialogEdit(String value){
	    	EditText retVal = new EditText(parent);
	    	retVal.setText(value);
	    	retVal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	    	retVal.setInputType(InputType.TYPE_CLASS_NUMBER);
	    	
	    	return retVal;	
	    }
	    
	    public TextView createDialogText(String value){
	    	TextView retVal = new TextView(parent);
	    	retVal.setText(value);
	    	retVal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	    	retVal.setGravity(Gravity.RIGHT);
	    	return retVal;	
	    }
	    
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	LinearLayout view = new LinearLayout(parent);
	    	view.setOrientation(LinearLayout.VERTICAL);
	        GridView gridView = new GridView(parent);
	        final SkillAdapter adapter = new SkillAdapter(parent, buff, buffButton);	        
	        gridView.setAdapter(adapter);
	        gridView.setNumColumns(2);
	        
	        Button addSkill = new Button(parent);
	        addSkill.setText("Add Skill Buff");
	        addSkill.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					buff.getBuffSkills().add(new BuffSkill("","0"));
					adapter.notifyDataSetChanged();
				}	        	
	        });
	        
	        view.addView(gridView);
	        view.addView(addSkill);
	        main_view = view;
	        
	        return view;
	    }
	}
	
	
	public class SkillAdapter extends BaseAdapter {
	    //private Context mContext;
	    public Buff buff;
	    private Activity parent;
	    private Button buffButton;
	    public SkillAdapter(Activity parent, Buff buff, Button buffButton) {
	        this.parent = parent;
	        this.buff = buff;
	        this.buffButton = buffButton;
	    }

	    public int getCount() {
	        return buff.getBuffSkills().size();
	    }

	    public Object getItem(int position) {
	        return buff.getBuffSkills().get(position);
	    }

	    public long getItemId(int position) {
	        return 0;
	    }
	    
	    
	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parentGroup) {
	        LinearLayout view = new LinearLayout(parent);
	        view.setOrientation(LinearLayout.HORIZONTAL);
	        view.setPadding(18, 18, 0, 0);
	        
	        TextView nameText = new TextView(parent);
	        nameText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	        nameText.setGravity(Gravity.RIGHT);
	        nameText.setText("Skill: ");
	        
	        EditText skillName = new EditText(parent);
	        skillName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	        skillName.setText(buff.getBuffSkills().get(position).name);
	        
	        final BuffSkill buffSkill = buff.getBuffSkills().get(position);
	        
	        skillName.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buffSkill.name = s.toString();			
			    	buffButton.setText(buff.getSkillDescription());
			    }
			});
	        
	        
	        EditText skillValue = new EditText(parent);
	        skillValue.setInputType(InputType.TYPE_CLASS_NUMBER);
	        skillValue.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
	        skillValue.setText(buff.getBuffSkills().get(position).value);
	        skillValue.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	buffSkill.value = s.toString();			
			    	buffButton.setText(buff.getSkillDescription());
			    }
			});
	        
	        view.addView(skillName);
	        view.addView(skillValue);
	        
	        return view;
	    }
	}
}