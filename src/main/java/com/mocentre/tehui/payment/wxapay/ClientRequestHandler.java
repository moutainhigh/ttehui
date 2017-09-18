package com.mocentre.tehui.payment.wxapay;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ClientRequestHandler extends PrepayIdRequestHandler {

    public ClientRequestHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getXmlBody() {
        StringBuffer sb = new StringBuffer();
        Set es = super.getAllParameters().entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"appkey".equals(k)) {
                sb.append("<" + k + ">" + v + "</" + k + ">" + "\r\n");
            }
        }
        return sb.toString();
    }
}
