package com.company.Lab1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileBooks {

   public List<Book> LoadBooks() {
       List<Book> BooksList = new ArrayList<>();;
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

       // Просматриваем все подэлементы корневого - т.е. книги
       NodeList books = root.getChildNodes();
       for (int i = 0; i < books.getLength(); i++) {
           Node book = books.item(i);
           Book bookClass = new Book();
           // Если нода не текст, то это книга - заходим внутрь
           if (book.getNodeType() != Node.TEXT_NODE) {
               NodeList bookProps = book.getChildNodes();
               for(int j = 0; j < bookProps.getLength(); j++) {
                   Node bookProp = bookProps.item(j);

                   // Если нода не текст, то это один из параметров книги - печатаем
                   if (bookProp.getNodeType() != Node.TEXT_NODE) {

                       bookClass.setId(i);
                       if(bookProp.getNodeName().equals("Title")) {
                           bookClass.setName(bookProp.getChildNodes().item(0).getTextContent());
                       } else
                       if(bookProp.getNodeName().equals("Count")){
                           bookClass.setCount(Integer.parseInt(bookProp.getChildNodes().item(0).getTextContent()));
                       }
                   }
               }
               BooksList.add(bookClass);
           }

       }

       return BooksList;
   }

   public void saveBooks(List<Book> books) throws ParserConfigurationException {

       Document doc = DocumentBuilderFactory.newInstance()
               .newDocumentBuilder().newDocument();

       Element RootElement = doc.createElement("Books");
       doc.appendChild(RootElement);

       for (int i = 0; i < books.size(); i++) {

           Element book = doc.createElement("Book");

           Element NameElementTitle = doc.createElement("Title");
           NameElementTitle.appendChild(doc.createTextNode(books.get(i).getName()));
           book.appendChild(NameElementTitle);

           Element NameElementCount = doc.createElement("Count");
           NameElementCount.appendChild(doc.createTextNode(String.valueOf(books.get(i).getCount())));
           book.appendChild(NameElementCount);

           RootElement.appendChild(book);
       }

       Transformer t = null;
       try {
           t = TransformerFactory.newInstance().newTransformer();
       } catch (TransformerConfigurationException e) {
           e.printStackTrace();
       }
       try {
           t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("books.xml")));
       } catch (TransformerException e) {
           e.printStackTrace();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
   }
}
