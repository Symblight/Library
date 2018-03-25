package com.company.Lab1;

    /*
    * Система Библиотека. Читатель имеет возможность осуществлять поиск и заказ Книг в Каталоге.
    * Библиотекарь выдает Читателю Книгу на абонемент или в читальный зал.
    * Книга может присутствовать в Библиотеке в одном или нескольких экземплярах.
    */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {

    private static List<Subscription> SubscriptionList;
    private static List<Book> BooksList; // Создаем экземпляр книг библиотеки (List - Массив только в нем можно хранить классы грубо говоря)
    private static Subscription currentSubscription; // Создаем экземпляр абонемента

    public static void main(String[] args) { // Главный класс, отсюда стартуем

        BooksList = new ArrayList<>(); // Инициализируем книги

        BooksList.add( new Book("Woman", 6));
        BooksList.add( new Book("Происхождение", 5));
        BooksList.add( new Book("Когда я вернусь, будь дома", 4));
        BooksList.add( new Book("451 градус по Фаренгейту", 2));
        BooksList.add( new Book("Оно", 12));

        SubscriptionList = new ArrayList<>();

        SubscriptionList.add(new Subscription("Nika"));
        SubscriptionList.add(new Subscription("Alexey"));
        SubscriptionList.add(new Subscription("Vasya"));

        Library lib = new Library();// Инициализация главного класса
        lib.MainMenu(); // Вызов нашего меню

    }

    public void MainMenu() {
        while(true) {
            System.out.println("================ Библиотека! ================" +
                    "\n Введите имя читателя "
            );
            Scanner in = new Scanner(System.in);

            String nameReader = in.nextLine();

            if(searchReader(nameReader)){
                MenuReader();
            }
        }
    }

    public void MenuReader() {
        while(true) { // Создаем главный цикл чтобы постоянно быть в меню, как только в этом цикле мы напишем return (то же самое что и false) мы выходим из этого цикла и программа закрывается
            System.out.println("================ Библиотека! ================" +
                    "\nВыберете цифру: \n1. Поиск \n2. Заказ \n3. Абонемент \n4. Сдать книгу \n0. Выйти \nРешение:"
            );
            Scanner in = new Scanner(System.in); // Сканнер отвечает за ввод в консоль

            int select = in.nextInt(); // Введенное значение сохраняем в переменную
            switch (select) { // По номеру мы вызываем методы
                case 1: {
                    searchBook(); // Вызываем метод поиска книги
                    break;
                }
                case 2: {
                    makeOrder(); // Вызываем метод поиска книги
                    break;
                }
                case 3: {
                    currentSubscription.checkSubscription(); // Вызываем метод поиска книги
                    break;
                }
                case  4: {
                    returnBook(); // Вызываем метод поиска книги
                    break;
                }
                case 0: {
                    return; // Выходим из цикла
                }
            }
        }
    }

    public Boolean searchReader(String name) {

        for(int i = 0; i < SubscriptionList.size(); i++) {
            if(SubscriptionList.get(i).getFirstName().equals(name)) {

                currentSubscription = SubscriptionList.get(i);
                return true;
            }
        }
        return false;
    }

    public void searchBook() {
        while(true) {
            Scanner in = new Scanner(System.in);

            System.out.println("\nПоиск! Для выхода - введите 0 \nНапишите название книги:");
            String nameBook = in.nextLine(); // Пишем в консоль

            switch (nameBook) {
                case "0": {
                    return; // Если ввели 0 то мы выходим из данного цикла, но не забывай что мы все еще находимся в главном цикле где отображается главное меню
                }
                default: {
                    Catalog ct = new Catalog();
                    int index =  ct.search(nameBook, BooksList); // Получаем индекс книги, для этого закидываем название книги в функцию

                   if(index > -1) {
                       System.out.println("Найденно! \nid:" + BooksList.get(index).getId() + "\nНазвание " + BooksList.get(index).getName() + "\nКоличество: " + BooksList.get(index).getCount());
                       return;
                   } else {
                       System.out.println("Не найдено!");
                   }
                }
            }
        }
    }

    public void makeOrder() {
        while(true) {
            Scanner in = new Scanner(System.in);

            Catalog ctg = new Catalog();

            ctg.catalogBooks(BooksList);

            System.out.println("\nЗаказ \nНазвание книги: ");

            String nameBook = in.nextLine(); // Пишем в консоль

            switch (nameBook) {
                default: {
                    Catalog ct = new Catalog();
                    int index =  ct.search(nameBook, BooksList); // Получаем индекс книги, для этого закидываем название книги в функцию

                    if(index > -1) {
                        System.out.println("Найденно! \nid:" + BooksList.get(index).getId() +
                                "\nНазвание" + BooksList.get(index).getName() +
                                "\nКоличество: " +
                                BooksList.get(index).getCount()
                        );

                        menuOrder(index); // Вызываем меню заказа
                        return;

                    } else {
                        System.out.println("Не найдено!");
                    }
                }
            }
        }
    }

    public void menuOrder(int indexBook) { // Меню заказа и аргумент данного метода будет индекс выбранной книги
        while (true) {
            System.out.println("\n Взять? \n 1 - Да\n 2 - Нет");
            Scanner in = new Scanner(System.in);
            int select = in.nextInt(); // пишем цифру

            switch (select) {
                case 1: {
                    if(BooksList.get(indexBook).getCount() > 0) {
                        Book sb = BooksList.get(indexBook); // Создаем обьект выбранной книги

                        int countRes = BooksList.get(indexBook).getCount() - 1; // Уменьшаем

                        System.out.println(countRes);

                        BooksList.get(indexBook).setCount(countRes); // Устанавливаем новое количество книг

                         setBookSub(sb); // Метод в котором отнимаем книгу в абонементе

                        System.out.println("Вы взяли книгу: " + BooksList.get(indexBook).getName() + " Осталось " + BooksList.get(indexBook).getCount() + "шт");

                        return;
                    } else {
                        System.out.println("Нету в наличии");
                    }
                }
                case 2: {
                    return; // Выходим из данного цикла, не забывай что ты находишься в цикле заказа т.к. это функция находится внутри другой функции -> makeOrder()
                }
                default: {
                    return; // Выходим из данного цикла, не забывай что ты находишься в цикле заказа т.к. это функция находится внутри другой функции -> makeOrder()
                }
            }
        }
    }

    public void setBookSub(Book book) {
        List<Book> subBooks; // Создаем новый, пустой список книг
        subBooks = currentSubscription.getBooks(); // Копируем из абонемента список используемых книг

        int findBook = -1;

        for(int i = 0; i < subBooks.size(); i++) {
            if(subBooks.get(i).getName().equals(book.getName())) {
                findBook = i; //Ищем индекс по названию книги
            }
        }

        if(findBook > -1) {
            subBooks.get(findBook).setCount(subBooks.get(findBook).getCount() + 1); // Находим книгу в абонементе и увеличиваем на один
        } else {
            Book newBook = new Book(book.getName(), 1); // Создаем экземпляр новой книги и кидаем в список который отправится в абонемент
            subBooks.add(newBook);
        }

        currentSubscription.setBooks(subBooks); // Заменяем старый список в абонементе новым
    }

    public void returnBook() {
        while(true) {
            Scanner in = new Scanner(System.in);

            System.out.println("\nПоиск! Для выхода - введите 0 \nНапишите название книги:");
            String nameBook = in.nextLine();

            switch (nameBook) {
                case "0": {
                    return;
                }
                default: {
                    List<Book> booksReader = currentSubscription.getBooks(); // Копируем список книг абонемента чтоб уменьшить быдло-код наш любимый
                    int indexSubBook = -1;
                    for(int i = 0; i < booksReader.size(); i++) { // По старой схеме ищем индекс
                        if(nameBook.equals(booksReader.get(i).getName())) {
                            indexSubBook = i;
                        }
                    }

                    if(indexSubBook > -1) {
                        Catalog ct = new Catalog();
                        int index =  ct.search(nameBook, BooksList);

                        BooksList.get(index).setCount(BooksList.get(index).getCount() + 1); // В Библиотеке увеличиваем книгу к которому обратились по найденному индексу
                        booksReader.get(indexSubBook).setCount(booksReader.get(indexSubBook).getCount() - 1 ); // А здеся уменьшаем (абонемент список книг)

                        currentSubscription.setBooks(booksReader);  // Заменяем старый список в абонементе новым


                        System.out.println("Вернули книгу!");
                    } else {
                        System.out.println("Отсутствует книга!");
                        return;
                    }

                    return;
                }
            }
        }
    }
}

// Структура программы состоит из лестницы, главное не запутаться :) P.S. с формами пришлось бы еще пилить код для работы с кнопками, писать классы для работы с таблицами и т.д.