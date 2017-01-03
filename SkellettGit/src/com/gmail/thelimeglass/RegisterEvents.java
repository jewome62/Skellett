package com.gmail.thelimeglass;

import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import com.gmail.thelimeglass.Expressions.ExprBlockXP;
import com.gmail.thelimeglass.Expressions.ExprBreedingBreeder;
import com.gmail.thelimeglass.Expressions.ExprBreedingEntity;
import com.gmail.thelimeglass.Expressions.ExprBreedingFather;
import com.gmail.thelimeglass.Expressions.ExprBreedingItem;
import com.gmail.thelimeglass.Expressions.ExprBreedingMother;
import com.gmail.thelimeglass.Expressions.ExprBreedingXP;
import com.gmail.thelimeglass.Expressions.ExprUnleashHitch;
import com.gmail.thelimeglass.Expressions.ExprUnleashReason;
import com.gmail.thelimeglass.Expressions.ExprWorldChangeFrom;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.ExprNewMaterial;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

public class RegisterEvents {
	
	public static void register(){
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.FireworkExplode")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerEvent("On firework explode", SimpleEvent.class, FireworkExplodeEvent.class, "[on] [skellett] firework explo(de|sion)");
				EventValues.registerEventValue(FireworkExplodeEvent.class, Entity.class, new Getter<Entity, FireworkExplodeEvent>() {
					@Override
					public Entity get(FireworkExplodeEvent e) {
						return e.getEntity();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ItemDespawn")) {
			Skript.registerEvent("[On] [skellett] item[ ][stack] (despawn|remove|delete):", SimpleEvent.class, ItemDespawnEvent.class, "[on] [skellett] item[ ][stack] (despawn|remove|delete)");
			EventValues.registerEventValue(ItemDespawnEvent.class, Entity.class, new Getter<Entity, ItemDespawnEvent>() {
				@Override
				public Entity get(ItemDespawnEvent e) {
					return e.getEntity();
				}
			}, 0);
			EventValues.registerEventValue(ItemDespawnEvent.class, Location.class, new Getter<Location, ItemDespawnEvent>() {
				@Override
				public Location get(ItemDespawnEvent e) {
					return e.getLocation();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ItemMerge")) {
			if (!Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8")) {
				Skript.registerEvent("[On] [skellett] item[ ][stack] (merge|combine[d]):", SimpleEvent.class, ItemMergeEvent.class, "[on] [skellett] item[ ][stack] (merge|combine[d])");
				EventValues.registerEventValue(ItemMergeEvent.class, Entity.class, new Getter<Entity, ItemMergeEvent>() {
					public Entity get(ItemMergeEvent e) {
						return e.getEntity();
					}
				}, 0);
				EventValues.registerEventValue(ItemMergeEvent.class, Item.class, new Getter<Item, ItemMergeEvent>() {
					@Override
					public Item get(ItemMergeEvent e) {
						return e.getTarget();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The item merge event is only for 1.9+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.MultiBlockPlace")) {
			Skript.registerEvent("[On] (multi[ple]|double)[ ][block][ ]place:", SimpleEvent.class, BlockMultiPlaceEvent.class, "[on] (multi[ple]|double)[ ][block][ ]place");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ArrowPickup")) {
			if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				Skript.registerEvent("[on] [skellett] arrow pickup:", SimpleEvent.class, PlayerPickupArrowEvent.class, "[on] [skellett] arrow pickup");
				EventValues.registerEventValue(PlayerPickupArrowEvent.class, Arrow.class, new Getter<Arrow, PlayerPickupArrowEvent>() {
					@Override
					public Arrow get(PlayerPickupArrowEvent e) {
						return e.getArrow();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The arrow pickup event is only for 1.8+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.OffhandSwitch")) {
			if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				Skript.registerEvent("[on] [skellett] off[ ]hand (switch|move):", SimpleEvent.class, PlayerSwapHandItemsEvent.class, "[on] [skellett] off[ ]hand (switch|move)");
				EventValues.registerEventValue(PlayerSwapHandItemsEvent.class, ItemStack.class, new Getter<ItemStack, PlayerSwapHandItemsEvent>() {
					@Override
					public ItemStack get(PlayerSwapHandItemsEvent e) {
						return e.getMainHandItem();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The offhand switch event is only for 1.9+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.CreativeInventoryClick")) {
			Skript.registerEvent("[on] creative inventory click:", SimpleEvent.class, InventoryCreativeEvent.class, "[on] creative inventory click");
			EventValues.registerEventValue(InventoryCreativeEvent.class, ItemStack.class, new Getter<ItemStack, InventoryCreativeEvent>() {
				@Override
				public ItemStack get(InventoryCreativeEvent e) {
					return e.getCursor();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityTeleport")) {
			Skript.registerEvent("[on] entity teleport:", SimpleEvent.class, EntityTeleportEvent.class, "[on] entity teleport");
			EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
				@Override
				public Location get(EntityTeleportEvent e) {
					return e.getTo();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityTeleport")) {
			Skript.registerEvent("[on] entity teleport:", SimpleEvent.class, EntityTeleportEvent.class, "[on] entity teleport");
			EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
				@Override
				public Location get(EntityTeleportEvent e) {
					return e.getTo();
				}
			}, 1);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.VehicleMove")) {
			Skript.registerEvent("[on] (vehicle|minecart|boat) move:", SimpleEvent.class, VehicleMoveEvent.class, "[on] (vehicle|minecart|boat) move");
			EventValues.registerEventValue(VehicleMoveEvent.class, Location.class, new Getter<Location, VehicleMoveEvent>() {
				@Override
				public Location get(VehicleMoveEvent e) {
					return e.getFrom();
				}
			}, -1);
			EventValues.registerEventValue(VehicleMoveEvent.class, Location.class, new Getter<Location, VehicleMoveEvent>() {
				@Override
				public Location get(VehicleMoveEvent e) {
					return e.getTo();
				}
			}, 1);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityBlockChange")) {
			Skript.registerEvent("[on] entity block (change|modify):", SimpleEvent.class, EntityChangeBlockEvent.class, "[on] entity block (change|modify)");
			Skript.registerExpression(ExprNewMaterial.class, Material.class, ExpressionType.SIMPLE, "[skellett] new [changed] material");
			EventValues.registerEventValue(EntityChangeBlockEvent.class, Block.class, new Getter<Block, EntityChangeBlockEvent>() {
				@Override
				public Block get(EntityChangeBlockEvent e) {
					return e.getBlock();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.HotbarSwitch")) {
			Skript.registerEvent("[on] (hotbar|held [(item|slot)]|inventory slot) (switch|change):", SimpleEvent.class, PlayerItemHeldEvent.class, "[on] (hotbar|held [(item|slot)]|inventory slot) (switch|change)");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Breeding")) {
			if (Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				Skript.registerEvent("[on] [skellett] bre[e]d[ing]:", SimpleEvent.class, EntityBreedEvent.class, "[on] [skellett] bre[e]d[ing]");
				Skript.registerExpression(ExprBreedingItem.class, ItemStack.class, ExpressionType.SIMPLE, "bre[e]d[ing] (item|material) [used]");
				Skript.registerExpression(ExprBreedingBreeder.class, LivingEntity.class, ExpressionType.SIMPLE, "breeder");
				Skript.registerExpression(ExprBreedingEntity.class, LivingEntity.class, ExpressionType.SIMPLE, "[final] bre[e]d[ed] entity");
				Skript.registerExpression(ExprBreedingXP.class, Number.class, ExpressionType.SIMPLE, "bre[e]d[ing] (xp|experience)");
				Skript.registerExpression(ExprBreedingFather.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] father");
				Skript.registerExpression(ExprBreedingMother.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] mother");
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The breeding event is only for 1.10+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.WorldChange")) {
			Skript.registerEvent("[on] [skellett] [player] world change:", SimpleEvent.class, PlayerChangedWorldEvent.class, "[on] [skellett] [player] world change");
			Skript.registerExpression(ExprWorldChangeFrom.class, World.class, ExpressionType.SIMPLE, "(previous|past) [changed] world");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.CropGrow")) {
			Skript.registerEvent("[on] [skellett] (block|crop) grow[ing]:", SimpleEvent.class, BlockGrowEvent.class, "[on] [skellett] (block|crop) grow[ing]");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.BlockExperienceDrop")) {
			Skript.registerEvent("[on] block [break] (xp|exp|experience) [drop]:", SimpleEvent.class, BlockExpEvent.class, "[on] block [break] (xp|exp|experience) [drop]");
			Skript.registerExpression(ExprBlockXP.class, Number.class,ExpressionType.SIMPLE, "[dropped] block[[']s] (xp|experience)");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityUnleash")) {
			Skript.registerEvent("[on] [entity] (un(leash|lead)|(leash|lead) break):", SimpleEvent.class, EntityUnleashEvent.class, "[on] [entity] (un(leash|lead)|(leash|lead) break)");
			EventValues.registerEventValue(EntityUnleashEvent.class, Block.class, new Getter<Block, EntityUnleashEvent>() {
				@Override
				public Block get(EntityUnleashEvent e) {
					if (((LivingEntity) e.getEntity()).isDead()) {
						return null;
					}
					return ((LivingEntity) e.getEntity()).getLeashHolder().getLocation().getBlock();
				}
			}, 0);
			EnumClassInfo.create(EntityUnleashEvent.UnleashReason.class, "unleashreason").register();
			Skript.registerExpression(ExprUnleashReason.class, EntityUnleashEvent.UnleashReason.class, ExpressionType.SIMPLE, "(un(leash|lead)|(leash|lead) break) reason");
			Skript.registerExpression(ExprUnleashHitch.class, Entity.class, ExpressionType.SIMPLE, "event-hitch");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Elytra")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9")) {
				Skript.registerEvent("[on] [entity] (elytra|glide) [toggle]:", SimpleEvent.class, EntityToggleGlideEvent.class, "[on] [entity] (elytra|glide) [toggle]");
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.BrewingFuel")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10") && !Bukkit.getServer().getVersion().contains("MC: 1.11)") && !Bukkit.getServer().getVersion().contains("MC: 1.11.1")) {
				Skript.registerEvent("[on] brew[ing] [stand] fuel [increase]:", SimpleEvent.class, BrewingStandFuelEvent.class, "[on] brew[ing] [stand] fuel [increase]");
				EventValues.registerEventValue(BrewingStandFuelEvent.class, Number.class, new Getter<Number, BrewingStandFuelEvent>() {
					@Override
					public Number get(BrewingStandFuelEvent e) {
						return e.getFuelPower();
					}
				}, 0);
				EventValues.registerEventValue(BrewingStandFuelEvent.class, ItemStack.class, new Getter<ItemStack, BrewingStandFuelEvent>() {
					@Override
					public ItemStack get(BrewingStandFuelEvent e) {
						return e.getFuel();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.AnvilPrepare")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerEvent("[on] ([item] anvil prepare|prepare [item] anvil):", SimpleEvent.class, PrepareAnvilEvent.class, "[on] ([item] anvil prepare|prepare [item] anvil)");
				Classes.registerClass(new ClassInfo<AnvilInventory>(AnvilInventory.class, "anvilinventory")
					.name("anvilinventory")
					.description("A getter for AnvilInventory")
					.parser(new Parser<AnvilInventory>() {
						@Override
						@Nullable
						public AnvilInventory parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(AnvilInventory e, int flags) {
							return e.toString();
						}
						@Override
						public String toVariableNameString(AnvilInventory b) {
							return b.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				EventValues.registerEventValue(PrepareAnvilEvent.class, ItemStack.class, new Getter<ItemStack, PrepareAnvilEvent>() {
					@Override
					public ItemStack get(PrepareAnvilEvent e) {
						return e.getResult();
					}
				}, 0);
				EventValues.registerEventValue(PrepareAnvilEvent.class, Number.class, new Getter<Number, PrepareAnvilEvent>() {
					@Override
					public Number get(PrepareAnvilEvent e) {
						return e.getInventory().getRepairCost();
					}
				}, 0);
				EventValues.registerEventValue(PrepareAnvilEvent.class, String.class, new Getter<String, PrepareAnvilEvent>() {
					@Override
					public String get(PrepareAnvilEvent e) {
						return e.getInventory().getRenameText();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Resurrect")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10")) {
				Skript.registerEvent("[on] [entity] (resurrect|revive):", SimpleEvent.class, EntityResurrectEvent.class, "[on] [entity] (resurrect|revive)");
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.SlimeSplit")) {
			Skript.registerEvent("[on] [skellett] slime split:", SimpleEvent.class, SlimeSplitEvent.class, "[on] [skellett] slime split");
		}
	}
}