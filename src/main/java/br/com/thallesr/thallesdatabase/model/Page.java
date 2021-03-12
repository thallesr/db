package br.com.thallesr.thallesdatabase.model;

import br.com.thallesr.thallesdatabase.main.FileManager;
import lombok.Data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
//@Value
public class Page {


    public byte[] contents;
    public int id;
    boolean pinned;

    boolean modified;
    FileManager pw;

    public Page(byte[] contents, int id, FileManager pw) {
        this.contents = contents;
        this.id = id;
        this.pw = pw;
    }

    public void unpin() {
        if (isModified()) {
            try {
                pw.write(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String toString() {
        return new String(contents, StandardCharsets.UTF_16);
    }

    public void pin() {
        setPinned(true);

    }

}
