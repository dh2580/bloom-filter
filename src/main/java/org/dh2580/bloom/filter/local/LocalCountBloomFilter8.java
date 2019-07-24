package org.dh2580.bloom.filter.local;

import java.util.List;

import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/24-下午8:18
 */
public class LocalCountBloomFilter8<T> extends LocalCountBloomFilter<T> {

    private byte[] counters8;

    public LocalCountBloomFilter8(BloomFilterConfig<T> config) {
        super(config);

        this.counters8 = new byte[config.size()];
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

            counters8[hashPosition]--;

            if (counters8[hashPosition] == 0) {
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

            if (!getBit(hashPosition) || counters8[hashPosition] < Byte.MAX_VALUE) {
                added = true;
                longs[longIdx] = longs[longIdx] | (1L << (63 - bitOffset));
                counters8[hashPosition]++;
            }
        }

        return added;
    }
}
