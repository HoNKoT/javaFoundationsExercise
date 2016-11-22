package honkot.java.foundation.training;

import honkot.java.foundation.training.sub.Human;
import honkot.java.foundation.training.sub.SuperHuman;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter5_1 implements ChapterBase {

    @Override
    public void main() {
        SuperHuman human = new SuperHuman();
        human.setFirstName("Honda");
        human.setLastName("Hiroki");
        human.setAge(28);
        human.setNickName("Hondy");
        human.printAllInfo();
    }
}
