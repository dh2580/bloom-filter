package org.dh2580.bloom.filter.config;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午6:02
 */
public class RedisBloomFilterConfig<T> extends BloomFilterConfig<T> {

    public static final String KEY_SUFFIX = ":bit";

    public static final String COUNTER_SUFFIX = ":count";

    private String name;

    private String host = "127.0.0.1";

    private int port = 6379;

    private int database = 0;

    private int maxTotal = 20;

    private int minIdle = 5;

    public RedisBloomFilterConfig(int expectedElems, double falsePositiveProbability) {
        super(expectedElems, falsePositiveProbability);
    }
}
