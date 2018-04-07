/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apinbp;

import static apinbp.Apinbp.getAndVerifyInput;
import static apinbp.Apinbp.getAvg;
import static apinbp.Apinbp.getStandardDeviation;
import static apinbp.Apinbp.parseXMLData;
import static apinbp.Apinbp.requestDataFromServer;

/**
 *
 * @author Komabjn
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = getAndVerifyInput(args);
        String response = requestDataFromServer(url);
        if (response != null) {
            float[] bids = parseXMLData(response, "Bid");
            float[] asks = parseXMLData(response, "Ask");
            //calculate and print average bid
            System.out.println(String.format("%.4f", getAvg(bids)));
            //calculate and print standard deviation for asks
            System.out.println(String.format("%.4f", getStandardDeviation(asks)));
        } else {
            System.out.println("SORRY, something went wrong");
        }
    }
}
