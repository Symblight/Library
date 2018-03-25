package com.company.Lab1;

public class Book {
    // Переменные наши класса Книга
    private int id;
    private String name;
    private int count;

    public Book(String name, int count) { // Конструктор, он срабатывает когда так пишем: new Book(0, Woman, 3)
        this.name = name;
        this.count = count;
    }

    public Book() { // Конструктор, он срабатывает когда так пишем: new Book(0, Woman, 3)

    }

    public String getName() { return this.name; } // Get and Set Функции которые возвращают значение или устанавливают новое

    public  void setName( String name) { this.name = name; }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }

    @Override
    public String toString() { // Это чтоб посмотреть че внутри класса System.out.println(Book)
        return getClass().getSimpleName() + "{id: " + this.id
                + ", Name: " + this.name
                + ", Count: " + this.count
                + "}";
    }
}
