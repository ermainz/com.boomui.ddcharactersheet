package com.boomui.ddcharactersheet;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoTabFragment extends Fragment{
	FragmentCommunicator com;
	View mainView;
	Map<CharacterDataKey, Integer> editableFields;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		com = (FragmentCommunicator) activity;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mainView = inflater.inflate(R.layout.info_tab_fragment_layout, container, false);
		
		editableFields = new HashMap<CharacterDataKey, Integer>();
		editableFields.put(CharacterDataKey.NAME, R.id.character_name);
		editableFields.put(CharacterDataKey.INFO_CLASS_AND_LEVEL, R.id.class_and_level);
		editableFields.put(CharacterDataKey.INFO_EXPERIENCE, R.id.experience);
		editableFields.put(CharacterDataKey.INFO_RACE, R.id.race);
		editableFields.put(CharacterDataKey.INFO_PATRON_DEITY, R.id.patron_deity);
		editableFields.put(CharacterDataKey.INFO_ALIGNMENT, R.id.alignment);
		editableFields.put(CharacterDataKey.INFO_SIZE, R.id.sizeof_char);
		editableFields.put(CharacterDataKey.INFO_GENDER, R.id.gender);
		editableFields.put(CharacterDataKey.INFO_AGE, R.id.age);
		editableFields.put(CharacterDataKey.INFO_CHAR_DESCR, R.id.character_description);
		
		for (CharacterDataKey key : editableFields.keySet()) {
			EditText txtBox = (EditText) mainView.findViewById( editableFields.get(key) );
			String txtVal = com.loadData(key);
			if (txtVal == null) {
				txtVal = "";
			}
			txtBox.setText(txtVal);
		}
		
		return mainView;
	}
	
	public void onPause() {
		super.onPause();
		for (CharacterDataKey key : editableFields.keySet()) {
			EditText txtBox = (EditText) mainView.findViewById( editableFields.get(key) );
			com.saveData( key, txtBox.getText().toString() );
		}
	}
}