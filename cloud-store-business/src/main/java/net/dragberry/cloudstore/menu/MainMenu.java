package net.dragberry.cloudstore.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Serializable {

	private static final long serialVersionUID = -3925663733001419135L;
	
	private List<MenuItem> leftList = new ArrayList<MenuItem>();
	
	private List<MenuItem> rightList = new ArrayList<MenuItem>();

	public List<MenuItem> getLeftList() {
		return leftList;
	}

	public void setLeftList(List<MenuItem> leftList) {
		this.leftList = leftList;
	}

	public List<MenuItem> getRightList() {
		return rightList;
	}

	public void setRightList(List<MenuItem> rightList) {
		this.rightList = rightList;
	}
	
	public void addLeftMenuItem(MenuItem item) {
		leftList.add(item);
	}
	
	public void addRightMenuItem(MenuItem item) {
		rightList.add(item);
	}

}
