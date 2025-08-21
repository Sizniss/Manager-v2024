package kr.sizniss.manager.customevents;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomDamageEntityEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private LivingEntity attacker;
    private LivingEntity victim;
    private double damage;

    public CustomDamageEntityEvent(LivingEntity victim, double damage) {
        new CustomDamageEntityEvent(null, victim, damage);
    }

    public CustomDamageEntityEvent(LivingEntity attacker, LivingEntity victim, double damage) {
        this.attacker = attacker;
        this.victim = victim;
        this.damage = damage;
    }

    public LivingEntity getAttacker() {
        return attacker;
    }

    public LivingEntity getVictim() {
        return victim;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
