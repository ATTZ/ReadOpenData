/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.attz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author attz
 */
public class OpenData {
    private static Logger _log = Logger.getLogger(OpenData.class.getName());
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new OpenData();
    }

    public OpenData() {
        try {
            URL oracle = new URL("http://file.data.gov.tw/opendatafile/%E6%94%BF%E5%BA%9C%E8%B3%87%E6%96%99%E9%96%8B%E6%94%BE%E5%B9%B3%E8%87%BA%E8%B3%87%E6%96%99%E9%9B%86%E6%B8%85%E5%96%AE.json");

            URLConnection connection = oracle.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                sb.append(inputLine);
            }
            reader.close();
            
            
            
            JSONObject data = new JSONObject(sb.toString());
           
            JSONArray dataArray = data.getJSONArray("Records");
            
            PrintWriter writer = new PrintWriter("output.csv", "UTF-8");
            
            String br="\r\n";
            
            StringBuilder builder = new StringBuilder();
            for(int i =0;i<dataArray.length();i++){
                JSONObject item = dataArray.getJSONObject(i);
                builder.append("=========");
                builder.append(br);
                builder.append(item.getString("檔案格式"));
                builder.append(br);
                builder.append(item.getString("資料集名稱"));
                builder.append(br);
                builder.append(item.getString("計費方式"));
                builder.append(br);
                builder.append(item.getString("更新頻率"));
                builder.append(br);
                builder.append(item.getString("下載連結"));   
                builder.append(br);
            }
            _log.info(builder.toString());
            writer.print(builder.toString());
            writer.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(OpenData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
