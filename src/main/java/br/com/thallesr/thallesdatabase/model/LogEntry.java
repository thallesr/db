package br.com.thallesr.thallesdatabase.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LogEntry {
    //TODO remove attr from this class and put only one bytebuffer
    public static int FULL_ENTRY_SIZE = 256;
    public static int ENTRY_DATA_LIMIT = FULL_ENTRY_SIZE -2*Long.BYTES -2*Integer.BYTES;


    public final long txId;
    public final long pageId;
    public final int start;
    public final int end;
    public final byte[] valueBefore;
}
