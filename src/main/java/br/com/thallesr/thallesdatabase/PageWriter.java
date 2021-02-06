package br.com.thallesr.thallesdatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/* will use FileChannel based on Baeldung

            Reading and writing at a specific position in a file
            Loading a section of a file directly into memory, which can be more efficient
            We can transfer file data from one channel to another at a faster rate
            We can lock a section of a file to restrict access by other threads
            To avoid data loss, we can force writing updates to a file immediately to storage

            FileChannel operations are blocking and do not have a non-blocking mode
*/
public class PageWriter {
    public void main(String[] args) throws FileNotFoundException {
    //some play in writing a file to get used to FileChannel

        RandomAccessFile writer = new RandomAccessFile("testfile.bin", "rw");
        FileChannel channel = writer.getChannel();
        ByteBuffer buff = ByteBuffer.wrap("Hello world".getBytes(StandardCharsets.UTF_8));
        //wrap is the way to construct a ByteBuffer with a Byte[] as constructor

        try {
            channel.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // verify out of order on purpose, before flushing or closing
        RandomAccessFile reader = new RandomAccessFile("testfile.bin", "r");
        try {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        };
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
