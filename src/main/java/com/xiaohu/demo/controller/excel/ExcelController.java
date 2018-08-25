package com.xiaohu.demo.controller.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohu.demo.utils.excel.ExcelHelper;

@Controller
@RequestMapping("/excel")
public class ExcelController {
		
	@RequestMapping(value = "/excel")
	@ResponseBody
	public List<String> excel(Model model) {
    	String path = "D:\\excel.xlsx";
    	File file = new File(path);
    	List<String> list = null;
		try {
			list = ExcelHelper.exportListFromExcel(file, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
		
}
