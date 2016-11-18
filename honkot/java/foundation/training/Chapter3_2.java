package honkot.java.foundation.training;

/**
 * Created by hhonda on 2016-11-17.
 */
public class Chapter3_2 extends ChapterBase {

    @Override
    public void main() {
        // write your code here
        //System.out.print("Type here 'anytown, USA' : ");

        String college = new String("PoDunk College");
        String getTown = "Anytown, USA";//new Scanner(System.in).next();

        int stringLength;
        String change1, change2, change3;

        stringLength = college.length();
        System.out.println(college + " contains " + stringLength + " characters.");

//        char temp;
//        for (int i = 0; i < college.length(); i++) {
//            temp = college.charAt(i);
//            if (Character.isUpperCase(temp)) {
//
//            }
//        }
        change1 = college.toUpperCase();
        change2 = change1.replace("O", "*");
        change3 = college.concat(getTown);

        System.out.println("The final string is " + change3);
    }
}
