package com.unionman.shiro.jpa.simple;

import com.unionman.shiro.constants.CommonConstant;
import com.unionman.shiro.constants.NumberConstant;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 简单条件表达式
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public class SimplePredicate implements IPredicate {
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * 对应值
     */
    private Object value;
    /**
     * 计算符
     */
    private Operator operator;

    public SimplePredicate(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public SimplePredicate(String fieldName, Operator operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(Root<?> root, CriteriaBuilder builder) {

        Path path;

        //此处是表关联数据，注意仅限一层关联，如user.address，
        //查询user的address集合中，address的name为某个值
        if (StringUtils.contains(fieldName, CommonConstant.POINT)) {

            String[] names = StringUtils.split(fieldName, CommonConstant.POINT);

            //获取该属性的类型，Set？List？Map？
            path = root.get(names[0]);
            Class clazz = path.getJavaType();
            if (clazz.equals(Set.class)) {
                SetJoin setJoin = root.joinSet(names[0]);
                path = setJoin.get(names[1]);
            } else if (clazz.equals(List.class)) {
                ListJoin listJoin = root.joinList(names[0]);
                path = listJoin.get(names[1]);
            } else if (clazz.equals(Map.class)) {
                MapJoin mapJoin = root.joinMap(names[0]);
                path = mapJoin.get(names[1]);
            } else {
                //是many to one时
                path = path.get(names[1]);
            }

//            expression = root.get(names[0]);
//            for (int i = 1; i < names.length; i++) {
//                expression = expression.get(names[i]);
//            }

        } else {

            //单表查询
            path = root.get(fieldName);
        }

        switch (operator) {
            case IS_NULL:
                return builder.isNull(path);
            case IS_NOT_NULL:
                return builder.isNotNull(path);
            case EQ:
            case JOIN_EQ:
                return builder.equal(path, value);
            case NE:
            case JOIN_NE:
                return builder.notEqual(path, value);
            case LIKE:
            case JOIN_LIKE:
                return builder.like((Expression<String>) path, CommonConstant.PERCENT + value + CommonConstant.PERCENT);
            case LT:
                return builder.lessThan(path, (Comparable) value);
            case GT:
                return builder.greaterThan(path, (Comparable) value);
            case LTE:
                return builder.lessThanOrEqualTo(path, (Comparable) value);
            case GTE:
                return builder.greaterThanOrEqualTo(path, (Comparable) value);
            case IS_MEMBER:
                return builder.isMember(value, path);
            case IS_NOT_MEMBER:
                return builder.isNotMember(value, path);
            case NOT_LIKE:
                return builder.notLike((Expression<String>) path, CommonConstant.PERCENT + value + CommonConstant.PERCENT);
            case BETWEEN:
            case JOIN_BETWEEN:
                String[] betweenArr = StringUtils.split(value.toString(), CommonConstant.STRIKETHROUGH);
                return builder.between(path, Long.valueOf(betweenArr[NumberConstant.ZERO]), Long.valueOf(betweenArr[NumberConstant.ONE]));
            default:
                return null;
        }
    }

}