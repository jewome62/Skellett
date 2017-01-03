package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(the|all)] [of] [the] methods (from|of|in) [the] [expression] %object%")
@Config("Main.ReturnType")
@FullConfig
@PropertyType("SIMPLE")
public class ExprReturnTypeMethods extends SimpleExpression<String>{
	
	private Expression<?> expression;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		expression = e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] methods (from|of|in) [the] [expression] %object%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		ArrayList<String> meths = new ArrayList<String>();
		for (Method m : expression.getReturnType().getMethods()) {
			if (m.getReturnType() != null && m.getReturnType() != void.class && m.getParameterCount() == 0) {
				meths.add(m.getName());
			}
		}
		return meths.toArray(new String[meths.size()]);
	}
}