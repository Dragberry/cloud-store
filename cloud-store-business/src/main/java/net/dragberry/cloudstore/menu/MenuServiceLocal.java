package net.dragberry.cloudstore.menu;

import javax.ejb.Local;

@Local
public interface MenuServiceLocal {

	MainMenu getMainMenu();
}
