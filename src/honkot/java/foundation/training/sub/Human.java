package honkot.java.foundation.training.sub;

/**
 * Created by hhonda on 2016-11-21.
 */
public class Human {
    private String firstName, lastName;
    private int age;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void printAllInfo() {
        StringBuffer buf = new StringBuffer();
        buf.append("first name : ");
        buf.append(firstName);
        buf.append(System.getProperty("line.separator"));
        buf.append("last name : ");
        buf.append(lastName);
        buf.append(System.getProperty("line.separator"));
        buf.append("age : ");
        buf.append(age);
        System.out.println(buf.toString());
    }
}
