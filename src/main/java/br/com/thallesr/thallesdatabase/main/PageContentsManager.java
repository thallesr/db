package br.com.thallesr.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.exceptions.NoMemoryForPage;
import br.com.thallesr.thallesdatabase.exceptions.OffLimitsException;
import br.com.thallesr.thallesdatabase.exceptions.PageAlreadyPinned;
import br.com.thallesr.thallesdatabase.model.Page;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PageContentsManager {

    MemoryManager mm = new MemoryManager();

    public static void main(String[] main) throws OffLimitsException, IOException, NoMemoryForPage, PageAlreadyPinned {
        PageContentsManager pm = new PageContentsManager();
        Page page = pm.getRefPage(3);
        pm.write(page,10,"helloModified3");
        Page page1 = pm.getRefPage(4);
        pm.write(page1,10,"helloModified4");
        Page page2 = pm.getRefPage(5);
        pm.write(page2,10,"helloModified5");
        pm.shutdown();


    }

    private void shutdown() {
        mm.shutdown();
    }

    public void write(Page p, int pos, String text) throws OffLimitsException {
        byte[] contents = p.getContents();
        byte[] textbytes = text.getBytes(StandardCharsets.UTF_16);

        //test limits pretending one page is just one field, ask page
        if (pos + textbytes.length> FileManager.PAGE_SIZE){
            throw new OffLimitsException();

        }
        p.setModified(true);
        for (int i = 0;i< textbytes.length;i++){
            contents[i+pos] = textbytes[i];
        }
    }


    public Page getRefPage(int i) throws IOException, NoMemoryForPage, PageAlreadyPinned {
        return mm.getRefPage(i);

    }
}
