package com.boomui.ddcharactersheet;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

class SpellsKnownExpandableListAdapter extends BaseExpandableListAdapter{
	private String[] groups = {"Level 0", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"};
	private SpellsKnown children;
	Activity parent;
	final SpellInteractionListener listener;
	final boolean canRemove;
	
	public SpellsKnownExpandableListAdapter(SpellsKnown children, Activity parent, SpellInteractionListener listener){
		this(children, parent, listener, false);
	}
	
	public SpellsKnownExpandableListAdapter(SpellsKnown children, Activity parent, SpellInteractionListener listener, boolean canRemove){
		this.children = children;
		this.parent = parent;
		this.listener = listener;
		this.canRemove = canRemove;
	}
	
	public String getChild(int groupPosition, int childPosition){
		return children.get(groupPosition, childPosition);
	}
	
	public long getChildId(int groupPosition, int childPosition){
		return childPosition;
	}
	
	public int getChildrenCount(int groupPosition){
		return children.getNumSpells(groupPosition);
	}
	
	public TextView getGenericView(){
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 48);
		
		TextView textView = new TextView(parent);
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(50, 0, 0, 0);
		return textView;
	}
	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentView){
		LinearLayout lView = new LinearLayout(parent);

		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition));
		textView.setPadding(64, 0, 0, 0);
		
		Button delete = new Button(parent);
		delete.setText("X");
		delete.setLayoutParams(new LinearLayout.LayoutParams(35, 35, 0) );
		delete.setPadding(0, 0, 0, 0);

		
		// textView.setLayoutParams(new
		// LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		
		final int gPos = groupPosition;
		final int cPos = childPosition;
		final Activity listenerActivity = parent;
		textView.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.spellClicked(getChild(gPos, cPos), gPos);
			}
		});
		textView.setOnLongClickListener(new OnLongClickListener(){
			public boolean onLongClick(View v){
				FragmentManager fragmentManager = listenerActivity.getFragmentManager();
				Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentView);
				
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.remove(currentFragment);
				fragmentTransaction.commit();

				fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.fragmentView, new SpellDataDisplayFragment(getChild(gPos, cPos), currentFragment) );
				fragmentTransaction.commit();
				
				return true;
			}
		});

		final SpellsKnownExpandableListAdapter skela = this;
		delete.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				// TODO Auto-generated method stub
				children.removeSpell(children.get(gPos, cPos), gPos);
				skela.notifyDataSetChanged();
			}
		});
		delete.setTextColor(Color.RED);

		if(canRemove){
			textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );

			lView.addView(textView);
			lView.addView(delete);
			lView.setOrientation(LinearLayout.HORIZONTAL);
		
			return lView;
		}
		else{
			return textView;
		}
	}
	
	public Object getGroup(int groupPosition){
		return groups[groupPosition];
	}
	
	public int getGroupCount(){
		return groups.length;
	}
	
	public long getGroupId(int groupPosition){
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition).toString());
		textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64));
		
		if(getChildrenCount(groupPosition) == 0){
			textView.setTextColor(Constants.DISABLED);
		}
		
		return textView;
	}
	
	public boolean isChildSelectable(int groupPosition, int childPosition){
		return true;
	}
	
	public boolean hasStableIds(){
		return true;
	}
}
