package com.willwang.bitwaterfall.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteToInputStream {
    public static final InputStream byte2stream(byte[] bt) {
        return new ByteArrayInputStream(bt);
    }

    public static final byte[] stream2byte(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int size = 0;

        while ((size = bis.read(buffer)) != -1) {
          out.write(buffer, 0, size);
        }

        bis.close();
        is.close();

        return out.toByteArray();
    }
}
