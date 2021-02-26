package com.example.excel.business;

import java.util.Arrays;

public class CustomObject {
	private String fileName;
	private int mode;
	private String[] args;

	public CustomObject(String fileName, int mode,String[] args) {
		this.fileName = fileName;
		this.mode = mode;
		this.args = args;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "CustomObject{" +
				"fileName='" + fileName + '\'' +
				", mode=" + mode +
				", args=" + Arrays.toString(args) +
				'}';
	}
}
