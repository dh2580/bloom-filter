package org.dh2580.bloom.filter;

import org.dh2580.bloom.filter.local.ImmutableBloomFilter;

/**
 * 布隆存储对象
 *
 * @Author: hao.deng
 * @Date: 2019/7/24-下午7:55
 */
public class BloomData {
    public static BloomData of(long[] longs, int maxBitIndex, int hashCount) {
        return new BloomData(longs, maxBitIndex, hashCount);
    }

    public static <T> BloomFilter<T> build(BloomData data) {
        return new ImmutableBloomFilter<T>(data);
    }

    /**
     * bit map 数据
     */
    private long[] longs;

    /**
     * 最大位下标
     */
    private int maxBitIndex;

    /**
     * hash计算轮数
     */
    private int hashCount;

    private BloomData(long[] longs, int maxBitIndex, int hashCount) {
        this.longs = longs;
        this.maxBitIndex = maxBitIndex;
        this.hashCount = hashCount;
    }

    public long[] getLongs() {
        return longs;
    }

    public int getMaxBitIndex() {
        return maxBitIndex;
    }

    public int getHashCount() {
        return hashCount;
    }
}
