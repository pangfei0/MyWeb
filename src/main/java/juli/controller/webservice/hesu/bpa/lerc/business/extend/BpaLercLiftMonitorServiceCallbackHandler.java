
/**
 * BpaLercLiftMonitorServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */

    package juli.controller.webservice.hesu.bpa.lerc.business.extend;

    /**
     *  BpaLercLiftMonitorServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BpaLercLiftMonitorServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BpaLercLiftMonitorServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BpaLercLiftMonitorServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getDevListForIOT method
            * override this method for handling normal response from getDevListForIOT operation
            */
           public void receiveResultgetDevListForIOT(
                    GetDevListForIOTResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDevListForIOT operation
           */
            public void receiveErrorgetDevListForIOT(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getDevListByCreateDateForIOT method
            * override this method for handling normal response from getDevListByCreateDateForIOT operation
            */
           public void receiveResultgetDevListByCreateDateForIOT(
                    GetDevListByCreateDateForIOTResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDevListByCreateDateForIOT operation
           */
            public void receiveErrorgetDevListByCreateDateForIOT(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFaultDetail method
            * override this method for handling normal response from getFaultDetail operation
            */
           public void receiveResultgetFaultDetail(
                    GetFaultDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFaultDetail operation
           */
            public void receiveErrorgetFaultDetail(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setDevFaultForIOT method
            * override this method for handling normal response from setDevFaultForIOT operation
            */
           public void receiveResultsetDevFaultForIOT(
                    SetDevFaultForIOTResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setDevFaultForIOT operation
           */
            public void receiveErrorsetDevFaultForIOT(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setDevFaultDealForIOT method
            * override this method for handling normal response from setDevFaultDealForIOT operation
            */
           public void receiveResultsetDevFaultDealForIOT(
                    SetDevFaultDealForIOTResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setDevFaultDealForIOT operation
           */
            public void receiveErrorsetDevFaultDealForIOT(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setDevOnlineForIOT method
            * override this method for handling normal response from setDevOnlineForIOT operation
            */
           public void receiveResultsetDevOnlineForIOT(
                    SetDevOnlineForIOTResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setDevOnlineForIOT operation
           */
            public void receiveErrorsetDevOnlineForIOT(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFaultList method
            * override this method for handling normal response from getFaultList operation
            */
           public void receiveResultgetFaultList(
                    GetFaultListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFaultList operation
           */
            public void receiveErrorgetFaultList(Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getDevInfoForIOT method
            * override this method for handling normal response from getDevInfoForIOT operation
            */
           public void receiveResultgetDevInfoForIOT(
                    GetDevInfoForIOTResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDevInfoForIOT operation
           */
            public void receiveErrorgetDevInfoForIOT(Exception e) {
            }
                


    }
    