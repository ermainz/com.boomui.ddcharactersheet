package com.boomui.ddcharactersheet;

public class Attack {
	public static String ATTACK_SPLIT = "!###@!";
	public static String ATTACK_VALUE_SPLIT = "!##@#!";
	
	String name=" ",attack="0",damage=" ",range=" ",crit=" ",type=" ",notes=" ";
	
	public String save(){
		String retVal = "";
		if(name.isEmpty())
			name = " ";
		if(attack.isEmpty())
			attack = "0";
		if(damage.isEmpty())
			damage = " ";
		if(range.isEmpty())
			range = " ";
		if(crit.isEmpty())
			crit = " ";
		if(type.isEmpty())
			type = " ";
		if(notes.isEmpty())
			notes = " ";
		
		retVal += name + ATTACK_VALUE_SPLIT;
		retVal += attack + ATTACK_VALUE_SPLIT;
		retVal += damage + ATTACK_VALUE_SPLIT;
		retVal += range + ATTACK_VALUE_SPLIT;
		retVal += crit + ATTACK_VALUE_SPLIT;
		retVal += type + ATTACK_VALUE_SPLIT;
		retVal += notes + ATTACK_VALUE_SPLIT;
		return retVal;
	}
	
	public String rollDamage(){
		String retVal = "";
		String damage_string = new String(damage);
		int total_damage = 0;
		try{
		damage_string = damage_string.replaceAll("\\s","");
		
		if(damage_string == null || damage_string.isEmpty() )
			damage_string = "";
		else{
			String[] values = damage_string.split("\\+");
			for(String value : values){
				String[] constant = value.split("d");
				if(constant.length < 2){
					total_damage += Integer.parseInt(constant[0]);
					retVal += constant[0] + " + ";
				} else {
					int die_size = Integer.parseInt(constant[1]);
					int die_number = 1;
					int dice_roll = 0;
					if(!constant[0].isEmpty())
						die_number = Integer.parseInt(constant[0]);
					for(int i = 0; i < die_number ; i++)
						dice_roll += (int)(Math.random()*die_size) + 1;
					total_damage+=dice_roll;
					retVal += dice_roll + " + ";
				}
			}
			if(!retVal.isEmpty())
				retVal = retVal.substring(0,retVal.length() - 2);		
			retVal += "= "+total_damage;
		}
		return retVal;
		} catch (Exception e){
			return null;
		}
	}
	public String rollAttack(){
		String retVal = "";
		if(attack == null || attack.isEmpty())
			attack = "0";
		int bonus = Integer.parseInt(attack);
		int die_roll = (int)(Math.random()*20) + 1;
		int total = bonus + die_roll;
		retVal = die_roll + " + " + bonus + " = " + total ;
		return retVal;
	}
	
	
}
