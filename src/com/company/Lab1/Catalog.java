package com.company.Lab1;

import java.util.List;

public class Catalog {

    public int search(String nameBook, List<Book> BooksList) { // Это числовая int функция, она должна вернуть число (Метод (void) ничего не возвращает, так, для справки)
        String result = "";
        int index = 0;

        for (int i = 0; i < BooksList.size(); i++) {
            if (BooksList.get(i).getName().equals(nameBook)) { // Ищем по имени книгу и записываем результат и его индекс (индекс это номер положения в массиве)
                result =  BooksList.get(i).getName();
                index = i;
            }
        }

        if (!result.equals("")) {
            return index; // Возвращаем индекс книги
        }

        return -1; // Возвращаем число -1 если ничего не нашли
    }

    public void catalogBooks(List<Book> BooksList) {
        System.out.println("~~~ Каталог ~~~");
        for (int i = 0; i < BooksList.size(); i++) {
            System.out.println("Название: " + BooksList.get(i).getName() + "\nКоличество: " + BooksList.get(i).getCount() + "\n");
        }
    }
}
