package net.dragberry.cloudstore.query.sort;

import java.io.Serializable;

public class SortItem implements Comparable<SortItem>, Serializable {

    private static final long serialVersionUID = 8161692558402308558L;
    
    private SortOrder direction;
    
    private String field;
    
    private Class<?> type;
    
    private Integer order = Integer.MAX_VALUE;
    
    @Override
	public int compareTo(SortItem o) {
		return order - o.order;
	}

    public SortOrder getDirection() {
        return direction;
    }

    public void setDirection(SortOrder direction) {
        this.direction = direction;
    }

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		if (order < 0) {
			this.order = Integer.MAX_VALUE;
		}
		this.order = order;
	}

}
