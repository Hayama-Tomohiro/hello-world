package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

    public static void main(String[] args) {

        // 取得したいURL
        String url = "http://tool-taro.com";
        // ユーザエージェント
        String userAgent = "mozilla/5.0 (windows nt 10.0; win64; x64) applewebkit/537.36 (khtml, like gecko) chrome/51.0.2704.79 safari/537.36 edge/14.14393";

        // 取得・パース処理
        try {
            Document document = Jsoup.connect(url).userAgent(userAgent).get();
            Elements elements = document.body().getAllElements();
            StringBuilder sb = new StringBuilder();
            for (Element element : elements) {
                // <a>タグの場合のみhref属性の値を出力する
                if (element.tag().getName() == "a") {
                    sb.append(element.attr("href")).append("\r\n");
                }
            }
            // タイトルを出力
            System.out.println("タイトル：" + document.title());
            // 本文を出力
            System.out.println("本文：" + sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
