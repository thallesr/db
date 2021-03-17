package br.com.thallesr.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.model.LogEntry;
import br.com.thallesr.thallesdatabase.model.Operation;

import java.io.IOException;

public class LogManager {

    LogFileManager logFileManager = new LogFileManager();

    public LogManager() throws IOException {

    }

    public void log(int txId,int pageId,Operation write, byte[] contentBefore, int pos, int length, byte[] newContent) throws IOException {
        LogEntry le = new LogEntry(txId,pageId,pos,pos+length*2,contentBefore);
        logFileManager.write(le);
    }

    public void flush(int id) throws IOException {
        logFileManager.flush();
    }
}
