package org.dh2580.bloom.filter.local;

import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/24-下午8:18
 */
public class LocalCountBloomFilter16<T> extends LocalCountBloomFilter<T>{


    private short[] counters16;

    public LocalCountBloomFilter16(BloomFilterConfig<T> config) {
        super(config);

        this.counters16 = new short[config.size()];
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

            counters16[hashPosition]--;

            if (counters16[hashPosition] == 0) {
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

            if (!getBit(hashPosition) || counters16[hashPosition] < Byte.MAX_VALUE) {
                added = true;
                longs[longIdx] = longs[longIdx] | (1L << (63 - bitOffset));
                counters16[hashPosition]++;
            }
        }

        return added;
    }
}
