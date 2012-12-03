package com.boomui.ddcharactersheet;

import java.util.List;

public interface SpellLoader{
	
	/*
	 * Should return null if the spells aren't loaded yet
	 */
	public List<SpellListSpell> getAllSpells();
	
	public boolean isNameOfCasterClass(String s);
	
	public String loadSpellData(String spellName);
}
