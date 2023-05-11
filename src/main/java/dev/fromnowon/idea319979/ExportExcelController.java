package dev.fromnowon.idea319979;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 导出 Excel 文件
 *
 * @author hansai
 */
@RestController
@RequestMapping("exportExcel")
public class ExportExcelController {

    @GetMapping
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("Sheet1");

        // 创建数据行，并在第一行中写入标题
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("姓名");
        headerRow.createCell(1).setCellValue("年龄");
        headerRow.createCell(2).setCellValue("性别");

        // 写入数据行
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("张三");
        dataRow1.createCell(1).setCellValue(25);
        dataRow1.createCell(2).setCellValue("男");

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("李四");
        dataRow2.createCell(1).setCellValue(30);
        dataRow2.createCell(2).setCellValue("男");

        // 将数据写入响应流
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("用户-角色", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
    }

}
