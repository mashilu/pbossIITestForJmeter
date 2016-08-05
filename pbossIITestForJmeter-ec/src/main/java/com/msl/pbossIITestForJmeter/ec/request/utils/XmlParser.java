package com.msl.pbossIITestForJmeter.ec.request.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by zhaofeng on 2016/7/27.
 */
public class XmlParser {
    public static Document getDocument(String xml) throws DocumentException {
        Document doc;
        doc = DocumentHelper.parseText(xml);
        return doc;
    }

    public static Element getRootElement(String xml) {
        Document doc;
        try {
            doc = getDocument(xml);
        } catch (Exception e) {
           return null;
        }
        return doc != null ? doc.getRootElement() : null;
    }
}
