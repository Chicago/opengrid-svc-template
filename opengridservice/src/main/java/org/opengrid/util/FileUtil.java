package org.opengrid.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.opengrid.service.OpenGridException;

public class FileUtil {
	
	public static InputStream readFileAsResource(String fileName) throws IOException {
		InputStream f;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			f = fis;
		} catch (FileNotFoundException ex) {
			// FileNotFound
			f = new Object() {
			}.getClass().getEnclosingClass().getClassLoader()
					.getResourceAsStream(fileName);
		}
		if (f == null) {
			throw new IOException("Cannot load file " + fileName);
		}
		return f;
	}

	
	public static String streamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
	
	public static String getJsonFileContents(String filePath) {
		InputStream is = null;
		try {
			is = FileUtil.readFileAsResource(filePath);
			return FileUtil.streamToString(is);
		} catch (Exception ex) {
			ex.printStackTrace();
			
			//wrap and bubble up
			throw new OpenGridException(ex);
		} finally {
			if (is!=null) {
				//ignore errors from close
				try {is.close(); } catch (Exception x) {};
			}
		}		
	}
}
