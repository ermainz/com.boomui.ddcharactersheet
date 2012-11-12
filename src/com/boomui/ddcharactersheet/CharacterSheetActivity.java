package com.boomui.ddcharactersheet;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class CharacterSheetActivity extends Activity implements ActionBar.TabListener {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar bar = getActionBar();
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
           
        bar.addTab(bar.newTab().setText("DiceRoller").setTabListener(this));
        bar.addTab(bar.newTab().setText("SpellLookup").setTabListener(this));
        bar.addTab(bar.newTab().setText("Info").setTabListener(this));
        bar.addTab(bar.newTab().setText("Combat").setTabListener(this));
        bar.addTab(bar.newTab().setText("Buffs").setTabListener(this));
        bar.addTab(bar.newTab().setText("Skills").setTabListener(this));
        bar.addTab(bar.newTab().setText("Magic").setTabListener(this));
        bar.addTab(bar.newTab().setText("Feats").setTabListener(this));
        bar.addTab(bar.newTab().setText("Inventory").setTabListener(this));
        bar.addTab(bar.newTab().setText("Notes").setTabListener(this));
        
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
