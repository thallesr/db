package br.com.thallesr.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.exceptions.NoMemoryForPage;
import br.com.thallesr.thallesdatabase.exceptions.OffLimitsException;
import br.com.thallesr.thallesdatabase.exceptions.PageAlreadyPinned;
import br.com.thallesr.thallesdatabase.model.Operation;
import br.com.thallesr.thallesdatabase.model.Page;
import br.com.thallesr.thallesdatabase.model.Transaction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TransactionManager {
    PageContentsManager pcm = new PageContentsManager();
    LogManager lrm = new LogManager();

    public TransactionManager() throws IOException {
    }


    public void writeText(int pageId, int startPos, String text) throws PageAlreadyPinned, IOException, NoMemoryForPage, OffLimitsException {

        //if we do not store the value before, it may be necessary to traverse all log to find previous value. or we may store dumps of db.
        Transaction tx = new Transaction();
        Page p = pcm.getRefPage(pageId);
        byte[] contentBefore = p.getContents();
        lrm.log(tx.id,pageId, Operation.Write, contentBefore, startPos, text.length(), text.getBytes(StandardCharsets.UTF_16));

        //create logic when tx contains more than one entry;
        lrm.flush(tx.id);
        pcm.write(p, startPos, text);
    }

}
