package com.pattern.utiltest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class JavaClientTest {

	public static void main(String[] args) {

		HttpURLConnection httpConnection = null;
		BufferedReader reader = null;

		try {

			final URL urlRequest = new URL("http://localhost:8080/simple-security/persons/1");
			httpConnection = (HttpURLConnection) urlRequest.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("Authorization", "Basic " + encodeUserNamePassword("usuario", "123456"));

			reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));

			final StringBuilder retorno = new StringBuilder();

			String inputLine = null;

			while ((inputLine = reader.readLine()) != null) {

				retorno.append(inputLine);
			}

			System.out.println(retorno.toString());

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			IOUtils.closeQuietly(reader);

			if (null != httpConnection) {

				httpConnection.disconnect();
			}
		}
	}

	private static String encodeUserNamePassword(String user, String password) {

		String userPassword = user + ":" + password;

		return new String(Base64.encodeBase64(userPassword.getBytes()));
	}
}
