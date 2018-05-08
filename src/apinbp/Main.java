/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apinbp;

import java.util.Scanner;

/**
 *
 * @author Komabjn
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Apinbp exchangeRateFetcher = new Apinbp();
            boolean success = false;
            //if input provided in args try fetch
            if(args.length > 0){
                success = exchangeRateFetcher.fetchData(args);
            }
            Scanner sc = null;
            //if fetch was unsuccesfull read new args from console and try fetch
            while (!success) {
                if (sc == null) {
                    sc = new Scanner(System.in);
                }else{
                    System.out.println("Wrong input");
                }
                args = sc.nextLine().split(" ");
                success = exchangeRateFetcher.fetchData(args);
            }
            //print average bid and standard deviation with 4 digit precision
            System.out.println(String.format("%.4f", exchangeRateFetcher.getAvgBid()));
            System.out.println(String.format("%.4f", exchangeRateFetcher.getStandardDeviationFromAsks()));
        } catch (CorruptedServerResponseException csre) {
            System.err.print(csre.toString());
        }
    }
}
