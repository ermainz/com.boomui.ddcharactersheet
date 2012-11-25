package com.boomui.ddcharactersheet;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class TabExpansionAnimation extends Animation{
	LinearLayout grower;
	LinearLayout shrinker;
	int maxHeight;
	int minHeight;
	boolean ended = false;
	
	public TabExpansionAnimation(LinearLayout grower, LinearLayout shrinker, long duration){
		setDuration(duration);
		this.grower = grower;
		this.shrinker = shrinker;
		maxHeight = shrinker.getHeight();
		minHeight = 1;
	}
	
	protected void applyTransformation(float interpolatedTime, Transformation t){
		super.applyTransformation(interpolatedTime, t);
		
		if(interpolatedTime < 1.0f){
			int growHeight = (int)( (maxHeight * interpolatedTime) + (minHeight * (1 - interpolatedTime) ) );
			int shrinkHeight = (int)( (minHeight * interpolatedTime) + (maxHeight * (1 - interpolatedTime) ) );
			
			grower.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, growHeight, 0));
			shrinker.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, shrinkHeight, 0));
		}
		else if(!ended){
			//set ending stuff
			shrinker.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 0));
			grower.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

			ended = true;
		}
	}
}

/*
public class TabExpansionAnimation extends Animation{
LinearLayout layout;
int target;
int start;
boolean ended = false;
boolean shrinking;

public TabExpansionAnimation(LinearLayout layout, long duration, int target, boolean collapse){
	setDuration(duration);
	this.layout = layout;
	this.target = target;
	this.start = layout.getHeight();
	this.shrinking = collapse;
}

protected void applyTransformation(float interpolatedTime, Transformation t){
	super.applyTransformation(interpolatedTime, t);
	
	if(interpolatedTime < 1.0f){
		int height = (int)( (target * interpolatedTime) + (start * (1 - interpolatedTime) ) );
		
		layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height, 0));
		
		layout.requestLayout();
	}
	else if(!ended){
		//set ending stuff
		if(shrinking){
			layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 0));
		}
		else{
			layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
		}
		
		layout.requestLayout();
		
		ended = true;
	}
}
}
*/