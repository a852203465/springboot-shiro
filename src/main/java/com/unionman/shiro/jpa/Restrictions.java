package com.unionman.shiro.jpa;

import com.unionman.shiro.constants.CommonConstant;
import com.unionman.shiro.constants.NumberConstant;
import com.unionman.shiro.jpa.logical.LogicalPredicate;
import com.unionman.shiro.jpa.simple.*;
import com.unionman.shiro.utils.AssertUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 条件构造器
 * 用于创建条件表达式
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public class Restrictions {

    public static SimpleSelector pick(String fieldName) {
        return new SimpleSelector(fieldName, ISelector.Operator.PICK);
    }

    public static List<SimpleSelector> pickSome(String... fieldNames) {
        List<SimpleSelector> simpleSelectors = new ArrayList<>(fieldNames.length);
        for (String field : fieldNames) {
            simpleSelectors.add(pick(field));
        }
        return simpleSelectors;
    }

    public static SimpleSelector count(String fieldName) {
        return new SimpleSelector(fieldName, ISelector.Operator.COUNT);
    }

    public static SimpleSelector countDistinct(String fieldName) {
        return new SimpleSelector(fieldName, ISelector.Operator.COUNT_DISTINCT);
    }

    public static SimpleSelector sum(String fieldName) {
        return new SimpleSelector(fieldName, ISelector.Operator.SUM);
    }

    public static SimpleSelector max(String fieldName) {
        return new SimpleSelector(fieldName, ISelector.Operator.MAX);
    }

    public static SimpleSelector min(String fieldName) {
        return new SimpleSelector(fieldName, ISelector.Operator.MIN);
    }

    public static SimpleExpression groupBy(String fieldName) {
        return new SimpleExpression(fieldName);
    }

    /**
     * 等于 null
     */
    public static SimplePredicate isNull(String fieldName) {

        return new SimplePredicate(fieldName, IPredicate.Operator.IS_NULL);
    }

    /**
     * 不等于 null
     */
    public static SimplePredicate isNotNull(String fieldName) {

        return new SimplePredicate(fieldName, IPredicate.Operator.IS_NOT_NULL);
    }

    /**
     * 等于
     */
    public static SimplePredicate eq(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.EQ);
    }

    /**
     * 集合包含某个元素
     */
    public static SimplePredicate hasMember(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.IS_MEMBER);
    }

    /**
     * join 模糊查询
     */
    public static SimplePredicate joinLike(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.JOIN_LIKE);
    }

    /**
     * join 等于
     */
    public static SimplePredicate joinEq(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.JOIN_EQ);
    }

    /**
     * join 不等于
     */
    public static SimplePredicate joinNe(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.JOIN_NE);
    }

    /**
     * 不等于
     */
    public static SimplePredicate ne(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.NE);
    }

    /**
     * 模糊匹配
     */
    public static SimplePredicate like(String fieldName, String value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.LIKE);
    }

    /**
     * 反模糊匹配
     */
    public static SimplePredicate notLike(String fieldName, String value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.NOT_LIKE);
    }

    /**
     * 时间区间查询
     * @param fieldName 属性名称
     * @param lo 属性起始值
     * @param go 属性结束值
     */
    public static SimplePredicate between(String fieldName, Long lo, Long go, boolean ignoreNull) {

        if (ignoreNull && (AssertUtils.isNull(lo) || AssertUtils.isNull(go)) || (lo <= NumberConstant.ZERO.longValue() || go <= NumberConstant.ZERO.longValue())) {
            return null;
        }

        String value = String.valueOf(lo) + CommonConstant.STRIKETHROUGH + String.valueOf(go);

        return new SimplePredicate(fieldName, value, IPredicate.Operator.BETWEEN);
    }

    /**
     * 时间区间查询
     * @param fieldName 属性名称
     * @param lo 属性起始值
     * @param go 属性结束值
     */
    public static SimplePredicate joinBetween(String fieldName, Long lo, Long go, boolean ignoreNull) {

        if (ignoreNull && (AssertUtils.isNull(lo) || AssertUtils.isNull(go)) || (lo <= NumberConstant.ZERO.longValue() || go <= NumberConstant.ZERO.longValue())) {
            return null;
        }

        String value = String.valueOf(lo) + CommonConstant.STRIKETHROUGH + String.valueOf(go);

        return new SimplePredicate(fieldName, value, IPredicate.Operator.JOIN_BETWEEN);
    }

    /**
     * 大于
     */
    public static SimplePredicate gt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.GT);
    }

    /**
     * 小于
     */
    public static SimplePredicate lt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.LT);
    }

    /**
     * 小于等于
     */
    public static SimplePredicate lte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.LTE);
    }

    /**
     * 大于等于
     */
    public static SimplePredicate gte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && AssertUtils.isNull(value)) {
            return null;
        }
        return new SimplePredicate(fieldName, value, IPredicate.Operator.GTE);
    }

    /**
     * 并且
     */
    public static LogicalPredicate and(IPredicate... iPredicates) {
        return new LogicalPredicate(iPredicates, IPredicate.Operator.AND);
    }

    /**
     * 或者
     */
    public static LogicalPredicate or(IPredicate... iPredicates) {
        return new LogicalPredicate(iPredicates, IPredicate.Operator.OR);
    }

    /**
     * 包含于
     */
    @SuppressWarnings("rawtypes")
    public static LogicalPredicate in(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimplePredicate[] ses = new SimplePredicate[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimplePredicate(fieldName, obj, IPredicate.Operator.EQ);
            i++;
        }
        return new LogicalPredicate(ses, IPredicate.Operator.OR);
    }

    /**
     * 集合包含某几个元素，譬如可以查询User类中Set<String> set包含"ABC","bcd"的User集合，
     * 或者查询User中Set<Address>的Address的name为"北京"的所有User集合
     * 集合可以为基本类型或者JavaBean，可以是one to many或者是@ElementCollection
     *
     * @param fieldName
     *         列名
     * @param value
     *         集合
     * @return expresssion
     */
    public static LogicalPredicate hasMembers(String fieldName, Object... value) {

        SimplePredicate[] ses = new SimplePredicate[value.length];

        int i = 0;

        //集合中对象是基本类型，如Set<Long>，List<String>
        IPredicate.Operator operator = IPredicate.Operator.IS_MEMBER;

        //集合中对象是JavaBean
        if (StringUtils.contains(fieldName, CommonConstant.POINT)) {
            operator = IPredicate.Operator.EQ;
        }

        for (Object obj : value) {

            ses[i] = new SimplePredicate(fieldName, obj, operator);
            i++;
        }
        return new LogicalPredicate(ses, IPredicate.Operator.OR);
    }

}