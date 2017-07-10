package com.aegeus.game.item.info;

import com.aegeus.game.item.AgItem;
import net.minecraft.server.v1_9_R1.NBTTagCompound;

import java.time.LocalDateTime;

public interface SingletonInfo {
	static <T extends AgItem & SingletonInfo> void impo(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		t.setTime((info.hasKey("time")) ? LocalDateTime.parse(info.getString("time")) : LocalDateTime.now());
	}

	static <T extends AgItem & SingletonInfo> void store(T t) {
		NBTTagCompound info = t.getAegeusInfo();
		info.setString("time", t.getTime().toString());
		t.setAegeusInfo(info);
	}

	LocalDateTime getTime();

	void setTime(LocalDateTime time);
}
