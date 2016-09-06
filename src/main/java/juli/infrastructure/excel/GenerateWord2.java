//package juli.infrastructure.excel;
//
//import org.apache.poi.POIXMLDocument;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.usermodel.*;
//import org.apache.xmlbeans.XmlException;
//import org.apache.xmlbeans.XmlToken;
//import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
//import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
//import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class GenerateWord2 {
//
//    /**
//     * 表格
//     * 读取模板生成相应的文件并导出
//     * @param map
//     */
//    public static void  generateWord(Map<String,Object> map){
//            try{
////                OPCPackage opc = POIXMLDocument.openPackage("./wordTemplates/半年维保.docx");
//                OPCPackage opc = POIXMLDocument.openPackage("E:"+File.separator+"半年维保.docx");
//
//                CustomXWPFDocument doc = new CustomXWPFDocument(opc);
//
////                XWPFDocument xwpf = new XWPFDocument(opc);
//                Iterator<XWPFTable> it = doc.getTablesIterator();
//                while(it.hasNext()) {
//                    XWPFTable table = it.next();
//                    List<XWPFTableRow> rows = table.getRows();
//                    for(XWPFTableRow row : rows){
//                        List<XWPFTableCell> cells = row.getTableCells();
//                        for(XWPFTableCell cell : cells){
//                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
//                            processParagraphs(paragraphListTable,map,doc);
//                        }
//                    }
//                }
//                //插入图片
////                setPic(xwpf,mapImage);
//                //生成word并下载到指定位置
//                FileOutputStream os = new FileOutputStream(new File("E:"+File.separator+"test1.docx"));
//                doc.write(os);
//               // xwpf.close();
//                os.close();
//            }catch (Exception e1){
//                e1.printStackTrace();
//            }
//
//    }
//
//    public static void setPic(XWPFDocument document,Map<String,String> mapImage){
//       // XWPFParagraph p = doc.createParagraph();
//       // XWPFRun r = p.createRun();
//        List listRun;
//        List<XWPFParagraph> listParagraphs = document.getParagraphs();
//        for (int sa = 0; sa < listParagraphs.size(); sa++) {
//            for (Map.Entry<String,String> e : mapImage.entrySet()) {
//                String key = e.getKey();
//                if (listParagraphs.get(sa).getText().indexOf(e.getKey()) != -1) {
//                    listRun = listParagraphs.get(sa).getRuns();
//                    for (int p = 0; p < listRun.size(); p++) {
//                        if (listRun.get(p).toString().equals(e.getKey())) {
//                            listParagraphs.get(sa).removeRun(p);//移除占位符
//                            //获得当前CTInline
//                            CTInline inline = listParagraphs.get(sa).createRun().getCTR().addNewDrawing().addNewInline();
//                            try {
//                                insertPicture(document,e.getValue(),inline,200,200);
//                            } catch (InvalidFormatException e1) {
//                                e1.printStackTrace();
//                            } catch (FileNotFoundException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public static void insertPicture(XWPFDocument document,String filePath,CTInline inline,int width, int height) throws InvalidFormatException, FileNotFoundException{
//        String ind = document.addPictureData(new FileInputStream(filePath), 5);
//        int id = document.getAllPictures().size()-1;
//        final int EMU = 9525;
//        width *= EMU;
//        height *= EMU;
//        String blipId = document.getAllPictures().get(id).getPackageRelationship()
//                .getId();
//        String picXml = ""
//                + ""
//                + "   "
//                + "      "
//                + "         " + ""
//                + id
//                + "\" name=\"Generated\"/>"
//                + "            "
//                + "         "
//                + "         "
//                + ""
//                + blipId
//                + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
//                + "            "
//                + "               "
//                + "            "
//                + "         "
//                + "         "
//                + "            "
//                + "               "
//                + ""
//                + width
//                + "\" cy=\""
//                + height
//                + "\"/>"
//                + "            "
//                + "            "
//                + "               "
//                + "            "
//                + "         "
//                + "      "
//                + "   " + "";
//        inline.addNewGraphic().addNewGraphicData();
//        XmlToken xmlToken = null;
//        try {
//            xmlToken = XmlToken.Factory.parse(picXml);
//        } catch (XmlException xe) {
//            xe.printStackTrace();
//        }
//        inline.set(xmlToken);
//        inline.setDistT(0);
//        inline.setDistB(0);
//        inline.setDistL(0);
//        inline.setDistR(0);
//        CTPositiveSize2D extent = inline.addNewExtent();
//        extent.setCx(width);
//        extent.setCy(height);
//        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
//        docPr.setId(id);
//        docPr.setName("IMG_" + id);
//        docPr.setDescr("IMG_" + id);
//    }
//
//    public static void processParagraphs(List<XWPFParagraph> paragraphListTable,Map<String,Object> map,
//                                         CustomXWPFDocument doc){
//        if(paragraphListTable !=null && paragraphListTable.size()>0){
//
//            for(XWPFParagraph paragraph :paragraphListTable){
//
//                List<XWPFRun> runs = paragraph.getRuns();
//                for(XWPFRun run : runs){
//                    String text = run.getText(0);
//                    if(null != text && text.length() >0){
//                        boolean isSetText = false;
//                        for(Map.Entry<String,Object> entry : map.entrySet()){
//                            String key = entry.getKey();
//                            if(text.indexOf(key) !=-1){
//                                isSetText = true;
//                                Object value= entry.getValue();
//                                if(value instanceof String){
//                                    text = text.replace(key,value.toString());
//                                }else if (value instanceof Map) {//图片替换
//                                    text = text.replace(key, "");
//                                    Map pic = (Map)value;
//                                    int width = Integer.parseInt(pic.get("width").toString());
//                                    int height = Integer.parseInt(pic.get("height").toString());
//                                    int picType = getPictureType(pic.get("type").toString());
//                                    byte[] byteArray = (byte[]) pic.get("content");
//                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
//                                    try {
//                                        String ind = doc.addPictureData(byteInputStream, picType);
//                                        doc.createPicture(doc.getAllPictures().size() - 1, width, height, paragraph);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }
//                        if(isSetText){
//                            run.setText(text,0);
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 根据图片类型，取得对应的图片类型代码
//     * @param picType
//     * @return int
//     */
//    private static int getPictureType(String picType){
//        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
//        if(picType != null){
//            if(picType.equalsIgnoreCase("png")){
//                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
//            }else if(picType.equalsIgnoreCase("dib")){
//                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
//            }else if(picType.equalsIgnoreCase("emf")){
//                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
//            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
//                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
//            }else if(picType.equalsIgnoreCase("wmf")){
//                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
//            }
//        }
//        return res;
//    }
//
//    /**
//     * 将输入流中的数据写入字节数组
//     * @param in
//     * @return
//     */
//    public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){
//        byte[] byteArray = null;
//        try {
//            int total = in.available();
//            byteArray = new byte[total];
//            in.read(byteArray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            if(isClose){
//                try {
//                    in.close();
//                } catch (Exception e2) {
//                    System.out.println("关闭流失败");
//                }
//            }
//        }
//        return byteArray;
//    }
//
////        public static void  main(String[] args) {
////            GenerateWord2 g = new GenerateWord2();
////            Map<String,Object> map = new HashMap<>();
////            map.put("test","bbbb");
////            map.put("name2","aaa");
////            Map<String,Object> header = new HashMap<String, Object>();
////            header.put("width", 100);
////            header.put("height", 150);
////            header.put("type", "png");
////            try {
////                header.put("content", inputStream2ByteArray(new FileInputStream("E:\\joylive.png"), true));
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////            }
////            map.put("Pic",header);
////            g.generateWord(map);
////}
//
//}