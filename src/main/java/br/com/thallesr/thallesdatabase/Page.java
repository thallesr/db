package br.com.thallesr.thallesdatabase;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
@AllArgsConstructor
public class Page {

    ByteBuffer content;
}
