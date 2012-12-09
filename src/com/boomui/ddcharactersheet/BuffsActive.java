package com.boomui.ddcharactersheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BuffsActive {
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
	
	public BuffsActive(FragmentCommunicator com){
		this(com.loadData(CharacterDataKey.BUFFS_ACTIVE));
	}
	public BuffsActive(String savedData){
		
		characterClass = "Misc.";
		buffs = new LinkedList<Buff>();
		
		//There won't be anything stored yet, this is just a sample
		if(savedData == null || savedData.isEmpty()){
			savedData = "";
			//savedData = "Ring of Wisdom +2" + NAME_SPLIT + "2" + TURNS_SPLIT + "FORT" + TYPE_DATA_SPLIT + "2" + TYPE_SPLIT + "REF" + TYPE_DATA_SPLIT + "1" + BUFF_SPLIT;
		}
		else {
				String[] buffs = savedData.split(BUFF_SPLIT);
				for(int i = 0; i < buffs.length; i++){
					String[] name_split = buffs[i].split(NAME_SPLIT);
					String name = name_split[0];
					String[] turns_split = name_split[1].split(TURNS_SPLIT);
					String turns = turns_split[0];					
					Buff currentBuff = new Buff();
					currentBuff.name = name;
					currentBuff.turns = turns;
					if(turns_split.length == 2){
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
class Buff{
	String fort= "0",ref= "0",will= "0",str= "0",dex= "0",con= "0",intl= "0",wis= "0",cha= "0",ac= "0", turns = "0";
	String name = "";
	private List<BuffSkill> buffSkills = new ArrayList<BuffSkill>();
	
	public Buff(){
		/*
		BuffSkill skill1 = new BuffSkill("Concentration","3");
		BuffSkill skill2 = new BuffSkill("Jump","-2");
		buffSkills.add(skill1);
		buffSkills.add(skill2);
		*/
	}
	public Buff(Buff buff){
		fort = buff.fort;
		ref = buff.ref;
		will = buff.will;
		str = buff.str;
		dex = buff.dex;
		con = buff.con;
		intl = buff.intl;
		wis = buff.wis;
		cha = buff.cha;
		ac = buff.ac;
		turns = buff.turns;
		name = buff.name;
		for(BuffSkill buffSkill: buff.buffSkills){
			BuffSkill new_buffSkill = new BuffSkill(buffSkill.name, buffSkill.value);
			buffSkills.add(new_buffSkill);
		}
	}
	
	public List<BuffSkill> getBuffSkills(){
		return buffSkills;
	}
	
	public String save(){
		String retVal = name + BuffsActive.NAME_SPLIT + turns + BuffsActive.TURNS_SPLIT;
		if(fort != null && !fort.equals("0") && !fort.equals(""))
			retVal += "FORT"+BuffsActive.TYPE_DATA_SPLIT+fort+BuffsActive.TYPE_SPLIT;
		if(ref != null && !ref.equals("0") && !ref.equals(""))
			retVal += "REF"+BuffsActive.TYPE_DATA_SPLIT+ref+BuffsActive.TYPE_SPLIT;
		if(will != null && !will.equals("0") && !will.equals(""))
			retVal += "WILL"+BuffsActive.TYPE_DATA_SPLIT+will+BuffsActive.TYPE_SPLIT;
		if(str != null && !str.equals("0") && !str.equals(""))
			retVal += "STR"+BuffsActive.TYPE_DATA_SPLIT+str+BuffsActive.TYPE_SPLIT;
		if(dex != null && !dex.equals("0") && !dex.equals(""))
			retVal += "DEX"+BuffsActive.TYPE_DATA_SPLIT+dex+BuffsActive.TYPE_SPLIT;
		if(con != null && !con.equals("0") && !con.equals(""))
			retVal += "CON"+BuffsActive.TYPE_DATA_SPLIT+con+BuffsActive.TYPE_SPLIT;
		if(intl != null && !intl.equals("0") && !intl.equals(""))
			retVal += "INT"+BuffsActive.TYPE_DATA_SPLIT+intl+BuffsActive.TYPE_SPLIT;
		if(wis != null && !wis.equals("0") && !wis.equals(""))
			retVal += "WIS"+BuffsActive.TYPE_DATA_SPLIT+wis+BuffsActive.TYPE_SPLIT;
		if(cha != null && !cha.equals("0") && !cha.equals(""))
			retVal += "CHA"+BuffsActive.TYPE_DATA_SPLIT+cha+BuffsActive.TYPE_SPLIT;
		if(ac != null && !ac.equals("0") && !ac.equals(""))
			retVal += "AC"+BuffsActive.TYPE_DATA_SPLIT+ac+BuffsActive.TYPE_SPLIT;
		retVal += BuffsActive.BUFF_SPLIT;
		
		return retVal;
	}
	public String getDescription(){
		String retVal = "";
		if(fort != null && !fort.equals("0") && !fort.equals(""))
			retVal += "FORT "+fort+", ";
		if(ref != null && !ref.equals("0") && !ref.equals(""))
			retVal += "REF "+ref+", ";
		if(will != null && !will.equals("0") && !will.equals(""))
			retVal += "WILL "+will+", ";
		if(str != null && !str.equals("0") && !str.equals(""))
			retVal += "STR "+str+", ";
		if(dex != null && !dex.equals("0") && !dex.equals(""))
			retVal += "DEX "+dex+", ";
		if(con != null && !con.equals("0") && !con.equals(""))
			retVal += "CON "+con+", ";
		if(intl != null && !intl.equals("0") && !intl.equals(""))
			retVal += "INT "+intl+", ";
		if(wis != null && !wis.equals("0") && !wis.equals(""))
			retVal += "WIS "+wis+", ";
		if(cha != null && !cha.equals("0") && !cha.equals(""))
			retVal += "CHA "+cha+", ";
		if(ac != null && !ac.equals("0") && !ac.equals(""))
			retVal += "AC "+ac+", ";
		return retVal;
	}
	
	public String getSkillDescription(){
		String retVal = "";
		for(BuffSkill skill : buffSkills)
			retVal+= skill.name+" "+skill.value+", ";
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
	*/
	
}
class BuffSkill{
	String name, value;
	public BuffSkill(String n, String v){
		name = n;
		value = v;
	}
}
