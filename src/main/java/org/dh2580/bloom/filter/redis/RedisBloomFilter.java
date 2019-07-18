package org.dh2580.bloom.filter.redis;

import java.util.List;

import org.dh2580.bloom.filter.BloomFilter;
import org.dh2580.bloom.filter.config.RedisBloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:27
 */
public class RedisBloomFilter<T> implements BloomFilter<T> {

    private RedisBloomFilterConfig<T> config;

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
    public Data export() {
        return null;
    }
}
