package net.dragberry.cloudstore.query.sort;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import net.dragberry.cloudstore.domain.Product;

public class SortItem implements Serializable {

    private static final long serialVersionUID = 8161692558402308558L;
    
    private SortOrder direction;
    
    private SingularAttribute<? super Product, ?> attribute;

    public SortOrder getDirection() {
        return direction;
    }

    public void setDirection(SortOrder direction) {
        this.direction = direction;
    }

    public SingularAttribute<? super Product, ?> getAttribute() {
        return attribute;
    }

    public void setAttribute(SingularAttribute<? super Product, ?> attribute) {
        this.attribute = attribute;
    }
    
}
