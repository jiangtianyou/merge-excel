package com.example.excel.business;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.excel.ExcelApplication;
import com.example.excel.model.ExcelPickedColumnData;
import com.example.excel.model.ExcelRowData;
import com.example.excel.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RowDataListener extends AnalysisEventListener<ExcelRowData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RowDataListener.class);
    /**
     * 每一条数据解析都会来调用
     */
    public List<ExcelRowData> tmp = new ArrayList<>();

    public int currentRow = 1;
    @Override
    public void invoke(ExcelRowData data, AnalysisContext context) {
        CustomObject custom = (CustomObject) context.getCustom();
		LOGGER.info("文件{}=>第{}行数据:{}", Helper.getShortFileName(custom.getFileName()),currentRow++,data.toString());
        int mode = custom.getMode();
        if (mode == 0) {
            // 直接合并
            ExcelApplication.fullMergeData.add(data);
        } else {
            // 借助tmp等文件全部写完成后，进行转换后合并
            tmp.add(data);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //LOGGER.info("doAfterAll调用:{}", System.nanoTime());
        CustomObject custom = (CustomObject) context.getCustom();
        int mode = custom.getMode();
        String[] args = custom.getArgs();
        if (mode == 0) {
            return;
        }
        // 转换后添加到ExcelApplication.data
        List<String> column = new ExcelPickedColumnData(tmp, args[1]).getColumn();
        ExcelApplication.columnsData.add(column);
    }
}
