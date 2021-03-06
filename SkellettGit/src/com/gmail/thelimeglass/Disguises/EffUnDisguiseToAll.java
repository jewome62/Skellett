package com.gmail.thelimeglass.Disguises;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[skellett] [[Libs]Disguises] Un[( |-)]Disguise %entitys%")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
public class EffUnDisguiseToAll extends Effect {
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[[Libs]Disguises] Un[( |-)]Disguise %entitys%";
	}
	@Override
	protected void execute(Event e) {
		if (DisguiseAPI.isDisguised(entity.getSingle(e))) {
			DisguiseAPI.undisguiseToAll(entity.getSingle(e));
		} else {
			return;
		}
	}
}
