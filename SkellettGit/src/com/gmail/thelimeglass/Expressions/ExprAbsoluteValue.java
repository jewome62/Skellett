package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("absolute [value] of %number%")
@Config("MathExpressions")
@PropertyType("COMBINED")
public class ExprAbsoluteValue extends SimpleExpression<Number>{
	
	private Expression<Number> num;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		num = (Expression<Number>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "absolute [value] of %number%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{Math.abs((Double)num.getSingle(e))};
	}
}