package apinbp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;

/**
 *
 * @author Komabjn
 */
public class Apinbp {
    
    private float[] bids, asks;
    
    public void fetchData(String[] input){
        String url = getAndVerifyInput(input);
        String rawXml = requestDataFromServer(url);
        bids = parseXMLData(rawXml, "Bid");
        asks = parseXMLData(rawXml, "Ask");
    }
    
    public float getAvgBid(){
        return getAvg(bids);
    }
    
    public float getStandardDeviationFromAsks(){
        return getStandardDeviation(asks);
    }

    /**
     * Calculates average value of values given in an array
     *
     * @param values    - to calculate average of
     * @return
     */
    public static float getAvg(float[] values) {
        float sumOfValues = 0;
        for (float value : values) {
            sumOfValues += value;
        }
        return (sumOfValues / values.length);
    }

    /**
     * Calculates standard deviation on population of values, taking desired
     * value as an avg of all those values
     *
     * @param values    - values to count standard deviation on
     * @return
     */
    public static float getStandardDeviation(float[] values) {
        float valuesAvg = getAvg(values);
        float sumOfPowers = 0;
        for (float value : values) {
            sumOfPowers += (value - valuesAvg) * (value - valuesAvg);
        }
        return ((float) Math.sqrt(sumOfPowers / values.length));
    }

    /**
     * Gets an input from arguments or from console if not supllied, and forms
     * http address to perform request
     *
     * @param args  - args from commandline or command (single word in single
     * segment)
     * @return url representing requested data on api.nbp.pl
     */
    public static String getAndVerifyInput(String[] args) {
        StringBuilder url = new StringBuilder();
        url.append("http://api.nbp.pl/api/exchangerates/rates/c/");
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            args = sc.nextLine().split(" ");
        }
        url.append(args[0]).append("/");
        if (args.length > 1) {
            url.append(args[1]).append("/");
        }
        if (args.length > 2) {
            url.append(args[2]).append("/");
        }
        url.append("?format=xml");
        return url.toString();
    }

    /**
     * This method extracts values out of raw xml
     *
     * @param input - String xml with server response
     * @param tag   - tag in xml after which comes value to be extracted
     * @return values of particular exchange rates in array
     */
    public static float[] parseXMLData(String input, String tag) {
        String[] rates = input.split("<" + tag + ">");
        float[] numeralRates = new float[rates.length - 1];
        for (int i = 1; i < rates.length; i++) {
            /**
             * NOTE!!! this part assumes response to be 6 letter (1 digit, dot,
             * and 4 more digits), and it may lose precision if exchange rate
             * hits 2 digit values
             */
            numeralRates[i - 1] = Float.parseFloat(rates[i].substring(0, 6));
        }
        return numeralRates;
    }

    /**
     * This method requests data from server and returns it as a string
     *
     * @param targetURL - url at which GET will be requested
     * @return          - String representation of response
     */
    public static String requestDataFromServer(String targetURL) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
