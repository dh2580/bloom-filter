package org.dh2580.bloom.filter.hash;

/**
 * @Author: hao.deng
 * @Date: 2019/7/12-下午6:22
 */
public interface HashFunction {
    int[] hash(byte[] bytes, int m, int k);
}
