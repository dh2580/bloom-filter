package org.dh2580.bloom.filter.local;

import org.dh2580.bloom.filter.BloomData;

/**
 * @Author: hao.deng
 * @Date: 2019/7/24-下午7:46
 */
public class MutableBloomFilter<T> extends LocalBloomFilter<T> {

    private long[] longs;

    private int maxBitIndex;

    private int hashCount;

    public MutableBloomFilter(BloomData data) {
        this.longs = data.getLongs();
        this.maxBitIndex = data.getMaxBitIndex();
        this.hashCount = data.getHashCount();
    }

    @Override
    public boolean add(T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BloomData export() {
        return BloomData.of(longs, maxBitIndex, hashCount);
    }
}
