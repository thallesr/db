package br.com.thallesr.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.exceptions.NoMemoryForPage;
import br.com.thallesr.thallesdatabase.exceptions.PageAlreadyPinned;
import br.com.thallesr.thallesdatabase.exceptions.PageNotFoundInMemory;
import br.com.thallesr.thallesdatabase.model.Page;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class MemoryManager {
    private static int numberOfPages = 3;
    private static Page memory[] = new Page[numberOfPages];
    private final FileManager pw = new FileManager("testfile.bin");


    public static void main(String[] args) throws NoMemoryForPage, PageNotFoundInMemory, PageAlreadyPinned {
        MemoryManager pm = new MemoryManager();
        FileManager pw = new FileManager("testfile.bin");
        Page page = new Page("test page 1".getBytes(StandardCharsets.UTF_16), 1, pw);
        Page page2 = new Page("test page 2".getBytes(StandardCharsets.UTF_16), 2, pw);
        Page page3 = new Page("test page 3".getBytes(StandardCharsets.UTF_16), 3, pw);
        Page page4 = new Page("test page 4".getBytes(StandardCharsets.UTF_16), 4, pw);

        pm.pin(page);
        pm.pin(page2);
        //pm.pin(page2);
        pm.pin(page3);
        pm.pin(page3);
        System.out.println(Arrays.toString(memory));
        pm.unpin(page3);
        System.out.println(Arrays.toString(memory));
        pm.pin(page4);
        pm.pin(null);
        System.out.println(Arrays.toString(memory));
    }

    public void unpin(@NotNull Page page) throws PageNotFoundInMemory {
        int i = -1;
        while (!page.equals(memory[++i]) && i < memory.length) {
        }
        if (page.equals(memory[i])) {
            page.unpin();
            memory[i] = null;
            page.setPinned(false);
        } else {
            throw new PageNotFoundInMemory();
        }
    }

    //@NotNull not validated yet
    public void pin(@NotNull Page page) throws NoMemoryForPage, PageAlreadyPinned {
        int i = -1;
        while (++i < memory.length && memory[i] != null && !memory[i].equals(page)) {
        }
        if (i < memory.length && page.equals(memory[i])) {
            throw new PageAlreadyPinned();
        }
        if (i < memory.length && memory[i] == null) {
            memory[i] = page;
            page.pin();
            System.out.println("pageid: " + page.id + " memory place  " + i + " ");

        } else {
            throw new NoMemoryForPage();
        }
    }

    public void shutdown() {
        //write all modified pages pinned to disk
        Arrays.stream(memory).filter(Objects::nonNull).filter(Page::isModified)
                .forEach(p -> {
            try {
                pw.write(p);
            } catch (IOException e) {
                e.printStackTrace();
                RuntimeException r = new RuntimeException("Erro writing to disk");
                r.addSuppressed(e);
                throw r;
            }
        });


    }

    public Page getRefPage(int i) throws IOException, NoMemoryForPage, PageAlreadyPinned {
        Page p = pw.read(i);
        pin(p);
        return p;
    }
}
