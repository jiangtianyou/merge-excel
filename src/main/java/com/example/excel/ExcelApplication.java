package com.example.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.excel.business.RowDataListener;
import com.example.excel.model.ExcelRowData;
import com.example.excel.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ExcelApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(RowDataListener.class);
	// 接解析出来的所有excel的容器
	public static List<ExcelRowData> listFullMerge = new ArrayList<>();
	public static List<List<String>> pickMerge = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ExcelApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		if (args.length == 0) {
			LOGGER.info("请输入文件路径");
			System.exit(0);
		}
		List<String> excelList = Helper.getAllExcelPath(args[0]);
		if (CollectionUtils.isEmpty(excelList)) {
			LOGGER.info("未找到excel文件");
			System.exit(0);
		}

		LOGGER.info("找到excel文件:" + excelList);
		for (String path : excelList) {
			ExcelApplication.readExcel(path, args);
			ExcelApplication.readExcel(path, args);
		}

		// 整理数据 输出excel
		int i = Helper.chooseMode(args);
		if (i == 0) {
			directWrite(args[0] + "\\\\" + "输出文件.xlsx");

		}

	}

	public static void readExcel(String path, String[] args) {
		ExcelReader excelReader = null;
		try {
			excelReader = EasyExcel.read(path, ExcelRowData.class, new RowDataListener()).build();
			ReadSheet readSheet = EasyExcel.readSheet(0).build();
			readSheet.setHeadRowNumber(0);
			excelReader.read(readSheet);
		} finally {
			if (excelReader != null) {
				excelReader.finish();
			}
		}
	}


	/**
	 * excel数据直接合并
	 */
	public static void directWrite(String fileName) {
		ExcelWriter excelWriter = null;
		try {
			excelWriter = EasyExcel.write(fileName, ExcelRowData.class).build();
			WriteSheet writeSheet = EasyExcel.writerSheet("直接合并").build();
			writeSheet.setNeedHead(false);
			excelWriter.write(listFullMerge, writeSheet);
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}
	}


	/**
	 * 根据输入的参数 把数据进行转换再合并
	 */
	public static void convertAndWrite() {
		List<Object> data = new ArrayList<>();

		data = Arrays.asList(Arrays.asList("1", "2", "3", "4"), Arrays.asList("5", "6", "7", "8"));

		String fileName = "E:\\Desktop\\数据处理\\测试\\result.xlsx";
		ExcelWriter excelWriter = null;
		try {
			excelWriter = EasyExcel.write(fileName, null).build();
			WriteSheet writeSheet1 = EasyExcel.writerSheet("test").build();
			writeSheet1.setNeedHead(false);
			excelWriter.write(data, writeSheet1);
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}
	}

}




