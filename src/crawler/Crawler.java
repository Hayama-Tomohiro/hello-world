package crawler;

import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

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

        Gson gson = new Gson();

        // 取得したいJSON
        String json = "http://zozo.jp/coupon/json/couponitemjson_10321_all.txt?_=1501808125476";

        // HttpClientを、HttpClientBuilderで作成するか、簡易にHttpClientsから取得。戻り値の型は、CloseableHttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // GETするURLを指定
            HttpGet httpGet = new HttpGet(json);
            // HTTPリクエストを行い、HTTPレスポンスを取得
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                // HTTPレスポンスよりHTTPステータスを取得
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // レスポンスのBODYを取得
                    HttpEntity entity = response.getEntity();
                    // JSONからの変換
                    ItemWrapper itemWrapper = gson.fromJson(EntityUtils.toString(entity, StandardCharsets.UTF_8), ItemWrapper.class);
                    for (ListItem item : itemWrapper.getItem().getData().getItemlist()) {
                        System.out.println(item.getGoodsid() + "は、" + item.getPrice() + "円です。");
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

}
