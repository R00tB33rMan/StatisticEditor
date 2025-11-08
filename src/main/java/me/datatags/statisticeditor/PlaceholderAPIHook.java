package me.datatags.statisticeditor;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

	private final StatisticEditor se;

	public PlaceholderAPIHook(@NotNull StatisticEditor se) {
		this.se = se;
	}

	@Override
	public @NotNull String getAuthor() {
		return se.getDescription().getAuthors().getFirst();
	}

	@Override
	public @NotNull String getIdentifier() {
		return "stat";
	}

	@Override
	public @NotNull String getVersion() {
		return se.getDescription().getVersion();
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public boolean persist() {
	    return true;
	}

	@Override
	public String onPlaceholderRequest(Player p, String params) {
		String[] args = params.split("-");
		Statistic stat = StatisticManager.getStatistic(args[0]);
		return StatisticManager.getStatValue(p, stat, args.length > 1 ? args[1] : null).getValue() + "";
	}
}
