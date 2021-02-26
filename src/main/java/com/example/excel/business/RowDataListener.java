package com.example.excel.business;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.excel.ExcelApplication;
import com.example.excel.model.ExcelRowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RowDataListener extends AnalysisEventListener<ExcelRowData> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RowDataListener.class);
	/**
	 * 每一条数据解析都会来调用
	 */
	public  List<ExcelRowData> tmp = new ArrayList<>();
	@Override
	public void invoke(ExcelRowData data, AnalysisContext context) {
		context.getCustom();
		LOGGER.info("解析到一条数据:{}", data.toString());
		ExcelApplication.listFullMerge.add(data);
	}

	/**
	 * 所有数据解析完成了 都会来调用
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		//每个excel写入一个条特殊的数据作为分隔
		String fileName = ((CustomObject) context.getCustom()).getFileName();
	}
}
