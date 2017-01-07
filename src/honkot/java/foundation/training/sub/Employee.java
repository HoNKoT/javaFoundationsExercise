package honkot.java.foundation.training.sub;

/**
 * Created by hhonda on 2017-01-06.
 */
public abstract class Employee {
    private String firstName;
    private String lastName;
    private int id;
    private boolean paidThisMonth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getPaidThisMonth() {
        return paidThisMonth;
    }

    public void setPaidThisMonth(boolean paidThisMonth) {
        this.paidThisMonth = paidThisMonth;
    }

    public abstract void pay();

    @Override
    public String toString() {
        return "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                ", paidThisMonth=" + paidThisMonth +
                '}';
    }
}
