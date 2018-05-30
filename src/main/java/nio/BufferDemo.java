package nio;

import sun.nio.ch.FileChannelImpl;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        File f = new File("");
        FileOutputStream os = new FileOutputStream(f);
        os.write(new byte[]{});
        os.flush();
        
//create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {

            buf.flip();  //make buffer ready for read

            while(buf.hasRemaining()){
                System.out.print((char) buf.get()); // read 1 byte at a time
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
