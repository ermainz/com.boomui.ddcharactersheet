package com.boomui.ddcharactersheet;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

public class SpellDataDisplayFragment extends Fragment{
	FragmentCommunicator com;
	Activity parent;
	SpellLoader loader;
	String spellName;
	
	final Fragment returnTo;
	
	public SpellDataDisplayFragment(String spellName, Fragment returnTo){
		this.spellName = spellName;
		this.returnTo = returnTo;
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		com = (FragmentCommunicator)activity;
		loader = (SpellLoader)activity;
		parent = activity;

		//This code hides the keyboard
		InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow((null == activity.getCurrentFocus()) ? null : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		String dataToDisplay = loader.loadSpellData(spellName);
		
		LinearLayout layout = new LinearLayout(parent);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		ListView spellHolder = new ListView(parent);
		spellHolder.setDivider(null);
		
		TextView name = new TextView(parent);
		name.setText(spellName);
		name.setTextSize(30);
		
		TextView text = new TextView(parent);
		text.setText(dataToDisplay);

		spellHolder.setAdapter(new SpellDataListAdapter(parent, name, text) );
		spellHolder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1) );
		
		Button close = new Button(parent);
		close.setText("Back");
		close.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0) );
		
		final Activity listenerActivity = parent;
		close.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				FragmentManager fragmentManager = listenerActivity.getFragmentManager();
				Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentView);
				
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.remove(currentFragment);
				fragmentTransaction.commit();

				fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.fragmentView, returnTo);
				fragmentTransaction.commit();
			}
		});
		
		layout.addView(spellHolder);
		layout.addView(close);
		
		return layout;
	}
	
	class SpellDataListAdapter extends BaseAdapter{
		Activity parent;
		
		TextView[] parts;
		
		public SpellDataListAdapter(Activity parent, TextView name, TextView body){
			this.parent = parent;
			
			parts = new TextView[2];
			parts[0] = name;
			parts[1] = body;

			parts[0].setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT, 0) );
			parts[1].setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT, 1) );
		}

		public int getCount(){
			return parts.length;
		}

		public Object getItem(int index){
			return parts[index];
		}

		public long getItemId(int arg0){
			return 0;
		}

		public View getView(int index, View v1, ViewGroup v2){
			return parts[index];
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

}