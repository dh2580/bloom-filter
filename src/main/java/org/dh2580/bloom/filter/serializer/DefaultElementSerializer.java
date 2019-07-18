package org.dh2580.bloom.filter.serializer;

import java.nio.charset.Charset;

/**
 * @Author: hao.deng
 * @Date: 2019/7/17-下午5:49
 */
public class DefaultElementSerializer<T> implements ElementSerializer<T> {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(T elem) {
        return elem.toString().getBytes(UTF8);
    }
}
