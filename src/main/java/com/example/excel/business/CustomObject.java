package com.example.excel.business;

public class CustomObject {
	private String fileName;
	private int mode;

	public CustomObject(String fileName, int mode) {
		this.fileName = fileName;
		this.mode = mode;
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

	@Override
	public String toString() {
		return "CustomObject{" +
				"fileName='" + fileName + '\'' +
				", mode=" + mode +
				'}';
	}
}
