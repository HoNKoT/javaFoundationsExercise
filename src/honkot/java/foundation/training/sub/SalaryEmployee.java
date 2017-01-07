package honkot.java.foundation.training.sub;

/**
 * Created by hhonda on 2017-01-06.
 */
public class SalaryEmployee extends Employee {

    private int monthlySalarry;

    public SalaryEmployee() {
        setPaidThisMonth(true);
    }

    public int getMonthlySalarry() {
        return monthlySalarry;
    }

    public void setMonthlySalarry(int monthlySalarry) {
        this.monthlySalarry = monthlySalarry;
    }

    @Override
    public void pay() {

    }

    @Override
    public String toString() {
        return "Salary Employee";
    }
}
