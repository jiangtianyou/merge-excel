package com.example.excel.model;

import com.example.excel.utils.Reflections;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据输入的例如a1b2c3d8-d10的数据，从一张excel取出数据，组成一列
 */

public class ExcelPickedColumnData{

	private final List<ExcelRowData> rows;
	private String pattern;


	public ExcelPickedColumnData(List<ExcelRowData> rows, String pattern) {
		this.rows = rows;
		this.pattern = pattern;
	}

	// 一个excel表的一个竖列
	public List<String> getColumn() {
		if (rows == null || StringUtils.isEmpty(pattern)) {
			return new ArrayList<>();
		}
		Map<String, String> stringStringMap = toMap();
		List<String> list = patternToCell();

		List<String> rtn = new ArrayList<>();
		for (String cellReference : list) {
			String s = stringStringMap.get(cellReference);
			rtn.add(ObjectUtils.defaultIfNull(s, ""));
		}
		return rtn;
	}



	private Map<String, String> toMap() {
		if (CollectionUtils.isEmpty(rows)) {
			return new HashMap<>();
		}
		List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
		Map<String, String> rtn = new HashMap<>();

		for (int i = 0; i < rows.size(); i++) {
			ExcelRowData row = rows.get(i);
			if (row == null) {
				continue;
			}
			// 转换成以单元格名例如 a1,d2等为键的map
			for (int j = 0; j < list.size(); j++) {
				String cellValue = (String)Reflections.getFieldValue(row, "a" + (j + 1));
				rtn.put(list.get(j) + (i + 1), ObjectUtils.defaultIfNull(cellValue, ""));
			}
		}
		return rtn;
	}



	private List<String> patternToCell() {
		pattern = pattern.replace(" ", "");
		String regex = "(\\w{1}\\d|-)";
		//先进行移除
		Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE)
				.matcher(pattern);
		List<String> tokens = new ArrayList<>();
		while (matcher.find()) {
			tokens.add(matcher.group(1));
		}

		List<String> rtn = new ArrayList<>();
		int size = tokens.size();
		int maxIndex = size -1;
		for (int i = 0; i < size; i++) {
			String token = tokens.get(i);
			if (token.equals("-")) {
				continue;
			}
			if (i  == maxIndex) {
				rtn.add(token);
				continue;
			}
			if (!tokens.get(i + 1).equals("-")) {
				// 是单纯一个单元格
				rtn.add(token);
				continue;
			}
			// 是一个区间，对区间进行解析
			int min = Integer.parseInt(tokens.get(i).substring(1));
			int max = Integer.parseInt(tokens.get(i+2).substring(1));
			if (max > min) {
				for (int j = min; j <= max; j++) {
					String e = token.substring(0,1) + "" + j;
					rtn.add(e);
				}
				//指针移到区间后面
				i = i + 2;
			}
		}
		return rtn;
	}

}
