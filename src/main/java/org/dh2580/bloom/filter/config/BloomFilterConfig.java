package org.dh2580.bloom.filter.config;

import org.dh2580.bloom.filter.hash.HashFunction;
import org.dh2580.bloom.filter.hash.Murmur3;
import org.dh2580.bloom.filter.serializer.DefaultElementSerializer;
import org.dh2580.bloom.filter.serializer.ElementSerializer;

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
    private ElementSerializer<T> elementSerializer = new DefaultElementSerializer<>();

    public HashFunction getHashFunction() {
        return hashFunction;
    }

    public ElementSerializer<T> getElementSerializer() {
        return elementSerializer;
    }
}
