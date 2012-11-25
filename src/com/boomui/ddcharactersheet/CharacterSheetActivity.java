package com.boomui.ddcharactersheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.boomui.ddcharactersheet.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
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
}
