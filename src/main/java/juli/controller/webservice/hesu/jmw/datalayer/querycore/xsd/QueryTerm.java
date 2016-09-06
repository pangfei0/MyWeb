
/**
 * QueryTerm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:09:26 BST)
 */

            
                package juli.controller.webservice.hesu.jmw.datalayer.querycore.xsd;
            

            /**
            *  QueryTerm bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class QueryTerm
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = QueryTerm
                Namespace URI = http://querycore.datalayer.jmw.hesu.com/xsd
                Namespace Prefix = ns3
                */
            

                        /**
                        * field for AllowPaging
                        */

                        
                                    protected boolean localAllowPaging ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowPagingTracker = false ;

                           public boolean isAllowPagingSpecified(){
                               return localAllowPagingTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAllowPaging(){
                               return localAllowPaging;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AllowPaging
                               */
                               public void setAllowPaging(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAllowPagingTracker =
                                       true;
                                   
                                            this.localAllowPaging=param;
                                       

                               }
                            

                        /**
                        * field for ChildTerms
                        * This was an Array!
                        */

                        
                                    protected QueryTerm[] localChildTerms ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localChildTermsTracker = false ;

                           public boolean isChildTermsSpecified(){
                               return localChildTermsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.hesu.jmw.datalayer.querycore.xsd.QueryTerm[]
                           */
                           public  QueryTerm[] getChildTerms(){
                               return localChildTerms;
                           }

                           
                        


                               
                              /**
                               * validate the array for ChildTerms
                               */
                              protected void validateChildTerms(QueryTerm[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param ChildTerms
                              */
                              public void setChildTerms(QueryTerm[] param){
                              
                                   validateChildTerms(param);

                               localChildTermsTracker = true;
                                      
                                      this.localChildTerms=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.hesu.jmw.datalayer.querycore.xsd.QueryTerm
                             */
                             public void addChildTerms(QueryTerm param){
                                   if (localChildTerms == null){
                                   localChildTerms = new QueryTerm[]{};
                                   }

                            
                                 //update the setting tracker
                                localChildTermsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localChildTerms);
                               list.add(param);
                               this.localChildTerms =
                             (QueryTerm[])list.toArray(
                            new QueryTerm[list.size()]);

                             }
                             

                        /**
                        * field for Conditions
                        * This was an Array!
                        */

                        
                                    protected QueryCondition[] localConditions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localConditionsTracker = false ;

                           public boolean isConditionsSpecified(){
                               return localConditionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.hesu.jmw.datalayer.querycore.xsd.QueryCondition[]
                           */
                           public  QueryCondition[] getConditions(){
                               return localConditions;
                           }

                           
                        


                               
                              /**
                               * validate the array for Conditions
                               */
                              protected void validateConditions(QueryCondition[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Conditions
                              */
                              public void setConditions(QueryCondition[] param){
                              
                                   validateConditions(param);

                               localConditionsTracker = true;
                                      
                                      this.localConditions=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.hesu.jmw.datalayer.querycore.xsd.QueryCondition
                             */
                             public void addConditions(QueryCondition param){
                                   if (localConditions == null){
                                   localConditions = new QueryCondition[]{};
                                   }

                            
                                 //update the setting tracker
                                localConditionsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localConditions);
                               list.add(param);
                               this.localConditions =
                             (QueryCondition[])list.toArray(
                            new QueryCondition[list.size()]);

                             }
                             

                        /**
                        * field for DataObjName
                        */

                        
                                    protected String localDataObjName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataObjNameTracker = false ;

                           public boolean isDataObjNameSpecified(){
                               return localDataObjNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getDataObjName(){
                               return localDataObjName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataObjName
                               */
                               public void setDataObjName(String param){
                            localDataObjNameTracker = true;
                                   
                                            this.localDataObjName=param;
                                       

                               }
                            

                        /**
                        * field for JoinTerms
                        * This was an Array!
                        */

                        
                                    protected QueryJoin[] localJoinTerms ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localJoinTermsTracker = false ;

                           public boolean isJoinTermsSpecified(){
                               return localJoinTermsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.hesu.jmw.datalayer.querycore.xsd.QueryJoin[]
                           */
                           public  QueryJoin[] getJoinTerms(){
                               return localJoinTerms;
                           }

                           
                        


                               
                              /**
                               * validate the array for JoinTerms
                               */
                              protected void validateJoinTerms(QueryJoin[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param JoinTerms
                              */
                              public void setJoinTerms(QueryJoin[] param){
                              
                                   validateJoinTerms(param);

                               localJoinTermsTracker = true;
                                      
                                      this.localJoinTerms=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.hesu.jmw.datalayer.querycore.xsd.QueryJoin
                             */
                             public void addJoinTerms(QueryJoin param){
                                   if (localJoinTerms == null){
                                   localJoinTerms = new QueryJoin[]{};
                                   }

                            
                                 //update the setting tracker
                                localJoinTermsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localJoinTerms);
                               list.add(param);
                               this.localJoinTerms =
                             (QueryJoin[])list.toArray(
                            new QueryJoin[list.size()]);

                             }
                             

                        /**
                        * field for LogicDefine
                        */

                        
                                    protected QueryLogicDefine localLogicDefine ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLogicDefineTracker = false ;

                           public boolean isLogicDefineSpecified(){
                               return localLogicDefineTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.hesu.jmw.datalayer.querycore.xsd.QueryLogicDefine
                           */
                           public  QueryLogicDefine getLogicDefine(){
                               return localLogicDefine;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LogicDefine
                               */
                               public void setLogicDefine(QueryLogicDefine param){
                            localLogicDefineTracker = true;
                                   
                                            this.localLogicDefine=param;
                                       

                               }
                            

                        /**
                        * field for MaxRowCount
                        */

                        
                                    protected int localMaxRowCount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxRowCountTracker = false ;

                           public boolean isMaxRowCountSpecified(){
                               return localMaxRowCountTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getMaxRowCount(){
                               return localMaxRowCount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxRowCount
                               */
                               public void setMaxRowCount(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localMaxRowCountTracker =
                                       param != Integer.MIN_VALUE;
                                   
                                            this.localMaxRowCount=param;
                                       

                               }
                            

                        /**
                        * field for SelectedPresent
                        */

                        
                                    protected int localSelectedPresent ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSelectedPresentTracker = false ;

                           public boolean isSelectedPresentSpecified(){
                               return localSelectedPresentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getSelectedPresent(){
                               return localSelectedPresent;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SelectedPresent
                               */
                               public void setSelectedPresent(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localSelectedPresentTracker =
                                       param != Integer.MIN_VALUE;
                                   
                                            this.localSelectedPresent=param;
                                       

                               }
                            

                        /**
                        * field for TermId
                        */

                        
                                    protected String localTermId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTermIdTracker = false ;

                           public boolean isTermIdSpecified(){
                               return localTermIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  String getTermId(){
                               return localTermId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TermId
                               */
                               public void setTermId(String param){
                            localTermIdTracker = true;
                                   
                                            this.localTermId=param;
                                       

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(this,parentQName));
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                String prefix = null;
                String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   String namespacePrefix = registerPrefix(xmlWriter,"http://querycore.datalayer.jmw.hesu.com/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":QueryTerm",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "QueryTerm",
                           xmlWriter);
                   }

               
                   }
                if (localAllowPagingTracker){
                                    namespace = "http://querycore.datalayer.jmw.hesu.com/xsd";
                                    writeStartElement(null, namespace, "allowPaging", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("allowPaging cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowPaging));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localChildTermsTracker){
                                       if (localChildTerms!=null){
                                            for (int i = 0;i < localChildTerms.length;i++){
                                                if (localChildTerms[i] != null){
                                                 localChildTerms[i].serialize(new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","childTerms"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "childTerms", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "childTerms", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localConditionsTracker){
                                       if (localConditions!=null){
                                            for (int i = 0;i < localConditions.length;i++){
                                                if (localConditions[i] != null){
                                                 localConditions[i].serialize(new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","conditions"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "conditions", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "conditions", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localDataObjNameTracker){
                                    namespace = "http://querycore.datalayer.jmw.hesu.com/xsd";
                                    writeStartElement(null, namespace, "dataObjName", xmlWriter);
                             

                                          if (localDataObjName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDataObjName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localJoinTermsTracker){
                                       if (localJoinTerms!=null){
                                            for (int i = 0;i < localJoinTerms.length;i++){
                                                if (localJoinTerms[i] != null){
                                                 localJoinTerms[i].serialize(new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","joinTerms"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "joinTerms", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "joinTerms", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localLogicDefineTracker){
                                    if (localLogicDefine==null){

                                        writeStartElement(null, "http://querycore.datalayer.jmw.hesu.com/xsd", "logicDefine", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localLogicDefine.serialize(new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","logicDefine"),
                                        xmlWriter);
                                    }
                                } if (localMaxRowCountTracker){
                                    namespace = "http://querycore.datalayer.jmw.hesu.com/xsd";
                                    writeStartElement(null, namespace, "maxRowCount", xmlWriter);
                             
                                               if (localMaxRowCount== Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("maxRowCount cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxRowCount));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSelectedPresentTracker){
                                    namespace = "http://querycore.datalayer.jmw.hesu.com/xsd";
                                    writeStartElement(null, namespace, "selectedPresent", xmlWriter);
                             
                                               if (localSelectedPresent== Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("selectedPresent cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSelectedPresent));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTermIdTracker){
                                    namespace = "http://querycore.datalayer.jmw.hesu.com/xsd";
                                    writeStartElement(null, namespace, "termId", xmlWriter);
                             

                                          if (localTermId==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTermId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static String generatePrefix(String namespace) {
            if(namespace.equals("http://querycore.datalayer.jmw.hesu.com/xsd")){
                return "ns3";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(String prefix, String namespace, String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix,String namespace,String attName,
                                    String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace,attName,attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace,attName,attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace,String attName,
                                    String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace), namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(String namespace, String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                String attributeNamespace = qname.getNamespaceURI();
                String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(attributePrefix, namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, String namespace) throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{
        private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static QueryTerm parse(javax.xml.stream.XMLStreamReader reader) throws Exception{
            QueryTerm object =
                new QueryTerm();

            int event;
            javax.xml.namespace.QName currentQName = null;
            String nillableValue = null;
            String prefix ="";
            String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                currentQName = reader.getName();
                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"QueryTerm".equals(type)){
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (QueryTerm)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list2 = new java.util.ArrayList();
                    
                        java.util.ArrayList list3 = new java.util.ArrayList();
                    
                        java.util.ArrayList list5 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","allowPaging").equals(reader.getName()) || new javax.xml.namespace.QName("","allowPaging").equals(reader.getName()) ){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"allowPaging" +"  cannot be null");
                                    }
                                    

                                    String content = reader.getElementText();
                                    
                                              object.setAllowPaging(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","childTerms").equals(reader.getName()) || new javax.xml.namespace.QName("","childTerms").equals(reader.getName()) ){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list2.add(null);
                                                              reader.next();
                                                          } else {
                                                        list2.add(QueryTerm.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone2 = false;
                                                        while(!loopDone2){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone2 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","childTerms").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list2.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list2.add(QueryTerm.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone2 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setChildTerms((QueryTerm[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                QueryTerm.class,
                                                                list2));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","conditions").equals(reader.getName()) || new javax.xml.namespace.QName("","conditions").equals(reader.getName()) ){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list3.add(null);
                                                              reader.next();
                                                          } else {
                                                        list3.add(QueryCondition.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone3 = false;
                                                        while(!loopDone3){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone3 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","conditions").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list3.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list3.add(QueryCondition.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone3 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setConditions((QueryCondition[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                QueryCondition.class,
                                                                list3));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","dataObjName").equals(reader.getName()) || new javax.xml.namespace.QName("","dataObjName").equals(reader.getName()) ){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    String content = reader.getElementText();
                                    
                                              object.setDataObjName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","joinTerms").equals(reader.getName()) || new javax.xml.namespace.QName("","joinTerms").equals(reader.getName()) ){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list5.add(null);
                                                              reader.next();
                                                          } else {
                                                        list5.add(QueryJoin.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone5 = false;
                                                        while(!loopDone5){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone5 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","joinTerms").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list5.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list5.add(QueryJoin.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone5 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setJoinTerms((QueryJoin[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                QueryJoin.class,
                                                                list5));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","logicDefine").equals(reader.getName()) || new javax.xml.namespace.QName("","logicDefine").equals(reader.getName()) ){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setLogicDefine(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setLogicDefine(QueryLogicDefine.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","maxRowCount").equals(reader.getName()) || new javax.xml.namespace.QName("","maxRowCount").equals(reader.getName()) ){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maxRowCount" +"  cannot be null");
                                    }
                                    

                                    String content = reader.getElementText();
                                    
                                              object.setMaxRowCount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMaxRowCount(Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","selectedPresent").equals(reader.getName()) || new javax.xml.namespace.QName("","selectedPresent").equals(reader.getName()) ){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"selectedPresent" +"  cannot be null");
                                    }
                                    

                                    String content = reader.getElementText();
                                    
                                              object.setSelectedPresent(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setSelectedPresent(Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://querycore.datalayer.jmw.hesu.com/xsd","termId").equals(reader.getName()) || new javax.xml.namespace.QName("","termId").equals(reader.getName()) ){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    String content = reader.getElementText();
                                    
                                              object.setTermId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // 2 - A start element we are not expecting indicates a trailing invalid property
                                
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    