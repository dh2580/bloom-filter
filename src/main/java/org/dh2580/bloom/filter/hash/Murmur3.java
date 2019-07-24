package org.dh2580.bloom.filter.hash;

import java.nio.charset.Charset;

/**
 * 参考guava中的Murmur3_32HashFunction实现
 *
 * @Author: hao.deng
 * @Date: 2019/7/12-下午6:25
 */
public class Murmur3 implements HashFunction {

    @Override
    public int[] hash(byte[] bytes, int m, int k) {
        int[] hashes = new int[k];

        for (int pos = 0; pos < k; pos++) {
            int len = bytes.length;
            int h1 = SEED;

            int i = 0;

            while (i + 4 <= len) {
                byte c0 = bytes[i];
                byte c1 = bytes[i + 1];
                byte c2 = bytes[i + 2];
                byte c3 = bytes[i + 3];

                int k1 = c0 | (c1 << 8) | (c2 << 16) | (c3 << 24);

                k1 = mixK1(k1);
                h1 = mixH1(h1, k1);

                i += 4;
            }

            int buffer = 0;
            int shift = 0;
            for (; i < len; i++) {
                byte c = bytes[i];

                buffer |= c << shift;
                shift += 8;
            }

            int k1 = mixK1(buffer);
            h1 ^= k1;

            h1 = fmix(h1, len);

            hashes[pos] = Math.abs(h1) % m;
        }

        return hashes;
    }

    @Override
    public int[] hash(String val, int m, int k) {

        byte[] bytes = val.getBytes(Charset.forName("UTF-8"));

        return hash(bytes, m, k);
    }

    @Override
    public int[] hash(int val, int m, int k) {
        int[] hashes = new int[k];

        int h1 = SEED;

        for (int pos = 0; pos < k; pos++) {
            int k1 = mixK1(val);
            h1 = mixH1(h1, k1);

            h1 = fmix(h1, 4);

            hashes[pos] = Math.abs(h1) % m;
        }

        return hashes;
    }

    @Override
    public int[] hash(long val, int m, int k) {
        int[] hashes = new int[k];

        int h1 = SEED;

        for (int pos = 0; pos < k; pos++) {
            int low = (int) val;
            int high = (int) (val >>> 32);

            int k1 = mixK1(low);
            h1 = mixH1(h1, k1);

            k1 = mixK1(high);
            h1 = mixH1(h1, k1);

            h1 = fmix(h1, 8);

            hashes[pos] = Math.abs(h1) % m;
        }

        return hashes;
    }

    //------------ Private Method

    private static final int C1 = 0xcc9e2d51;

    private static final int C2 = 0x1b873593;

    private static final int SEED = 0;

    private int mixK1(int k1) {
        k1 *= C1;
        k1 = Integer.rotateLeft(k1, 15);
        k1 *= C2;
        return k1;
    }

    private int mixH1(int h1, int k1) {
        h1 ^= k1;
        h1 = Integer.rotateLeft(h1, 13);
        h1 = h1 * 5 + 0xe6546b64;
        return h1;
    }

    private int fmix(int h1, int length) {
        h1 ^= length;
        h1 ^= h1 >>> 16;
        h1 *= 0x85ebca6b;
        h1 ^= h1 >>> 13;
        h1 *= 0xc2b2ae35;
        h1 ^= h1 >>> 16;
        return h1;
    }
}
