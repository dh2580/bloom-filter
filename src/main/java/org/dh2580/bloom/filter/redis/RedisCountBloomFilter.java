package org.dh2580.bloom.filter.redis;

import java.util.List;

import org.dh2580.bloom.filter.BloomData;
import org.dh2580.bloom.filter.CountBloomFilter;
import org.dh2580.bloom.filter.config.RedisBloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:28
 */
public class RedisCountBloomFilter<T> implements CountBloomFilter<T> {

    private RedisBloomFilterConfig<T> config;

    @Override
    public boolean remove(Object elem) {
        return false;
    }

    @Override
    public boolean add(Object elem) {
        return false;
    }

    @Override
    public boolean mightContains(Object elem) {
        return false;
    }

    @Override
    public List filter(List elems) {
        return null;
    }

    @Override
    public BloomData export() {
        return null;
    }
}
