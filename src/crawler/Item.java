package crawler;

public class Item {

    private Data data;

    public Item(Data data) {
        super();
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Item [data=" + data + "]";
    }

}
