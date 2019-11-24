package com.unionman.shiro.jpa.simple;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * 构建groupBy语句
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public class SimpleExpression implements IExpression {

    private String field;

    public SimpleExpression(String field) {
        this.field = field;
    }

    @Override
    public Expression getGroupBy(Root root) {
        return root.get(field);
    }

}
