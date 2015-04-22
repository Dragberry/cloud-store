package net.dragberry.cloudstore.query;

public class PageableQuery {
    
    private int pageSize = -1;
    
    private int pageNumber = -1;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
    
}
