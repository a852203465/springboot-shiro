package com.unionman.shiro.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description 抽取service层通用方法
 * @param <T>
 * @author Rong.Jia
 * @date 2019/01/14 17:00
 */
public interface BaseService<T> {

    /**
     * @description 根据id 获取信息
     * @param id 信息唯一标识
     * @return T 获取的信息
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    T findById(Integer id);

    /**
     * @description 获取所有的信息
     * @return 将获取的信息封装到List中 返回
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    List<T> findAll();

    /**
     * @description 删除指定的信息
     * @param entity 实体类信息
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    void delete(T entity);

    /**
     * @description 根据id 删除信息
     * @param id 唯一标识
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    void deleteById(Integer id);

    /**
     * @description 添加一个新的信息
     * @param entity 信息对象
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     * @return T 新增的对象信息
     */
    T insetNew(T entity);


    /**
     * @description 添加多个新的信息
     * @param entity 信息对象List
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     * @return  List<T> 批量增加的对象列表
     */
    List<T> insetNewAll(List<T> entity);

    /**
     * @description 修改信息
     * @param entity 信息对象
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     * @return T 修改成功的信息对象
     */
    T modify(T entity);

    /**
     * @description 分页
     * @param pageable 分页参数
     * @return 分页数据
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    Page<T> findAll(Pageable pageable);

    /**
     * @description 多选删除
     * @param entities 待删除对象集合
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    void deleteInBatch(List<T> entities);

    /**
     * @description: 删除所有
     * @author Rong.Jia
     * @date 2019/01/14 17:00
     */
    void deleteAll();


}
