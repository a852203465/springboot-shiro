package com.unionman.shiro.jpa.simple;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 * 要查询的字段
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public class SimpleSelector implements ISelector {

    /**
     * 属性名
     */
    private String fieldName;

    private ISelector.Operator operator;

    public SimpleSelector(String fieldName, ISelector.Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    @Override
    public Selection getSelection(Root root, CriteriaBuilder builder) {
        Path path = root.get(fieldName);

        switch (operator) {
            case COUNT:
                return builder.count(path);
            case COUNT_DISTINCT:
                return builder.countDistinct(path);
            case AVG:
                return builder.avg(path);
            case SUM:
                return builder.sum(path);
            case MAX:
                return builder.max(path);
            case MIN:
                return builder.min(path);
            //单独查某列
            case PICK:
                return path;
            default:
                return null;
        }
    }
}
