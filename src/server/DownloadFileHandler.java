package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DownloadFileHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		try {
			URI uri = exchange.getRequestURI();
			String path = "./Records" + uri + "/";
			File file = new File(path);
			InputStream is = new FileInputStream(file);
			byte[] contents = new byte[(int)file.length()];
			is.read(contents);
			is.close();
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, contents.length);
			OutputStream os = exchange.getResponseBody();
			os.write(contents);
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
