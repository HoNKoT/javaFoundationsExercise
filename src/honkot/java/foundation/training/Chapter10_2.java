package honkot.java.foundation.training;

import com.sun.istack.internal.NotNull;
import honkot.java.foundation.training.sub.CommissionEmployee;
import honkot.java.foundation.training.sub.Employee;
import honkot.java.foundation.training.sub.SalaryEmployee;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hhonda on 2017-01-06.
 */
public class Chapter10_2 implements ChapterBase {

    /**
     * I use ArrayList instead of String Array since more useful.
     * According to the document, the maximum number of employee is only 10,
     * but it does not matter in this case if the number will be increased in the future.
     */
    private static ArrayList<Employee> mEmployees = new ArrayList<>();

    // uses for repeating operation until the user inputted Exit.
    private static boolean loop = true;

    // uses for err case
    private static final String ERRMSG_INPUT_MISMATCH = "TYPE A NUMBER PLEASE.";
    private static final String ERRMSG_NULL_POINTER = "UNSUPPORTED NUMBER.";
    private static final String ERRMSG_UNEXPECTED = "UNEXPECTED ERROR OCCURS.";

    // uses for print func
    private static NumberFormat nfCur = NumberFormat.getCurrencyInstance();
    private static final String LF = System.getProperty("line.separator");
    private static final String SEPARATOR_ROW = " | ";
    private static final String SEPARATOR_COL = "-";
    private static final String SEPARATOR_CROSS = "-+-";
    private static final int TYPE_LENGTH = 12;

    /**
     * This is for my own program for training.
     * So just ignore this!
     */
    @Override
    public void main() {
        main(null);
    }

    /**
     * Main operation.
     * Repeating print menu, input command by user and execute until exit.
     * @param args
     */
    public static void main (String[] args) {

        // Repeat operation until the user input Exit(4).
        do {
            // Print menu with enough space.
            System.out.println(LF);
            printMenu();

            // read command
            System.out.print("> ");
            Scanner scan = new Scanner(System.in);
            Command command = null;
            try {
                command = Command.findByCommand(scan.nextInt());
                if (command == null) {
                    throw new NullPointerException();
                }
            } catch (InputMismatchException e) {
                System.out.println(ERRMSG_INPUT_MISMATCH);
                continue;
            } catch (NullPointerException e) {
                System.out.println(ERRMSG_NULL_POINTER);
                continue;
            } catch (Exception e) {
                System.out.println(ERRMSG_UNEXPECTED);
                continue;
            }

            // execute command
            execute(command);

        } while(loop);

        System.out.println("Thanks for using, Have a nice day! :)");
    }

    /**
     * Print out all options the user can select.
     */
    private static void printMenu() {
        StringBuffer buf = new StringBuffer();
        buf.append("Welcome to Employee Salary System, please choose from the following:"); buf.append(LF);
        buf.append(" 1. Create an employee."); buf.append(LF);
        buf.append(" 2. Print all employees (names and salaries)."); buf.append(LF);
        buf.append(" 3. Pay all employees for the month and print amount."); buf.append(LF);
        buf.append(" 4. Exit."); buf.append(LF);
        System.out.println(buf.toString());
    }

    /**
     * Execute each command depending on argument, Command instance.
     * @param command Check Not Null before call this.
     */
    private static void execute(@NotNull Command command) {
        switch (command) {
            case Create: createEmployee(); break;
            case PrintAll: printAll(); break;
            case PayAll: payAll(); break;
            case Exit: exit(); break;
        }
    }

    /**
     * 1. Create an employee.
     */
    private static void createEmployee() {
        // first name
        System.out.println("Enter first name");
        System.out.print("> ");
        Scanner scan = new Scanner(System.in);
        String firstName = scan.next();

        // last name
        System.out.println("Enter last name");
        System.out.print("> ");
        String lastName = scan.next();

        // Select which type
        System.out.println("Salary employee (1) or Commission employee (2)?");
        System.out.print("> ");
        Employee newEmployee = null;
        int type = 0;
        try {
            type = scan.nextInt();

            // Create new employee instance depending on the type use inputted
            switch (type) {
                case 1:
                    newEmployee = new SalaryEmployee();
                    break;
                case 2:
                    newEmployee = new CommissionEmployee();
                    break;
                default:
                    throw new NullPointerException();
            }
        } catch (InputMismatchException e) {
            System.out.println(ERRMSG_INPUT_MISMATCH);
            return;
        } catch (NullPointerException e) {
            System.out.println(ERRMSG_NULL_POINTER);
            return;
        } catch (Exception e) {
            System.out.println(ERRMSG_UNEXPECTED);
            return;
        }

        // Salary
        switch (type) {
            case 1: System.out.println("Enter monthly salary"); break;
            case 2: System.out.println("Enter gross sales of this month"); break;
        }
        System.out.print("> ");
        int salary = 0;
        try {
            salary = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(ERRMSG_INPUT_MISMATCH);
            return;
        } catch (Exception e) {
            System.out.println(ERRMSG_UNEXPECTED);
            return;
        }

        // print fixed message
        System.out.println(newEmployee + "created!!");

        // set values into employee instance and add into ArrayList
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setId(mEmployees.size() + 1);
        switch (type) {
            case 1: ((SalaryEmployee)newEmployee).setMonthlySalarry(salary); break;
            case 2: ((CommissionEmployee)newEmployee).setGrossSales(salary); break;
        }
        mEmployees.add(newEmployee);
    }

