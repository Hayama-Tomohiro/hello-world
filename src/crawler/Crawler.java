package crawler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        String json = "http://zozo.jp/coupon/json/couponitemjson_10316_all.txt?_=1502094095948";
        // 変換後オブジェクト
        ItemWrapper itemWrapper = null;

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
                    itemWrapper = gson.fromJson(EntityUtils.toString(entity, StandardCharsets.UTF_8), ItemWrapper.class);
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

        /*
        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("Sheet1");

        try (OutputStream out = new FileOutputStream("doc/sample.xlsx")) {
            wb.write(out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        */

        try (InputStream in = new FileInputStream("doc/sample.xlsx");
                Workbook wb = WorkbookFactory.create(in);
                OutputStream out = new FileOutputStream("doc/sample.xlsx")) {
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("〇");
            wb.write(out);
            System.out.println(cell.getStringCellValue());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Excel読み込み
        try (InputStream in = new FileInputStream("doc/input.xlsx");
                Workbook wbIn = WorkbookFactory.create(in)) {

            // 出力用Workbookを作成
            Workbook wbOut = new XSSFWorkbook();
            Sheet sheetOut = wbOut.createSheet("チェック結果");
            // ヘッダー行を作成
            Row rowOut0 = sheetOut.createRow(0);
            rowOut0.createCell(0).setCellValue("商品ID");
            rowOut0.createCell(1).setCellValue("価格");
            rowOut0.createCell(2).setCellValue("サイト価格");
            rowOut0.createCell(3).setCellValue("チェック結果");

            // 入力用Workbookの一番最初のシートを選択
            Sheet sheetIn = wbIn.getSheetAt(0);

            // 商品ID、価格を読み取り、Mapに格納
            Map<String, Integer> map = new LinkedHashMap<>();

            for (int i = 0; i <= sheetIn.getLastRowNum(); i++) {
                // ヘッダー行の場合
                if (i == 0) {
                    Row header = sheetIn.getRow(i);
                    // 正しいヘッダーになっているかチェック
                    if (!StringUtils.equals(header.getCell(0).getStringCellValue(), "商品ID")) {
                        throw new Exception();
                    }
                    if (!StringUtils.equals(header.getCell(1).getStringCellValue(), "価格")) {
                        throw new Exception();
                    }
                    continue;
                }
                String key = "";
                int value = 0;
                Row row = sheetIn.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // 商品ID列の場合
                    if (j == 0) {
                        if (row.getCell(j).getCellTypeEnum() == CellType.STRING) {
                            key = row.getCell(j).getStringCellValue();
                        }
                        else if (row.getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
                            key = Integer.toString((int) row.getCell(j).getNumericCellValue());
                        }
                        else {
                            throw new Exception();
                        }
                    }
                    // 価格列の場合
                    else if (j == 1) {
                        if (row.getCell(j).getCellTypeEnum() == CellType.STRING) {
                            value = Integer.parseInt(row.getCell(j).getStringCellValue());
                        }
                        else if (row.getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
                            value = (int) row.getCell(j).getNumericCellValue();
                        }
                    }
                    // 3列目がある場合
                    else {
                        throw new Exception();
                    }
                }
                map.put(key, value);
            }

            // ファイルから読み込んだMapをもとにサイト価格と突き合わせ、結果出力
            int rownum = 1; // ← 0はヘッダー行
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                // 明細行を作成
                Row rowOut = sheetOut.createRow(rownum++);
                rowOut.createCell(0).setCellValue(entry.getKey());
                rowOut.createCell(1).setCellValue(entry.getValue());
                // 価格チェック
                for (ListItem item : itemWrapper.getItem().getData().getItemlist()) {
                    if (StringUtils.equals(entry.getKey(), item.getGoodsid())) {
                        rowOut.createCell(2).setCellValue(NumberFormat.getInstance().parse(item.getPrice()).intValue());
                        if (entry.getValue() == NumberFormat.getInstance().parse(item.getPrice()).intValue()) {
                            rowOut.createCell(3).setCellValue("〇");
                        }
                        else {
                            rowOut.createCell(3).setCellValue("×");
                        }
                        break;
                    }
                }
                if (rowOut.getCell(2) == null) {
                    rowOut.createCell(2).setCellValue("存在しません");
                    rowOut.createCell(3).setCellValue("×");
                }
            }

            map.forEach((key, value) -> System.out.println(key + ":" + value));

            // 結果を出力
            try (OutputStream out = new FileOutputStream("doc/output.xlsx")) {
                wbOut.write(out);
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
