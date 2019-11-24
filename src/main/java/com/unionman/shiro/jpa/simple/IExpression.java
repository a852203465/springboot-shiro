package com.unionman.shiro.jpa.simple;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public interface IExpression {

    Expression getGroupBy(Root<?> root);

}
