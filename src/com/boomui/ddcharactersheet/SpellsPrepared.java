package com.boomui.ddcharactersheet;

import java.util.*;

public class SpellsPrepared{
	public static String CHARACTER_SPLIT = "####";
	public static String LEVEL_SPLIT = "###@";
	public static String SPELL_SPLIT = "##@#";
	public static String SPELL_DATA_SPLIT = "##@@";

	private List<PreparedSpellLevel> levels;
	String characterClass;
	
	public SpellsPrepared(FragmentCommunicator com, String characterClass){
		this(com.loadData(CharacterDataKey.SPELLS_PREPARED), characterClass);
	}
	public SpellsPrepared(String savedData, String characterClass){
		this.characterClass = characterClass;
		
		levels = new LinkedList<PreparedSpellLevel>();
		
		for(int i = 0; i < 10; i++){
			levels.add(new PreparedSpellLevel() );
		}
		
		//There won't be anything stored yet, this is just a sample
		savedData = "Misc."+LEVEL_SPLIT+"Detect Magic"+SPELL_DATA_SPLIT+"false"+SPELL_SPLIT+"Read Magic"+SPELL_DATA_SPLIT+"false"+SPELL_SPLIT+" "+SPELL_DATA_SPLIT+"true"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-" + CHARACTER_SPLIT + "Sorcerer."+LEVEL_SPLIT+"Acid Splash"+SPELL_DATA_SPLIT+"false"+LEVEL_SPLIT+"Magic Missile"+SPELL_DATA_SPLIT+"false"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-" + CHARACTER_SPLIT + "Wizard."+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+CHARACTER_SPLIT;
		
		String[] classes = savedData.split(CHARACTER_SPLIT);
		for(String c : classes){
			if(c.startsWith(characterClass) ){
				//Remove the name of the class from the list of spells
				c = c.substring(c.indexOf(LEVEL_SPLIT) + LEVEL_SPLIT.length() );
				
				String[] spellLevels = c.split(LEVEL_SPLIT);
				for(int i = 0; i < spellLevels.length; i++){
					if(spellLevels[i].equals("-") ){
						continue;
					}
					
					PreparedSpellLevel levelHolder = levels.get(i);
					String[] spells = spellLevels[i].split(SPELL_SPLIT);
					for(String spell : spells){
						String[] spellData = spell.split(SPELL_DATA_SPLIT, -1);
						
						levelHolder.add(new PreparedSpell(spellData[0], Boolean.parseBoolean(spellData[1]) ) );
					}
				}
				break;
			}
		}
	}
	
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
}
class PreparedSpellLevel{
	private List<PreparedSpell> spells;
	
	public String getSpellName(int index){
		return spells.get(index).name;
	}
	public int getNumSpells(){
		return spells.size();
	}
	public PreparedSpell getSpell(int index){
		return spells.get(index);
	}
	
	public void add(PreparedSpell sp){
		spells.add(sp);
	}
	public void removeLastSpell(){
		if(!spells.isEmpty() ){
			spells.remove(spells.size() - 1);
		}
	}
	
	public PreparedSpellLevel(){
		spells = new LinkedList<PreparedSpell>();
	}
}
class PreparedSpell{
	String name;
	boolean used;
	
	public PreparedSpell(String name, boolean used){
		this.name = name;
		this.used = used;
	}
}
