//package juli.infrastructure.excel;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.POIXMLDocument;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class GenerateWord {
//
//    /**
//     * 段落
//     * 读取模板生成相应的文件并导出
//     * @param map
//     * @param response
//     */
//    public static void  generateWord(Map<String,String> map,HttpServletResponse response){
//            try{
//                OPCPackage opc = POIXMLDocument.openPackage("E:"+File.separator+"半年维保.docx");
//                XWPFDocument xwpf = new XWPFDocument(opc);
//                Iterator<XWPFParagraph> itPara = xwpf.getParagraphsIterator();
//                while(itPara.hasNext()){
//                    XWPFParagraph paragraph = itPara.next();
//                    List<XWPFRun> runs = paragraph.getRuns();
//                    if(runs.size()>0){
//                        for(int i=0;i<runs.size();i++){
//                            String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
//                            if(StringUtils.isNotEmpty(oneparaString)){
//                                for(Map.Entry<String,String> entry:map.entrySet()){
//                                    oneparaString = oneparaString.replace(entry.getKey(),entry.getValue());
//                                }
//                                runs.get(i).setText(oneparaString,0);
//                            }
//                        }
//                    }
//                }
//                //生成word并下载到指定位置
//                String filename = "合同.doc";
//                response.setHeader("Content-disposition", "attachment; filename="
//                        + new String(filename.toString().getBytes("GB2312"), "ISO8859-1"));
//                response.setContentType("application/msword;charset=gb2312");
//                OutputStream os= response.getOutputStream();
//                xwpf.write(os);
////                os.flush();
//                os.close();
//                xwpf.close();
//            }catch (Exception e1){
//                e1.printStackTrace();
//            }
//
//    }
//
//}