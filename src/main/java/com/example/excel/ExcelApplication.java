package com.example.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.excel.business.CustomObject;
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
import java.util.List;

@SpringBootApplication
public class ExcelApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(RowDataListener.class);
	// 接解析出来的所有excel的容器
	public static List<ExcelRowData> fullMergeData = new ArrayList<>();
	public static List<List<String>> columnsData = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ExcelApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

		for (int i = 0; i < args.length; i++) {
			LOGGER.info("args:{}",args);
		}

		if (args.length == 0) {
			LOGGER.info("请输入文件路径");
			System.exit(0);
		}
		List<String> excelList = Helper.getAllExcelPath(args[0]);
		if (CollectionUtils.isEmpty(excelList)) {
			LOGGER.info("未找到excel文件");
			System.exit(0);
		}

		LOGGER.info("找到excel{}个Excel文件:" , excelList.size());
		excelList.forEach(item ->{
			LOGGER.info("文件:{}", Helper.getShortFileName(item));
		});

		for (String path : excelList) {
			ExcelApplication.readExcel(path, args);
		}
		writeExcel(args[0] + "\\\\" + Helper.FILE_NAME_PRE + System.nanoTime() + ".xls",Helper.getMode(args));

	}

	public static void readExcel(String path, String[] args) {
		ExcelReader excelReader = null;
		try {
			excelReader = EasyExcel.read(path, ExcelRowData.class, new RowDataListener()).build();
			excelReader.analysisContext().readWorkbookHolder().setCustomObject(new CustomObject(path, Helper.getMode(args),args));
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
	public static void writeExcel(String fileName, int mode) {
		ExcelWriter excelWriter = null;
		if (mode == 0) {
			try {
				excelWriter = EasyExcel.write(fileName, ExcelRowData.class).build();
				WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
				writeSheet.setNeedHead(false);
				excelWriter.write(fullMergeData, writeSheet);
			} finally {
				if (excelWriter != null) {
					excelWriter.finish();
				}
			}
			return;
		}

		// mode == 1 列转换成行再写

		List<List<String>> result = new ArrayList<>();
		int fileCount = columnsData.size();
		if (fileCount != 0) {
			int colBeforeCount = columnsData.iterator().next().size();
			for (int i = 0; i < colBeforeCount; i++) {
				result.add(new ArrayList<>());
			}
			for (List<String> beforeRowData : columnsData) {
				for (int i = 0; i < colBeforeCount; i++) {
					List<String> finalRow = result.get(i);
					finalRow.add(beforeRowData.get(i));
				}
			}
		}
		try {
			excelWriter = EasyExcel.write(fileName).build();
			WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
			writeSheet.setNeedHead(false);
			excelWriter.write(result, writeSheet);
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}

	}
}




