package com.example.excel.business;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.excel.ExcelApplication;
import com.example.excel.model.ExcelRowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowDataListener extends AnalysisEventListener<ExcelRowData> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RowDataListener.class);
	/**
	 * 每一条数据解析都会来调用
	 */
	@Override
	public void invoke(ExcelRowData data, AnalysisContext context) {
		LOGGER.info("解析到一条数据:{}", data.toString());
		ExcelApplication.listFullMerge.add(data);
	}

	/**
	 * 所有数据解析完成了 都会来调用
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {

	}
}
