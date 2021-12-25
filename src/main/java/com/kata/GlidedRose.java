package com.kata;

class GildedRose {

	private static final int SELLIN_QUALITY_DEGREGATION_LIMIT = 0;
	private static final int MIN_QUALITY_LIMIT = 0;
	private static final int MAX_QUALITY_LIMIT = 50;
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
		if (!itemIsOneOf(item, SULFURAS, AGED_BRIE, BACKSTAGE_PASSES) && item.quality > MIN_QUALITY_LIMIT) {
			item.quality = item.quality - 1;
		}

		if (!itemBelowMaxQuality(item)) {
			return;
		}

		if (item.name.equals(AGED_BRIE)) {
			item.quality = item.quality + 1;
		}

		if (item.name.equals(BACKSTAGE_PASSES)) {
			if (item.sellIn >= 11) {
				item.quality = item.quality + 1;
			}
			if (itemInSellInRange(item, 6, 10)) {
				item.quality = item.quality + 2;
			}
			if (itemInSellInRange(item, 0, 5)) {
				item.quality = item.quality + 3;
			}
		}
	}

	private boolean itemInSellInRange(Item item, int lowerBound, int upperBound) {
		return item.sellIn >= lowerBound && item.sellIn <= upperBound;
	}

	private boolean itemBelowMaxQuality(Item item) {
		return item.quality < MAX_QUALITY_LIMIT;
	}

	private boolean itemIsOneOf(Item item, String... itemNames) {
		for (String itemName : itemNames) {
			if (item.name.equals(itemName)) {
				return true;
			}
		}
		return false;
	}

	private void updateSellIn(Item item) {
		if (!item.name.equals(SULFURAS)) {
			item.sellIn = item.sellIn - 1;
		}
	}

	private void updateQualityAfterSellByDate(Item item) {
		if (item.sellIn < SELLIN_QUALITY_DEGREGATION_LIMIT) {
			if (!item.name.equals(AGED_BRIE)) {
				if (!item.name.equals(BACKSTAGE_PASSES)) {
					if (item.quality > MIN_QUALITY_LIMIT) {
						if (!item.name.equals(SULFURAS)) {
							item.quality = item.quality - 1;
						}
					}
				}
				if (item.name.equals(BACKSTAGE_PASSES)) {
					item.quality = 0;
				}
			}
			if (item.name.equals(AGED_BRIE)) {
				if (itemBelowMaxQuality(item)) {
					item.quality = item.quality + 1;
				}
			}
		}
	}
}