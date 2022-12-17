package com.mygdx.arkanoid.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;


public class KeyListener implements InputProcessor{
	

	private boolean right = false, left = false;
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode==Keys.A) {
			left = true;
		}
		
		if(keycode==Keys.D) {
			right = true;
		}
		
	
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Keys.A) {
			left = false;
		}
		
		if(keycode==Keys.D) {
			right = false;
		}
		
	
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	public boolean isRight() {
		return right;
	}
	

}
