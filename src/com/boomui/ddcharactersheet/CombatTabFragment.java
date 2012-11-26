package com.boomui.ddcharactersheet;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CombatTabFragment extends Fragment{
	
	FragmentCommunicator com;
	Activity parent;
	View main_view;
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		parent = activity;
		com = (FragmentCommunicator)activity;
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		
		try {
	         //number input
	       //final EditText number_edit_text = (EditText) this.findViewById(R.id.str_input);
	           
	       }catch(Exception e){
	            Log.e("Android Edit Text Example", e.toString());
	       }
		
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View view = inflater.inflate(R.layout.combat_tab_fragment_layout, container, false);
		main_view = view;
		
		String hp = com.loadData(CharacterDataKey.HP);
		if(hp == null){
			hp = "0";
			com.saveData(CharacterDataKey.HP, hp);
		} 
		EditText hp_text = (EditText)view.findViewById(R.id.max_hp);
		
		hp_text.setText(hp);
		
		String temp_hp = com.loadData(CharacterDataKey.TEMP_HP);
		if(temp_hp == null){
			temp_hp = "0";
			com.saveData(CharacterDataKey.TEMP_HP, temp_hp);
		} 
		((EditText)view.findViewById(R.id.current_hp)).setText(temp_hp);
		
		String non_lethal_hp = com.loadData(CharacterDataKey.NONLETHAL_HP);
		if(non_lethal_hp == null){
			non_lethal_hp = "0";
			com.saveData(CharacterDataKey.NONLETHAL_HP, non_lethal_hp);
		} 
		((EditText)view.findViewById(R.id.non_lethal_hp)).setText(non_lethal_hp);
		
		int str_total, dex_total, con_total, int_total, wis_total, cha_total;
		
		
		String str = com.loadData(CharacterDataKey.STR);
		if(str == null){
			str = "0";
			com.saveData(CharacterDataKey.STR, str);} 
		((EditText)view.findViewById(R.id.str_base)).setText(str);
		String str_buff = com.loadData(CharacterDataKey.STR_BUFF);
		if(str_buff == null){
			((TextView)view.findViewById(R.id.str_value)).setText("STR: "+str+" = ");	
			str_total = Integer.parseInt(str);
		} else {
			str_total = Integer.parseInt(str) + Integer.parseInt(str_buff);
			((TextView)view.findViewById(R.id.str_value)).setText("STR: "+str_total+" = ");
			((TextView)view.findViewById(R.id.str_buff)).setText(str_buff);
		}
		String dex = com.loadData(CharacterDataKey.DEX);
		if(dex == null){
			dex = "0";
			com.saveData(CharacterDataKey.DEX, dex);} 
		((EditText)view.findViewById(R.id.dex_base)).setText(dex);
		String dex_buff = com.loadData(CharacterDataKey.DEX_BUFF);
		if(dex_buff == null){
			((TextView)view.findViewById(R.id.dex_value)).setText("DEX: "+dex+" = ");
			dex_total = Integer.parseInt(dex);
		} else {
			dex_total = Integer.parseInt(dex) + Integer.parseInt(dex_buff);
			((TextView)view.findViewById(R.id.dex_value)).setText("DEX: "+dex_total+" = ");
			((TextView)view.findViewById(R.id.dex_buff)).setText(dex_buff);
		}
		String con = com.loadData(CharacterDataKey.CON);
		if(con == null){
			con = "0";
			com.saveData(CharacterDataKey.CON, con);} 
		((EditText)view.findViewById(R.id.con_base)).setText(con);
		String con_buff = com.loadData(CharacterDataKey.CON_BUFF);
		if(con_buff == null){
			((TextView)view.findViewById(R.id.con_value)).setText("CON: "+con+" = ");
			con_total = Integer.parseInt(con);
		} else {
			con_total = Integer.parseInt(con) + Integer.parseInt(con_buff);
			((TextView)view.findViewById(R.id.con_value)).setText("CON: "+con_total+" = ");
			((TextView)view.findViewById(R.id.con_buff)).setText(str_buff);
		}
		String intelligence = com.loadData(CharacterDataKey.INT);
		if(intelligence == null){
			intelligence = "0";
			com.saveData(CharacterDataKey.INT, intelligence);} 
		((EditText)view.findViewById(R.id.int_base)).setText(intelligence);
		String int_buff = com.loadData(CharacterDataKey.INT_BUFF);
		if(int_buff == null){
			((TextView)view.findViewById(R.id.int_value)).setText("INT: "+intelligence+" = ");
			int_total = Integer.parseInt(intelligence);
		} else {
			int_total = Integer.parseInt(intelligence) + Integer.parseInt(int_buff);
			((TextView)view.findViewById(R.id.int_value)).setText("INT: "+int_total+" = ");
			((TextView)view.findViewById(R.id.int_buff)).setText(int_buff);
		}
		String wis = com.loadData(CharacterDataKey.WIS);
		if(wis == null){
			wis = "0";
			com.saveData(CharacterDataKey.WIS, wis);} 
		((EditText)view.findViewById(R.id.wis_base)).setText(wis);
		
		com.saveData(CharacterDataKey.WIS_BUFF, "2");
		
		String wis_buff = com.loadData(CharacterDataKey.WIS_BUFF);
		if(wis_buff == null){
			((TextView)view.findViewById(R.id.wis_value)).setText("WIS: "+wis+" = ");
			wis_total = Integer.parseInt(wis);
		} else {
			wis_total = Integer.parseInt(wis) + Integer.parseInt(wis_buff);
			((TextView)view.findViewById(R.id.wis_value)).setText("WIS: "+wis_total+" = ");
			((TextView)view.findViewById(R.id.wis_buff)).setText(" + "+wis_buff+ " ");
		}
		String cha = com.loadData(CharacterDataKey.CHA);
		if(cha == null){
			cha = "0";
			com.saveData(CharacterDataKey.CHA, cha);} 
		((EditText)view.findViewById(R.id.cha_base)).setText(cha);
		String cha_buff = com.loadData(CharacterDataKey.CHA_BUFF);
		if(cha_buff == null){
			((TextView)view.findViewById(R.id.cha_value)).setText("CHA: "+cha+" = ");
			cha_total = Integer.parseInt(cha);
		} else {
			cha_total = Integer.parseInt(cha) + Integer.parseInt(cha_buff);
			((TextView)view.findViewById(R.id.cha_value)).setText("CHA: "+cha_total+" = ");
			((TextView)view.findViewById(R.id.cha_buff)).setText(cha_buff);
		}
		
		
		int str_mod, dex_mod, con_mod, int_mod, wis_mod, cha_mod;
		str_mod = str_total / 2 - 5;
		dex_mod = dex_total / 2 - 5;
		con_mod = con_total / 2 - 5;
		int_mod = int_total / 2 - 5;
		wis_mod = wis_total / 2 - 5;
		cha_mod = cha_total / 2 - 5;
		
		((TextView)view.findViewById(R.id.str_modifier)).setText(""+str_mod);
		((TextView)view.findViewById(R.id.dex_modifier)).setText(""+dex_mod);
		((TextView)view.findViewById(R.id.con_modifier)).setText(""+con_mod);
		((TextView)view.findViewById(R.id.int_modifier)).setText(""+int_mod);
		((TextView)view.findViewById(R.id.wis_modifier)).setText(""+wis_mod);
		((TextView)view.findViewById(R.id.cha_modifier)).setText(""+cha_mod);
		
		
		int armor, shield, deflection, natural, dodge, misc;
		String ac_armor = com.loadData(CharacterDataKey.AC_ARMOR);
		if(ac_armor == null){ac_armor = "0"; com.saveData(CharacterDataKey.AC_ARMOR, "0"); armor = 0;}
		else armor = Integer.parseInt(ac_armor);
		String ac_shield = com.loadData(CharacterDataKey.AC_SHIELD);
		if(ac_shield == null){ac_shield = "0"; com.saveData(CharacterDataKey.AC_SHIELD, "0"); shield = 0;}
		else shield = Integer.parseInt(ac_shield);
		String ac_deflection = com.loadData(CharacterDataKey.AC_DEFLECTION);
		if(ac_deflection == null){ac_deflection = "0"; com.saveData(CharacterDataKey.AC_DEFLECTION, "0"); deflection = 0;}
		else deflection = Integer.parseInt(ac_deflection);
		String ac_natural = com.loadData(CharacterDataKey.AC_NATURAL);
		if(ac_natural == null){ac_natural = "0"; com.saveData(CharacterDataKey.AC_NATURAL, "0"); natural = 0;}
		else natural = Integer.parseInt(ac_natural);
		String ac_dodge = com.loadData(CharacterDataKey.AC_DODGE);
		if(ac_dodge == null){ac_dodge = "0"; com.saveData(CharacterDataKey.AC_DODGE, "0"); dodge = 0;}
		else dodge = Integer.parseInt(ac_natural);
		String ac_misc = com.loadData(CharacterDataKey.AC_MISC);
		if(ac_misc == null){ac_misc = "0"; com.saveData(CharacterDataKey.AC_MISC, "0"); misc = 0;}
		else misc = Integer.parseInt(ac_misc);
		
		//int ac_dex = Integer.parseInt();
		int ac_total = 10 + dex_mod+ armor + shield + deflection + natural + dodge + misc ;
		int ac_ff = 10 + armor + shield + deflection + natural + misc ;
		int ac_touch = 10 +dex_mod+ deflection + dodge;
		
		((Button)view.findViewById(R.id.ac_button)).setText("AC: "+ac_total);
		((Button)view.findViewById(R.id.ff_button)).setText("Flat Footed: "+ac_ff);
		((Button)view.findViewById(R.id.touch_button)).setText("Touch: "+ac_touch);
		
		
		int initiative_total = 0;
		String initiative_misc = com.loadData(CharacterDataKey.INITIATIVE);
		if(initiative_misc == null || initiative_misc.equals("")){
			initiative_misc = "0"; com.saveData(CharacterDataKey.INITIATIVE, "0");}
		else initiative_total += Integer.parseInt(initiative_misc);
		((EditText)view.findViewById(R.id.initiative_misc)).setText(initiative_misc);
		
		String initiative_buff = com.loadData(CharacterDataKey.INITIATIVE_BUFF);
		if(initiative_buff == null){
			initiative_buff = "0"; com.saveData(CharacterDataKey.INITIATIVE_BUFF, "0");}
		else { 
			initiative_total += Integer.parseInt(initiative_buff);
			((TextView)view.findViewById(R.id.initiative_buff)).setText(" + "+initiative_buff);
		}
		
		initiative_total += dex_mod;
		
		((TextView)view.findViewById(R.id.initiative_text)).setText("Initiative: "+initiative_total+" = ");
		
		
		String bab = com.loadData(CharacterDataKey.BAB);
		if(bab == null){
			bab = "+0"; com.saveData(CharacterDataKey.BAB, "+0");}
		((EditText)view.findViewById(R.id.bab_edit)).setText(bab);
		
		
		int fortitude_total = 0;
		String fortitude = com.loadData(CharacterDataKey.FORT);
		if (fortitude == null){
			fortitude = "0"; com.saveData(CharacterDataKey.FORT, "0");}
		else fortitude_total += Integer.parseInt(fortitude);
		String fortitude_class = com.loadData(CharacterDataKey.FORT_CLASS);
		if (fortitude_class == null){
			fortitude = "0"; com.saveData(CharacterDataKey.FORT_CLASS, "0");}
		else fortitude_total += Integer.parseInt(fortitude_class);
		String fortitude_buff = com.loadData(CharacterDataKey.FORT_BUFF);
		if (fortitude_buff == null){
			fortitude = "0"; com.saveData(CharacterDataKey.FORT_BUFF, "0");}
		else fortitude_total += Integer.parseInt(fortitude_buff);
		fortitude_total += con_mod;
		((Button)view.findViewById(R.id.fort_button)).setText("Fortitude: "+fortitude_total);
		
		int reflex_total = 0;
		String reflex = com.loadData(CharacterDataKey.REF);
		if (reflex == null){
			reflex = "0"; com.saveData(CharacterDataKey.REF, "0");}
		else reflex_total += Integer.parseInt(reflex);
		String reflex_class = com.loadData(CharacterDataKey.REF_CLASS);
		if (reflex_class == null){
			reflex = "0"; com.saveData(CharacterDataKey.REF_CLASS, "0");}
		else reflex_total += Integer.parseInt(reflex_class);
		String reflex_buff = com.loadData(CharacterDataKey.REF_BUFF);
		if (reflex_buff == null){
			reflex = "0"; com.saveData(CharacterDataKey.REF_BUFF, "0");}
		else reflex_total += Integer.parseInt(reflex_buff);
		reflex_total += dex_mod;
		((Button)view.findViewById(R.id.ref_button)).setText("Reflex: "+reflex_total);
		
		int will_total = 0;
		String will = com.loadData(CharacterDataKey.WILL);
		if (will == null){
			will = "0"; com.saveData(CharacterDataKey.WILL, "0");}
		else will_total += Integer.parseInt(will);
		String will_class = com.loadData(CharacterDataKey.WILL_CLASS);
		if (will_class == null){
			will = "0"; com.saveData(CharacterDataKey.WILL_CLASS, "0");}
		else will_total += Integer.parseInt(will_class);
		String will_buff = com.loadData(CharacterDataKey.WILL_BUFF);
		if (will_buff == null){
			will = "0"; com.saveData(CharacterDataKey.WILL_BUFF, "0");}
		else will_total += Integer.parseInt(will_buff);
		will_total += wis_mod;
		((Button)view.findViewById(R.id.will_button)).setText("Will: "+will_total);
		
		
		((EditText)view.findViewById(R.id.bab_edit)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	com.saveData(CharacterDataKey.BAB, s.toString());
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {}
		});
		
		
		((EditText)view.findViewById(R.id.max_hp)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	com.saveData(CharacterDataKey.HP, s.toString());
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {}
		});
		((EditText)view.findViewById(R.id.current_hp)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	com.saveData(CharacterDataKey.TEMP_HP, s.toString());
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {}
		});
		((EditText)view.findViewById(R.id.non_lethal_hp)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	com.saveData(CharacterDataKey.NONLETHAL_HP, s.toString());
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {}
		});
		
		
		((EditText)view.findViewById(R.id.initiative_misc)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    }
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	com.saveData(CharacterDataKey.INITIATIVE, s.toString());
		    	int initiative = 
		    			Integer.parseInt(com.loadData(CharacterDataKey.INITIATIVE_BUFF)) +
		    			Integer.parseInt(((TextView)main_view.findViewById(R.id.dex_modifier)).getText().toString()) +
		    			Integer.parseInt(s.toString());
		    	
		    	((TextView)main_view.findViewById(R.id.initiative_text)).setText("Initiative: "+initiative+" = ");
		    }
		});
		
		
		((EditText)view.findViewById(R.id.str_base)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	com.saveData(CharacterDataKey.STR, s.toString());
		    	int str_total = 0;
		    	String str = s.toString();
				String str_buff = com.loadData(CharacterDataKey.STR_BUFF);
				if(str_buff == null){
					((TextView)main_view.findViewById(R.id.str_value)).setText("STR: "+str+" = ");	
					str_total = Integer.parseInt(str);
				} else {
					str_total = Integer.parseInt(str) + Integer.parseInt(str_buff);
					((TextView)main_view.findViewById(R.id.str_value)).setText("STR: "+str_total+" = ");
					((TextView)main_view.findViewById(R.id.str_buff)).setText(str_buff);
				}
				int str_mod = str_total / 2 - 5;
				((TextView)main_view.findViewById(R.id.str_modifier)).setText(""+str_mod);
		    }
		});
		
		((EditText)view.findViewById(R.id.dex_base)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	//int dex_mod_before = Integer.parseInt(((TextView)main_view.findViewById(R.id.dex_modifier)).getText().toString());
		    	com.saveData(CharacterDataKey.DEX, s.toString());
		    	int dex_total = 0;
		    	String dex = s.toString();
				String dex_buff = com.loadData(CharacterDataKey.DEX_BUFF);
				if(dex_buff == null){
					((TextView)main_view.findViewById(R.id.dex_value)).setText("DEX: "+dex+" = ");	
					dex_total = Integer.parseInt(dex);
				} else {
					dex_total = Integer.parseInt(dex) + Integer.parseInt(dex_buff);
					((TextView)main_view.findViewById(R.id.dex_value)).setText("DEX: "+dex_total+" = ");
					((TextView)main_view.findViewById(R.id.dex_buff)).setText(dex_buff);
				}
				int dex_mod = dex_total / 2 - 5;
				((TextView)main_view.findViewById(R.id.dex_modifier)).setText(""+dex_mod);
				
				int armor, natural, shield, deflection, dodge, misc;
				armor = Integer.parseInt(com.loadData(CharacterDataKey.AC_ARMOR));
				shield = Integer.parseInt(com.loadData(CharacterDataKey.AC_SHIELD));
				natural = Integer.parseInt(com.loadData(CharacterDataKey.AC_NATURAL));
				deflection = Integer.parseInt(com.loadData(CharacterDataKey.AC_DEFLECTION));
				dodge = Integer.parseInt(com.loadData(CharacterDataKey.AC_DODGE));
				misc = Integer.parseInt(com.loadData(CharacterDataKey.AC_MISC));
				
				int ac_total = 10 + dex_mod + armor + shield + natural + deflection + dodge + misc;
				((Button)main_view.findViewById(R.id.ac_button)).setText("AC: "+ac_total);
				int ac_ff = 10 + dex_mod + deflection + dodge + misc;
				((Button)main_view.findViewById(R.id.ff_button)).setText("Flat Footed: "+ac_ff);
				
				int ref_misc, ref_class, ref_buff;
				ref_misc = Integer.parseInt(com.loadData(CharacterDataKey.REF));
				ref_class = Integer.parseInt(com.loadData(CharacterDataKey.REF_CLASS));
				ref_buff = Integer.parseInt(com.loadData(CharacterDataKey.REF_BUFF));
				int ref_total = ref_misc + ref_class + ref_buff + dex_mod;
				((Button)main_view.findViewById(R.id.ref_button)).setText("Reflex: "+ref_total);
				
		    }
		});
		
		((EditText)view.findViewById(R.id.con_base)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	com.saveData(CharacterDataKey.CON, s.toString());
		    	int con_total = 0;
		    	String con = s.toString();
				String con_buff = com.loadData(CharacterDataKey.CON_BUFF);
				if(con_buff == null){
					((TextView)main_view.findViewById(R.id.str_value)).setText("CON: "+con+" = ");	
					con_total = Integer.parseInt(con);
				} else {
					con_total = Integer.parseInt(con) + Integer.parseInt(con_buff);
					((TextView)main_view.findViewById(R.id.con_value)).setText("CON: "+con_total+" = ");
					((TextView)main_view.findViewById(R.id.con_buff)).setText(con_buff);
				}
				int con_mod = con_total / 2 - 5;
				((TextView)main_view.findViewById(R.id.con_modifier)).setText(""+con_mod);
				
				int fort_misc, fort_class, fort_buff;
				fort_misc = Integer.parseInt(com.loadData(CharacterDataKey.FORT));
				fort_class = Integer.parseInt(com.loadData(CharacterDataKey.FORT_CLASS));
				fort_buff = Integer.parseInt(com.loadData(CharacterDataKey.FORT_BUFF));
				int fort_total = fort_misc + fort_class + fort_buff + con_mod;
				((Button)main_view.findViewById(R.id.fort_button)).setText("Fortitude: "+fort_total);
				
		    }
		});
		
		((EditText)view.findViewById(R.id.int_base)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	com.saveData(CharacterDataKey.INT, s.toString());
		    	int int_total = 0;
		    	String intelligence = s.toString();
				String int_buff = com.loadData(CharacterDataKey.INT_BUFF);
				if(int_buff == null){
					((TextView)main_view.findViewById(R.id.str_value)).setText("INT: "+intelligence+" = ");	
					int_total = Integer.parseInt(intelligence);
				} else {
					int_total = Integer.parseInt(intelligence) + Integer.parseInt(int_buff);
					((TextView)main_view.findViewById(R.id.int_value)).setText("INT: "+int_total+" = ");
					((TextView)main_view.findViewById(R.id.int_buff)).setText(int_buff);
				}
				int int_mod = int_total / 2 - 5;
				((TextView)main_view.findViewById(R.id.int_modifier)).setText(""+int_mod);
		    }
		});
		
		((EditText)view.findViewById(R.id.wis_base)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	com.saveData(CharacterDataKey.WIS, s.toString());
		    	int wis_total = 0;
		    	String wis = s.toString();
				String wis_buff = com.loadData(CharacterDataKey.WIS_BUFF);
				if(wis_buff == null){
					((TextView)main_view.findViewById(R.id.wis_value)).setText("WIS: "+wis+" = ");	
					wis_total = Integer.parseInt(wis);
				} else {
					wis_total = Integer.parseInt(wis) + Integer.parseInt(wis_buff);
					((TextView)main_view.findViewById(R.id.wis_value)).setText("WIS: "+wis_total+" = ");
					((TextView)main_view.findViewById(R.id.wis_buff)).setText(wis_buff);
				}
				int wis_mod = wis_total / 2 - 5;
				((TextView)main_view.findViewById(R.id.wis_modifier)).setText(""+wis_mod);
				
				int wil_misc, wil_class, wil_buff;
				wil_misc = Integer.parseInt(com.loadData(CharacterDataKey.WILL));
				wil_class = Integer.parseInt(com.loadData(CharacterDataKey.WILL_CLASS));
				wil_buff = Integer.parseInt(com.loadData(CharacterDataKey.WILL_BUFF));
				int wil_total = wil_misc + wil_class + wil_buff + wis_mod;
				((Button)main_view.findViewById(R.id.will_button)).setText("Will: "+wil_total);
		    }
		});
		
		((EditText)view.findViewById(R.id.cha_base)).addTextChangedListener(new TextWatcher() {
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    public void afterTextChanged(Editable s) {
		    	
		    	try{
		    		Integer.parseInt(s.toString());
		    	} catch (Exception e){
		    		return;
		    	}
		    	com.saveData(CharacterDataKey.CHA, s.toString());
		    	int cha_total = 0;
		    	String cha = s.toString();
				String cha_buff = com.loadData(CharacterDataKey.CHA_BUFF);
				if(cha_buff == null){
					((TextView)main_view.findViewById(R.id.cha_value)).setText("CHA: "+cha+" = ");	
					cha_total = Integer.parseInt(cha);
				} else {
					cha_total = Integer.parseInt(cha) + Integer.parseInt(cha_buff);
					((TextView)main_view.findViewById(R.id.cha_value)).setText("CHA: "+cha_total+" = ");
					((TextView)main_view.findViewById(R.id.cha_buff)).setText(cha_buff);
				}
				int cha_mod = cha_total / 2 - 5;
				((TextView)main_view.findViewById(R.id.cha_modifier)).setText(""+cha_mod);
		    }
		});
		
		return view;	
	}	
}
