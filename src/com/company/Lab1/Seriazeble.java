package com.company.Lab1;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Seriazeble {
   public  void LoadBooks() {
       // Создается построитель документа
       DocumentBuilder documentBuilder = null;
       try {
           documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
       } catch (ParserConfigurationException e) {
           e.printStackTrace();
       }
       // Создается дерево DOM документа из файла
       Document document = null;
       try {
           document = documentBuilder.parse("books.xml");
       } catch (SAXException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

       // Получаем корневой элемент
       Node root = document.getDocumentElement();

       System.out.println("List of books:");
       System.out.println();
       // Просматриваем все подэлементы корневого - т.е. книги
       NodeList books = root.getChildNodes();
       for (int i = 0; i < books.getLength(); i++) {
           Node book = books.item(i);
           // Если нода не текст, то это книга - заходим внутрь
           if (book.getNodeType() != Node.TEXT_NODE) {
               NodeList bookProps = book.getChildNodes();
               for(int j = 0; j < bookProps.getLength(); j++) {
                   Node bookProp = bookProps.item(j);
                   // Если нода не текст, то это один из параметров книги - печатаем
                   if (bookProp.getNodeType() != Node.TEXT_NODE) {
                       System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
                   }
               }
               System.out.println("===========>>>>");
           }
       }
   }

   public void getBook(){

   }

   public void saveBooks() {

   }
}
