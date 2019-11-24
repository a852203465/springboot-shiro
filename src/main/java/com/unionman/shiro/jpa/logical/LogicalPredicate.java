package com.unionman.shiro.jpa.logical;

import com.unionman.shiro.jpa.simple.IPredicate;
import com.unionman.shiro.utils.AssertUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑条件表达式 用于复杂条件时使用，如单属性多对应值的OR查询等
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public class LogicalPredicate implements IPredicate {

    /**
     * 逻辑表达式中包含的表达式
     */
    private IPredicate[] iCriteriaList;

    /**
     * 计算符
     */
    private Operator operator;

    public LogicalPredicate(IPredicate[] iCriteriaList, Operator operator) {
        this.iCriteriaList = iCriteriaList;
        this.operator = operator;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        for (IPredicate iPredicate : this.iCriteriaList) {
            if (AssertUtils.isNotNull(iPredicate)) {
                predicates.add(iPredicate.toPredicate(root, builder));
            }
        }

        switch (operator) {
            case AND:
                return builder.and(predicates.toArray(new Predicate[0]));
            case OR:
                return builder.or(predicates.toArray(new Predicate[0]));
            default:
                return null;
        }
    }

}