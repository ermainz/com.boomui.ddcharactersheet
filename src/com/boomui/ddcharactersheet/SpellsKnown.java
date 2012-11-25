package com.boomui.ddcharactersheet;

import java.util.LinkedList;
import java.util.List;

public class SpellsKnown{
	public static String CHARACTER_SPLIT = "!####!";
	public static String LEVEL_SPLIT = "!###@!";
	public static String SPELL_SPLIT = "!##@#!";
	
	List<List<String>> spellsKnown;
	String characterClass;
	
	public SpellsKnown(FragmentCommunicator com, String characterClass){
		this(com.loadData(CharacterDataKey.SPELLS_KNOWN), characterClass);
	}
	public SpellsKnown(String savedData, String characterClass){
		this.characterClass = characterClass;
		
		spellsKnown = new LinkedList<List<String>>();
		
		for(int i = 0; i < 10; i++){
			spellsKnown.add(new LinkedList<String>() );
		}
		
		//There won't be anything stored yet, this is just a sample
		savedData = "Misc."+LEVEL_SPLIT+"Detect Magic"+SPELL_SPLIT+"Read Magic"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-" + CHARACTER_SPLIT + "Sorcerer."+LEVEL_SPLIT+"Acid Splash"+LEVEL_SPLIT+"Magic Missile"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-" + CHARACTER_SPLIT + "Wizard."+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+LEVEL_SPLIT+"-"+CHARACTER_SPLIT;
		
		String[] classes = savedData.split(CHARACTER_SPLIT);
		for(String c : classes){
			if(c.startsWith(characterClass) ){
				//Remove the name of the class from the list of spells
				c = c.substring(c.indexOf(LEVEL_SPLIT) + LEVEL_SPLIT.length() );
				
				String[] levels = c.split(LEVEL_SPLIT);
				for(int i = 0; i < levels.length; i++){
					if(levels[i].equals("-") ){
						continue;
					}
					
					List<String> levelHolder = spellsKnown.get(i);
					String[] spells = levels[i].split(SPELL_SPLIT);
					for(String spell : spells){
						levelHolder.add(spell);
					}
				}
				break;
			}
		}
	}
	
	/*
	 * UNTESTED
	 */
	public String save(){
		String retVal = characterClass;
		
		for(List<String> level : spellsKnown){
			retVal += LEVEL_SPLIT;
			for(String spell : level){
				retVal += spell + SPELL_SPLIT;
			}
		}
		
		return retVal + CHARACTER_SPLIT;
	}
	
	public String get(int level, int index){
		return spellsKnown.get(level).get(index);
	}
	
	public int getNumSpells(int level){
		return spellsKnown.get(level).size();
	}
}
