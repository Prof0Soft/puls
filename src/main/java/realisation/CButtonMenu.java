package main.java.realisation;

import javafx.scene.control.Button;

public class CButtonMenu {

	Button buttonMenu;
	int levelView;

	/**
	 * Constructor
	 *
	 * @param buttonMenu - button of create menu
	 * @param levelView	- preview level the button
	 */
	public CButtonMenu (Button buttonMenu, int levelView){
		this.buttonMenu = buttonMenu;
		this.levelView = levelView;
	}

	public void instVisible(int currentLevel){
		if (levelView >= currentLevel){
			buttonMenu.setVisible(true);
		}else{
			buttonMenu.setVisible(false);
			buttonMenu.setManaged(false);
		}
	}

}
