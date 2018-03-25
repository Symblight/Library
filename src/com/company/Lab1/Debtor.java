package com.company.Lab1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Debtor {
    private Boolean debtor = false;

    Debtor(Subscription subscription) {
        List<Book> subBooks; // Создаем новый, пустой список книг
        subBooks = subscription.getBooks(); // Копируем из абонемента список используемых книг

        Date date = new Date(); //Текущая дата

        Date dateOne = getDate(date);

        for(int i = 0; i<subBooks.size(); i++) {
            Date  dateTwo = getDate(subBooks.get(i).getTime());

            // Количество дней между датами в миллисекундах
            long difference = dateOne.getTime() - dateTwo.getTime();
            // Перевод количества дней между датами из миллисекунд в дни
            int days =  (int)(difference / (24 * 60 * 60 * 1000)); // миллисекунды / (24ч * 60мин * 60сек * 1000мс)
            // Вывод разницы между датами в днях на экран
            if(days > 0) {
                this.debtor = true;
            }
        }
    }

    Debtor(){}

    private Date getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Date newDate = null;

        String dateString = format.format(date);

        try {
            newDate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }

    private int checkDebtors() {
        return 0;
    }

    public Boolean getDebtor() { return this.debtor; }

    public void setDebtor( Boolean debtor) { this.debtor = debtor; }
}
