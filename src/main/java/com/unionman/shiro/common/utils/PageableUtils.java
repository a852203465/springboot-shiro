package com.unionman.shiro.common.utils;

import com.unionman.shiro.common.constants.CommonConstant;
import com.unionman.shiro.common.dto.SortDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @description: 分页封装类
 * @author: Rong.Jia
 * @date: 2018/6/20 10:58
 */
public class PageableUtils {

    /**
     * 获取基础分页对象
     * @param page 获取第几页
     * @param size 每页条数
     * @param dtos 排序对象数组
     * @return Pageable 分页信息对象
     */
    public static Pageable basicPage(Integer page, Integer size, SortDTO... dtos) {
        Sort sort = SortUtils.basicSort(dtos);
        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size <= 0) ? CommonConstant.DEFAULT_PAGE_SIZE : size;
        Pageable pageable = PageRequest.of(page, size, sort);
        return pageable;
    }

    /**
     * 获取基础分页对象，每页条数默认20条
     *  - 默认以id降序排序
     * @param page 获取第几页
     * @return Pageable 分页信息对象
     */
    public static Pageable basicPage(Integer page) {
        return basicPage(page, 0, new SortDTO(CommonConstant.DEFAULT_ORDER_TYPE, CommonConstant.DEFAULT_ORDER_FIELD));
    }

    /**
     * 获取基础分页对象，每页条数默认20条
     * @param page 获取第几页
     * @param dtos 排序对象数组
     * @return Pageable 分页信息对象
     */
    public static Pageable basicPage(Integer page, SortDTO... dtos) {
        return basicPage(page, 0, dtos);
    }

    /**
     * 获取基础分页对象，排序方式默认降序
     * @param page 获取第几页
     * @param size 每页条数
     * @param orderField 排序字段
     * @return Pageable 分页信息对象
     */
    public static Pageable basicPage(Integer page, Integer size, String orderField) {
        return basicPage(page, size, new SortDTO(CommonConstant.DEFAULT_ORDER_TYPE, orderField));
    }

    /**
     * 获取基础分页对象
     *  - 每页条数默认20条
     *  - 排序方式默认降序
     * @param page 获取第几页
     * @param orderField 排序字段
     * @return Pageable 分页信息对象
     */
    public static Pageable basicPage(Integer page, String orderField) {
        return basicPage(page, 0, new SortDTO(CommonConstant.DEFAULT_ORDER_TYPE, orderField));
    }
}
