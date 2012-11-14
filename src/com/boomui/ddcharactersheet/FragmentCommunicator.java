package com.boomui.ddcharactersheet;

public interface FragmentCommunicator{
	
	/*
	 * Tag identifies the data to be saved (for example, CharacterDataKeys.DEX)
	 */
	public void saveData(CharacterDataKey tag, String data);
	public String loadData(CharacterDataKey tag);
}
