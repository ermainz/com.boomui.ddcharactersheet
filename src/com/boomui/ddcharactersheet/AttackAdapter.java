package com.boomui.ddcharactersheet;

import java.util.ArrayList;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AttackAdapter extends ArrayAdapter<Attack>{

	public static String ATTACK_SPLIT = "!###@!";
	public static String ATTACK_VALUE_SPLIT = "!##@#!";
	private CombatTabRollListener roll;
    private ArrayList<Attack> attacks;
    private Context parent;
    private FragmentCommunicator com;
    
    
    public ArrayList<Attack> getAttacks(){
    	return attacks;
    }
    
    
    public AttackAdapter(Context context, int textViewResourceId,
            ArrayList<Attack> objects, FragmentCommunicator com, CombatTabRollListener rollListener) {
        super(context, textViewResourceId, objects);
        this.attacks = objects;
        parent = context;
        this.com = com;
        this.roll = rollListener;
    }
    
    public LinearLayout createRow(){
    	LinearLayout row = new LinearLayout(parent);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0) );
        return row;
    }
    
    public TextView createLabel(String label){
    	TextView text = new TextView(parent);
    	text.setText(label);
    	text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
    	return text;
    }
    
    public EditText createEdit(String value){
    	EditText text = new EditText(parent);
    	text.setText(value);
    	text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1) );
    	return text;
    }
    
    public View getView(int position, View convertView, ViewGroup parentGroup){
       // View v = convertView;
        //LinearLayout v = new LinearLayout(parent);
        //v.setOrientation(LinearLayout.VERTICAL);
        View v;
        //View v = convertView;

       // if(v == null){
            LayoutInflater vi = (LayoutInflater)parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            v = vi.inflate(R.layout.attack_item, null);
        //}
            //LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            //v = vi.inflate(R.layout.list_item, null);
        
        final Attack attack = attacks.get(position);
        
        EditText name = (EditText)v.findViewById(R.id.name);
        name.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.name = s.toString();
		    }
		});
        
        EditText attack_field = (EditText)v.findViewById(R.id.attack);
        attack_field.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.attack = s.toString();
		    }
		});
        
        EditText damage = (EditText)v.findViewById(R.id.damage);
        damage.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.damage = s.toString();
		    }
		});
        
        EditText type = (EditText)v.findViewById(R.id.type);
        type.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.type = s.toString();
		    }
		});
        
        EditText range = (EditText)v.findViewById(R.id.range);
        range.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.range = s.toString();
		    }
		});
        
        EditText crit = (EditText)v.findViewById(R.id.crit);
        crit.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.crit = s.toString();
		    }
		});
        
        EditText notes = (EditText)v.findViewById(R.id.notes);
        notes.addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	attack.notes = s.toString();
		    }
		});
        
        name.setText(attack.name);
        attack_field.setText(attack.attack);
        damage.setText(attack.damage);
        type.setText(attack.type);
        range.setText(attack.range);
        crit.setText(attack.crit);
        notes.setText(attack.notes);
        
        ImageButton roll_attack = (ImageButton)v.findViewById(R.id.attackRoll);
        ImageButton roll_damage = (ImageButton)v.findViewById(R.id.damageRoll);
        
        roll_attack.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				roll.roll(attack.rollAttack());
			}
		});
        roll_damage.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				roll.roll(attack.rollDamage());
			}
		});
        
        Button remove_attack = (Button)v.findViewById(R.id.remove_attack);
        remove_attack.setOnLongClickListener(new OnLongClickListener(){
			public boolean onLongClick(View arg0) {
				attacks.remove(attack);
				notifyDataSetChanged();
				return true;
			}        	
        });
        /*
        LinearLayout labels1 = createRow();
        LinearLayout fields1 = createRow();
        LinearLayout labels2 = createRow();
        LinearLayout fields2 = createRow();
        //LinearLayout notes = createRow();
        
        TextView name_label = createLabel("Name");
        TextView attack_label = createLabel("Attack");
        TextView damage_label = createLabel("Damage");
        TextView range_label = createLabel("Range");
        TextView crit_label = createLabel("Crit");
        TextView type_label = createLabel("Type");
        
        labels1.addView(name_label);
        labels1.addView(attack_label);
        labels1.addView(damage_label);
        
        labels2.addView(range_label);
        labels2.addView(crit_label);
        labels2.addView(type_label);
        
        EditText name_edit = createEdit(attack.name);
        EditText attack_edit = createEdit(attack.attack);
        EditText damage_edit = createEdit(attack.damage);
        	damage_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText range_edit = createEdit(attack.range);
        EditText crit_edit = createEdit(attack.crit);
        EditText type_edit = createEdit(attack.type);
        EditText notes_edit = createEdit(attack.notes);
             
        /*
        Item item = (Item) items.get(position);

        if (item != null){
            TextView resName = (TextView) v.findViewById(R.id.result_item_name_object);
            TextView resDistance = (TextView) v.findViewById(R.id.result_item_distance);  

            if (resName != null){
                resName.setText(item.name);
            }

            if (resDistance != null){
                resDistance.setText(item.distance);
            }           
        }  */     
        return v;           
    }   
    public void save(){
    	String data = "";
    	for (Attack attack : attacks)
    		data += attack.save() + ATTACK_SPLIT;
    	com.saveData(CharacterDataKey.ATTACKS, data);
    }
}
