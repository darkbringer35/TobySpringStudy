package com.jwh.toby.ch3.ch3_5.ch3_5_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			return callback.doSomethingWithReader(br);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			T res = initVal;
			String line = null;
			while ((line = br.readLine()) != null)
				res = callback.doSomethingWithLine(line, res);
			return res;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public Integer calcSum(final String filePath) throws IOException {
		return fileReadTemplate(filePath,
			new BufferedReaderCallback() {
				@Override
				public Integer doSomethingWithReader(BufferedReader br) throws IOException {
					Integer sum = 0;
					String line;
					while ((line = br.readLine()) != null)
						sum += Integer.valueOf(line);
					return sum;
				}
			}
		);
	}

	public Integer calcMultiply(final String filePath) throws IOException {
		LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath, multiplyCallback, 1);
	}

	public String concatenate(final String filePath) throws IOException {
		LineCallback<String> concatenateCallback = new LineCallback<String>() {
			@Override
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		};
		return lineReadTemplate(filePath, concatenateCallback, "");
	}
}
