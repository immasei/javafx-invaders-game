package invaders.rendering;

import java.util.List;

public class Animator {
	private final List<Animation> animations;
	private Animation state;

	public Animator(List<Animation> animations){
		if(animations.isEmpty()){
			throw new IllegalArgumentException("Animations list must contain at least 1 animation!");
		}

		this.animations = animations;
		this.state = animations.get(0);
	}

	public void setState(String name){
		this.state = animations.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(animations.get(0));
	}

	public Animation getState(){
		return this.state;
	}

}
