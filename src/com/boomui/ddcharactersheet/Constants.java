package com.boomui.ddcharactersheet;

import android.graphics.Color;

public class Constants{
	//Used for when an expandable list item is empty
	public static int DISABLED = Color.rgb(100, 100, 100);
	public static int SPELL_USED = Color.rgb(50, 50, 50);
	public static int BACKGROUND_COLOR = Color.rgb(255, 255, 255);
	public static int TRANSPARENT = Color.argb(0, 0, 0, 0);
	public static String CHARACTER_CLASS_SEPARATOR = "!###!";
	public static int MAGIC_TAB_ANIMATION_LENGTH = 500;
	
	public static String defaultClasses = "Misc."+CHARACTER_CLASS_SEPARATOR+"Sorcerer"+CHARACTER_CLASS_SEPARATOR+"Wizard";
	
	public static String[] allCasterClassNames = {"Bard", "Cleric", "Druid", "Paladin", "Ranger", "Sorcerer", "Wizard"};
	public static String miscCasterClass = "Non-class Spells";
}
