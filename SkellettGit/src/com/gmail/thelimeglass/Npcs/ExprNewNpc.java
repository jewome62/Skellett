package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

@Syntax({"[a] [new] (npc|citizen) [with] (name[d]|id|string) %string% [and] [with] [entity [type]] %skellettentitytype%", "[a] [new] npc [with] [entity [type]] %skellettentitytype% [and] [with] (name[d]|id|string) %string%"})
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType("SIMPLE")
public class ExprNewNpc extends SimpleExpression<NPC>{
	
	private Expression<String> name;
	private Expression<EntityType> type;
	@Override
	public Class<? extends NPC> getReturnType() {
		return NPC.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (matchedPattern == 0) {
			name = (Expression<String>) e[0];
			type = (Expression<EntityType>) e[1];
		} else {
			type = (Expression<EntityType>) e[0];
			name = (Expression<String>) e[1];
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[a] [new] npc [with] (name[d]|id|string) %string% [and] [with] [entity [type]] %entitytypes%";
	}
	@Override
	@Nullable
	protected NPC[] get(Event e) {
		NPCRegistry registry = CitizensAPI.getNPCRegistry();
		return new NPC[]{registry.createNPC(type.getSingle(e), name.getSingle(e))};
	}
}