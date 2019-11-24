package com.unionman.shiro.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.List;

/**
 * 适用于对单表做sum、avg、count等运算时使用，并且查询条件不固定，需要动态生成predicate</p>
 * 如select sum(a), count(b), count distinct(c) from table where a = ? & b = ?
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public interface CriteriaQueryBuilder<T> extends Specification<T> {

    /**
     * 构建select字段
     */
    List<Selection<?>> buildSelections(CriteriaBuilder builder, Root<T> root);

    /**
     * 构建groupBy字段
     */
    List<Expression<?>> buildGroupBy(Root<T> root);

    /**
     * 获取返回的结果集
     */
    List<Tuple> findResult(EntityManager entityManager, Class<T> t);
}
                        