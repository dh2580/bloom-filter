package org.dh2580.bloom.filter.hash;

/**
 * Hash接口
 *
 * @Author: hao.deng
 * @Date: 2019/7/12-下午6:22
 */
public interface HashFunction {

    /**
     * @param val String类型参数
     * @param m   布隆过滤器bits数量
     * @param k   哈希计算次数
     * @return 返回计算出的bit index列表
     */
    int[] hash(String val, int m, int k);

    /**
     * @param val int类型参数
     * @param m   布隆过滤器bits数量
     * @param k   哈希计算次数
     * @return 返回计算出的bit index列表
     */
    int[] hash(int val, int m, int k);

    /**
     * @param val long类型参数
     * @param m   布隆过滤器bits数量
     * @param k   哈希计算次数
     * @return 返回计算出的bit index列表
     */
    int[] hash(long val, int m, int k);
}
