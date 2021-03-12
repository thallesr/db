package br.com.thallesr.thallesdatabase.model;

import java.nio.ByteBuffer;

public class LogPage {
    public static int LOG_PAGE_SIZE = 1024;



    public final byte[] contents = new byte[LOG_PAGE_SIZE];

    public ByteBuffer buffer = ByteBuffer.wrap(contents);
}