    /**
     * 2. Print all employees (names and salaries).
     */
    private static void printAll() {
        // void check
        if (mEmployees.isEmpty()) {
            System.out.println("You does not input any employee yet.");
            return;
        }

        // print header
        StringBuffer buf = new StringBuffer();
        appendHeader(buf, Command.PrintAll);

        // print employee in ArrayList
        for (Employee employee : mEmployees) {
            buf.append(LF);
            appendName(buf, employee);
            buf.append(SEPARATOR_ROW);
            appendType(buf, employee);
            buf.append(SEPARATOR_ROW);
            appendSalary(buf, employee, Command.PrintAll);
        }
        buf.append(LF);
        System.out.println(buf.toString());
    }

    /**
     * 3. Pay all employees for the month and print amount.
     */
    private static void payAll() {
        // void check
        if (mEmployees.isEmpty()) {
            System.out.println("You does not input any employee yet.");
            return;
        }

        // print header
        StringBuffer buf = new StringBuffer();
        appendHeader(buf, Command.PayAll);

        // print unpaid employee first
        ArrayList<Employee> unpaidEmployees = new ArrayList<>();
        for (Employee employee : mEmployees) {
            if (!employee.getPaidThisMonth()) {
                buf.append(LF);
                appendName(buf, employee);
                buf.append(SEPARATOR_ROW);
                appendType(buf, employee);
                buf.append(SEPARATOR_ROW);
                appendSalary(buf, employee, Command.PayAll);

                // remember unpaid employee
                unpaidEmployees.add(employee);
            }
        }

        // do not print header if every body already has paid status.
        if (unpaidEmployees.isEmpty()) {
            buf = new StringBuffer();
        }

        // print paid employee finally
        for (Employee employee : mEmployees) {
            if (employee.getPaidThisMonth()) {
                buf.append(LF);
                buf.append(employee.getFullName());
                buf.append(" already got paid for the month!");
            }
        }

        // turn off paid frag
        for (Employee employee : unpaidEmployees) {
            employee.setPaidThisMonth(true);
        }

        buf.append(LF);
        System.out.println(buf.toString());

    }

    private static int getMaximumLengthOfFullName() {
        int max = 4; // length of header, 'Name'.
        for (Employee employee : mEmployees) {
            int totalLength =
                    employee.getFirstName().length() + employee.getLastName().length()
                    + 1; // space between first name and last name
            if (totalLength > max) {
                max = totalLength;
            }
        }
        return max;
    }

    private static String genarateNameSpace(Employee employee) {
        return genarateNameSpace(employee.getFullName());
    }

    private static String genarateNameSpace(String value) {
        for (int i = value.length(); i <=
                getMaximumLengthOfFullName(); i++) {
            value += " ";
        }
        return value;
    }

    private static void appendHeader(StringBuffer buf, Command command) {
        // add header
        buf.append(genarateNameSpace("Name"));
        buf.append(SEPARATOR_ROW);
        buf.append(String.format("%-" + TYPE_LENGTH + "s", "Type"));
        buf.append(SEPARATOR_ROW);
        switch (command) {
            case PayAll: buf.append("Paid for month amount"); break;
            case PrintAll: buf.append("Salary/Pay amount"); break;
        }

        // add separator between header and value
        buf.append(LF);
        for (int i = 0; i <= getMaximumLengthOfFullName(); i++) {
            buf.append(SEPARATOR_COL);
        }
        buf.append(SEPARATOR_CROSS);
        for (int i = 0; i < 12; i++) {
            buf.append(SEPARATOR_COL);
        }
        buf.append(SEPARATOR_CROSS);
        for (int i = 0; i < 20; i++) {
            buf.append(SEPARATOR_COL);
        }
    }

    private static void appendName(StringBuffer buf, Employee employee) {
        buf.append(genarateNameSpace(employee));
    }

    private static void appendType(StringBuffer buf, Employee employee) {
        buf.append(String.format("%-" + TYPE_LENGTH + "s",
                employee instanceof SalaryEmployee ? "Salary" : "Commission"));
    }

    private static void appendSalary(StringBuffer buf, Employee employee, Command command) {
        if (employee instanceof SalaryEmployee) {
            SalaryEmployee salaryEmployee = (SalaryEmployee)employee;
            buf.append(nfCur.format(salaryEmployee.getMonthlySalarry()));
        } else {
            CommissionEmployee commissionEmployee = (CommissionEmployee)employee;
            switch (command) {
                case PayAll:
                    buf.append(nfCur.format(
                            commissionEmployee.getGrossSales() * commissionEmployee.getCommisionRate()));
                    break;

                case PrintAll:
                    buf.append(nfCur.format(
                            commissionEmployee.getGrossSales()));
                    buf.append(" * ");
                    buf.append(commissionEmployee.getCommisionRate());
                    buf.append(" = ");
                    buf.append(nfCur.format(
                            commissionEmployee.getGrossSales() * commissionEmployee.getCommisionRate()));
                    break;
            }
        }
    }

    /**
     * 4. Exit.
     */
    private static void exit() {
        // turn off the loop flag.
        loop = false;
    }

    /**
     * Manage the all commands this program have.
     * Note: Execute methods all are out of this class. (Chapter10_2#execute())
     */
    private enum Command {
        Create(1),
        PrintAll(2),
        PayAll(3),
        Exit(4);

        private int commandId;

        Command(int commandId) {
            this.commandId = commandId;
        }

        public static Command findByCommand(int commandId) {
            for (Command command : values()) {
                if (command.commandId == commandId) {
                    return command;
                }
            }
            return null;
        }
    }
}
