package com.unionman.shiro.jpa.simple;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 * 要筛选的列，或者表达式（平均数、sum等）
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public interface ISelector {

    enum Operator {

        // 操作枚举
        COUNT, COUNT_DISTINCT, SUM, AVG, MAX, MIN, PICK
    }

    Selection getSelection(Root<?> root, CriteriaBuilder builder);
}