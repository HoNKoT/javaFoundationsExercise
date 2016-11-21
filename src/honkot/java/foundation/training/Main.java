package honkot.java.foundation.training;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final long LOOP_STOP_ALTERNATE_TIME = 200;

    private static final Chapter[] CHAPTER = {
            new Chapter(3, 2, new Chapter3_2(), "Play String"),
            new Chapter(3, 3, new Chapter3_3(), "Enum"),
            new Chapter(3, 4, new Chapter3_4(), "Pin Encryption"),
            new Chapter(3, 5, new Chapter3_5(), "Input and do some tasks on program"),
            new Chapter(4, 1, new Chapter4_1(), "Computing A Raise"),
            new Chapter(4, 2, new Chapter4_2(), "Counting and Summing"),
            new Chapter(4, 3, new Chapter4_3(), "String Reverser"),
            new Chapter(4, 4, new Chapter4_4(), "Rock, Paper, Scissors"),
    };

    public static void main(String[] args) {
        int major = 0, minor = 0;
        boolean called = false;
        int callCounter = 1;

        do {
            // already fixed major and minor, just loop infinity the function.
            if (called) {
                boolean loopStopper = false;
                for (Chapter chapter: CHAPTER) {
                    if (chapter.isThisChapter(major, minor)) {
                        long beforeSystemClock = System.currentTimeMillis();
                        System.out.println(System.getProperty("line.separator"));
                        System.out.println(" --------------- Start (" + callCounter + ") "
                                + chapter.getChapterInfo() + " -------------------");
                        chapter.callMain();
                        callCounter++;

                        // Check infinity loop
                        if (System.currentTimeMillis() - beforeSystemClock
                                < LOOP_STOP_ALTERNATE_TIME)
                            loopStopper = true;
                    }
                }

                if (!loopStopper) {
                    continue;
                } else {
                    System.out.println("DONE!");
                    break;
                }
            }

            // print all chapter info
            for (Chapter chapter: CHAPTER) {
                chapter.println();
            }

            // input major
            System.out.println("Type Chapter Major/Minor Number (x-x): ");
            Scanner scan = new Scanner(System.in);

            // check format
            String userInput = scan.next();
            if (userInput.contains("-")) {
                String[] values = userInput.split("-");

                try {
                    major = Integer.parseInt(values[0]);
                    minor = Integer.parseInt(values[1]);
                } catch (NumberFormatException e) {
                    System.out.println("INPUT ERROR!! TRY AGA®IN!!");
                    continue;
                }
            } else {

                // get the major value
                try {
                    major = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    System.out.println("INPUT ERROR!! TRY AGAIN!!");
                    continue;
                }

                // check error
                boolean error = true;
                for (Chapter chapter : CHAPTER) {
                    if (chapter.isThisChapter(major)) {
                        error = false;
                    }
                }
                if (error) {
                    System.out.println("Invalid major (" + major + "). Try again!");
                    continue;
                }

                // check scan has minor TODO: DOES NOT WORK!!
//                if (!scan.hasNext()) {
//                    System.out.println("Type Chapter Minor Number : ");
//                    for (Chapter chapter : CHAPTER) {
//                        if (chapter.isThisChapter(major)) {
//                            chapter.println();
//                        }
//                    }
//                }

                // check the minor value
                try {
                    minor = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("INPUT ERROR!! TRY AGAIN from input major!!");
                    continue;
                }
            }

            boolean error = true;
            for (Chapter chapter: CHAPTER) {
                if (chapter.isThisChapter(major, minor)) {
                    error = false;

                    // fixed getting major and minor.
                    // start call main() in the Chapter infinity
                    called = true;
                    break;
                }
            }

            if (error) {
                System.out.println("Invalid chapter (" + major + "-" + minor + "). Try again!");
                continue;
            }

        } while (true);
    }


    private static class Chapter {
        int major, minor;
        String description;
        Object chapterClass;
        public Chapter(int major, int minor, Object chapter) {
            this(major, minor, chapter, "");
        }

        public Chapter(int major, int minor, Object chapter, String desc) {
            this.major = major;
            this.minor = minor;
            this.chapterClass = chapter;
            this.description = desc;
        }

        /**
         * Just check major
         * @param major
         * @return
         */
        public boolean isThisChapter(int major) {
            return this.major == major;
        }

        public boolean isThisChapter(int major, int minor) {
            return this.major == major && this.minor == minor;
        }

        public void callMain() {
            if (chapterClass instanceof ChapterBase) {
                ((ChapterBase) chapterClass).main();
            }
        }

        public void println() {
            System.out.println(getChapterInfo());
        }

        public String getChapterInfo() {
            StringBuffer buf = new StringBuffer();
            buf.append(" - Chapter ");
            buf.append(major);
            buf.append("-");
            buf.append(minor);
            if (!description.isEmpty()) {
                buf.append(" : ");
                buf.append(description);
            }
            return buf.toString();
        }
    }
}
