package com.yanp.traficmetro.core;

import java.util.HashMap;

import com.yanp.traficmetro.R;
import com.yanp.traficmetro.R.anim;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Manage the differents animation.
 * Create all the animation contains in the res/anim folder in a Map
 * Retrieve the animation from the map with the id of the animations
 * @author YPierru
 *
 */
public class AnimationManager {
	
	private Activity activityReference;
	private HashMap<Integer, Animation> mapAnimations;

	public AnimationManager(Activity activityReference) {
		this.activityReference=activityReference;
		this.mapAnimations = new HashMap<>();
		this.createAllAnimations();
	}
	
	/**
	 * Instantiate all the animation with the xml contains in the res/anim folder
	 */
	private void createAllAnimations(){
		this.mapAnimations.put(R.anim.animationbtnaddappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationbtnaddappear));
		this.mapAnimations.put(R.anim.animationbtnadddisappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationbtnadddisappear));
		this.mapAnimations.put(R.anim.animationinfostationappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationinfostationappear));
		this.mapAnimations.put(R.anim.animationinfostationdisappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationinfostationdisappear));
		this.mapAnimations.put(R.anim.animationlistappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationlistappear));
		this.mapAnimations.put(R.anim.animationlistdisappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationlistdisappear));
		this.mapAnimations.put(R.anim.animationpanelappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationpanelappear));
		this.mapAnimations.put(R.anim.animationpaneldisappear,AnimationUtils.loadAnimation(this.activityReference, R.anim.animationpaneldisappear));
	}

	/**
	 * Retrieve an animation choose by its id.
	 * @param id
	 * @return
	 */
	public Animation getAnimation(int id){
		return this.mapAnimations.get(id);
	}

}
