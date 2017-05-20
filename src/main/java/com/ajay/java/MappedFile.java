package com.ajay.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MappedFile {

	private static final String file = "/Users/ajayv/Documents/Manning.Lucene.in.Action.2nd.Edition.2010.pdf";

	public void readFileMBB(Path path) throws IOException {
		try (FileChannel channel = FileChannel.open(path)) {
			long length = channel.size();
			System.out.println("Channel size =" + length);
			MappedByteBuffer mBB = channel.map(MapMode.READ_ONLY, 0, length);
			//StringBuilder sb = new StringBuilder();
			int lines = 0, chars = 0;
			byte b;
			while (mBB.hasRemaining()) {
				b = mBB.get();
				if (b == '\n' ||  b == '\r') {
					lines++;
					//chars += sb.length();
					//sb.setLength(0);
				} else {
					chars++;
					//sb.append(b);
				}
			}
			System.out.printf("lines = %d, chars = %d\n", lines, chars);
		}
	}

	public void readBR(Path path) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
			String line;
			int lines = 0;
			int chars = 0;
			while ((line = br.readLine()) != null) {
				lines++;
				chars += line.length();
			}
			System.out.printf("lines = %d, chars = %d\n", lines, chars);
		}
	}

	public static void main(String[] args) throws IOException {
		MappedFile mf = new MappedFile();
		long startTime = System.currentTimeMillis();
		mf.readFileMBB(Paths.get(file));
		long millis = (System.currentTimeMillis() - startTime);
		System.out.println("Time for mm = " + millis);

		startTime = System.currentTimeMillis();
		mf.readBR(Paths.get(file));

		millis = (System.currentTimeMillis() - startTime);
		System.out.println("Time for buff = " + millis);
	}
}
