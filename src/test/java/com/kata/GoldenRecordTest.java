package com.kata;

import static com.kata.GoldenMasterRecordCreation.GOLDEN_MASTER_FILE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.kata.GoldenMasterRecordCreation.GoldenRecord;
import com.kata.util.IOUtils;

import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
public class GoldenRecordTest {

	@Test
	void golden_master_test() {
		List<GoldenRecord> goldenRecords = (List<GoldenRecord>) IOUtils
				.retrieve(GOLDEN_MASTER_FILE_NAME);

		goldenRecords.forEach(goldenRecord -> {
			Item[] inputItems = goldenRecord.getInput();
			Item[] outputItems = goldenRecord.getOutput();

			new GildedRose(inputItems).updateQuality();

			for (int i = 0; i < inputItems.length; i++) {
				assertItemsAreEqual(outputItems[i], inputItems[i]);
			}
		});
	}

	private void assertItemsAreEqual(Item item1, Item item2) {
		assertEquals(item1.name, item2.name);
		assertEquals(item1.sellIn, item2.sellIn);
		assertEquals(item1.quality, item2.quality);
	}

}
