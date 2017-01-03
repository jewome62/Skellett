package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.BrewingStandFuelEvent;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[(the|is)] brew[ing] [stand[s]] consuming [state]")
@Config("Syntax.Events.BrewingFuel")
@FullConfig
@Version("1.11.2")
@PropertyType("SIMPLE")
public class ExprBrewingFuelConsuming extends SimpleExpression<Boolean>{
	
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(BrewingStandFuelEvent.class)) {
			Skript.error("You can not use brewing stand fuel consuming expression in any event but brewing stand fuel event!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|is)] brew[ing] [stand[s]] consuming [state]";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		return new Boolean[]{((BrewingStandFuelEvent)e).isConsuming()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			((BrewingStandFuelEvent)e).setConsuming((Boolean)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}