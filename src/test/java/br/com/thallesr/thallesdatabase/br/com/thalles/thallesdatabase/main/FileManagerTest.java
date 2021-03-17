package br.com.thallesr.thallesdatabase.br.com.thalles.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.main.FileManager;
import br.com.thallesr.thallesdatabase.model.Page;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    public  static final String contentTest = "Contentsa";

    //FileManager fm = Mockito.mock(FileManager.class);

    public void createFile() throws IOException {
        RandomAccessFile file = new RandomAccessFile("testdb.db", "rw");
        FileChannel ch = file.getChannel();
        byte[] content = new byte[FileManager.PAGE_SIZE * 10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                content[(FileManager.PAGE_SIZE * i)+j] = (contentTest+i).getBytes(StandardCharsets.UTF_16)[j+2];
            }
        }
        ByteBuffer bb = ByteBuffer.wrap(content);
        ch.position(0);
        ch.write(bb);
        ch.force(false);
        ch.close();
    }

    @SneakyThrows
    @Test
    public void testBasic() {
        createFile();
        FileManager fm = new FileManager("testdb.db");

//        when(fm.read(anyInt())).thenReturn(null);
        Page p = fm.read(0);
        assertEquals(512,p.getContents().length);
       //  assertEquals(512,512);
        byte[] slice = ArrayUtils.subarray(p.getContents(),0,20);
        Assertions.assertArrayEquals(slice,ArrayUtils.subarray((contentTest+0).getBytes(StandardCharsets.UTF_16),2,22));
    }

    @SneakyThrows
    @Test
    public void testBasic2() {
        createFile();
        FileManager fm = new FileManager("testdb.db");

//        when(fm.read(anyInt())).thenReturn(null);
        Page p = fm.read(0);
        byte[] content = new byte[512];
        ArrayUtils.insert(0,content,ArrayUtils.subarray((contentTest+0).getBytes(StandardCharsets.UTF_16),2,22));
        (contentTest+0).getBytes(StandardCharsets.UTF_16);

        assertEquals(512,p.getContents().length);
        //  assertEquals(512,512);
        byte[] slice = ArrayUtils.subarray(p.getContents(),0,20);
        Assertions.assertArrayEquals(slice,ArrayUtils.subarray((contentTest+0).getBytes(StandardCharsets.UTF_16),2,22));
    }

  }
