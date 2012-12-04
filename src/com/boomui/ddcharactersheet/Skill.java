package com.boomui.ddcharactersheet;

public class Skill {
	String skillName;
	CharacterDataKey keyAbility;
	int skillModifier;
	int ranks;
	int abilityModifier;
	int miscModifier;
	int buffsModifier;

	public Skill(String name, CharacterDataKey ability, int ranks,
			int abilityModifier, int miscModifier, int buffsModifier) {
		this.skillName = name;
		this.keyAbility = ability;
		this.ranks = ranks;
		this.abilityModifier = abilityModifier;
		this.miscModifier = miscModifier;
		this.buffsModifier = buffsModifier;

		this.skillModifier = this.ranks + this.abilityModifier
				+ this.miscModifier + this.buffsModifier;
	}
}
