package honkot.java.foundation.training;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-23.
 */
public class Chapter5_4 implements ChapterBase {

    private static Student[] mStudents = new Student[5];

    @Override
    public void main() {
        do {
            // 1. show menu
            showMenu();

            // 2. get command
            Command command = null;
            try {
                int userInput = new Scanner(System.in).nextInt();
                command = Command.getCommand(userInput);
                if (command == null) {
                    throw new NullPointerException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Failed... Try again from start!");
            } catch (NullPointerException e) {
                System.out.println("Failed... Try again from start!");
                continue;
            }

            // 3. execute
            command.execute();
            if (command.shouldFinish()) {
                break;
            }

        } while(true);
    }

    private void showMenu() {
        System.out.println("Welcome to Midterm app, please choose from the following:");
        System.out.println("1. Fill students data (one by one)");
        System.out.println("2. Print all students");
        System.out.println("3. Add student by index");
        System.out.println("4. Remove a student by id");
        System.out.println("5. Exit");
    }

    private enum Command {
        FillData(1),
        PrintAll(2),
        AddById(3),
        RemoveById(4),
        Exit(5);

        private final int command;

        Command(int command) {
            this.command = command;
        }

        public static Command getCommand(int command) {
            for (Command tempCmd: Command.values()) {
                if (tempCmd.command == command) {
                    return tempCmd;
                }
            }
            return null;
        }

        public void execute() {
            switch(this) {
                case FillData: fillData(); break;
                case PrintAll: printAll(); break;
                case AddById: addById(); break;
                case RemoveById: removeById(); break;
                case Exit: // nothing to do.
                    break;
            }
        }

        public boolean shouldFinish() {
            return this == Exit;
        }

        public void fillData() {
            // user input
            Student student = inputStudent();

            // check error
            if (student == null) return;

            // add student
            int addIndex = 0;
            for (; addIndex < mStudents.length; addIndex++) {
                if (mStudents[addIndex] == null) {
                    break;
                }
            }
            if (addIndex < mStudents.length) {
                mStudents[addIndex] = student;
            } else {
                System.out.println("Students are full! You can not add more!");
            }
        }

        private Student inputStudent() {
            String name;
            int  age, id;
            Student student = null;

            try {
                System.out.print("Enter name:");
                Scanner scan = new Scanner(System.in);
                name = scan.next();
                System.out.print("Enter age:");
                age = scan.nextInt();
                System.out.print("Enter id:");
                id = scan.nextInt();

                student = new Student();
                student.setName(name);
                student.setAge(age);
                student.setId(id);
            } catch (InputMismatchException e) {
                System.out.println("Failed... Try again from start!");
            }

            return student;
        }

        public void printAll() {
            // check null first
//            if (mInputCount < mStudents.length) {
//                System.out.println("Failed... Students have not filled yet.");
//                return;
//            }

            // print all based on the format
            System.out.println(
                    "Name        |    Age       |    Id      ");
            System.out.println(
                    "==============================================");
//            for (Student student: mStudents) {
//                if (student != null) {
//                    System.out.println(student);
//                }
//            }
            // TODO for debug. I should delete this later
            for (int i = 0; i < mStudents.length; i++) {
                if (mStudents[i] != null) {
                    System.out.println(mStudents[i].toString() + "  (index=" + i + ")");
                }
            }
        }

        private void addById() {
            // user input
            Student student = inputStudent();

            // check error
            if (student == null) return;

            // add student by id(index)
            if (student.getId() > mStudents.length) {
                // expand array and copy old to new.
                Student[] temps = mStudents;
                mStudents = new Student[student.getId() + 1];
                for (int i = 0; i < temps.length; i++) {
                    mStudents[i] = temps[i];
                }
                mStudents[student.getId()] = student;
            } else {
                // check index and add
                if (mStudents[student.getId()] == null) {
                    mStudents[student.getId()] = student;
                } else {
                    System.out.println("The index is already existed. You can not add one.");
                }
            }
        }

        private void removeById() {
            try {
                System.out.print("Remove id you want:");
                int userInput = new Scanner(System.in).nextInt();
                for (int i = 0; i < mStudents.length; i++) {
                    if (mStudents[i] != null
                            && mStudents[i].getId() == userInput) {
                        mStudents[i] = null;
                        System.out.print("Remove successful!");
                        return;
                    }
                }

                System.out.println("No record by the id.");

            } catch (InputMismatchException e) {
                System.out.println("Failed... Try again from start!");
            }
        }
    }

    private static class Student {
        private String name;
        private int id, age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append(String.format("%-12s", getName()));
            buf.append("|");
            buf.append(String.format("    %2d        ", getAge()));
            buf.append("|");
            buf.append(String.format("    %02d", getId()));
            return buf.toString();
        }
    }

}
