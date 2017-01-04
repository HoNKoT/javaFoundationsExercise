package honkot.java.foundation.training;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter10_1 implements ChapterBase {

    /**
     * Container for each products.
     * Put product by key = vin as unique No.
     */
    private static HashMap<Integer, ProductBase> mMap = new HashMap<>();
    private static Random mVinGenerator = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        generateTestSales();
        printAll();
    }

    /**
     * This is for my own programming code.
     * Ignore this for rating.
     */
    @Override
    public void main() {
        generateTestSales();
        printAll();
    }

    private static void generateTestSales() {
        int incrementInteger = 0;

        for (int i = 0; i < 3; i++) {
            Car car = new Car(
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++);
            mMap.put(car.getVin(), car);
        }

        for (int i = 0; i < 2; i++) {
            Boat boat = new Boat(
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++);
            mMap.put(boat.getVin(), boat);
        }

        for (int i = 0; i < 4; i++) {
            Motorcycle motorcycle = new Motorcycle(
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++,
                    incrementInteger++);
            mMap.put(motorcycle.getVin(), motorcycle);
        }
    }

    private static void printAll() {
        for (Integer key : mMap.keySet()) {
            System.out.println(mMap.get(key));
            if (mMap.get(key) instanceof testInterface) {
                ((testInterface) mMap.get(key)).turnIgnitionOn();
                ((testInterface) mMap.get(key)).steer();
            }
        }
    }

    private static int generateVin(ProductBase newProduct) {
        boolean loop = false;
        int tempVin;

        do {
            // generate new random No
            // Note: In this point, the number might not be unique
            tempVin = mVinGenerator.nextInt(999999);

            // check what the temporary No is unique or not
            loop = mMap.containsKey(tempVin);
        } while(loop);

        // put unique No
        newProduct.setVin(tempVin);

        // put product class to map
//        mMap.put(tempVin, newProduct);
        return tempVin;
    }

    /**
     * Abstract class for each product classes.
     */
    private static abstract class ProductBase implements testInterface {
        private int model;
        private int make;
        private int year;
        private int color;
        private int engine;
        private int vin;

        public ProductBase(int model, int make, int year, int color, int engine) {
            this.model = model;
            this.make = make;
            this.year = year;
            this.color = color;
            this.engine = engine;

            // generate vin No
            generateVin(this);
        }

        public int getModel() {
            return model;
        }

        public void setModel(int model) {
            this.model = model;
        }

        public int getMake() {
            return make;
        }

        public void setMake(int make) {
            this.make = make;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getEngine() {
            return engine;
        }

        public void setEngine(int engine) {
            this.engine = engine;
        }

        public int getVin() {
            return vin;
        }

        public void setVin(int vin) {
            this.vin = vin;
        }

        @Override
        public String toString() {
            return ", model=" + model +
                    ", make=" + make +
                    ", year=" + year +
                    ", color=" + color +
                    ", engine=" + engine +
                    ", vin=" + String.format("%06d", vin) +
                    '}';
        }

        @Override
        public void turnIgnitionOn() {
            System.out.println("Vin " + String.format("%06d", getVin()) + ": turn ignition on");
        }

        @Override
        public void steer() {
            System.out.println("Vin " + String.format("%06d", getVin()) + ": steer");
        }
    }

    /**
     * Car
     */
    private static class Car extends ProductBase {
        private int doors;

        public Car(int model, int make, int year, int color, int engine, int doors) {
            super(model, make, year, color, engine);
            this.doors = doors;
        }

        public int getDoors() {
            return doors;
        }

        public void setDoors(int doors) {
            this.doors = doors;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "doors=" + doors +
                    super.toString() +
                    '}';
        }
    }

    /**
     * Boat
     */
    private static class Boat extends ProductBase {
        private int type;
        private int size;

        public Boat(int model, int make, int year, int color, int engine, int type, int size) {
            super(model, make, year, color, engine);
            this.type = type;
            this.size = size;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Boat{" +
                    "type=" + type +
                    ", size=" + size +
                    super.toString() +
                    '}';
        }
    }

    /**
     * Motorcycle
     */
    private static class Motorcycle extends ProductBase {

        public Motorcycle(int model, int make, int year, int color, int engine) {
            super(model, make, year, color, engine);
        }

        @Override
        public String toString() {
            return "Motorcycle{" +
                    super.toString() +
                    '}';
        }
    }

    private interface testInterface {
        void turnIgnitionOn();
        void steer();
    }
}
