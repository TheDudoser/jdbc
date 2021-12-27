package com.dudoser.database;

import java.util.ArrayList;
import java.util.Scanner;

public class UserUI {
    class rec {
        public String Name;
        public String Desc;

        public rec(String Name, String Desc) {
            this.Name = Name;
            this.Desc = Desc;
        }
    }
    private ArrayList<rec> records = new ArrayList<rec>();

    public UserUI () {
        //ArrayList<rec> records = new ArrayList<rec>();
        boolean skip = false;

        while (!skip) {
            Scanner in = new Scanner(System.in);
            System.out.println("Чтобы добавить информацию о фильме, введите название фильма и его описание:");
            System.out.print("Название:");
            String name = in.nextLine();
            System.out.print("Описание:");
            String desc = in.nextLine();

            records.add(new rec(name, desc));
            System.out.printf("name: %s, description: %s\n", name, desc);
            System.out.println("будет добавлена в БД");
            System.out.print("Если вы не хотите больше добавлять фильмы, введите 1, иначе 0:");
            int nextInt = in.nextInt();
            if (nextInt != 0 && nextInt != 1)
                System.out.println("Введено некоректное значение!");
            else if (nextInt == 1)
                skip = true;
        }
        //return records;
    }

    public ArrayList<rec> getRecords() {
        return records;
    }
}
