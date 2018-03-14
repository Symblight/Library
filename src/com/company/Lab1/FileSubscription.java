package com.company.Lab1;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSubscription {

    public List<Subscription> LoadSub() {
        List<Subscription> SubscriptionList = new ArrayList<>();;
        // Создается построитель документа
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        // Создается дерево DOM документа из файла
        org.w3c.dom.Document document = null;
        try {
            document = documentBuilder.parse("subscription.xml");
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Получаем корневой элемент
        org.w3c.dom.Node root = document.getDocumentElement();

        // Просматриваем все подэлементы корневого - т.е. книги
        NodeList subs = root.getChildNodes();
        for (int i = 0; i < subs.getLength(); i++) {
            org.w3c.dom.Node sub = subs.item(i);
            Subscription SubscriptionClass = new Subscription();
            // Если нода не текст, то это книга - заходим внутрь
            if (sub.getNodeType() != org.w3c.dom.Node.TEXT_NODE) {
                NodeList subProps = sub.getChildNodes();

                List<Book> BooksList = new ArrayList<>();
                for(int j = 0; j < subProps.getLength(); j++) {
                    org.w3c.dom.Node subProp = subProps.item(j);

                    // Если нода не текст, то это один из параметров книги - печатаем
                    if (subProp.getNodeType() != org.w3c.dom.Node.TEXT_NODE) {

                        if(subProp.getNodeName().equals("Name")) {
                            SubscriptionClass.setFirstName(subProp.getChildNodes().item(0).getTextContent());
                        }

                        NodeList books = subProp.getChildNodes();
                        BooksList = LoadBooks(books);
                    }
                }

                SubscriptionClass.setBooks(BooksList);

                SubscriptionList.add(SubscriptionClass);
            }
        }
        return SubscriptionList;
    }

    private List<Book> LoadBooks(NodeList root) {
        List<Book> BooksList = new ArrayList<>();
        for(int i = 0; i < root.getLength(); i++)
        {
            Book bookClass = new Book();
            org.w3c.dom.Node books = root.item(i);
            if (books.getNodeType() != org.w3c.dom.Node.TEXT_NODE) {

                NodeList book = books.getChildNodes();
                for(int j = 0; j < book.getLength(); j++) {
                    org.w3c.dom.Node subProp = book.item(j);
                    // Если нода не текст, то это один из параметров книги - печатаем
                    if (subProp.getNodeType() != org.w3c.dom.Node.TEXT_NODE) {
                        bookClass.setId(i);
                        if(subProp.getNodeName().equals("Title")) {
                            bookClass.setName(subProp.getChildNodes().item(0).getTextContent());
                        } else
                        if(subProp.getNodeName().equals("Count")){
                            bookClass.setCount(Integer.parseInt(subProp.getChildNodes().item(0).getTextContent()));
                        }
                    }
                }
                BooksList.add(bookClass);
            }
        }
        return BooksList;
    }

    public void saveSub(List<Subscription> subscriptions) throws ParserConfigurationException {
        org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();

        Element RootElement = doc.createElement("Subscriptions");
        doc.appendChild(RootElement);

        for (int i = 0; i < subscriptions.size(); i++) {

            Element Subscription = doc.createElement("Subscription");

            Element name = doc.createElement("Name");
            name.appendChild(doc.createTextNode(subscriptions.get(i).getFirstName()));
            Subscription.appendChild(name);

            Element books = doc.createElement("Books");

            List<Book> bookList = subscriptions.get(i).getBooks();

            for (int j = 0; j < bookList.size(); j++) {

                Element book = doc.createElement("Book");
                Element NameElementTitle = doc.createElement("Title");
                NameElementTitle.appendChild(doc.createTextNode(bookList.get(j).getName()));
                book.appendChild(NameElementTitle);

                Element NameElementCount = doc.createElement("Count");
                NameElementCount.appendChild(doc.createTextNode(String.valueOf(bookList.get(j).getCount())));
                book.appendChild(NameElementCount);

                books.appendChild(book);
            }

            Subscription.appendChild(name);
            Subscription.appendChild(books);

            RootElement.appendChild(Subscription);
        }

        Transformer t = null;
        try {
            t = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("subscription.xml")));
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
