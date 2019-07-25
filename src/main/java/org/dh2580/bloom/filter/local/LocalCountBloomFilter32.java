package org.dh2580.bloom.filter.local;

import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/24-下午8:18
 */
public class LocalCountBloomFilter32<T> extends LocalCountBloomFilter<T> {
    private int[] counters32;


    public LocalCountBloomFilter32(BloomFilterConfig<T> config) {
        super(config);

        this.counters32 = new int[config.size()];
    }

    @Override
    public boolean remove(T elem) {
        if (elem == null) {
            throw new NullPointerException("elem is null");
        }

        //如果布隆过滤不包含，则不进行移除操作
        if (!this.mightContains(elem)) {
            return false;
        }

        int[] hashPositions = hashPositions(elem);

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            counters32[hashPosition]--;

            if (counters32[hashPosition] == 0) {
                longs[longIdx] = longs[longIdx] & ~(1L << (63 - bitOffset));
            }
        }

        return true;
    }

    @Override
    public boolean add(T elem) {
        if (elem == null) {
            throw new NullPointerException("elem is null");
        }

        int[] hashPositions = hashPositions(elem);

        boolean canAdd = true;
        for (int hashPosition : hashPositions) {
            if (counters32[hashPosition] >= Byte.MAX_VALUE) {
                canAdd = false;
                break;
            }
        }
        if (!canAdd) {
            return false;
        }

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            longs[longIdx] = longs[longIdx] | (1L << (63 - bitOffset));
            counters32[hashPosition]++;
        }

        return true;
    }
}
