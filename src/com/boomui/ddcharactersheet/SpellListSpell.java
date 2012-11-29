package com.boomui.ddcharactersheet;

class SpellListSpell{
	String name;
	String levels; //Comma separated
	
	public SpellListSpell(String name, String levels){
		this.name = name;
		this.levels = levels;
	}
	
	public boolean castBy(String characterClass){
		return (levels.toLowerCase().indexOf(characterClass.toLowerCase() ) != -1);
	}
	public boolean castBy(String characterClass, int level){
		if(level < 0 || level > 9){
			return castBy(characterClass);
		}
		
		return castBy(characterClass + " " + level);
	}
	
	public String getName(){
		return name;
	}
}