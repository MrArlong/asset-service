/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.macro.mall.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;

import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * File工具类，扩展 hutool 工具包
 * @author Zheng Jie
 * @date 2018-12-27
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 系统临时目录
     * <br>
     * windows 包含路径分割符，但Linux 不包含,
     * 在windows \\==\ 前提下，
     * 为安全起见 同意拼装 路径分割符，
     * <pre>
     *       java.io.tmpdir
     *       windows : C:\Users/xxx\AppData\Local\Temp\
     *       linux: /temp
     * </pre>
     */
    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;
    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;
    /**
     * 上传文件名最大长度
     */
    private static final int FILENAME_MAX_LENGTH = 255;
    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public static final String IMAGE = "图片";
    public static final String TXT = "文档";
    public static final String MUSIC = "音乐";
    public static final String VIDEO = "视频";
    public static final String OTHER = "其他";


    /**
     * MultipartFile转File
     */
    public static File toFile(MultipartFile multipartFile) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = "." + getExtensionName(fileName);
        File file = null;
        try {
            // 用uuid作为文件名，防止生成的临时文件重复
            file = File.createTempFile(IdUtil.simpleUUID(), prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        }catch(IOException e) {
            log.error(e.getMessage(), e);
        }
        return file;
    }

    /**
     * 获取文件扩展名，不带 .
     */
    public static String getExtensionName(String filename) {
        if((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 文件大小转换
     */
    public static String getSize(long size) {
        String resultSize;
        if(size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        }else if(size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        }else if(size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        }else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

    /**
     * inputStream 转 File
     */
    static File inputStreamToFile(InputStream ins, String name) {
        File file = new File(SYS_TEM_DIR + name);
        if(file.exists()) {
            return file;
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while((bytesRead = ins.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            IoUtil.close(os);
            IoUtil.close(ins);
        }
        return file;
    }

    /**
     * 将文件名解析成文件的上传路径
     */
    public static File upload(MultipartFile file, String filePath) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
        String name = getFileNameNoEx(file.getOriginalFilename());
        if(name.contains("\\") || name.contains("/")) {
            String[] names = name.split("\\\\|/");
            name = names[names.length - 1];
        }
        name = name.replaceAll("\\.", "");
        String suffix = getExtensionName(file.getOriginalFilename());
        String nowStr = "-" + format.format(new Date());
        try {
            String fileName = name + nowStr + "." + suffix;
            int fileNameLength = fileName.getBytes(StandardCharsets.UTF_8).length;
            if(fileNameLength > FILENAME_MAX_LENGTH) {
                fileName = IdUtil.fastSimpleUUID() + "." + suffix;
            }
            String path = filePath + fileName;
            // getCanonicalFile 可解析正确各种路径
            File dest = new File(path).getCanonicalFile();
            // 检测是否存在目录
            if(!dest.getParentFile().exists()) {
                if(!dest.getParentFile().mkdirs()) {
                    System.out.println("was not successful.");
                }
            }
            // 文件写入
            file.transferTo(dest);
            return dest;
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String newRelativeFilePath(String name, String suffix) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
        String type = FileUtil.getFileType(suffix);
        String fileName = type + File.separator + name + "-" + format.format(date) + "." + suffix;
        return fileName;
    }

    /**
     * 导出excel
     */
    public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
        String tempPath = SYS_TEM_DIR + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        /*BigExcelWriter writer = ExcelUtil.getBigWriter(file);*/
        MyExcel writer =MyExcel.getBigWriter();
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        SXSSFSheet sheet = (SXSSFSheet) writer.getSheet();
        //上面需要强转SXSSFSheet  不然没有trackAllColumnsForAutoSizing方法
        sheet.trackAllColumnsForAutoSizing();
        //列宽自适应
        writer.autoSizeColumnAll();
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream out = response.getOutputStream();
        // 终止后删除临时文件
        file.deleteOnExit();
        writer.flush(out, false);
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }
    /**
     * 导出excel自定义表名
     */
    public static void downloadExcelSheels(List<Map<String, Object>> list,List<Map<String,Object>> sheet2,String sheetName,String content1,String content2, HttpServletResponse response) throws IOException {
        String tempPath = SYS_TEM_DIR + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        /*BigExcelWriter  writer = ExcelUtil.getBigWriter(file);*/
        MyExcel writer =MyExcel.getBigWriter();
        if(StrUtil.isNotBlank(content1)){
            writer.merge(15, content1);
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //列宽自适应
        writer.autoSizeColumnAll();
        if(CollUtil.isNotEmpty(sheet2)){
            writer.setSheet(sheetName);
            writer.merge(20, content2);
            writer.write(sheet2, true);
            writer.autoSizeColumnAll();
        }
        SXSSFSheet sheet = (SXSSFSheet) writer.getSheet();
        //上面需要强转SXSSFSheet  不然没有trackAllColumnsForAutoSizing方法
        sheet.trackAllColumnsForAutoSizing();

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        ServletOutputStream out = response.getOutputStream();
        // 终止后删除临时文件
        file.deleteOnExit();
        writer.flush(out, false);
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    public static String getFileType(String type) {
        String documents = "txt doc pdf ppt pps xlsx xls docx";
        String music = "mp3 wav wma mpa ram ra aac aif m4a";
        String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg";
        if(image.contains(type)) {
            return IMAGE;
        }else if(documents.contains(type)) {
            return TXT;
        }else if(music.contains(type)) {
            return MUSIC;
        }else if(video.contains(type)) {
            return VIDEO;
        }else {
            return OTHER;
        }
    }

    public static void checkSize(long maxSize, long size) {
        // 1M
        int len = 1024 * 1024;
        if(size > (maxSize * len)) {
            throw new RuntimeException("文件超出规定大小:" + maxSize + "MB");
        }
    }

    /**
     * 判断两个文件是否相同
     */
    public static boolean check(File file1, File file2) {
        String img1Md5 = getMd5(file1);
        String img2Md5 = getMd5(file2);
        if(img1Md5 != null) {
            return img1Md5.equals(img2Md5);
        }
        return false;
    }

    /**
     * 判断两个文件是否相同
     */
    public static boolean check(String file1Md5, String file2Md5) {
        return file1Md5.equals(file2Md5);
    }

    private static byte[] getByte(File file) {
        // 得到文件长度
        byte[] b = new byte[(int) file.length()];
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            try {
                System.out.println(in.read(b));
            }catch(IOException e) {
                log.error(e.getMessage(), e);
            }
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }finally {
            IoUtil.close(in);
        }
        return b;
    }

    public static String getMd5(byte[] bytes) {
        // 16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(bytes);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            // 移位 输出字符串
            for(byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getMd5(File file) {
        return getMd5(getByte(file));
    }

    /**
     * 导出自定义excel
     */
    public static void downloadPOIExcel(List<Map<String, Object>> list, HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        //创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet();

        boolean isTitle = true;

        //创建字体
        HSSFFont ftSmall = workbook.createFont();
        ftSmall.setFontHeightInPoints((short) 8);

        HSSFFont ftNormal = workbook.createFont();
        ftNormal.setFontName("宋体");
        ftNormal.setFontHeightInPoints((short) 11);

        for(int i = 0; i < list.size(); i++) {

            Map<String, Object> rows = list.get(i);
            if(isTitle) {
                int index = 0;
                // 设置表头
                HSSFRow rowTitle = sheet.createRow(i);
                HSSFCellStyle style = workbook.createCellStyle();
                style.setFont(ftNormal);
                for(Map.Entry<String, Object> entry : rows.entrySet()) {
                    //添加单元格
                    HSSFCell titleCell = rowTitle.createCell(index);
                    titleCell.setCellStyle(style);
                    titleCell.setCellType(CellType.STRING);
                    titleCell.setCellValue(entry.getKey());
                    index++;
                }
                isTitle = false;
            }

            // 设置内容
            int index_content = 0;
            HSSFRow rowContent = sheet.createRow(i + 1);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFont(ftNormal);
            for(Map.Entry<String, Object> entry : rows.entrySet()) {

                //添加单元格
                HSSFCell valueCell = rowContent.createCell(index_content);
                valueCell.setCellStyle(style);
                valueCell.setCellType(CellType.STRING);
                String value = "";
                if(entry.getValue() != null) {
                    value = entry.getValue().toString();
                }
                //往单元格中写入的内容,并使用ft格式化单词
                if(value.contains("(")) {
                    String[] subStr = value.split("\\(");
                    HSSFRichTextString textString = new HSSFRichTextString(value);
                    textString.applyFont(
                            value.indexOf(subStr[0]),
                            value.indexOf(subStr[0]) + subStr[0].length(),
                            ftNormal
                    );
                    textString.applyFont(
                            value.indexOf(subStr[1]),
                            value.indexOf(subStr[1]) + subStr[1].length(),
                            ftSmall
                    );
                    valueCell.setCellValue(textString);
                }else {
                    valueCell.setCellValue(value);
                }

                index_content++;
            }
        }
        setSizeColum(sheet, list.size());
        ServletOutputStream out = response.getOutputStream();
        response.reset();

        //解决不同浏览器压缩包名字含有中文时乱码的问题
        String agent = request.getHeader("USER-AGENT");
        try {
            if(agent.contains("MSIE") || agent.contains("Trident")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/octet-stream");
        //告诉客户端该文件不是直接解析 而是以附件形式打开(下载)----filename="+filename 客户端默认对名字进行解码
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        workbook.write(response.getOutputStream());
        out.close();
    }

    /**
     * 设置列宽自适应
     * @param sheet 需要设置宽度自适应的sheet
     * @param size  列数
     */
    public static void setSizeColum(HSSFSheet sheet, int size) {
        for(int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for(int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if(sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                }else {
                    currentRow = sheet.getRow(rowNum);
                }
                if(currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    //判断该列的数据类型是否为String类型
                    if(currentCell.getCellType() == CellType.STRING) {
                        //这里值转换String类型的数据的列，不然currentCell.getStringCellValue()会报错
                        int length = currentCell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length;
                        if(columnWidth < length) {
                            columnWidth = length;
                        }
                    }else {
                        //如果有的列的数据类型不是String类型需在此处补充；如果是日期类型或者其他就得这样先转成String再设置
                        //此处示例日期类型
//                        int length = currentCell.getDateCellValue().toString().getBytes(StandardCharsets.UTF_8).length;
//                        if (columnWidth < length){
//                            columnWidth = length;
//                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @param file
     * @param fileName
     * @param deleteOnExit
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file, String fileName, boolean deleteOnExit) {
        // 文件名需要带后缀名，按需补全
        if(StrUtil.isEmpty(fileName)) {
            fileName = file.getName();
        }else {
            int dotIndex = file.getName().lastIndexOf(".");
            if(dotIndex > -1) {
                String fileSuffix = file.getName().substring(dotIndex);
                if(!fileName.endsWith(fileSuffix)) {
                    fileName += fileSuffix;
                }
            }
        }

        // 对文件名中的特殊符号进行处理
        fileName = fileName.replaceAll(",", "，");

        // 返回ISO8859-1编码的字符串
        try {
            fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-Length", Long.toString(file.length()));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        FileInputStream fis = null;
        ServletOutputStream outputStream = null;
        try {
            fis = new FileInputStream(file);
            outputStream = response.getOutputStream();
            IOUtils.copy(fis, outputStream);
            response.flushBuffer();
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                    if(deleteOnExit) {
                        file.deleteOnExit();
                    }
                }catch(IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                }catch(IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

}
