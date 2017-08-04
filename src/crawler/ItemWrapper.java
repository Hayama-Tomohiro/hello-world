package crawler;

public class ItemWrapper {

    private Item item;

    public ItemWrapper(Item item) {
        super();
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ItemWrapper [item=" + item + "]";
    }

}
