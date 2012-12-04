package com.boomui.ddcharactersheet;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class InventoryTabFragment extends Fragment{
	FragmentCommunicator com;
	View mainView;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		com = (FragmentCommunicator) activity;
		
		//This code hides the keyboard
		InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mainView = inflater.inflate(R.layout.inventory_tab_fragment_layout, container, false);
		
		String txt = com.loadData(CharacterDataKey.INVENTORY);
		if (txt == null) {
			txt = "";
		}
		EditText txtBox = (EditText) mainView.findViewById( R.id.inventory );
		txtBox.setText(txt);
		
		return mainView;
	}
	
	public void onPause() {
		super.onPause();
		
		EditText txtBox = (EditText) mainView.findViewById( R.id.inventory );
		com.saveData(CharacterDataKey.INVENTORY, txtBox.getText().toString());
	}
}
