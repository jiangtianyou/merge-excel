package com.example.excel.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class Helper {
	public static String FILE_NAME_PRE = "合并输出";
	/**
	 * 返回 0 直接全部合并
	 *     1 根据输入的单元格合并
	 */
	public static int getMode(String[] args) {
		if (args.length == 1){
			// 只有文件路径，文件夹下全部合并
			return 0;
		}
		return 1;
	}

	public static List<String> getAllExcelPath(String dir) {
		File[] files = new File(dir).listFiles();
		if (files == null) {
			files = new File[0];
		}
		return Arrays.stream(files).map(File::getAbsolutePath)
				.filter(item -> item.endsWith("xls") && !Helper.getShortFileName(item).startsWith(FILE_NAME_PRE))
				.collect(Collectors.toList());
	}


	public static String getShortFileName(String fileName) {
		int i = fileName.lastIndexOf("\\");
		return fileName.substring(i+1);
	}
}
