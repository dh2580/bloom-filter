package org.dh2580.bloom.filter.local;

import java.util.ArrayList;
import java.util.List;

import org.dh2580.bloom.filter.BloomData;
import org.dh2580.bloom.filter.BloomFilter;
import org.dh2580.bloom.filter.config.BloomFilterConfig;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:26
 */
public class LocalBloomFilter<T> implements BloomFilter<T> {

    private BloomFilterConfig<T> config;

    private long[] longs;

    LocalBloomFilter() {
    }

    public LocalBloomFilter(BloomFilterConfig<T> config) {
        this.config = config;

        longs = new long[config.size() % 64 == 0 ? config.size() / 64 : config.size() / 64 + 1];
    }

    @Override
    public boolean add(T elem) {
        if (elem == null) {
            throw new NullPointerException("elem is null");
        }

        int[] hashPositions = hashPositions(elem);

        for (int hashPosition : hashPositions) {
            int longIdx = hashPosition / 64;
            int bitOffset = hashPosition % 64;

            longs[longIdx] = longs[longIdx] | (1L << (63 - bitOffset));
        }

        return true;
    }

    @Override
    public boolean mightContains(T elem) {
        if (elem == null) {
            throw new NullPointerException("elem is null");
        }

        int[] hashPositions = hashPositions(elem);

        boolean contains = true;

        for (int hashPosition : hashPositions) {
            if (!getBit(hashPosition)) {
                contains = false;
                break;
            }
        }

        return contains;
    }

    @Override
    public List<T> filter(List<T> elems) {
        if (elems == null) {
            throw new NullPointerException("elems is null");
        }

        List<T> res = new ArrayList<>(elems.size());

        for (T elem : elems) {
            if (this.mightContains(elem)) {
                continue;
            }
            res.add(elem);
        }

        return res;
    }

    @Override
    public BloomData export() {
        return BloomData.of(longs, config.size(), config.hashCount());
    }

    private int[] hashPositions(T elem) {
        if (elem instanceof Integer) {
            return config.hashFunction().hash((Integer) elem, config.size(), config.hashCount());
        }

        if (elem instanceof Long) {
            return config.hashFunction().hash((Long) elem, config.size(), config.hashCount());
        }

        if (elem instanceof String) {
            return config.hashFunction().hash((String) elem, config.size(), config.hashCount());
        }

        return config.hashFunction().hash(config.elemSerializer().serialize(elem), config.size(), config.hashCount());
    }

    private boolean getBit(int position) {
        int longIdx = position / 64;
        int bitOffset = position % 64;

        return (longs[longIdx] & (1L << (63 - bitOffset))) != 0L;
    }
}
