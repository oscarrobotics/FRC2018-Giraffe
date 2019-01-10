package org.usfirst.frc.team832.robot.func;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProfileFromCsv {
    public static double[][] profMainArr;
    public static int pointCount = 0;

    public static void readProfile(String filename) {
        double[][] readArr;
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        int lineCounter = 0;
        int totalLines = 0;

        try {

            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                totalLines++;
            }
            br.reset();
            readArr = new double[totalLines][10];
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] vals = line.split(cvsSplitBy);

                // check first line for header and skip if necessary
                if (lineCounter == 0 && vals[0].contains("dt")) {
                    lineCounter++;
                    continue;
                }
                for (int i = 0; i < vals.length; i++) {
                    readArr[lineCounter][i] = Double.parseDouble(vals[i]);
                }
                lineCounter++;
            }

            // output all our variables
            profMainArr = readArr;
            pointCount = profMainArr.length;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
