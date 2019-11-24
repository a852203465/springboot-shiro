package com.unionman.shiro.jpa;

import com.unionman.shiro.jpa.simple.IExpression;
import com.unionman.shiro.jpa.simple.IPredicate;
import com.unionman.shiro.jpa.simple.ISelector;
import com.unionman.shiro.utils.AssertUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义一个查询条件容器，用于构建where条件、select字段、groupBy字段
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public class NbQueryBuilder<T> implements CriteriaQueryBuilder<T> {

    private static final long serialVersionUID = 5798621000240292591L;

    private List<IPredicate> criterionList = new ArrayList<>();

    private List<ISelector> selectorList = new ArrayList<>();

    private List<IExpression> expressionList = new ArrayList<>();

    @Override
    public List<Selection<?>> buildSelections(CriteriaBuilder builder, Root<T> root) {
        List<Selection<?>> selections = new ArrayList<>();
        for (ISelector iSelector : selectorList) {
            selections.add(iSelector.getSelection(root, builder));
        }
        return selections;
    }

    @Override
    public List<Expression<?>> buildGroupBy(Root<T> root) {
        List<Expression<?>> expressions = new ArrayList<>();
        for (IExpression expression : expressionList) {
            expressions.add(expression.getGroupBy(root));
        }
        return expressions;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if (AssertUtils.isNotNull(criterionList)) {
            List<Predicate> predicates = new ArrayList<>();
            for (IPredicate c : criterionList) {
                if (AssertUtils.isNotNull(c)) {
                    predicates.add(c.toPredicate(root, criteriaBuilder));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
        return criteriaBuilder.conjunction();
    }

    @Override
    public List<Tuple> findResult(EntityManager entityManager, Class<T> t) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<T> root = criteriaQuery.from(t);

        if (AssertUtils.isNotNull(selectorList)) {
            criteriaQuery.multiselect(buildSelections(criteriaBuilder, root));
        }
        if (AssertUtils.isNotNull(criterionList)) {
            criteriaQuery.groupBy(buildGroupBy(root));
        }

        criteriaQuery.where(toPredicate(root, criteriaQuery, criteriaBuilder));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * 增加简单条件表达式
     */
    public void add(ISelector iSelector) {
        if (AssertUtils.isNotNull(iSelector)) {
            selectorList.add(iSelector);
        }
    }

    /**
     * 增加where子语句
     */
    public void add(IPredicate iPredicate) {
        if (AssertUtils.isNotNull(iPredicate)) {
            criterionList.add(iPredicate);
        }
    }

    /**
     * 增加尾部语句
     */
    public void add(IExpression iExpression) {
        if (AssertUtils.isNotNull(iExpression)) {
            expressionList.add(iExpression);
        }
    }

    public <R extends ISelector> void addAll(List<R> selectors) {
        selectorList.addAll(selectors);
    }
}