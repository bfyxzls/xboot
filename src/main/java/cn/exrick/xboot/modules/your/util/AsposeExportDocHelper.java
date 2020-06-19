package cn.exrick.xboot.modules.your.util;

import cn.hutool.core.date.DateUtil;
import com.aspose.words.*;
import com.aspose.words.net.System.Data.DataRelation;
import com.aspose.words.net.System.Data.DataSet;
import com.aspose.words.net.System.Data.DataTable;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class AsposeExportDocHelper {
    private static InputStream license;
    private static InputStream fileInput;
    private static File outputFile;

    /**
     * 获取license
     *
     * @return
     */
    public static boolean getLicense(String templateName) {
        boolean result = false;
        try {

            license = new ClassPathResource("license.xml").getInputStream();
            fileInput = new ClassPathResource(templateName).getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void generateApproveForm(HttpServletResponse response, String[] flds, Object[] vals) {
        // 验证License
        if (!getLicense("templates/approveForm.docx")) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(fileInput);

            //主要调用aspose.words的邮件合并接口MailMerge
            //3.1 填充单个文本域
            doc.getMailMerge().execute(flds, vals); //调用接口
            //3.2 填充单层循环的表格
            DataTable approveTb = new DataTable("approve"); //审批意见表格
            approveTb.getColumns().add("approveNode"); //0 增加三个列
            approveTb.getColumns().add("suggestion"); //1
            approveTb.getColumns().add("approveTime"); //2
            doc.getMailMerge().executeWithRegions(approveTb); //调用接口
            DataTable counterTb = new DataTable("counterpart"); //相对方
            counterTb.getColumns().add("counterpartName"); //0
            counterTb.getColumns().add("contractDegree"); //1
            counterTb.getColumns().add("counterpartType"); //1
            counterTb.getColumns().add("legalPersonName");
            counterTb.getColumns().add("officeAddress");
            DataTable linkManTb = new DataTable("linkMan"); //相对方联系人
            linkManTb.getColumns().add("counterpartName");
            linkManTb.getColumns().add("linkman"); //0
            linkManTb.getColumns().add("contactPhone"); //1
            DataSet counterSet = new DataSet();
            counterSet.getTables().add(counterTb);
            counterSet.getTables().add(linkManTb);
            String[] contCols = {"counterpartName"};
            String[] lstCols = {"counterpartName"};
            counterSet.getRelations().add(new DataRelation("CounterInfo", counterTb, linkManTb, contCols, lstCols));
            doc.getMailMerge().executeWithRegions(counterSet); //调用接口


            //合并表格
            int count = doc.getChildNodes(NodeType.TABLE, true).getCount();
            for (int i = 0; i < count - 1; i++) {
                Table table1 = (Table) doc.getChild(NodeType.TABLE, i, true);
                Table table2 = (Table) doc.getChild(NodeType.TABLE, i + 1, true);
                table1.getParentNode().insertAfter(table2, table1);
            }

            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(vals[4] + "审批单" + "_" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss") + ".pdf", "UTF-8"));
            response.setContentType("application/octet-stream;charset=UTF-8");

            OutputStream output = response.getOutputStream();
            doc.save(output, SaveFormat.PDF);

            output.flush();
            output.close();

            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
