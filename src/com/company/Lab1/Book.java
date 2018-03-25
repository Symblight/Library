package com.company.Lab1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Book {
    // Переменные наши класса Книга
    private int id;
    private String name;
    private int count;
    private Date timeOfDelivery;

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

    public Date getTime() { return this.timeOfDelivery; }

    public void setTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.timeOfDelivery = date;
    }

    @Override
    public String toString() { // Это чтоб посмотреть че внутри класса System.out.println(Book)
        return getClass().getSimpleName() + "{id: " + this.id
                + ", Name: " + this.name
                + ", Count: " + this.count
                + "}";
    }
}
