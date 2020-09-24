# WordToPdfConverter
> 这个可以实现word导出为pdf 但是格式有点乱，造成格式这块样式不好
>
> 这里遇到问题最多的是中文字体的问题，要是找不到字体，word的内容不能正确输入到PDF中

## 这个工程的思路
> 通过POI获取内容，获取的是文本内容，把文本内容添加到Itext的Document中，输出PDF

### pom依赖
```
    <!--*************************WordToPdfConverter 示例使用依赖 开始**********************-->
        <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.itextpdf/itext-asian -->
        <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itext-asian</artifactId>
        <version>5.2.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.17</version>
        </dependency>
        <!--*************************WordToPdfConverter 示例使用依赖 结束**********************-->
```
### 示例代码
```
package com.zdltech.wordtopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 这个可以实现word导出为pdf 但是格式有点乱，造成格式这块样式不好
 */
public class WordToPdfConverter{
    public static void main(String[] args){
//        selectFiles();
    }

    public static void selectFiles(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Microsoft Word 2007+", "docx");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File[] Files=chooser.getSelectedFiles();
            System.out.println("Please wait...");
            for( int i=0;i<Files.length;i++){
                String wordfile=Files[i].toString();
                convertWordToPdf(wordfile,wordfile.substring(0,wordfile.indexOf('.'))+".pdf");
            }
            System.out.println("Conversion complete");
        }
    }
    public static void convertWordToPdf(String src, String desc){
        try{
            //create file inputstream object to read data from file
            FileInputStream fs=new FileInputStream(src);
            //create document object to wrap the file inputstream object
            XWPFDocument doc=new XWPFDocument(fs);
            //72 units=1 inch
            Document pdfdoc=new Document(PageSize.A4,72,72,72,72);
            //create a pdf writer object to write text to mypdf.pdf file
            PdfWriter pwriter=PdfWriter.getInstance(pdfdoc, new FileOutputStream(desc));
            //specify the vertical space between the lines of text
            pwriter.setInitialLeading(20);
            //get all paragraphs from word docx
            List<XWPFParagraph> plist=doc.getParagraphs();
            //FontFactory.register("C:\\Windows\\Fonts\\simfang.ttf","FangSong");//注册本地字体
//            Font f = FontFactory.getFont("C:\\Windows\\Fonts\\simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED,10f, Font.NORMAL, BaseColor.BLACK);
//            String path = "C:\\Windows\\Fonts\\simsun.ttc,0";
//            BaseFont bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font f = new Font(bf, 10f, Font.NORMAL, BaseColor.BLACK);
//            Font f = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,10f, Font.NORMAL, BaseColor.BLACK);//使用Itext 提供的字体  宋体
            //open pdf document for writing
            pdfdoc.open();
            for (int i = 0; i < plist.size(); i++) {
                //read through the list of paragraphs
                XWPFParagraph pa = plist.get(i);
                //get all run objects from each paragraph
                List<XWPFRun> runs = pa.getRuns();
                //read through the run objects
                Paragraph paragraph = new Paragraph();
                paragraph.setAlignment(pa.getAlignment().getValue());

                for (int j = 0; j < runs.size(); j++) {
                    XWPFRun run=runs.get(j);
                    //get pictures from the run and add them to the pdf document
                    List<XWPFPicture> piclist=run.getEmbeddedPictures();
                    //traverse through the list and write each image to a file
                    Iterator<XWPFPicture> iterator=piclist.iterator();
                    while(iterator.hasNext()){
                        XWPFPicture pic=iterator.next();
                        XWPFPictureData picdata=pic.getPictureData();
                        byte[] bytepic=picdata.getData();
                        Image imag=Image.getInstance(bytepic);
                        pdfdoc.add(imag);

                    }
                    //get color code
                    int color=getCode(run.getColor());
                    //construct font object
                    Font f=null;
                    if(run.isBold() && run.isItalic()) {
                        f =  FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,run.getFontSize(), Font.NORMAL, BaseColor.BLACK);
//                        f = FontFactory.getFont("FangSong", run.getFontSize(), Font.BOLDITALIC, new BaseColor(color));
//                        f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLDITALIC, new BaseColor(color));
                    }else if(run.isBold()) {
//                        f = FontFactory.getFont("FangSong", run.getFontSize(), Font.BOLD, new BaseColor(color));
                        f =  FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,run.getFontSize(), Font.BOLD, BaseColor.BLACK);
//                        f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLD, new BaseColor(color));
                    }else if(run.isItalic()) {
                        f =  FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,run.getFontSize(), Font.ITALIC, BaseColor.BLACK);
//                        f = FontFactory.getFont("FangSong", run.getFontSize(), Font.ITALIC, new BaseColor(color));
//                        f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.ITALIC, new BaseColor(color));
                    }else if(run.isStrikeThrough()) {
                        f =  FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,run.getFontSize(), Font.STRIKETHRU, BaseColor.BLACK);
//                        f = FontFactory.getFont("FangSong", run.getFontSize(), Font.STRIKETHRU, new BaseColor(color));
//                        f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.STRIKETHRU, new BaseColor(color));
                    }else {
                        f =  FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,run.getFontSize(), Font.NORMAL, BaseColor.BLACK);
//                        f = FontFactory.getFont("FangSong", run.getFontSize(), Font.NORMAL, new BaseColor(color));
//                        f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.NORMAL, new BaseColor(color));
                    }
                    //construct unicode string
                    String text=run.getText(-1);

                    byte[] bs;
                    if (text!=null){
                        bs=text.getBytes();
                        String str=new String(bs,"UTF-8");
                        //add string to the pdf document
                        Chunk chObj1=new Chunk(str,f);
                        pdfdoc.add(chObj1);
//                        paragraph.add(chObj1);//添加到段落
                    }

                }
//                pdfdoc.add(paragraph);//添加段落
                //output new line
                pdfdoc.add(new Chunk(Chunk.NEWLINE));
            }
            //close pdf document
            pdfdoc.add(new Paragraph("my first pdf demo"));
            pdfdoc.close();
        }catch(Exception e){e.printStackTrace();}
    }
    public static int getCode(String code){
        int colorCode;
        if(code!=null)
            colorCode=Long.decode("0x"+code).intValue();
        else
            colorCode=Long.decode("0x000000").intValue();
        return colorCode;
    }

}

```