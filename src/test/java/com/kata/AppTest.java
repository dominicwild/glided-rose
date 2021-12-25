package com.kata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kata.ItemFixture.ItemType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

public class AppTest {

	@Test
	void regular_item_decreases_in_quality_by_one_with_positive_sell_in() {
		Item regularItem = ItemFixture.create(ItemType.REGULAR_ITEM);
		regularItem.sellIn = 10;
		int expectedValue = regularItem.quality - 1;

		updateQuality(regularItem);

		assertEquals(expectedValue, regularItem.quality);
	}

	@Test
	void regular_item_decreases_in_quality_by_two_with_negative_sell_in() {
		Item regularItem = ItemFixture.create(ItemType.REGULAR_ITEM);
		regularItem.sellIn = -1;
		int expectedValue = regularItem.quality - 2;

		updateQuality(regularItem);

		assertEquals(expectedValue, regularItem.quality);
	}

	@Test
	void regular_item_quality_can_never_drop_below_zero() {
		Item regularItem = ItemFixture.create(ItemType.REGULAR_ITEM);
		regularItem.quality = 0;
		int expectedValue = 0;

		updateQuality(regularItem);

		assertEquals(expectedValue, regularItem.quality);
	}

	@Test
	void aged_brie_increases_in_quality_by_one_regardless_of_sell_in() {
		Item agedBrie = ItemFixture.create(ItemType.AGED_BRIE);
		agedBrie.sellIn = 10;
		int expectedValue = agedBrie.quality + 1;

		updateQuality(agedBrie);

		assertEquals(expectedValue, agedBrie.quality);
	}

	@Test
	void aged_brie_increases_in_quality_by_two_with_negative_sell_in() {
		Item agedBrie = ItemFixture.create(ItemType.AGED_BRIE);
		agedBrie.sellIn = -10;
		int expectedValue = agedBrie.quality + 2;

		updateQuality(agedBrie);

		assertEquals(expectedValue, agedBrie.quality);
	}

	@ParameterizedTest
	@EnumSource(value = ItemType.class, names = { "AGED_BRIE", "BACKSTAGE_PASSES" }, mode = Mode.INCLUDE)
	void value_of_an_item_is_never_more_than_50(ItemType itemType) {
		Item itemThatIncreasesInValue = ItemFixture.create(itemType);
		itemThatIncreasesInValue.quality = 50;
		int expectedValue = 50;

		updateQuality(itemThatIncreasesInValue);

		assertEquals(expectedValue, itemThatIncreasesInValue.quality);
	}

	@ParameterizedTest
	@ValueSource(ints = { -10, -1, 0, 1, 10 })
	void sulfuras_never_decreases_in_quality(int sellIn) {
		Item sulfuras = ItemFixture.create(ItemType.SULFURAS);
		int expectedValue = sulfuras.quality;
		sulfuras.sellIn = sellIn;

		updateQuality(sulfuras);

		assertEquals(expectedValue, sulfuras.quality);
	}

	@ParameterizedTest
	@EnumSource(value = ItemType.class, names = { "SULFURAS" }, mode = Mode.EXCLUDE)
	void all_items_sell_in_always_decreases_by_one_except_sulfuras(ItemType itemType) {
		Item sulfuras = ItemFixture.create(itemType);
		int expectedValue = sulfuras.sellIn - 1;

		updateQuality(sulfuras);

		assertEquals(expectedValue, sulfuras.sellIn);
	}

	@ParameterizedTest
	@EnumSource(value = ItemType.class, names = { "SULFURAS" }, mode = Mode.INCLUDE)
	void sulfuras_sell_in_does_not_decrease(ItemType itemType) {
		Item sulfuras = ItemFixture.create(itemType);
		int expectedValue = sulfuras.sellIn;

		updateQuality(sulfuras);

		assertEquals(expectedValue, sulfuras.sellIn);
	}

	@ParameterizedTest
	@ValueSource(ints = { 11, 20, 50 })
	void backstage_passes_increases_in_quality_by_one_with_sell_in_above_11(int sellInAbove10) {
		Item backstagePasses = ItemFixture.create(ItemType.BACKSTAGE_PASSES);
		backstagePasses.sellIn = sellInAbove10;
		int expectedValue = backstagePasses.quality + 1;

		updateQuality(backstagePasses);

		assertEquals(expectedValue, backstagePasses.quality);
	}

	@ParameterizedTest
	@ValueSource(ints = { 6, 7, 8, 9, 10 })
	void backstage_passes_increases_in_quality_by_two_with_sell_in_between_6_and_10(int sellInBetween6And10) {
		Item backstagePasses = ItemFixture.create(ItemType.BACKSTAGE_PASSES);
		backstagePasses.sellIn = sellInBetween6And10;
		int expectedValue = backstagePasses.quality + 2;

		updateQuality(backstagePasses);

		assertEquals(expectedValue, backstagePasses.quality);
	}

	@ParameterizedTest
	@ValueSource(ints = { 5, 4, 3, 2, 1 })
	void backstage_passes_increase_in_quality_by_three_when_sell_in_is_5_or_less(int sellInBelow6) {
		Item backstagePasses = ItemFixture.create(ItemType.BACKSTAGE_PASSES);
		backstagePasses.sellIn = sellInBelow6;
		int expectedValue = backstagePasses.quality + 3;

		updateQuality(backstagePasses);

		assertEquals(expectedValue, backstagePasses.quality);
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, -1, -10, -100 })
	void backstage_passes_quality_drops_to_zero_when_sell_in_is_below_zero() {
		Item backstagePasses = ItemFixture.create(ItemType.BACKSTAGE_PASSES);
		backstagePasses.sellIn = -1;
		int expectedValue = 0;

		updateQuality(backstagePasses);

		assertEquals(expectedValue, backstagePasses.quality);
	}

	private void updateQuality(Item regularItem) {
		new GildedRose(regularItem).updateQuality();
	}

}
