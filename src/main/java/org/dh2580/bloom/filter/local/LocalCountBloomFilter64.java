package org.dh2580.bloom.filter.local;

import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/24-下午8:19
 */
public class LocalCountBloomFilter64<T> extends LocalCountBloomFilter<T>{

    private long[] counters64;

    public LocalCountBloomFilter64(BloomFilterConfig<T> config) {
        super(config);

        this.counters64 = new long[config.size()];
    }

    @Override
    public boolean remove(T elem) {
        if (elem == null) {
            throw new NullPointerException("elem is null");
        }

        int[] hashPositions = hashPositions(elem);

        boolean removed = false;

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            if (!getBit(hashPosition)) {
                continue;
            }

            removed = true;

            counters64[hashPosition]--;

            if (counters64[hashPosition] == 0) {
                longs[longIdx] = longs[longIdx] & ~(1L << (63 - bitOffset));
            }
        }

        return removed;
    }

    @Override
    public boolean add(T elem) {
        if (elem == null) {
            throw new NullPointerException("elem is null");
        }

        int[] hashPositions = hashPositions(elem);

        boolean added = false;

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            if (!getBit(hashPosition) || counters64[hashPosition] < Byte.MAX_VALUE) {
                added = true;
                longs[longIdx] = longs[longIdx] | (1L << (63 - bitOffset));
                counters64[hashPosition]++;
            }
        }

        return added;
    }
}
