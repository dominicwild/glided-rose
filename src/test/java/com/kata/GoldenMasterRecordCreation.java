package com.kata;

import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.kata.ItemFixture.ItemType;
import com.kata.util.IOUtils;

public class GoldenMasterRecordCreation {

	public static final String GOLDEN_MASTER_FILE_NAME = "golden-master.ser";

	private static final int MAX_QUALITY = 10000000;
	private static final int MAX_SELLIN = 5000000;

	public static class GoldenRecord implements Serializable {
		private Item[] input;
		private Item[] output;
	
		public GoldenRecord(Item[] input, Item[] output) {
			this.input = input;
			this.output = output;
		}
	
		public Item[] getInput() {
			return input;
		}
	
		public Item[] getOutput() {
			return output;
		}
	}

	public static void main(String[] args) {
		List<GoldenRecord> goldenRecords = Stream.generate(() -> generateGoldenRecord())
				.limit(100)
				.collect(toList());
		IOUtils.store(goldenRecords, GOLDEN_MASTER_FILE_NAME);
	}

	private static GoldenRecord generateGoldenRecord() {
		Item[] inputItems = generateInputItems();
		Item[] outputItems = generateOutputFrom(cloneOf(inputItems));
		return new GoldenRecord(inputItems, outputItems);
	}

	private static Item[] cloneOf(Item[] items) {
		Item[] itemsCopy = new Item[items.length];
		for (int i = 0; i < items.length; i++) {
			itemsCopy[i] = new Item(items[i].name, items[i].sellIn, items[i].quality);
		}
		return itemsCopy;
	}

	private static Item[] generateOutputFrom(Item[] inputItems) {
		new GildedRose(inputItems).updateQuality();
		return inputItems;
	}

	private static Item[] generateInputItems() {
		int iterations = 100;
		List<Item> items = new ArrayList<>();
		Random rand = new Random();

		for (int i = 0; i < iterations; i++) {
			for (ItemType itemType : ItemType.values()) {
				Item inputItem = ItemFixture.create(itemType);
				inputItem.quality = rand.nextInt(MAX_QUALITY * 2) - MAX_QUALITY;
				inputItem.sellIn = rand.nextInt(MAX_SELLIN * 2) - MAX_SELLIN;
				items.add(inputItem);
			}
		}

		return items.toArray(new Item[0]);
	}

}
