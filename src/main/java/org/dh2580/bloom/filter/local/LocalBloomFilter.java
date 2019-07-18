package org.dh2580.bloom.filter.local;

import java.util.List;

import org.dh2580.bloom.filter.BloomFilter;
import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:26
 */
public class LocalBloomFilter<T> implements BloomFilter<T> {

    private BloomFilterConfig<T> config;

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
