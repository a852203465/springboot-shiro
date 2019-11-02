package com.unionman.shiro.service.base.impl;

import com.unionman.shiro.exception.SpringbootShiroException;
import com.unionman.shiro.repository.base.BaseRepository;
import com.unionman.shiro.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @description: service层通用方法实现
 * @author: Rong.Jia
 * @date: 2019/01/14 16:59
 */
@Slf4j
public class BaseServiceImpl<T> implements BaseService<T> {

    /**
     * @description: 注入baseRepository, 访问持久层
     * @author: Rong.Jia
     * @date: 2019/01/14 16:59
     */
    @Autowired
    private BaseRepository<T> baseRepository;

    @Override
    public T findById(Integer id) {

        T t;
        try {
            Optional optional = baseRepository.findById(id);
            t = (T) optional.get();
        } catch (NoSuchElementException e) {
            return null;
        }

        return t;
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Transactional(rollbackFor = SpringbootShiroException.class)
    @Override
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    @Transactional(rollbackFor = SpringbootShiroException.class)
    @Override
    public void deleteById(Integer id) {
        baseRepository.deleteById(id);
    }

    @Transactional(rollbackFor = SpringbootShiroException.class)
    @Override
    public T insetNew(T entity) {
        return baseRepository.save(entity);
    }

    @Transactional(rollbackFor = SpringbootShiroException.class)
    @Override
    public List<T> insetNewAll(List<T> entity){

        return baseRepository.saveAll(entity);
    }

    @Transactional(rollbackFor = SpringbootShiroException.class)
    @Override
    public T modify(T entity) {
        return baseRepository.saveAndFlush(entity);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {

        return baseRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = SpringbootShiroException.class)
    @Override
    public void deleteInBatch(List<T> entities) {

        baseRepository.deleteInBatch(entities);
    }

    @Override
    @Transactional(rollbackFor = SpringbootShiroException.class)
    public void deleteAll() {
        baseRepository.deleteAll();
    }
}
