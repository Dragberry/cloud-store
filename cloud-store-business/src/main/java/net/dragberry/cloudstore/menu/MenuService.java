package net.dragberry.cloudstore.menu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import net.dragberry.cloudstore.business.CategoryServiceLocal;
import net.dragberry.cloudstore.domain.Category;

@Stateless
public class MenuService implements MenuServiceLocal {
	
	@Resource
	private SessionContext sessionContext;
	
	@Inject
	private CategoryServiceLocal categoryService;

	@Override
	public MainMenu getMainMenu() {
		boolean isAdmin = sessionContext.isCallerInRole("admin");
		
		MainMenu mainMenu = new MainMenu();
		
		MenuItem homeMenuItem = new MenuItem();
		homeMenuItem.setTitleKey("CShome");
		homeMenuItem.setPath("");
		mainMenu.addLeftMenuItem(homeMenuItem);
		
		MenuItem shopMenuItem = new MenuItem();
		shopMenuItem.setTitleKey("CSshop");
		shopMenuItem.setSubMenu(getShopMenu());
		mainMenu.addLeftMenuItem(shopMenuItem);
		
		MenuItem servicesMenuItem = new MenuItem();
		servicesMenuItem.setTitleKey("CSservices");
		mainMenu.addLeftMenuItem(servicesMenuItem);
		
		if (isAdmin) {
			MenuItem adminMenuItem = new MenuItem();
			adminMenuItem.setTitleKey("CSadministration");
			adminMenuItem.setPath("admin/dashboard");
			mainMenu.addLeftMenuItem(adminMenuItem);
		}
		
		MenuItem aboutMenuItem = new MenuItem();
		aboutMenuItem.setTitleKey("CSaboutUs");
		aboutMenuItem.setPath("aboutUs");
		mainMenu.addRightMenuItem(aboutMenuItem);
		
		MenuItem cartMenuItem = new MenuItem();
		cartMenuItem.setTitleKey("CScart");
		cartMenuItem.setPath("cart");
		mainMenu.addRightMenuItem(cartMenuItem);
		
		return mainMenu;
	}
	
	private List<MenuItem> getShopMenu() {
		List<MenuItem> shopMenu = new ArrayList<MenuItem>();
		List<Category> categoryList = categoryService.fetchCategories();
		for (Category category : categoryList) {
			MenuItem item = new MenuItem();
			item.setTitleKey(category.getTitle());
			item.addParam("category", category.getCode());
			item.setPath("shop/productList");
			shopMenu.add(item);
		}
		return shopMenu;
	}
	
	

}
