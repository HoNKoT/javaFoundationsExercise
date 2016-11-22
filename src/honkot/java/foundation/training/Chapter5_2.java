package honkot.java.foundation.training;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-22.
 */
public class Chapter5_2 implements ChapterBase {

    Random mRandom = new Random(System.currentTimeMillis());

    @Override
    public void main() {
        System.out.println("Generate 4 students and print them.");

        Student studentA = new Student("Hiroki", "6336 PL", "Android");
        System.out.println(studentA.toString());

        Student studentB = new Student("Matia", "White House");
        System.out.println(studentB.toString());

        Student studentC = new Student("Suha", "Anywhere", "Graphic Design");
        System.out.println(studentC.toString());

        Student studentD = new Student("Michinobu", "Japan", "Web Master");
        System.out.println(studentD.toString());

    }

    class Student {
        private String name;
        private String address;
        private String major;
        private double GPA;

        Student (String name, String address, String major) {
            setName(name);
            setAddress(address);
            setMajor(major);
            computeGPA();
        }

        Student (String name, String address) {
            this(name, address, "undeclared");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public double getGPA() {
            return GPA;
        }

        private void computeGPA() {
            int baseRandomNum = mRandom.nextInt(35) + 5;
            this.GPA = (double)baseRandomNum / 10;
        }

        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("name : ");
            buf.append(getName());
            buf.append(System.getProperty("line.separator"));
            buf.append("address : ");
            buf.append(getAddress());
            buf.append(System.getProperty("line.separator"));
            buf.append("major : ");
            buf.append(getMajor());
            buf.append(System.getProperty("line.separator"));
            buf.append("Grade point average : ");
            buf.append(getGPA());
            buf.append(System.getProperty("line.separator"));

            return buf.toString();
        }
    }
}
