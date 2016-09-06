package juli.infrastructure.excel;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 适用于word 2007
 * poi 版本 3.7
 */
public class GenerateWord3 {

    /**
     * 根据指定的参数值、模板，生成 word 文档
     * @param param 需要替换的变量
     * @param template 模板
     */
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {

                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);

                //处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();

                List<XWPFTable> l = new ArrayList();
                for (; it.hasNext();) {
                    XWPFTable element = (XWPFTable) it.next();
                    l.add(element);
                }
                for(XWPFTable table1:l){
                        XWPFTable table = table1;
                        List<XWPFTableRow> rows = table.getRows();
                        for (XWPFTableRow row : rows) {
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                                processParagraphs(paragraphListTable, param, doc);
                            }
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
    /**
     * 处理段落
     * @param paragraphList
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList,Map<String, Object> param,CustomXWPFDocument doc){
        if(paragraphList != null && paragraphList.size() > 0){
            for(XWPFParagraph paragraph:paragraphList){
                List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if(text != null){
                            boolean isSetText = false;
                            for (Entry<String, Object> entry : param.entrySet()) {
                                String key = entry.getKey();
                                if(text.indexOf(key) != -1){
                                    isSetText = true;
                                    Object value = entry.getValue();
                                    if (value instanceof String) {//文本替换
                                        text = text.replace(key, value.toString());
                                    } else if (value instanceof Map) {//图片替换
                                        text = text.replace(key, "");
                                        Map pic = (Map)value;
                                        int width = Integer.parseInt(pic.get("width").toString());
                                        int height = Integer.parseInt(pic.get("height").toString());
                                        int picType = getPictureType(pic.get("type").toString());
                                        byte[] byteArray = (byte[]) pic.get("content");
                                        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                        try {
                                      //  String ind = doc.addPictureData(byteInputStream, picType);
                                        int ind = doc.addPicture(byteInputStream, picType);
                                           // doc.createPicture(doc.getAllPictures().size()-1, width , height,paragraph);
                                            doc.createPicture(ind, width , height,paragraph);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if(isSetText){
                                run.setText(text,0);
                            }
                        }
                    }
                }
        }
    }
    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType){
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if(picType != null){
            if(picType.equalsIgnoreCase("png")){
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            }else if(picType.equalsIgnoreCase("wmf")){
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }
    /**
     * 将输入流中的数据写入字节数组
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(isClose){
                try {
                    in.close();
                } catch (Exception e2) {
                    System.out.println("关闭流失败");
                }
            }
        }
        return byteArray;
    }


//    public static void main(String[] args) throws Exception {
//
//        Map<String, Object> param = new HashMap<String, Object>();
//
//        Map<String,Object> header = new HashMap<String, Object>();
//        header.put("width", 100);
//        header.put("height", 150);
//        header.put("type", "jpg");
//        header.put("content", inputStream2ByteArray(new FileInputStream("E:\\joylive.png"), true));
//        param.put("Pic",header);
//        param.put("Pic1",header);
//        param.put("Pic2",header);
//        param.put("test", "huangqiqing");
//        param.put("name2", "12121");
//        param.put("A", "△");
//        param.put("B", "☆");
//        param.put("C", "√");
//        param.put("D", "×");
//
//        CustomXWPFDocument doc = generateWord(param, "E:"+File.separator+"半年维保.docx");
//        FileOutputStream fopts = new FileOutputStream("E:"+File.separator+"test.docx");
//        doc.write(fopts);
//        fopts.close();
//    }
}
