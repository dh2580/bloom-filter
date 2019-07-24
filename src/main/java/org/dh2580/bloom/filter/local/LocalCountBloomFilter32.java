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

        int[] hashPositions = hashPositions(elem);

        boolean removed = false;

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            if (!getBit(hashPosition)) {
                continue;
            }

            removed = true;

            counters32[hashPosition]--;

            if (counters32[hashPosition] == 0) {
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

            if (!getBit(hashPosition) || counters32[hashPosition] < Byte.MAX_VALUE) {
                added = true;
                longs[longIdx] = longs[longIdx] | (1L << (63 - bitOffset));
                counters32[hashPosition]++;
            }
        }

        return added;
    }
}
