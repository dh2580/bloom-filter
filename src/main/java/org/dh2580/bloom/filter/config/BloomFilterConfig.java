package org.dh2580.bloom.filter.config;

import org.dh2580.bloom.filter.BloomFilter;
import org.dh2580.bloom.filter.CountBloomFilter;
import org.dh2580.bloom.filter.hash.HashFunction;
import org.dh2580.bloom.filter.hash.Murmur3;
import org.dh2580.bloom.filter.local.LocalBloomFilter;
import org.dh2580.bloom.filter.local.LocalCountBloomFilter;
import org.dh2580.bloom.filter.local.LocalCountBloomFilter16;
import org.dh2580.bloom.filter.local.LocalCountBloomFilter32;
import org.dh2580.bloom.filter.local.LocalCountBloomFilter64;
import org.dh2580.bloom.filter.local.LocalCountBloomFilter8;
import org.dh2580.bloom.filter.serializer.ElemSerializer;
import org.dh2580.bloom.filter.serializer.ElemStringSerializer;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:56
 */
public class BloomFilterConfig<T> {

    /**
     * Hash 计算函数
     */
    private HashFunction hashFunction = new Murmur3();

    /**
     * 元素序列化器
     */
    private ElemSerializer<T> elemSerializer = new ElemStringSerializer<>();

    /**
     * 期待的元素个数
     */
    private int expectedElems;

    /**
     * 布隆bit数量
     */
    private int size;

    /**
     * 哈希计算次数
     */
    private int hashCount;

    /**
     * 计数占用bit数量
     */
    private int countBits = 0;

    public BloomFilterConfig(int expectedElems, double falsePositiveProbability) {

        this.expectedElems = expectedElems;

        this.size = (int) Math.ceil(-1 * (expectedElems * Math.log(falsePositiveProbability)) / Math.pow(Math.log(2), 2));

        this.hashCount = (int) Math.ceil((Math.log(2) * this.size) / this.expectedElems);

    }

    public BloomFilterConfig setHashFunction(HashFunction hashFunction) {
        this.hashFunction = hashFunction;

        return this;
    }

    public BloomFilterConfig setElementSerializer(ElemSerializer<T> elemSerializer) {
        this.elemSerializer = elemSerializer;

        return this;
    }

    public BloomFilterConfig setCountBits(int countBits) {
        this.countBits = countBits;

        return this;
    }

    public BloomFilter<T> buildBloomFilter() {
        return new LocalBloomFilter<T>(this);
    }

    public CountBloomFilter<T> buildCountBloomFilter() {
        if (this.countBits() <= 0) {
            throw new IllegalArgumentException("countBit must be greater than zero");
        }

        if (countBits < 8) {
            countBits = 8;
            return new LocalCountBloomFilter8<>(this);
        }

        if (8 < countBits & countBits < 16) {
            countBits = 16;
            return new LocalCountBloomFilter16<>(this);
        }

        if (16 < countBits && countBits < 32) {
            countBits = 32;
            return new LocalCountBloomFilter32<>(this);
        }

        countBits = 64;
        return new LocalCountBloomFilter64<>(this);
    }

    //---------- Getter Property

    public HashFunction hashFunction() {
        return hashFunction;
    }

    public ElemSerializer<T> elemSerializer() {
        return elemSerializer;
    }

    public int expectedElems() {
        return expectedElems;
    }

    public int hashCount() {
        return hashCount;
    }

    public int size() {
        return size;
    }

    public int countBits() {
        return countBits;
    }
}
