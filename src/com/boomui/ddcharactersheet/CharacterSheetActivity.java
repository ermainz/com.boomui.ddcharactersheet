package com.boomui.ddcharactersheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.boomui.ddcharactersheet.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class CharacterSheetActivity extends Activity implements ActionBar.TabListener, FragmentCommunicator {
	Fragment currentFragment;
	
	HashMap<String, Fragment> fragmentMap;
	
	//We'll have to decide how to set this later.  At any rate, it will be necessary to distinguish the saved data
	//files from different characters.
	long uid = 3;
	
	public CharacterSheetActivity(){
        fragmentMap = new HashMap<String, Fragment>();
        
        fragmentMap.put("Info", new InfoTabFragment() );
        fragmentMap.put("Combat", new CombatTabFragment() );
        fragmentMap.put("Buffs", new BuffsTabFragment() );
        fragmentMap.put("Skills", new SkillsTabFragment() );
        fragmentMap.put("Magic", new MagicTabFragment() );
        fragmentMap.put("Feats", new FeatsTabFragment() );
        fragmentMap.put("Items", new InventoryTabFragment() );
        fragmentMap.put("Notes", new NotesTabFragment() );
        fragmentMap.put("Dice", new DiceRollerTabFragment() );
        fragmentMap.put("Spells", new SpellLookupTabFragment() );
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String data_print = loadData(CharacterDataKey.BUFFS_ACTIVE);
        System.err.println("Data active "+data_print);
        data_print = loadData(CharacterDataKey.BUFFS_SAVED);
        System.err.println("Data saved "+data_print);
        
        final ActionBar bar = getActionBar();
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
           
        bar.addTab(bar.newTab().setText("Info").setTabListener(this));
        bar.addTab(bar.newTab().setText("Combat").setTabListener(this));
        bar.addTab(bar.newTab().setText("Buffs").setTabListener(this));
        bar.addTab(bar.newTab().setText("Skills").setTabListener(this));
        bar.addTab(bar.newTab().setText("Magic").setTabListener(this));
        bar.addTab(bar.newTab().setText("Feats").setTabListener(this));
        bar.addTab(bar.newTab().setText("Items").setTabListener(this));
        bar.addTab(bar.newTab().setText("Notes").setTabListener(this));

      bar.addTab(bar.newTab().setText("Dice").setTabListener(this));
      bar.addTab(bar.newTab().setText("Spells").setTabListener(this));
        
        setContentView(R.layout.activity_character_sheet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_character_sheet, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){

    	default:
    		return false;
    	}
    }

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		String tabName = tab.getText().toString();

		currentFragment = fragmentMap.get(tabName);
		
		fragmentTransaction.add(R.id.fragmentView, currentFragment);
		fragmentTransaction.commit();
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(currentFragment);
		fragmentTransaction.commit();
	}
	
	public void saveData(CharacterDataKey tag, String data){
		saveData(tag, null, data);
	}
	public void saveData(CharacterDataKey tag, String strTag, String data){
		try{
			FileOutputStream fos = openFileOutput(uid + " " + tag.toString() + (strTag == null ? "" : " " + strTag), Context.MODE_PRIVATE);
			fos.write(data.getBytes() );
			fos.close();
			
			//System.out.println("Wrote: " + data + " to " + tag.toString() );
		}
		catch(IOException ex){
			System.err.println("Couldn't save data: " + tag.toString() );
		}
	}
	
	public String loadData(CharacterDataKey tag){
		return loadData(tag, null);
	}
	public String loadData(CharacterDataKey tag, String strTag){
		try{
			FileInputStream fis = openFileInput(uid + " " + tag.toString() + (strTag == null ? "" : " " + strTag) );
			int length = fis.available();
			
			byte[] bytes = new byte[length];
			fis.read(bytes);
			fis.close();
			
			String retVal = new String(bytes);
			return retVal;
		}
		catch(IOException ex){
			System.err.println("Couldn't load data: " + tag.toString());
		}
		
		return null;
	}
	
	
	public void roll_damage(View v){
		int roll = (int) (Math.random()*8);
		int damage = roll + 1;
		((TextView)findViewById(R.id.recent_roll)).setText("Last Roll: "+roll + " + "+ "1 = " + damage);
	}
	public void roll_attack(View v){
		int roll = (int) (Math.random()*20);
		int attack = roll + 3;
		((TextView)findViewById(R.id.recent_roll)).setText("Last Roll: "+roll + " + "+ "3 = " + attack);
	}
	public void roll_fort(View v){
		int roll = (int) (Math.random()*20);
		int fort_save = 
				Integer.parseInt(loadData(CharacterDataKey.FORT_CLASS)) + 
    			Integer.parseInt(loadData(CharacterDataKey.FORT_BUFF)) +
    			Integer.parseInt(((TextView)findViewById(R.id.con_modifier)).getText().toString()) +
    			Integer.parseInt(loadData(CharacterDataKey.FORT));
		int fort_total =  roll + fort_save;
		((TextView)findViewById(R.id.recent_roll)).setText("Last Roll: "+roll + " + "+fort_save+ " = " + fort_total);
	}
	public void roll_ref(View v){
		int roll = (int) (Math.random()*20);
		int save = 
				Integer.parseInt(loadData(CharacterDataKey.REF_CLASS)) + 
    			Integer.parseInt(loadData(CharacterDataKey.REF_BUFF)) +
    			Integer.parseInt(((TextView)findViewById(R.id.dex_modifier)).getText().toString()) +
    			Integer.parseInt(loadData(CharacterDataKey.REF));
		int total =  roll + save;
		((TextView)findViewById(R.id.recent_roll)).setText("Last Roll: "+roll + " + "+save+ " = " + total);
	}
	public void roll_will(View v){
		int roll = (int) (Math.random()*20);
		int save = 
				Integer.parseInt(loadData(CharacterDataKey.WILL_CLASS)) + 
    			Integer.parseInt(loadData(CharacterDataKey.WILL_BUFF)) +
    			Integer.parseInt(((TextView)findViewById(R.id.wis_modifier)).getText().toString()) +
    			Integer.parseInt(loadData(CharacterDataKey.WILL));
		int total =  roll + save;
		((TextView)findViewById(R.id.recent_roll)).setText("Last Roll: "+roll + " + "+save+ " = " + total);
	}
	
	public void editFortitude(View view){
		DialogFragment newFragment = new EditFortitudeFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editReflex(View view){
		DialogFragment newFragment = new EditReflexFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editWill(View view){
		DialogFragment newFragment = new EditWillFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editAC(View view){
		DialogFragment newFragment = new EditACFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editFF(View view){
		DialogFragment newFragment = new EditFFFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editTouch(View view){
		DialogFragment newFragment = new EditTouchFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	
	
	public class EditFortitudeFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		
		public void onAttach(Activity activity){
			super.onAttach(activity);
			parent = activity;
			com = (FragmentCommunicator)activity;			
		}
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	getDialog().setTitle("Fortitude");
	        View view = inflater.inflate(R.layout.edit_saves_fragment, container, false);
	        main_view = view;
	        
	        
	        
	        String fort_misc = com.loadData(CharacterDataKey.FORT);
	        if(fort_misc == null){
	        	fort_misc = "0"; com.saveData(CharacterDataKey.FORT, "0");}
	        ((EditText)view.findViewById(R.id.save_misc)).setText(fort_misc);
	        
	        String fort_class = com.loadData(CharacterDataKey.FORT_CLASS);
	        if(fort_class == null){
	        	fort_class = "0"; com.saveData(CharacterDataKey.FORT_CLASS, "0");}
	        ((EditText)view.findViewById(R.id.save_class)).setText(fort_class);
	        
	        String con = com.loadData(CharacterDataKey.CON);
	        if(con == null){
	        	con = "0"; com.saveData(CharacterDataKey.CON, "0");}
	        String con_buff = com.loadData(CharacterDataKey.CON_BUFF);
	        if(con_buff == null || con_buff.isEmpty()){
	        	con_buff = "0"; }//com.saveData(CharacterDataKey.CON_BUFF, "0");}
	        int modifier = (Integer.parseInt(con) + Integer.parseInt(con_buff))/2 -5;
	        ((TextView)view.findViewById(R.id.save_ability_score)).setText(""+modifier);
	        
	        String fort_buff = com.loadData(CharacterDataKey.FORT_BUFF);
	        if(fort_buff == null){
	        	fort_buff = "0"; com.saveData(CharacterDataKey.FORT_BUFF, "0");}
	        ((TextView)view.findViewById(R.id.save_buff)).setText(fort_buff);
	        
	        int fort_save = Integer.parseInt(fort_class) + 
	    			modifier +
	    			Integer.parseInt(fort_buff) +
	    			Integer.parseInt(fort_misc);
	        ((TextView)view.findViewById(R.id.modifier)).setText("Con: ");
	        
	        ((EditText)view.findViewById(R.id.save_misc)).addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	try{
			    		Integer.parseInt(s.toString());
			    	} catch (Exception e){
			    		return;
			    	}
			    	com.saveData(CharacterDataKey.FORT, s.toString());
			    	int fort_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.FORT_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.FORT_CLASS));
			    	
			    	((Button)parent.findViewById(R.id.fort_button)).setText("Fortitude: "+fort_save);
			    	//((TextView)parent.findViewById(R.id.save_value)).setText(fort_save+"");
			    }
			});
	        ((EditText)view.findViewById(R.id.save_class)).addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	try{
			    		Integer.parseInt(s.toString());
			    	} catch (Exception e){
			    		return;
			    	}
			    	com.saveData(CharacterDataKey.FORT_CLASS, s.toString());
			    	int fort_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.FORT_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.FORT));
			    	
			    	((Button)parent.findViewById(R.id.fort_button)).setText("Fortitude: "+fort_save);
			    	//((TextView)parent.findViewById(R.id.save_value)).setText(fort_save+"");
			    }
			});
	        return view;
	    }
	}
	public class EditReflexFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		
		public void onAttach(Activity activity){
			super.onAttach(activity);
			parent = activity;
			com = (FragmentCommunicator)activity;			
		}
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	getDialog().setTitle("Reflex");
	        View view = inflater.inflate(R.layout.edit_saves_fragment, container, false);
	        main_view = view;
	        
	        String ref_misc = com.loadData(CharacterDataKey.REF);
	        if(ref_misc == null){
	        	ref_misc = "0"; com.saveData(CharacterDataKey.REF, "0");}
	        ((EditText)view.findViewById(R.id.save_misc)).setText(ref_misc);
	        
	        String ref_class = com.loadData(CharacterDataKey.REF_CLASS);
	        if(ref_class == null){
	        	ref_class = "0"; com.saveData(CharacterDataKey.REF_CLASS, "0");}
	        ((EditText)view.findViewById(R.id.save_class)).setText(ref_class);
	        
	        String dex = com.loadData(CharacterDataKey.DEX);
	        if(dex == null){
	        	dex = "0"; com.saveData(CharacterDataKey.DEX, "0");}
	        String dex_buff = com.loadData(CharacterDataKey.DEX_BUFF);
	        if(dex_buff == null || dex_buff.isEmpty()){
	        	dex_buff = "0";}// com.saveData(CharacterDataKey.DEX_BUFF, "0");}
	        int modifier = (Integer.parseInt(dex) + Integer.parseInt(dex_buff))/2 -5;
	        ((TextView)view.findViewById(R.id.save_ability_score)).setText(""+modifier);
	        
	        String ref_buff = com.loadData(CharacterDataKey.REF_BUFF);
	        if(ref_buff == null){
	        	ref_buff = "0"; com.saveData(CharacterDataKey.REF_BUFF, "0");}
	        ((TextView)view.findViewById(R.id.save_buff)).setText(ref_buff);
	        
	        int save = Integer.parseInt(ref_class) + 
	    			modifier +
	    			Integer.parseInt(ref_buff) +
	    			Integer.parseInt(ref_misc);
	        ((TextView)view.findViewById(R.id.modifier)).setText("Dex: ");
	        
	        
	        ((EditText)view.findViewById(R.id.save_misc)).addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	try{
			    		Integer.parseInt(s.toString());
			    	} catch (Exception e){
			    		return;
			    	}
			    	com.saveData(CharacterDataKey.REF, s.toString());
			    	int ref_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.REF_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.REF_CLASS));
			    	
			    	((Button)parent.findViewById(R.id.ref_button)).setText("Reflex: "+ref_save);
			    	//((TextView)parent.findViewById(R.id.save_value)).setText(ref_save+"");
			    }
			});
	        ((EditText)view.findViewById(R.id.save_class)).addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	try{
			    		Integer.parseInt(s.toString());
			    	} catch (Exception e){
			    		return;
			    	}
			    	com.saveData(CharacterDataKey.REF_CLASS, s.toString());
			    	int ref_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.REF_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.REF));
			    	
			    	((Button)parent.findViewById(R.id.ref_button)).setText("Reflex: "+ref_save);
			    	//((TextView)parent.findViewById(R.id.save_value)).setText(ref_save+"");
			    }
			});
	        return view;
	    }
	}
	
	public class EditWillFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		
		public void onAttach(Activity activity){
			super.onAttach(activity);
			parent = activity;
			com = (FragmentCommunicator)activity;			
		}
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	getDialog().setTitle("Will");
	        View view = inflater.inflate(R.layout.edit_saves_fragment, container, false);
	        main_view = view;
	        
	        String will_misc = com.loadData(CharacterDataKey.WILL);
	        if(will_misc == null){
	        	will_misc = "0"; com.saveData(CharacterDataKey.WILL, "0");}
	        ((EditText)view.findViewById(R.id.save_misc)).setText(will_misc);
	        
	        String will_class = com.loadData(CharacterDataKey.WILL_CLASS);
	        if(will_class == null){
	        	will_class = "0"; com.saveData(CharacterDataKey.WILL_CLASS, "0");}
	        ((EditText)view.findViewById(R.id.save_class)).setText(will_class);
	        
	        String wis = com.loadData(CharacterDataKey.WIS);
	        if(wis == null){
	        	wis = "0"; com.saveData(CharacterDataKey.WIS, "0");}
	        String wis_buff = com.loadData(CharacterDataKey.WIS_BUFF);
	        if(wis_buff == null || wis_buff.isEmpty()){
	        	wis_buff = "0";}// com.saveData(CharacterDataKey.WIS_BUFF, "0");}
	        int modifier = (Integer.parseInt(wis) + Integer.parseInt(wis_buff))/2 -5;
	        ((TextView)view.findViewById(R.id.save_ability_score)).setText(""+modifier);
	        
	        String will_buff = com.loadData(CharacterDataKey.WILL_BUFF);
	        if(will_buff == null){
	        	will_buff = "0"; com.saveData(CharacterDataKey.WILL_BUFF, "0");}
	        ((TextView)view.findViewById(R.id.save_buff)).setText(will_buff);
	        
	        int save = Integer.parseInt(will_class) + 
	    			modifier +
	    			Integer.parseInt(will_buff) +
	    			Integer.parseInt(will_misc);
	        ((TextView)view.findViewById(R.id.modifier)).setText("Wis: ");
	        
	        ((EditText)view.findViewById(R.id.save_misc)).addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	try{
			    		Integer.parseInt(s.toString());
			    	} catch (Exception e){
			    		return;
			    	}
			    	com.saveData(CharacterDataKey.WILL, s.toString());
			    	int wil_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL_CLASS));
			    	
			    	((Button)parent.findViewById(R.id.will_button)).setText("Will: "+wil_save);
			    	//((TextView)parent.findViewById(R.id.save_value)).setText(wil_save+"");
			    }
			});
	        ((EditText)view.findViewById(R.id.save_class)).addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    	
			    	try{
			    		Integer.parseInt(s.toString());
			    	} catch (Exception e){
			    		return;
			    	}
			    	com.saveData(CharacterDataKey.WILL_CLASS, s.toString());
			    	int wil_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL));
			    	
			    	((Button)parent.findViewById(R.id.will_button)).setText("Will: "+wil_save);
			    	//((TextView)parent.findViewById(R.id.save_value)).setText(wil_save+"");
			    }
			});
	        return view;
	    }
	}
	
	
	public void printData(String data){
		System.out.println(data);
	}
	
	public class EditACFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		Button acButton;
		
		String dex, armor, shield, natural, deflection, dodge, buff, misc;

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
	    	getDialog().setTitle("Total AC");
	    	
	    	String dex_buff = loadData(CharacterDataKey.DEX_BUFF);
	    	if(dex_buff == null || dex_buff.isEmpty())
	    		dex_buff = "0";
	    	dex = ((Integer.parseInt(loadData(CharacterDataKey.DEX)) +
	    			Integer.parseInt(dex_buff))/2 - 5) +"";
	    	
	    	armor = loadData(CharacterDataKey.AC_ARMOR);
	    	shield = loadData(CharacterDataKey.AC_SHIELD);
	    	natural = loadData(CharacterDataKey.AC_NATURAL);
	    	deflection = loadData(CharacterDataKey.AC_DEFLECTION);
	    	dodge = loadData(CharacterDataKey.AC_DODGE);
	    	misc = loadData(CharacterDataKey.AC_MISC);
	    	buff = loadData(CharacterDataKey.AC_BUFF);
	    	
	    	
	        LinearLayout view ;//= inflater.inflate(R.layout.edit_saves_fragment, container, false);
	       view = new LinearLayout(parent);
	        LinearLayout row1 = new LinearLayout(parent);
	        LinearLayout row2 = new LinearLayout(parent);
	        
	        EditText armor_b = createDialogEdit(armor);
	        armor_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	armor = s.toString();
			    	if(armor.isEmpty())
			    		armor = "0";
			    	updateButtons();
			    }
			});
	        
	        EditText shield_b =  createDialogEdit(shield);
	        shield_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	shield = s.toString();
			    	if(shield.isEmpty())
			    		shield = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText natural_b =  createDialogEdit(natural);
	        natural_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	natural = s.toString();
			    	if(natural.isEmpty())
			    		natural = "0";
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText deflection_b =  createDialogEdit(deflection);
	        deflection_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	deflection = s.toString();
			    	if(deflection.isEmpty())
			    		deflection = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText dodge_b =  createDialogEdit(dodge);
	        dodge_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	dodge = s.toString();
			    	if(dodge.isEmpty())
			    		dodge = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText misc_b =  createDialogEdit(misc);
	        misc_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	misc = s.toString();
			    	if(misc.isEmpty())
			    		misc = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});	        
	        
	        TextView armor_text = createDialogText("Armor:");
	        TextView shield_text = createDialogText("Shield:");
	        TextView natural_text = createDialogText("Natural:");
	        TextView deflection_text =createDialogText("Deflection:");
	        TextView dodge_text = createDialogText("Dodge:");
	        TextView misc_text = createDialogText("Misc:");
	        TextView dex_text =createDialogText("Dex:");
	        TextView dex_edit_text = createDialogText(dex);
	        TextView buff_text =createDialogText("Buff:");
	        TextView buff_edit_text = createDialogText(buff);
	        
	        view.setOrientation(LinearLayout.VERTICAL);
	        	        
	        row1.setOrientation(LinearLayout.HORIZONTAL);
	        row2.setOrientation(LinearLayout.HORIZONTAL);
	        	        
	        row1.addView(dex_text);
	        row1.addView(dex_edit_text);
	        row1.addView(armor_text);
	        row1.addView(armor_b);
	        row1.addView(shield_text);
	        row1.addView(shield_b);
	        row1.addView(natural_text);
	        row1.addView(natural_b);
	        row2.addView(deflection_text);
	        row2.addView(deflection_b);
	        row2.addView(dodge_text);
	        row2.addView(dodge_b);
	        row2.addView(misc_text);
	        row2.addView(misc_b);
	        row2.addView(buff_text);
	        row2.addView(buff_edit_text);
	       
	        
	        view.addView(row1);
	        view.addView(row2);
	        
	        main_view = view;
	        
	        return view;
	    }
	    public void updateButtons(){
	    	int ac_total, ac_ff, ac_touch;
	    	int dex_n = Integer.parseInt(dex);
	    	int armor_n = Integer.parseInt(armor);
	    	int shield_n = Integer.parseInt(shield);
	    	int natural_n = Integer.parseInt(natural);
	    	int deflection_n = Integer.parseInt(deflection);
	    	int dodge_n = Integer.parseInt(dodge);
	    	int misc_n = Integer.parseInt(misc);
	    	int buff_n = Integer.parseInt(buff);
	    	
	    	ac_total = 10 + dex_n + armor_n + shield_n + natural_n + deflection_n + dodge_n + misc_n + buff_n;
	    	ac_ff = 10 + armor_n + shield_n + natural_n + deflection_n + misc_n + buff_n;
	    	ac_touch = 10 + dex_n + deflection_n + dodge_n + misc_n + buff_n;
	    	
	    	((Button)parent.findViewById(R.id.ac_button)).setText("AC: "+ac_total);
	    	((Button)parent.findViewById(R.id.ff_button)).setText("Flat Footed: "+ac_ff);
	    	((Button)parent.findViewById(R.id.touch_button)).setText("Touch: "+ac_touch);
	    }
	    public void onDestroy(){
	    	super.onDestroy();
	    	com.saveData(CharacterDataKey.AC_ARMOR, armor);
	    	com.saveData(CharacterDataKey.AC_SHIELD, shield);
	    	com.saveData(CharacterDataKey.AC_NATURAL, natural);
	    	com.saveData(CharacterDataKey.AC_DEFLECTION, deflection);
	    	com.saveData(CharacterDataKey.AC_DODGE, dodge);
	    	com.saveData(CharacterDataKey.AC_MISC, misc);
	    }    
	}
	
	public class EditFFFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		Button acButton;
		
		String dex, armor, shield, natural, deflection, dodge, buff, misc;

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
	    	getDialog().setTitle("Flat Footed AC");
	    	String dex_buff = loadData(CharacterDataKey.DEX_BUFF);
	    	if(dex_buff == null || dex_buff.isEmpty())
	    		dex_buff = "0";
	    	dex = ((Integer.parseInt(loadData(CharacterDataKey.DEX)) +
	    			Integer.parseInt(dex_buff))/2 - 5) +"";
	    	
	    	armor = loadData(CharacterDataKey.AC_ARMOR);
	    	shield = loadData(CharacterDataKey.AC_SHIELD);
	    	natural = loadData(CharacterDataKey.AC_NATURAL);
	    	deflection = loadData(CharacterDataKey.AC_DEFLECTION);
	    	dodge = loadData(CharacterDataKey.AC_DODGE);
	    	misc = loadData(CharacterDataKey.AC_MISC);
	    	buff = loadData(CharacterDataKey.AC_BUFF);
	    	
	    	
	        //LinearLayout view ;//= inflater.inflate(R.layout.edit_saves_fragment, container, false);
	       //view = new LinearLayout(parent);
	        LinearLayout row1 = new LinearLayout(parent);
	        //LinearLayout row2 = new LinearLayout(parent);
	        
	        EditText armor_b = createDialogEdit(armor);
	        armor_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	armor = s.toString();
			    	if(armor.isEmpty())
			    		armor = "0";
			    	updateButtons();
			    }
			});
	        
	        EditText shield_b =  createDialogEdit(shield);
	        shield_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	shield = s.toString();
			    	if(shield.isEmpty())
			    		shield = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText natural_b =  createDialogEdit(natural);
	        natural_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	natural = s.toString();
			    	if(natural.isEmpty())
			    		natural = "0";
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText deflection_b =  createDialogEdit(deflection);
	        deflection_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	deflection = s.toString();
			    	if(deflection.isEmpty())
			    		deflection = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText misc_b =  createDialogEdit(misc);
	        misc_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	misc = s.toString();
			    	if(misc.isEmpty())
			    		misc = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});	        
	        
	        TextView armor_text = createDialogText("Armor:");
	        TextView shield_text = createDialogText("Shield:");
	        TextView natural_text = createDialogText("Natural:");
	        TextView deflection_text =createDialogText("Deflection:");
	       // TextView dodge_text = createDialogText("Dodge:");
	        TextView misc_text = createDialogText("Misc:");
	       // TextView dex_text =createDialogText("Dex:");
	       // TextView dex_edit_text = createDialogText(dex);
	        TextView buff_text =createDialogText("Buff:");
	        TextView buff_edit_text = createDialogText(buff);
	        
	      //  view.setOrientation(LinearLayout.VERTICAL);
	        	        
	        row1.setOrientation(LinearLayout.HORIZONTAL);
	       // row2.setOrientation(LinearLayout.HORIZONTAL);
	        	        
	       // row1.addView(dex_text);
	       // row1.addView(dex_edit_text);
	        row1.addView(armor_text);
	        row1.addView(armor_b);
	        row1.addView(shield_text);
	        row1.addView(shield_b);
	        row1.addView(natural_text);
	        row1.addView(natural_b);
	        row1.addView(deflection_text);
	        row1.addView(deflection_b);
	      //  row2.addView(dodge_text);
	       // row2.addView(dodge_b);
	        row1.addView(misc_text);
	        row1.addView(misc_b);
	        row1.addView(buff_text);
	        row1.addView(buff_edit_text);
	       
	        
	      //  view.addView(row1);
	       // view.addView(row2);
	        
	        main_view = row1;
	        
	        return row1;
	    }
	    public void updateButtons(){
	    	int ac_total, ac_ff, ac_touch;
	    	int dex_n = Integer.parseInt(dex);
	    	int armor_n = Integer.parseInt(armor);
	    	int shield_n = Integer.parseInt(shield);
	    	int natural_n = Integer.parseInt(natural);
	    	int deflection_n = Integer.parseInt(deflection);
	    	int dodge_n = Integer.parseInt(dodge);
	    	int misc_n = Integer.parseInt(misc);
	    	int buff_n = Integer.parseInt(buff);
	    	
	    	ac_total = 10 + dex_n + armor_n + shield_n + natural_n + deflection_n + dodge_n + misc_n + buff_n;
	    	ac_ff = 10 + armor_n + shield_n + natural_n + deflection_n + misc_n + buff_n;
	    	ac_touch = 10 + dex_n + deflection_n + dodge_n + misc_n + buff_n;
	    	
	    	((Button)parent.findViewById(R.id.ac_button)).setText("AC: "+ac_total);
	    	((Button)parent.findViewById(R.id.ff_button)).setText("Flat Footed: "+ac_ff);
	    	((Button)parent.findViewById(R.id.touch_button)).setText("Touch: "+ac_touch);
	    }
	    public void onDestroy(){
	    	super.onDestroy();
	    	com.saveData(CharacterDataKey.AC_ARMOR, armor);
	    	com.saveData(CharacterDataKey.AC_SHIELD, shield);
	    	com.saveData(CharacterDataKey.AC_NATURAL, natural);
	    	com.saveData(CharacterDataKey.AC_DEFLECTION, deflection);
	    	com.saveData(CharacterDataKey.AC_DODGE, dodge);
	    	com.saveData(CharacterDataKey.AC_MISC, misc);
	    }    
	}
	public class EditTouchFragment extends DialogFragment {
	    FragmentCommunicator com;
		Activity parent;
		View main_view;
		Button acButton;
		
		String dex, armor, shield, natural, deflection, dodge, buff, misc;

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
	    	
	    	getDialog().setTitle("Touch AC");
	    	
	    	String dex_buff = loadData(CharacterDataKey.DEX_BUFF);
	    	if(dex_buff == null || dex_buff.isEmpty())
	    		dex_buff = "0";
	    	dex = ((Integer.parseInt(loadData(CharacterDataKey.DEX)) +
	    			Integer.parseInt(dex_buff))/2 - 5) +"";
	    	
	    	armor = loadData(CharacterDataKey.AC_ARMOR);
	    	shield = loadData(CharacterDataKey.AC_SHIELD);
	    	natural = loadData(CharacterDataKey.AC_NATURAL);
	    	deflection = loadData(CharacterDataKey.AC_DEFLECTION);
	    	dodge = loadData(CharacterDataKey.AC_DODGE);
	    	misc = loadData(CharacterDataKey.AC_MISC);
	    	buff = loadData(CharacterDataKey.AC_BUFF);
	    	
	    	
	        //LinearLayout view ;//= inflater.inflate(R.layout.edit_saves_fragment, container, false);
	       //view = new LinearLayout(parent);
	        LinearLayout row1 = new LinearLayout(parent);
	        //LinearLayout row2 = new LinearLayout(parent);
	        
	        
	        
	        EditText deflection_b =  createDialogEdit(deflection);
	        deflection_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	deflection = s.toString();
			    	if(deflection.isEmpty())
			    		deflection = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText dodge_b =  createDialogEdit(dodge);
	        dodge_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	dodge = s.toString();
			    	if(dodge.isEmpty())
			    		dodge = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});
	        
	        EditText misc_b =  createDialogEdit(misc);
	        misc_b.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {			    			    	
			    	misc = s.toString();
			    	if(misc.isEmpty())
			    		misc = "0";
			    	updateButtons();
			    	//buffButton.setText(buff.getDescription());
			    }
			});	        
	        
	       // TextView armor_text = createDialogText("Armor:");
	       // TextView shield_text = createDialogText("Shield:");
	       // TextView natural_text = createDialogText("Natural:");
	        TextView deflection_text =createDialogText("Deflection:");
	        TextView dodge_text = createDialogText("Dodge:");
	        TextView misc_text = createDialogText("Misc:");
	        TextView dex_text =createDialogText("Dex:");
	        TextView dex_edit_text = createDialogText(dex);
	        TextView buff_text =createDialogText("Buff:");
	        TextView buff_edit_text = createDialogText(buff);
	        
	     //   view.setOrientation(LinearLayout.VERTICAL);
	        	        
	        row1.setOrientation(LinearLayout.HORIZONTAL);
	       // row2.setOrientation(LinearLayout.HORIZONTAL);
	        	        
	        row1.addView(dex_text);
	        row1.addView(dex_edit_text);
	     
	        row1.addView(deflection_text);
	        row1.addView(deflection_b);
	        row1.addView(dodge_text);
	        row1.addView(dodge_b);
	        row1.addView(misc_text);
	        row1.addView(misc_b);
	        row1.addView(buff_text);
	        row1.addView(buff_edit_text);
	       
	        
	       // view.addView(row1);
	       // view.addView(row2);
	        
	        main_view = row1;
	        
	        return row1;
	    }
	    public void updateButtons(){
	    	int ac_total, ac_ff, ac_touch;
	    	int dex_n = Integer.parseInt(dex);
	    	int armor_n = Integer.parseInt(armor);
	    	int shield_n = Integer.parseInt(shield);
	    	int natural_n = Integer.parseInt(natural);
	    	int deflection_n = Integer.parseInt(deflection);
	    	int dodge_n = Integer.parseInt(dodge);
	    	int misc_n = Integer.parseInt(misc);
	    	int buff_n = Integer.parseInt(buff);
	    	
	    	ac_total = 10 + dex_n + armor_n + shield_n + natural_n + deflection_n + dodge_n + misc_n + buff_n;
	    	ac_ff = 10 + armor_n + shield_n + natural_n + deflection_n + misc_n + buff_n;
	    	ac_touch = 10 + dex_n + deflection_n + dodge_n + misc_n + buff_n;
	    	
	    	((Button)parent.findViewById(R.id.ac_button)).setText("AC: "+ac_total);
	    	((Button)parent.findViewById(R.id.ff_button)).setText("Flat Footed: "+ac_ff);
	    	((Button)parent.findViewById(R.id.touch_button)).setText("Touch: "+ac_touch);
	    }
	    public void onDestroy(){
	    	super.onDestroy();
	    	com.saveData(CharacterDataKey.AC_ARMOR, armor);
	    	com.saveData(CharacterDataKey.AC_SHIELD, shield);
	    	com.saveData(CharacterDataKey.AC_NATURAL, natural);
	    	com.saveData(CharacterDataKey.AC_DEFLECTION, deflection);
	    	com.saveData(CharacterDataKey.AC_DODGE, dodge);
	    	com.saveData(CharacterDataKey.AC_MISC, misc);
	    }    
	}
}
