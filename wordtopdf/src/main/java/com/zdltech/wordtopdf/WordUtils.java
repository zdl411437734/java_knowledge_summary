package com.zdltech.wordtopdf;
/*
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;*/

public class WordUtils {
    /*
    public static void main(String[] args) throws Exception {
        //注意看文件中字体的格式，不然坑死你
        WordUtils.convertDocxToPdfOne("F:/test/pdf/收入证明.docx","F:/test/pdf/收入证明09.pdf");
    }

    public static void convertDocxToPdf(String docxPath,String pdfPath) throws Exception{
        try {
            URL url = new URL("file:///C:/Windows/Fonts");
            PhysicalFonts.addPhysicalFont(url);
            Mapper  fontMapper = new IdentityPlusMapper();
//            fontMapper.put("隶书", PhysicalFonts.get("lisu"));
//            fontMapper.put("宋体",PhysicalFonts.get("simsun"));
//            fontMapper.put("微软雅黑",PhysicalFonts.get("microsoft yahei"));
//            fontMapper.put("黑体",PhysicalFonts.get("simhei"));
//            fontMapper.put("楷体",PhysicalFonts.get("kaiti"));
//            fontMapper.put("新宋体",PhysicalFonts.get("nsimsun"));
//            fontMapper.put("华文行楷", PhysicalFonts.get("stxingkai"));
//            fontMapper.put("华文仿宋", PhysicalFonts.get("fangsong"));
//            fontMapper.put("宋体扩展",PhysicalFonts.get("simsun-extb"));
            fontMapper.put("仿宋",PhysicalFonts.get("fangsong"));
//            fontMapper.put("仿宋_GB2312",PhysicalFonts.get("fangsong_gb2312"));
//            fontMapper.put("幼圆",PhysicalFonts.get("youyuan"));
//            fontMapper.put("华文宋体",PhysicalFonts.get("stsong"));
//            fontMapper.put("华文中宋",PhysicalFonts.get("stzhongsong"));
            //解决宋体（正文）和宋体（标题）的乱码问题
//            PhysicalFonts.put("PMingLiU", PhysicalFonts.get("simsun"));
//            PhysicalFonts.put("新細明體", PhysicalFonts.get("simsun"));
//            PhysicalFonts.put("隶书", PhysicalFonts.get("lisu"));
//            PhysicalFonts.put("宋体",PhysicalFonts.get("simsun"));

            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File(docxPath));
            mlPackage.setFontMapper(fontMapper);
            OutputStream os = new FileOutputStream(pdfPath);
            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setOpcPackage(mlPackage);
            Docx4J.toFO(foSettings,os,Docx4J.FLAG_EXPORT_PREFER_XSL);
        } catch (Docx4JException e) {
            e.printStackTrace();
        } finally {
        }
    }


  *//*  *//**//**
     * docx文档转换为PDF
     *
     * @param pdfPath PDF文档存储路径
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     *//*
    public static void convertDocxToPdfOne(String docxPath, String pdfPath) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(docxPath);
            fileOutputStream = new FileOutputStream(new File(pdfPath));
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(file);
            setFontMapper(mlPackage);
            Docx4J.toPDF(mlPackage, new FileOutputStream(new File(pdfPath)));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    private static void setFontMapper(WordprocessingMLPackage mlPackage) throws Exception {
        Mapper fontMapper = new IdentityPlusMapper();
        fontMapper.put("隶书", PhysicalFonts.get("lisu"));
        fontMapper.put("宋体", PhysicalFonts.get("simsun"));
        fontMapper.put("微软雅黑", PhysicalFonts.get("microsoft yahei"));
        fontMapper.put("黑体", PhysicalFonts.get("simhei"));
        fontMapper.put("楷体", PhysicalFonts.get("kaiti"));
        fontMapper.put("新宋体", PhysicalFonts.get("nsimsun"));
        fontMapper.put("华文行楷", PhysicalFonts.get("stxingkai"));
        fontMapper.put("华文仿宋", PhysicalFonts.get("stfangsong"));
        fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extb"));
        fontMapper.put("仿宋", PhysicalFonts.get("fangsong"));
        fontMapper.put("仿宋_GB2312", PhysicalFonts.get("fangsong_GB2312"));
        fontMapper.put("幼圆", PhysicalFonts.get("youyuan"));
        fontMapper.put("华文宋体", PhysicalFonts.get("stsong"));
        fontMapper.put("华文中宋", PhysicalFonts.get("stzhongsong"));
        mlPackage.setFontMapper(fontMapper);
    }
*/
}
