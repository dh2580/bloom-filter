package org.dh2580.bloom.filter.serializer;

/**
 * 元素序列化接口
 *
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:46
 */
public interface ElementSerializer<T> {

    /**
     * @param elem 元素
     * @return
     */
    byte[] serialize(T elem);
}
