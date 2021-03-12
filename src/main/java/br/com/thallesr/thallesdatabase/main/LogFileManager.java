package br.com.thallesr.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.model.LogEntry;
import br.com.thallesr.thallesdatabase.model.LogPage;
import br.com.thallesr.thallesdatabase.model.Page;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class LogFileManager {

    //we will have to limit the size of a entry, if changes exceed this size it will be splitted in two or more
    private RandomAccessFile file;
    private FileChannel channel;
    private LogPage currentLog;
    private int currentPos = 0;

    public LogFileManager() throws IOException {
        try {
            file = new RandomAccessFile("logfile.log", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        channel = file.getChannel();
        channel.position(channel.size());
    }

    public void close() throws IOException {
        file.close();
    }

    public void write(LogEntry entry) throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(LogEntry.FULL_ENTRY_SIZE);
        buff.putLong(entry.txId);
        buff.putLong(entry.pageId);
        buff.putInt(entry.start);
        buff.putInt(entry.end);
        buff.put(entry.valueBefore);
        write(buff);
    }

    private void write(ByteBuffer buff) throws IOException {
        if (currentPos + LogEntry.FULL_ENTRY_SIZE <= LogPage.LOG_PAGE_SIZE) {
            currentPos += LogEntry.FULL_ENTRY_SIZE;
            //future position after writing this
        } else {
            channel.write(currentLog.buffer);
            currentLog = new LogPage();
            currentPos = LogEntry.FULL_ENTRY_SIZE;
            //future position after writing this
        }
        currentLog.buffer.put(buff);
        currentLog.buffer.position(currentPos);
    }

    public LogEntry read(int txId) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void flush() throws IOException {
        channel.force(false);
    }
}
