package com.boomui.ddcharactersheet;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.boomui.*;

public class BuffsSaved {
	public static String BUFF_SPLIT = "!###@!";
	public static String NAME_SPLIT = "!##@#!";
	public static String TURNS_SPLIT = "!##@@!";
	public static String TYPE_SPLIT = "!#@##!";
	public static String TYPE_DATA_SPLIT = "!#@#@!";
	
	private List<Buff> buffs;
	String characterClass;
	
	public List<Buff> getBuffs(){
		return buffs;
	}
	
	public BuffsSaved(FragmentCommunicator com){
		this(com.loadData(CharacterDataKey.BUFFS_SAVED));
	}
	public BuffsSaved(String savedData){
		
		characterClass = "Misc.";
		
		buffs = new LinkedList<Buff>();
		
		//There won't be anything stored yet, this is just a sample
		if(savedData == null || savedData.isEmpty()){
			savedData = "Ring of Wisdom +2" + NAME_SPLIT + "2" + TURNS_SPLIT + "FORT" + TYPE_DATA_SPLIT + "2" + TYPE_SPLIT + "REF" + TYPE_DATA_SPLIT + "1" + BUFF_SPLIT;
		}
				String[] buffs = savedData.split(BUFF_SPLIT);
				for(int i = 0; i < buffs.length; i++){
					String[] name_split = buffs[i].split(NAME_SPLIT);
					String name = name_split[0];
					String[] turns_split = name_split[1].split(TURNS_SPLIT);
					String turns = turns_split[0];
					Buff currentBuff = new Buff();
					currentBuff.name = name;
					currentBuff.turns = turns;
					if (turns_split.length == 2){					
						String[] types = turns_split[1].split(TYPE_SPLIT);					
						for(String type : types){
							String[] type_split = type.split(TYPE_DATA_SPLIT, -1);
							if (type_split[0].equals("FORT")) currentBuff.fort = type_split[1]; 
							else if (type_split[0].equals("REF")) currentBuff.ref = type_split[1];
							else if (type_split[0].equals("WILL")) currentBuff.will = type_split[1];
							else if (type_split[0].equals("STR")) currentBuff.str = type_split[1];
							else if (type_split[0].equals("DEX")) currentBuff.dex = type_split[1];
							else if (type_split[0].equals("CON")) currentBuff.con = type_split[1];
							else if (type_split[0].equals("INT")) currentBuff.intl = type_split[1];
							else if (type_split[0].equals("WIS")) currentBuff.wis = type_split[1];
							else if (type_split[0].equals("CHA")) currentBuff.cha = type_split[1];
							else if (type_split[0].equals("AC")) currentBuff.ac = type_split[1];						
							//levelHolder.add(new PreparedSpell(name+" "+turns+" "+type, false ) );
						}
					}
					this.buffs.add(currentBuff);
				}			
	}
	/*
	public String getSpellName(int level, int index){
		return levels.get(level).getSpellName(index);
	}
	public PreparedSpell getSpell(int level, int index){
		return levels.get(level).getSpell(index);
	}
	public String getLevelName(int level){
		return "Level " + level;
	}
	
	public int getNumSpells(int level){
		return levels.get(level).getNumSpells();
	}
	public int getNumLevels(){
		return levels.size();
	}
	
	public void addSpell(String name, int level){
		levels.get(level).add(new PreparedSpell(name, false) );
	}
	public void removeLastSpell(int level){
		levels.get(level).removeLastSpell();
	}
	public void removeSpellByName(int level, String name){
		levels.get(level).removeSpellByName(name);
	}
	
	/*
	 * This method attempts to add a spell to an empty spell slot.  If one isn't found, it adds it to the end of the list instead
	 
	public void addNewSpell(String name, int lv){
		PreparedSpellLevel level = levels.get(lv);
		for(int i = 0; i < level.getNumSpells(); i++){
			if(level.getSpellName(i).equals(" ") ){
				level.getSpell(i).setName(name);
				level.sort();
				return;
			}
		}
		level.add(new PreparedSpell(name, false) );
	}
*/
	public String save(){
		String retVal = "";
		for(Buff buff : buffs){
			retVal += buff.save();
		}
		return retVal;
	}
	
	
}
/*
class Buff{
	String fort,ref,will,str,dex,con,intl,wis,cha,ac, turns;
	String name;
	
	
	
	public String save(){
		String retVal = name + BuffsSaved.NAME_SPLIT + turns + BuffsSaved.TURNS_SPLIT;
		if(!fort.equals("0") && fort != null && !fort.equals(""))
			retVal += "FORT"+BuffsSaved.TYPE_DATA_SPLIT+fort+BuffsSaved.TYPE_SPLIT;
		if(!ref.equals("0") && ref != null && !ref.equals(""))
			retVal += "REF"+BuffsSaved.TYPE_DATA_SPLIT+ref+BuffsSaved.TYPE_SPLIT;
		if(!will.equals("0") && will != null && !will.equals(""))
			retVal += "WILL"+BuffsSaved.TYPE_DATA_SPLIT+will+BuffsSaved.TYPE_SPLIT;
		if(!str.equals("0") && str != null && !str.equals(""))
			retVal += "STR"+BuffsSaved.TYPE_DATA_SPLIT+str+BuffsSaved.TYPE_SPLIT;
		if(!dex.equals("0") && dex != null && !dex.equals(""))
			retVal += "DEX"+BuffsSaved.TYPE_DATA_SPLIT+dex+BuffsSaved.TYPE_SPLIT;
		if(!con.equals("0") && con != null && !con.equals(""))
			retVal += "CON"+BuffsSaved.TYPE_DATA_SPLIT+con+BuffsSaved.TYPE_SPLIT;
		if(!intl.equals("0") && intl != null && !intl.equals(""))
			retVal += "INT"+BuffsSaved.TYPE_DATA_SPLIT+intl+BuffsSaved.TYPE_SPLIT;
		if(!wis.equals("0") && wis != null && !wis.equals(""))
			retVal += "WIS"+BuffsSaved.TYPE_DATA_SPLIT+wis+BuffsSaved.TYPE_SPLIT;
		if(!cha.equals("0") && cha != null && !cha.equals(""))
			retVal += "CHA"+BuffsSaved.TYPE_DATA_SPLIT+cha+BuffsSaved.TYPE_SPLIT;
		if(!ac.equals("0") && ac != null && !ac.equals(""))
			retVal += "AC"+BuffsSaved.TYPE_DATA_SPLIT+ac+BuffsSaved.TYPE_SPLIT;
		retVal += BuffsSaved.BUFF_SPLIT;
		
		return retVal;
	}
	
	public String getDescription(){
		String retVal = "";
		if(!fort.equals("0") && fort != null && !fort.equals(""))
			retVal += "FORT "+fort+", ";
		if(!ref.equals("0") && ref != null && !ref.equals(""))
			retVal += "REF "+ref+", ";
		if(!will.equals("0") && will != null && !will.equals(""))
			retVal += "WILL "+will+", ";
		if(!str.equals("0") && str != null && !str.equals(""))
			retVal += "STR "+str+", ";
		if(!dex.equals("0") && dex != null && !dex.equals(""))
			retVal += "DEX "+dex+", ";
		if(!con.equals("0") && con != null && !con.equals(""))
			retVal += "CON "+con+", ";
		if(!intl.equals("0") && intl != null && !intl.equals(""))
			retVal += "INT "+intl+", ";
		if(!wis.equals("0") && wis != null && !wis.equals(""))
			retVal += "WIS "+wis+", ";
		if(!cha.equals("0") && cha != null && !cha.equals(""))
			retVal += "CHA "+cha+", ";
		if(!ac.equals("0") && ac != null && !ac.equals(""))
			retVal += "AC "+ac+", ";
		return retVal;
	}
	
	/*
	public void add(PreparedSpell sp){
		spells.add(sp);
	}
	public void removeLastSpell(){
		if(!spells.isEmpty() ){
			spells.remove(spells.size() - 1);
		}
	}
	public void removeSpellByName(String name){
		for(PreparedSpell spell : spells){
			if(spell.getName().equals(name) ){
				spell.setName(" ");
				break;
			}
		}
	}
	
	public Buff(){
		spells = new LinkedList<PreparedSpell>();
	}
	
	
}*/
