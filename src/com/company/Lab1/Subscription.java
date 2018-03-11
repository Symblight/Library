package com.company.Lab1;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private int id;
    private String firstName;
    private List<Book> books = new ArrayList<>();

    public void checkSubscription() {
        List<Book> books;

        books = getBooks();

        while(true) {
            System.out.println("\n====== Абонемент ======" +
                    "\nВладелец: " + getFirstName() +
                    "\n----- Книги: -----"
            );

            for (int i = 0; i < books.size(); i++) {
                System.out.println("Название: " + books.get(i).getName() + "\nКоличество: " + books.get(i).getCount() + "\n");
            }

            return;
        }
    }

    public Subscription(String firstName) { // Конструктор, он срабатывает когда так пишем: new Subscription(Nika)
        this.firstName = firstName;
    }

    public String getFirstName() { return this.firstName; } //Get and Set Функции которые возвращают значение или устанавливают новое

    public void setFirstName( String firstName) { this.firstName = firstName; }

    public List<Book> getBooks() { return this.books; }

    public void setBooks( List book) { this.books = book; }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() { // Это чтоб посмотреть че внутри класса System.out.println(Book)
        return getClass().getSimpleName() + "{id: " + this.id
                + ", Firstname: " + this.firstName
                + ", Books: " + this.books
                + "}";
    }
}
