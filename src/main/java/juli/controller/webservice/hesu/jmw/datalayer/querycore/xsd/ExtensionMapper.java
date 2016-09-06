
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:09:26 BST)
 */

        
            package juli.controller.webservice.hesu.jmw.datalayer.querycore.xsd;

import juli.controller.webservice.hesu.bpa.lerc.business.extend.xsd.FaultDetailPack;
import juli.controller.webservice.hesu.bpa.lerc.business.extend.xsd.FaultDetailParam;
import juli.controller.webservice.hesu.bpa.lerc.business.extend.xsd.FaultListParam;
import juli.controller.webservice.hesu.bpa.lerc.business.extend.xsd.WSFaultDetailPack;
import juli.controller.webservice.hesu.jmw.wsobjs.xsd.*;

/**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static Object getTypeObject(String namespaceURI,
                                                       String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws Exception{

              
                  if (
                  "http://wsobjs.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "PagedListPack".equals(typeName)){
                   
                            return  PagedListPack.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryCndValue".equals(typeName)){
                   
                            return  QueryCndValue.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://wsobjs.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "PagedListParam".equals(typeName)){
                   
                            return  PagedListParam.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://extend.business.lerc.bpa.hesu.com/xsd".equals(namespaceURI) &&
                  "FaultDetailPack".equals(typeName)){
                   
                            return  FaultDetailPack.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://wsobjs.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "WebSvcError".equals(typeName)){
                   
                            return  WebSvcError.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryCondition".equals(typeName)){
                   
                            return  QueryCondition.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryLogicDefine".equals(typeName)){
                   
                            return  QueryLogicDefine.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://extend.business.lerc.bpa.hesu.com/xsd".equals(namespaceURI) &&
                  "WSFaultDetailPack".equals(typeName)){
                   
                            return  WSFaultDetailPack.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://wsobjs.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "WSReturnPackBase".equals(typeName)){
                   
                            return  WSReturnPackBase.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://extend.business.lerc.bpa.hesu.com/xsd".equals(namespaceURI) &&
                  "FaultDetailParam".equals(typeName)){
                   
                            return  FaultDetailParam.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://wsobjs.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "WSPagedListPack".equals(typeName)){
                   
                            return  WSPagedListPack.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryJoinField".equals(typeName)){
                   
                            return  QueryJoinField.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryTerm".equals(typeName)){
                   
                            return  QueryTerm.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryJoin".equals(typeName)){
                   
                            return  QueryJoin.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://querycore.datalayer.jmw.hesu.com/xsd".equals(namespaceURI) &&
                  "QueryLogicRef".equals(typeName)){
                   
                            return  QueryLogicRef.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://extend.business.lerc.bpa.hesu.com/xsd".equals(namespaceURI) &&
                  "FaultListParam".equals(typeName)){
                   
                            return  FaultListParam.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    