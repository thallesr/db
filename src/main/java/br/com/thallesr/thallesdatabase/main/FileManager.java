package br.com.thallesr.thallesdatabase.main;

import br.com.thallesr.thallesdatabase.model.Page;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/* will use FileChannel based on Baeldung

            Reading and writing at a specific position in a file
            Loading a section of a file directly into memory, which can be more efficient
            We can transfer file data from one channel to another at a faster rate
            We can lock a section of a file to restrict access by other threads
            To avoid data loss, we can force writing updates to a file immediately to storage

            FileChannel operations are blocking and do not have a non-blocking mode
*/
public class FileManager {

    public static int PAGE_SIZE = 500;

    private RandomAccessFile file;
    private FileChannel channel;



    public FileManager(){
        try {
        //    file = new RandomAccessFile("testfile.bin", "rws");
            file = new RandomAccessFile("testfile.bin", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        channel = file.getChannel();
    }
    public void close() throws IOException {
        file.close();
    }



    public void write(Page page) throws IOException {
            ByteBuffer buff = ByteBuffer.wrap(page.contents);
            channel.write(buff,PAGE_SIZE * page.id);
        }
    public Page read(int id) throws IOException {
        Page p = new Page(new byte[PAGE_SIZE],id,this);

        channel.read(ByteBuffer.wrap(p.getContents()),PAGE_SIZE * id);
        return p;
    }

   /* public static void main(String[] args) throws IOException {

        FileManager pg = new FileManager();

        try {
            pg.write("so close no matter how far\n", 0);
            pg.write("forever trust in who we are\n", 2);
            pg.write("couldn`t be much more from the heart\n", 1);
        }catch (Exception e){
            e.printStackTrace();
        }

        Page pg2 = pg.read(2);
        System.out.println(pg2);

        Page pg1 = pg.read(1);
        System.out.println(pg1);

        Page pg0 = pg.read(0);
        System.out.println(pg0);

    }
 */
    /*  public void write(String text,int pos) throws IOException {
        ByteBuffer buff = ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_16));
        channel.write(buff,PAGE_SIZE * pos);
    }*/
}
