package net.dragberry.cloudstore.menu;

import javax.ejb.Local;

import net.dragberry.cloudstore.domain.Customer;

@Local
public interface MenuServiceLocal {

	MainMenu initMainMenu(Customer customer);
}
