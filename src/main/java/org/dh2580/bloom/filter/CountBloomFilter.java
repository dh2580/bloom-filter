package org.dh2580.bloom.filter;

/**
 * 带有计数功能的布隆过滤器
 *
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:26
 */
public interface CountBloomFilter<T> extends BloomFilter<T> {
    /**
     * 从布隆过滤器中移除一个元素
     *
     * @param elem 元素
     * @return 返回是否从布隆过滤器移除成功
     */
    boolean remove(T elem);
}
