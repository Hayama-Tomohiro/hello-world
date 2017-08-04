package crawler;

public class ListItem {

    private String goodsid;

    private String shopid;

    private String shop;

    private String colorid;

    private String link;

    private String image;

    private String price;

    private String discount;

    private String pricetype;

    private String alt;

    public ListItem(String goodsid, String shopid, String shop, String colorid, String link, String image, String price,
            String discount, String pricetype, String alt) {
        super();
        this.goodsid = goodsid;
        this.shopid = shopid;
        this.shop = shop;
        this.colorid = colorid;
        this.link = link;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.pricetype = pricetype;
        this.alt = alt;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getColorid() {
        return colorid;
    }

    public void setColorid(String colorid) {
        this.colorid = colorid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "ListItem [goodsid=" + goodsid + ", shopid=" + shopid + ", shop=" + shop + ", colorid=" + colorid
                + ", link=" + link + ", image=" + image + ", price=" + price + ", discount=" + discount + ", pricetype="
                + pricetype + ", alt=" + alt + "]";
    }

}
