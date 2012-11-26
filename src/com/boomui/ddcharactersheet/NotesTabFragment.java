package com.boomui.ddcharactersheet;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.widget.TextView;
import android.widget.EditText;

public class NotesTabFragment extends Fragment{
	FragmentCommunicator com;
	
	View main_view;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		com = (FragmentCommunicator)activity;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		main_view = inflater.inflate(R.layout.notes_tab_fragment_layout, container, false);
		String notes = com.loadData(CharacterDataKey.NOTES);
		if(notes == null){
			notes = "";
		}
		((EditText)main_view.findViewById(R.id.notes)).setText(notes);
		return main_view;
	}
	
	@Override
	public void onPause(){
		super.onPause();
		com.saveData(CharacterDataKey.NOTES, ((EditText)main_view.findViewById(R.id.notes)).getText().toString());
	}
	
}
