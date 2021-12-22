package com.kata;

class GildedRose {

	private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
	private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
	private static final String AGED_BRIE = "Aged Brie";

	Item[] items;

	public GildedRose(Item... items) {
		this.items = items;
	}

	public void updateQuality() {
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];

			updateQualityBeforeSellByDate(item);
			updateQualityAfterSellByDate(item);
			updateSellIn(item);
		}
	}

	private void updateQualityBeforeSellByDate(Item item) {
		if (itemIsNotOneOf(item, SULFURAS, AGED_BRIE, BACKSTAGE_PASSES) && item.quality > 0) {
			item.quality = item.quality - 1;
		}

		if (item.name.equals(AGED_BRIE)
				|| item.name.equals(BACKSTAGE_PASSES)) {
			if (item.quality < 50) {
				item.quality = item.quality + 1;

				if (item.name.equals(BACKSTAGE_PASSES)) {
					if (item.sellIn < 11) {
						if (item.quality < 50) {
							item.quality = item.quality + 1;
						}
					}

					if (item.sellIn < 6) {
						if (item.quality < 50) {
							item.quality = item.quality + 1;
						}
					}
				}
			}
		}
	}

	private boolean itemIsNotOneOf(Item item, String... itemNames) {
		for (String itemName : itemNames) {
			if (item.name.equals(itemName)) {
				return false;
			}
		}
		return true;
	}

	private void updateSellIn(Item item) {
		if (!item.name.equals(SULFURAS)) {
			item.sellIn = item.sellIn - 1;
		}
	}

	private void updateQualityAfterSellByDate(Item item) {
		if (item.sellIn < 0) {
			if (!item.name.equals(AGED_BRIE)) {
				if (!item.name.equals(BACKSTAGE_PASSES)) {
					if (item.quality > 0) {
						if (!item.name.equals(SULFURAS)) {
							item.quality = item.quality - 1;
						}
					}
				} else {
					item.quality = item.quality - item.quality;
				}
			} else {
				if (item.quality < 50) {
					item.quality = item.quality + 1;
				}
			}
		}
	}
}