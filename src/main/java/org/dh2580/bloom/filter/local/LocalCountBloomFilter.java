package org.dh2580.bloom.filter.local;

import java.util.List;

import org.dh2580.bloom.filter.CountBloomFilter;
import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:27
 */
public class LocalCountBloomFilter<T> implements CountBloomFilter<T> {

    private BloomFilterConfig<T> config;

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
    public Data export() {
        return null;
    }
}
