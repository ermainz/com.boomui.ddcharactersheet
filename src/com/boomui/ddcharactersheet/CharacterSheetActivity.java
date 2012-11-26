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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;


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
			System.err.println("Couldn't load data: " + tag.toString() );
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
		DialogFragment newFragment = new EditFortitudeFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editWill(View view){
		DialogFragment newFragment = new EditFortitudeFragment();
	    newFragment.show(getFragmentManager(), "EditAbilityScore");
	}
	public void editAC(View view){
		
	}
	public void editFF(View view){
		
	}
	public void editTouch(View view){
		
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
	        if(con_buff == null){
	        	con_buff = "0"; com.saveData(CharacterDataKey.CON_BUFF, "0");}
	        int modifier = (Integer.parseInt(con) + Integer.parseInt(con_buff))/2 -5;
	        ((TextView)view.findViewById(R.id.save_ability_score)).setText(""+modifier);
	        
	        String fort_buff = com.loadData(CharacterDataKey.FORT_BUFF);
	        if(fort_buff == null){
	        	fort_buff = "0"; com.saveData(CharacterDataKey.FORT_BUFF, "0");}
	        ((TextView)view.findViewById(R.id.save_buff)).setText(fort_buff);
	        
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
			    	
			    	((Button)parent.findViewById(R.id.fort_button)).setText("Fortitude +"+fort_save);
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
			    	
			    	((Button)parent.findViewById(R.id.fort_button)).setText("Fortitude +"+fort_save);
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
	        if(dex_buff == null){
	        	dex_buff = "0"; com.saveData(CharacterDataKey.DEX_BUFF, "0");}
	        int modifier = (Integer.parseInt(dex) + Integer.parseInt(dex_buff))/2 -5;
	        ((TextView)view.findViewById(R.id.save_ability_score)).setText(""+modifier);
	        
	        String ref_buff = com.loadData(CharacterDataKey.REF_BUFF);
	        if(ref_buff == null){
	        	ref_buff = "0"; com.saveData(CharacterDataKey.REF_BUFF, "0");}
	        ((TextView)view.findViewById(R.id.save_buff)).setText(ref_buff);
	        
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
			    	int fort_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.REF_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.REF_CLASS));
			    	
			    	((Button)parent.findViewById(R.id.ref_button)).setText("Reflex +"+fort_save);
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
			    	
			    	((Button)parent.findViewById(R.id.ref_button)).setText("Reflex +"+ref_save);
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
	        if(wis_buff == null){
	        	wis_buff = "0"; com.saveData(CharacterDataKey.WIS_BUFF, "0");}
	        int modifier = (Integer.parseInt(wis) + Integer.parseInt(wis_buff))/2 -5;
	        ((TextView)view.findViewById(R.id.save_ability_score)).setText(""+modifier);
	        
	        String will_buff = com.loadData(CharacterDataKey.WILL_BUFF);
	        if(will_buff == null){
	        	will_buff = "0"; com.saveData(CharacterDataKey.WILL_BUFF, "0");}
	        ((TextView)view.findViewById(R.id.save_buff)).setText(will_buff);
	        
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
			    	int fort_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL_CLASS));
			    	
			    	((Button)parent.findViewById(R.id.will_button)).setText("Will +"+fort_save);
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
			    	int fort_save = Integer.parseInt(s.toString()) + 
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL_BUFF)) +
			    			Integer.parseInt(((TextView)main_view.findViewById(R.id.save_ability_score)).getText().toString()) +
			    			Integer.parseInt(com.loadData(CharacterDataKey.WILL));
			    	
			    	((Button)parent.findViewById(R.id.will_button)).setText("Will +"+fort_save);
			    }
			});
	        return view;
	    }
	}
}
