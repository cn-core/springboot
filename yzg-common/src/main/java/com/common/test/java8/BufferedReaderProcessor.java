package com.common.test.java8;

import java.io.IOException;

/**
 * @author yangzhiguo
 */
@FunctionalInterface
public interface BufferedReaderProcessor<T,R>
{
    String process(T t) throws IOException;

    default T processs(T t) throws IOException
    {
        return t;
    }
}
