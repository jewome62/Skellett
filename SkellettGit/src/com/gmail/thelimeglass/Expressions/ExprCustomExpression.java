package com.gmail.thelimeglass.Expressions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

//@Syntax("[(execute|parse[d])] [event] method %string% [with parameter[s] %-object%]")
@Syntax("[(execute|parse[d])] [event] method %string% [is [a] loop[able] %-boolean%] [(from|of) [(expression|type)] %-object%]")
@Config("Main.ReturnType")
@FullConfig
@PropertyType("SIMPLE")
public class ExprCustomExpression extends SimpleExpression<Object>{
	
	private Expression<String> string;
	private Expression<Boolean> loop;
	private Expression<?> expression;
	private Boolean loopable = true;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	@Override
	public boolean isSingle() {
		return loopable;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		string = (Expression<String>) e[0];
		loop = (Expression<Boolean>) e[1];
		expression = e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(execute|parse[d])] [event] method %string% [is [a] loop[able] %-boolean%] [(from|of) [(expression|type)] %-object%]";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		if (loop != null) {
			loopable = loop.getSingle(e);
		}
		Method m = null;
		m = null;
		Object involker = e;
		Class<?> clazz = e.getClass();
		if (expression != null) {
			clazz = expression.getReturnType();
			involker = expression.getSingle(e);
		}
		try {
			m = clazz.getDeclaredMethod(string.getSingle(e), null);
		} catch (NoSuchMethodException | SecurityException e1) {}
		if (m == null) {
			try {
				m = clazz.getMethod(string.getSingle(e), null);
			} catch (NoSuchMethodException | SecurityException e1) {}
		}
		if (m == null && expression == null) {
			try {
				m = clazz.getSuperclass().getDeclaredMethod(string.getSingle(e), null);
			} catch (NoSuchMethodException | SecurityException e1) {}
		}
		if (m != null) {
			try {
				return new Object[]{m.invoke(involker, null)};
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}