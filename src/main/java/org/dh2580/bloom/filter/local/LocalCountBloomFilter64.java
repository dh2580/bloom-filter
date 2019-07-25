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

        //如果布隆过滤不包含，则不进行移除操作
        if (!this.mightContains(elem)) {
            return false;
        }

        int[] hashPositions = hashPositions(elem);

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            counters64[hashPosition]--;

            if (counters64[hashPosition] == 0) {
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
            if (counters64[hashPosition] >= Byte.MAX_VALUE) {
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
            counters64[hashPosition]++;
        }

        return true;
    }
}
