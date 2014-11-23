package com.jamesots.bin2hex;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Bin2HexTest {
    @Test
    public void should_return_no_byte_string_when_nothing_has_been_added() {
        final Bin2Hex bin2Hex = new Bin2Hex();
        final String record = bin2Hex.getRecord();
        assertThat(record, is(":0000000000"));
    }

    @Test
    public void should_return_eof_record() {
        final Bin2Hex bin2Hex = new Bin2Hex();
        assertThat(bin2Hex.endFile(), is(":00000001FF"));
    }

    @Test
    public void should_work2() throws Exception {
        final Bin2Hex bin2Hex = new Bin2Hex();
        bin2Hex.addByte(0);
        final String record = bin2Hex.getRecord();
        assertThat(record, is(":0100000000FF"));
    }

    @Test
    public void should_do_checksum() throws Exception {
        final Bin2Hex bin2Hex = new Bin2Hex();
        bin2Hex.setAddress(0x0030);
        bin2Hex.addByte(0x02);
        bin2Hex.addByte(0x33);
        bin2Hex.addByte(0x7A);
        final String record = bin2Hex.getRecord();
        assertThat(record, is(":0300300002337A1E"));
    }

    @Test(expected = Exception.class)
    public void should_fail_to_add_more_than_256_bytes() throws Exception {
        final Bin2Hex bin2Hex = new Bin2Hex();
        for (int i = 0; i < 0x100; i++) {
            bin2Hex.addByte(0);
        }
    }

    @Test
    public void should_be_able_to_add_256_bytes() throws Exception {
        final Bin2Hex bin2Hex = new Bin2Hex();
        for (int i = 0; i < 0xFF; i++) {
            bin2Hex.addByte(0);
        }
    }

    @Test
    public void should_inc_address() throws Exception {
        final Bin2Hex bin2Hex = new Bin2Hex();
        bin2Hex.addByte(0);
        bin2Hex.addByte(0);
        bin2Hex.addByte(0);
        assertThat(bin2Hex.getRecord(), is(":03000000000000FD"));
        assertThat(bin2Hex.getRecord(), is(":00000300FD"));
    }

    @Test
    public void should_encode_examples_from_web() throws Exception {
        final Bin2Hex bin2Hex = new Bin2Hex();
        bin2Hex.setAddress(0x100);
        bin2Hex.addByte(0x21);
        bin2Hex.addByte(0x46);
        bin2Hex.addByte(0x01);
        bin2Hex.addByte(0x36);
        bin2Hex.addByte(0x01);
        bin2Hex.addByte(0x21);
        bin2Hex.addByte(0x47);
        bin2Hex.addByte(0x01);
        bin2Hex.addByte(0x36);
        bin2Hex.addByte(0x00);
        bin2Hex.addByte(0x7E);
        bin2Hex.addByte(0xFE);
        bin2Hex.addByte(0x09);
        bin2Hex.addByte(0xD2);
        bin2Hex.addByte(0x19);
        bin2Hex.addByte(0x01);
        assertThat(bin2Hex.getRecord(), is(":10010000214601360121470136007EFE09D2190140"));
    }
}
