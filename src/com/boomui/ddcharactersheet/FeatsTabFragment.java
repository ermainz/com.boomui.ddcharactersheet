package com.boomui.ddcharactersheet;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FeatsTabFragment extends Fragment{
	
	public static String FEAT_SPLIT = "!###@!";
	
	
	FragmentCommunicator com;
	Activity parent;
	View main_view;
	ArrayList<Attack> attacks;
	FeatAdapter adapter;
	
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		parent = activity;
		com = (FragmentCommunicator)activity;
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	public void onDestroyView(){
		super.onDestroyView();
		adapter.save();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		LinearLayout view = new LinearLayout(parent);
		view.setOrientation(LinearLayout.VERTICAL);
		
		ListView list = new ListView(parent);
		
		ArrayList<String> feats = new ArrayList<String>();
		
		
		String feats_data = com.loadData(CharacterDataKey.FEATS);
		if(feats_data != null){
			String[] feats_split = feats_data.split(FEAT_SPLIT);
			for(String feat: feats_split)
				feats.add(feat);
		} else {
			com.saveData(CharacterDataKey.FEATS, "");
		}
			
		adapter = new FeatAdapter(parent, R.layout.feat_item, feats, com);
		list.setAdapter(adapter);
	
		Button addFeat = new Button(parent);
		addFeat.setText("Add Feat");
		addFeat.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				adapter.getFeats().add("");
				adapter.notifyDataSetChanged();
			}
		});
		
		view.addView(list);
		view.addView(addFeat);
		
		return view;
		//return inflater.inflate(R.layout.feats_tab_fragment_layout, container, false);
	}
		
	
	class FeatAdapter extends ArrayAdapter<String>{

	    private ArrayList<String> feats;
	    private Context parent;
	    private FragmentCommunicator com;
	    
	    public ArrayList<String> getFeats(){
	    	return feats;
	    }
	    	    
	    public FeatAdapter(Context context, int textViewResourceId,
	            ArrayList<String> objects, FragmentCommunicator com) {
	        super(context, textViewResourceId, objects);
	        this.feats = objects;
	        parent = context;
	        this.com = com;
	    }
	    
	    public LinearLayout createRow(){
	    	LinearLayout row = new LinearLayout(parent);
	        row.setOrientation(LinearLayout.HORIZONTAL);
	        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
	        return row;
	    }
	    
	    public EditText createEdit(String value){
	    	EditText text = new EditText(parent);
	    	text.setText(value);
	    	text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
	    	return text;
	    }
	    
	    public View getView(int position, View convertView, ViewGroup parentGroup){
	        View v;
	        LayoutInflater vi = (LayoutInflater)parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        v = vi.inflate(R.layout.feat_item, null);
	            
	        final int gPos = position;
	        String feat = feats.get(position);
	        
	        EditText name = (EditText)v.findViewById(R.id.feat_name);
	        name.setText(feat);
	        name.addTextChangedListener(new TextWatcher() {
			    public void onTextChanged(CharSequence s, int start, int before, int count) {}
			    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			    public void afterTextChanged(Editable s) {
			    	feats.set(gPos, s.toString());
			    }
			});
	        
	        Button remove = (Button)v.findViewById(R.id.feat_remove);
	        remove.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					feats.remove(gPos);
					notifyDataSetChanged();
				}
	        });
	        
	        return v;           
	    }   
	    public void save(){
	    	String data = "";
	    	for (String feat : feats)
	    		data += feat + FEAT_SPLIT;
	    	com.saveData(CharacterDataKey.FEATS, data);
	    }
	}	
}
