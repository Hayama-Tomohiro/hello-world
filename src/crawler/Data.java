package crawler;

import java.util.List;

public class Data {

    private String couponid;

    private String sex;

    private List<ListItem> itemlist;

    public Data(String couponid, String sex, List<ListItem> itemlist) {
        super();
        this.couponid = couponid;
        this.sex = sex;
        this.itemlist = itemlist;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<ListItem> getItemlist() {
        return itemlist;
    }

    public void setItemlist(List<ListItem> itemlist) {
        this.itemlist = itemlist;
    }

    @Override
    public String toString() {
        return "Data [couponid=" + couponid + ", sex=" + sex + ", itemlist=" + itemlist + "]";
    }

}
