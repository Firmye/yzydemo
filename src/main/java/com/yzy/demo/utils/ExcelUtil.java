package com.yzy.demo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author firmye
 * @Date 2017年11月21日 上午11:16:27
 * @Description Excel工具类（未完成）
 */
public class ExcelUtil {

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";

    public static Workbook getWorkbook(MultipartFile file) throws IOException {
        Workbook workbook = null;
        InputStream is = file.getInputStream();
        if (file.getOriginalFilename().endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (file.getOriginalFilename().endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }

    private boolean isMergedCell(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) return "";

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }

    public static void inputExcel(String unitId, int users) {

    }

    public static void getUnits(String unitId, Sheet sheetNo) throws Exception {
        // 查看当前组织有无用户
        String apiPath = "/ebus/org/getusersbyunitid";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("unitid", unitId));

        CloseableHttpResponse response = ApiRequestUtil.apiGatewayRequest("Post", apiPath, list);
        String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseStr);

        JSONObject jo = JSONObject.parseObject(responseStr);
        JSONArray ja = JSONArray.parseArray(jo.getString("users"));
        System.out.println(ja.size());
        int users = ja.size();

        // 同步组织架构
        apiPath = "/ebus/org/getchildunitbyunitid";

        response = ApiRequestUtil.apiGatewayRequest("Post", apiPath, list);
        responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseStr);

        jo = JSONObject.parseObject(responseStr);
        ja = JSONArray.parseArray(jo.getString("units"));
        int sonUnits = ja.size();

        if (users > 0 && sonUnits > 0) {
            // 有用户信息的父组织
        } else if (users > 0 && sonUnits == 0) {
            // 子组织
        } else if (users == 0 && sonUnits > 0) {

        }

        for (int i = 0; i < ja.size(); i++) {

        }
    }

    public static void main(String[] args) throws Exception {
        // 读取Excel
        File file = new File("C:\\Users\\47660\\Downloads\\省直单位名单.xlsx");
        InputStream is = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        Sheet sheetNo = workbook.getSheetAt(1);

        int lastRowNum = sheet.getLastRowNum();
        System.out.println(lastRowNum);

        // 开始读取Sheet中的数据
        Row row = null;
        for (int i = 2; i <= 2; i++) {
            row = sheet.getRow(i);
            String unitPath = getCellValue(row.getCell(2));
            String unitId = getCellValue(row.getCell(3));
            System.out.println(unitPath + ": " + unitId);

            // 同步组织架构
            getUnits(unitId,sheetNo);
        }


        boolean b = true;
        if (b)
            return;

        String str = "无法登录+陈秋燕+13927651998+广东电网有限责任公司清远阳山供电局";
        String[] works = str.split(";");
        for (int i = 0; i < works.length; i++) {
            String name = getCellValue(row.getCell(1));


            CellStyle style = workbook.createCellStyle();
            Row rowOut = sheet.createRow(lastRowNum + i + 1);
            Cell cell = rowOut.createCell(0);
            // cell.setCellValue(String.valueOf(Double.valueOf(number).intValue() + i + 1));
            cell.setCellStyle(style);
            cell = rowOut.createCell(1);
            cell.setCellValue("系统管理");
            cell.setCellStyle(style);

            String[] work = works[i].split("\\+");
            //for(int j = 0;j < work.length;j++){
            //    System.out.println(work[j]);
            //}
            cell = rowOut.createCell(2);
            cell.setCellValue(work[3] + work[1] + work[0]);
            cell.setCellStyle(style);

            cell = rowOut.createCell(4);
            cell.setCellValue(work[1]);
            cell.setCellStyle(style);
            cell = rowOut.createCell(5);
            cell.setCellValue(work[2]);
            cell.setCellStyle(style);
            cell = rowOut.createCell(6);
            cell.setCellValue(work[3]);
            cell.setCellStyle(style);

            String date = DateUtil.parseDate(new Date(), "yyyy/MM/dd");
            cell = rowOut.createCell(7);
            cell.setCellValue(date);
            cell.setCellStyle(style);
            cell = rowOut.createCell(10);
            cell.setCellValue(date);
            cell.setCellStyle(style);
            cell = rowOut.createCell(11);
            cell.setCellValue("系统运维");
            cell.setCellStyle(style);
            cell = rowOut.createCell(13);
            cell.setCellValue(date);
            cell.setCellStyle(style);
            cell = rowOut.createCell(14);
            cell.setCellValue("已解决");
            cell.setCellStyle(style);
            cell = rowOut.createCell(15);
            cell.setCellValue("黄坚");
            cell.setCellStyle(style);
            cell = rowOut.createCell(18);
            cell.setCellValue("系统运维");
            cell.setCellStyle(style);
            cell = rowOut.createCell(20);
            cell.setCellValue("是");
            cell.setCellStyle(style);
            cell = rowOut.createCell(21);
            cell.setCellValue("黄坚");
            cell.setCellStyle(style);
            cell = rowOut.createCell(22);
            cell.setCellValue("已完成");
            cell.setCellStyle(style);
            cell = rowOut.createCell(23);
            cell.setCellValue(date);
            cell.setCellStyle(style);
        }

        File fileOut = new File("E:\\Downloads\\迅雷下载\\（汇总）工会管理实施运维工作日报-20190919.xlsx");
        if (!fileOut.exists()) {
            fileOut.createNewFile();
        }
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(fileOut);
            workbook.write(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void mainbak(String[] args) throws IOException {
        // 读取Excel
        File file = new File("D:\\SGS审厂名单.xlsx");
        InputStream is = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(2);

        // 创建Excel作导出用
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheetOut = wb.createSheet("普工");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow rowOut = sheetOut.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = rowOut.createCell(0);
        cell.setCellValue("工号");
        cell.setCellStyle(style);
        cell = rowOut.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = rowOut.createCell(2);
        cell.setCellValue("日期");
        cell.setCellStyle(style);
        cell = rowOut.createCell(3);
        cell.setCellValue("打卡1");
        cell.setCellStyle(style);
        cell = rowOut.createCell(4);
        cell.setCellValue("打卡2");
        cell.setCellStyle(style);
        cell = rowOut.createCell(5);
        cell.setCellValue("打卡3");
        cell.setCellStyle(style);
        cell = rowOut.createCell(6);
        cell.setCellValue("打卡4");
        cell.setCellStyle(style);
        cell = rowOut.createCell(7);
        cell.setCellValue("打卡5");
        cell.setCellStyle(style);
        cell = rowOut.createCell(8);
        cell.setCellValue("打卡6");
        cell.setCellStyle(style);
        cell = rowOut.createCell(9);
        cell.setCellValue("平时加班");
        cell.setCellStyle(style);
        cell = rowOut.createCell(10);
        cell.setCellValue("周末加班");
        cell.setCellStyle(style);

        sheetOut.setColumnWidth(0, 50 * 50);
        sheetOut.setColumnWidth(2, 50 * 60);

        // 时间设置
        String ym = "2018.05";
        Calendar calendar = Calendar.getInstance();
        int year = 2018;
        int month = Calendar.MAY;
        int date = 1;
        calendar.set(year, month, date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        int rowNum = 1;
        // 开始读取Sheet中的数据
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null)
                continue;

            String number = getCellValue(row.getCell(0));
            String name = getCellValue(row.getCell(1));

            int overtime = 0;

            for (int i = minDay; i <= maxDay; i++) {
                calendar.set(year, month, i);

                rowOut = sheetOut.createRow(rowNum);
                rowOut.createCell(0).setCellValue(number);
                rowOut.createCell(1).setCellValue(name);
                rowOut.createCell(2).setCellValue(DateUtil.parseDate(calendar.getTime(), "yyyy-MM-dd"));


                rowNum++;
            }
        }

        sheet = workbook.getSheetAt(3);

        sheetOut = wb.createSheet("职工");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        rowOut = sheetOut.createRow((int) 0);

        cell = rowOut.createCell(0);
        cell.setCellValue("工号");
        cell.setCellStyle(style);
        cell = rowOut.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = rowOut.createCell(2);
        cell.setCellValue("日期");
        cell.setCellStyle(style);
        cell = rowOut.createCell(3);
        cell.setCellValue("打卡1");
        cell.setCellStyle(style);
        cell = rowOut.createCell(4);
        cell.setCellValue("打卡2");
        cell.setCellStyle(style);

        sheetOut.setColumnWidth(0, 50 * 50);
        sheetOut.setColumnWidth(2, 50 * 60);

        rowNum = 1;
        // 开始读取Sheet中的数据
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null)
                continue;

            String number = getCellValue(row.getCell(0));
            String name = getCellValue(row.getCell(1));

            for (int i = minDay; i <= maxDay; i++) {
                calendar.set(year, month, i);

                rowOut = sheetOut.createRow(rowNum);
                rowOut.createCell(0).setCellValue(number);
                rowOut.createCell(1).setCellValue(name);
                rowOut.createCell(2).setCellValue(DateUtil.parseDate(calendar.getTime(), "yyyy-MM-dd"));


                rowNum++;
            }
        }

        File fileOut = new File("D:/" + ym + " 班表.xls");
        if (!fileOut.exists()) {
            fileOut.createNewFile();
        }
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(fileOut);
            wb.write(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
