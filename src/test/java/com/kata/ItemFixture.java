package com.kata;

public class ItemFixture {

    public enum ItemType {
        REGULAR_ITEM, AGED_BRIE, BACKSTAGE_PASSES, SULFURAS, CONJURED
    }

    public static Item create(ItemType type) {
        switch (type) {
            case AGED_BRIE:
                return agedBrie();
            case BACKSTAGE_PASSES:
                return backstagePasses();
            case SULFURAS:
                return sulfuras();
            case CONJURED:
                return conjured();
            case REGULAR_ITEM:
                return regularItem();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static Item regularItem() {
        return new Item("+5 Dexterity Vest", 10, 20);
    }

    private static Item agedBrie() {
        return new Item("Aged Brie", 2, 0);
    }

    private static Item backstagePasses() {
        return new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
    }

    private static Item sulfuras() {
        return new Item("Sulfuras, Hand of Ragnaros", 0, 80);
    }

    private static Item conjured() {
        return new Item("Conjured Mana Cake", 3, 6);
    }

}
