package honkot.java.foundation.training.sub;

/**
 * Created by hhonda on 2017-01-06.
 */
public class CommissionEmployee extends Employee {

    private int grossSales;
    private double commisionRate;

    public CommissionEmployee() {
        setPaidThisMonth(false);
        setCommisionRate(0.15);
    }

    public int getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(int grossSales) {
        this.grossSales = grossSales;
    }

    public double getCommisionRate() {
        return commisionRate;
    }

    public void setCommisionRate(double commisionRate) {
        this.commisionRate = commisionRate;
    }

    @Override
    public void pay() {

    }

    @Override
    public String toString() {
        return "Commission Employee";
    }
}
