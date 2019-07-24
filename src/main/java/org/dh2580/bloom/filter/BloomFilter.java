package org.dh2580.bloom.filter;

import java.util.List;

/**
 * 布隆过滤器接口
 *
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:26
 */
public interface BloomFilter<T> {

    /**
     * 添加一个元素到布隆过滤器
     *
     * @param elem 元素
     * @return 返回是否添加成功
     */
    boolean add(T elem);

    /**
     * 检测一个元素是否可能存在布隆过滤器中
     *
     * @param elem 元素
     * @return 返回是否可能包含
     */
    boolean mightContains(T elem);

    /**
     * 对一个元素列表进行过滤
     *
     * @param elems 元素列表
     * @return 返回过滤后的元素列表
     */
    List<T> filter(List<T> elems);

    /**
     * 导出布隆过滤器中的内容
     *
     * @return 返回导出的布隆过滤器内容
     */
    BloomData export();
}
