package com.aegeus.game.entity;

import com.aegeus.game.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AgPlayer extends AgLiving {
    private BossBar healthBar;
    private BossBar targetBar;

    public AgPlayer(Player player) {
        super(player);
        setAttackDelay(300);
    }

    public Player getEntity() {
        return (Player) super.getEntity();
    }

    public BossBar getHealthBar() {
        return healthBar;
    }

    @Override
    public void update() {
        super.update();
        displayHealthBar();
    }

    public void displayHealthBar() {
        if (healthBar == null) {
            healthBar = Bukkit.createBossBar("Health Display", BarColor.WHITE, BarStyle.SOLID);
            healthBar.setVisible(true);
            healthBar.addPlayer(getEntity());
        }

        double health = getEntity().getHealth();
        double maxHealth = Util.getMaxHealth(getEntity());
        String titleColor = "&";

        // Set colors based on health
        if (health < maxHealth * 0.2) {
            healthBar.setColor(BarColor.RED);
            titleColor += ChatColor.RED.getChar();
        } else if (health < maxHealth * 0.4) {
            healthBar.setColor(BarColor.YELLOW);
            titleColor += ChatColor.YELLOW.getChar();
        } else {
            healthBar.setColor(BarColor.GREEN);
            titleColor += ChatColor.GREEN.getChar();
        }

        healthBar.setTitle(Util.colorCodes(titleColor + Math.round(health) + " / " + Math.round(maxHealth) + "&f Health"));
        healthBar.setStyle(segmentBar(maxHealth));
        healthBar.setProgress(health / maxHealth);
    }

    public void displayTargetBar(LivingEntity target) {
        if (targetBar != null && target.isDead()) {
            targetBar.removePlayer(getEntity());
            targetBar = null;
        } else if (!target.isDead()) {
            if (targetBar == null) {
                targetBar = Bukkit.createBossBar("Target Display", BarColor.WHITE, BarStyle.SOLID);
                targetBar.setVisible(true);
                targetBar.addPlayer(getEntity());
            }

            double health = target.getHealth();
            double maxHealth = Util.getMaxHealth(target);

            targetBar.setTitle(Util.colorCodes(Util.getName(target) + "&7 [" + Math.round(health) + " / " + Math.round(maxHealth) + "]"));
            targetBar.setStyle(segmentBar(maxHealth));
            targetBar.setProgress(health / maxHealth);
        }
    }

    private BarStyle segmentBar(double maxHealth) {
        if (maxHealth >= 25000)
            return BarStyle.SEGMENTED_20;
        else if (maxHealth >= 12000)
            return BarStyle.SEGMENTED_12;
        else if (maxHealth >= 4500)
            return BarStyle.SEGMENTED_10;
        else if (maxHealth >= 1800)
            return BarStyle.SEGMENTED_6;

        return BarStyle.SOLID;
    }
}
