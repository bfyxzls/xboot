package cn.exrick.xboot.modules.your.util;

import com.aspose.words.*;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;

public class AsposeHelper {
    private static InputStream license;
    private static InputStream fileInput;
    private static File outputFile;

    /**
     * 获取license
     *
     * @param
     * @param urlstr
     * @return
     */
    public static boolean getLicense(String urlstr, String path) {
        boolean result = false;
        try {

            ClassPathResource resource = new ClassPathResource("lib/aspose-worlds-license.xml");
            license = resource.getInputStream();
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            fileInput = conn.getInputStream();
            outputFile = new File(path);// 输出路径
            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Inserts a watermark into a document.  you need saved the doc by yourself.
     *
     * @param doc           The input document file.
     * @param watermarkText Text of the watermark.
     */
    public static void insertWatermarkText(Document doc, String watermarkText) {
        // 居中
        insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
            @Override
            public Object apply(Shape watermark) {
                // Place the watermark in the page center.
                watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
                watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
                watermark.setWrapType(WrapType.NONE); // TOP_BOTTOM : 将所设置位置的内容往上下顶出去
                watermark.setVerticalAlignment(VerticalAlignment.CENTER);
                watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);

                return null;
            }
        });
//        // 顶部
//        insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
//            @Override
//            public Object apply(Shape watermark) {
//                watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
//                watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
//                watermark.setWrapType(WrapType.NONE);
//                //  我们需要自定义距离顶部的高度
//                // watermark.setVerticalAlignment(VerticalAlignment.TOP);
//                watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
//                // 设置距离顶部的高度
//                watermark.setTop(160);
//
//                return null;
//            }
//        });
//        // 底部
//        insertWatermarkText(doc, watermarkText, new Function<Shape, Object>() {
//            @Override
//            public Object apply(Shape watermark) {
//                watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.MARGIN);
//                watermark.setRelativeVerticalPosition(RelativeVerticalPosition.MARGIN);
//                watermark.setWrapType(WrapType.NONE);
//                // 我们需要自定义距离顶部的高度
//                // watermark.setVerticalAlignment(VerticalAlignment.BOTTOM);
//                watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
//                // 设置距离顶部的高度
//                watermark.setTop(480);
//
//                return null;
//            }
//        });
    }

    private static void insertWatermarkText(Document doc, String watermarkText,
                                            Function<Shape, Object> watermaskPositionConfigFunc) {
        // Create a watermark shape. This will be a WordArt shape.
        // You are free to try other shape types as watermarks.
        Shape watermark = new Shape(doc, ShapeType.TEXT_PLAIN_TEXT);
        // Set up the text of the watermark.
        watermark.getTextPath().setText(watermarkText);


        // Set up the text of the watermark.
        // 这里设置为宋体可以保证在转换为PDF时中文不是乱码.
        watermark.getTextPath().setFontFamily("宋体");//Arial;
        try {
            // 水印大小
            watermark.setWidth(400);
            watermark.setHeight(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Text will be directed from the bottom-left to the top-right corner.
        // 左下到右上
        watermark.setRotation(-30);

        // Remove the following two lines if you need a solid black text.
        final String colorStr = "E0E0E0";
        watermark.getFill().setColor(new java.awt.Color(Integer.parseInt(colorStr, 16))); // Try Color.lightGray to get more Word-style watermark
        watermark.setStrokeColor(new java.awt.Color(Integer.parseInt(colorStr, 16))); // Try Color.lightGray to get more Word-style watermark

        // Place the watermark in the special location of page .
        watermaskPositionConfigFunc.apply(watermark);

        // Create a new paragraph and append the watermark to this paragraph.
        Paragraph watermarkPara = new Paragraph(doc);
        watermarkPara.appendChild(watermark);

        // Insert the watermark into all headers of each document section.
        for (Section sect : doc.getSections()) {
            // There could be up to three different headers in each section, since we want
            // the watermark to appear on all pages, insert into all headers.
            insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_PRIMARY);
            // 以下注释掉不影响效果, 未作深入研究, 时间有限.
            //          insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_FIRST);
            //          insertWatermarkIntoHeader(watermarkPara, sect, HeaderFooterType.HEADER_EVEN);
        }
        // 参考下API : https://apireference.aspose.com/java/words/com.aspose.words/ShapeBase
        //watermark.setZOrder(-100);
    }

    private static void insertWatermarkIntoHeader(Paragraph watermarkPara, Section sect,
                                                  int headerType) {
        HeaderFooter header = sect.getHeadersFooters().getByHeaderFooterType(headerType);

        if (header == null) {
            // There is no header of the specified type in the current section, create it.
            header = new HeaderFooter(sect.getDocument(), headerType);
            sect.getHeadersFooters().add(header);
        }

        // Insert a clone of the watermark into the header.
        try {
            header.appendChild(watermarkPara.deepClone(true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static File ConvertWordToPdf(String urlstr, String path, String waterText) {
        // 验证License
        if (!getLicense(urlstr, path)) {
            return null;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(fileInput);

            insertWatermarkText(doc, waterText);

            FileOutputStream fileOS = new FileOutputStream(outputFile);

            doc.save(fileOS, SaveFormat.PDF);

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n" + "文件保存在:" + outputFile.getPath());

            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
