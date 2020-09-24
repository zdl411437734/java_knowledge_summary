package com.zdltech.wordtopdf;


import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beldon.
 * Copyright (c)  2017/7/27, All Rights Reserved.
 * https://www.fuyhui.com/
 */
public abstract class PdfUtil {

    private static BaseFont defaultFont = null;

    static {
        try {
            loadFont();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    //加载字体
    private static void loadFont() throws IOException, DocumentException {
        //arialuni能够显示中文
        defaultFont = BaseFont.createFont(new File("C:\\Windows\\Fonts\\simfang.ttf").getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        String path = new File("C:\\Windows\\Fonts\\simfang.ttf").getAbsolutePath();
        FontFactory.registerDirectory(path); //加载字体
    }

    /**
     * word模板解析转PDF
     * <p>
     * 主要用到 velocity 模板引擎，word里面要插入 MergeField 域，变量格式是velocity格式
     *
     * @param template     模板
     * @param data         模板数据
     * @param outputStream 输出流
     */
    public static void render(File template, Map<String, String> data, OutputStream outputStream) {
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            InputStream in = new FileInputStream(template);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
            report.setFieldsMetadata(new FieldsMetadata());

            // 2) Create context Java model
            IContext context = report.createContext();
            data.forEach(context::put);
            // report.process(context, out);
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);

            //设置字体
            PdfOptions pdfOptions = PdfOptions.create();
            pdfOptions.fontProvider((familyName, encoding, size, style, color) -> {
                try {
                    if (FontFactory.contains(familyName)) { //如果font工厂里面有font，则加载，否则使用默认字体
                        return FontFactory.getFont(familyName, encoding, size, style, color);
                    } else {
                        return new Font(defaultFont, size, style, color);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
                }
            });
            options.subOptions(pdfOptions);

            report.convert(context, options, outputStream);

        } catch (IOException | XDocReportException e) {
            e.printStackTrace();
        }
    }

    public static void render(String templatePath, Map<String, String> data, OutputStream outputStream) {
        render(new File(templatePath), data, outputStream);
    }

    public static void render(String templatePath, Map<String, String> data, String outputFilePath) throws FileNotFoundException {
        render(new File(templatePath), data, new FileOutputStream(new File(outputFilePath)));
    }


    public static void htmlToPdf(File htmlFile, String pdfPath) throws Exception {
        // step 1
        String url = htmlFile.toURI().toURL().getPath();
        System.out.println(url);
        // step 2
        OutputStream os = new FileOutputStream(pdfPath);
        ITextRenderer renderer = new ITextRenderer();
        String result = getHtmlByPageName2(url);
        System.out.println(result);
        renderer.setDocumentFromString(result);
        // step 3 解决中文支持
        ITextFontResolver fontResolver = renderer.getFontResolver();
        if("linux".equals(getCurrentOperationSystem())){
            fontResolver.addFont("/Users/jason/Desktop/test/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }else{
            fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("C:/Windows/Fonts/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            fontResolver.addFont("C:/Windows/Fonts/msyh.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        }
        renderer.layout();
        renderer.createPDF(os);
        os.close();
    }

    private static String getHtmlByPageName2(String filePath) throws IOException {
        // /BOOT-INF/classes/templates/dashboard.html
        // 返回读取指定资源的输入流
        InputStream is = new FileInputStream(new File(filePath));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = "";
        StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    private static String getCurrentOperationSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        return os;
    }

    //demo
    public static void main(String[] args) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("name", "Demo我的中文");
            data.put("project", "23231");
            data.put("demoName", "Demo我的中文");
            render("F:/test/pdf/收入证明.docx", data, "F:/test/pdf/收入证明06.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            // HTML中需要指定字体（支持中文的字体）
//            htmlToPdf(new File("F:\\html\\kaili.html"),"F:\\html\\test03.pdf");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }




    }
}

