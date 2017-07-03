package com.aegeus.game.item.info;

import java.util.ArrayList;
import java.util.List;

public interface ItemInfo {
	default String buildNamePrefix() {
		return "";
	}

	default String buildNameSuffix() {
		return "";
	}

	default List<String> buildLore() {
		return new ArrayList<>();
	}
}
