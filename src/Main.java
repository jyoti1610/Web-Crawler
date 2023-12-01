import java.io.*;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://facebook.com/";
        new BufferedWriter(new FileWriter("Result.csv"));
        crawl(1, url, new ArrayList<String>());
    }
        private static void crawl(int level, String url, ArrayList<String> visited){
        if(level <= 5) {
            Document doc = request(url, visited);
            if(doc != null){
                for(Element link : doc.select("a[href]")){
                    String next_link = link.absUrl("href");
                    if(!visited.contains(next_link)){
                        crawl(level++,next_link,visited);
                    }
                }
            }
        }
            }
            private static Document request(String url, ArrayList<String> v) {
                try {
                    Connection con = Jsoup.connect(url);
                    Document doc = con.get();
                    if (con.response().statusCode() == 200) {
                        System.out.println("Link: " + url);
                        System.out.println(doc.title());
                        writeCsvFile("Result.csv","title : " + doc.title(),"Link: " + url);
                        v.add(url);
                        return doc;
                    }
                    return null;
                } catch (IOException e) {
                    return null;
                }
            }



    private static void writeCsvFile(String filename, String Url,String title) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true))) {
            writer.write(Url + "\n");
            writer.write(title + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}