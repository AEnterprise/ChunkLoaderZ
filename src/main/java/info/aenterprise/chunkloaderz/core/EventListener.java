package info.aenterprise.chunkloaderz.core;

import info.aenterprise.chunkloaderz.items.ItemLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

/**
 * Created by AEnterprise
 */
public class EventListener {

	@SubscribeEvent
	public void livingDeath(LivingDropsEvent event) {
		if (event.entityLiving instanceof EntityGuardian) {
			EntityItem entity = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ItemLoader.guardianPearl));
			event.drops.add(entity);
		}
	}

	@SubscribeEvent
	public void detonate(ExplosionEvent.Detonate event) {
		Iterator<Entity> iterator = event.getAffectedEntities().iterator();
		while(iterator.hasNext()) {
			Entity entity = iterator.next();
			if (entity instanceof EntityItem) {
				ItemStack stack = ((EntityItem) entity).getEntityItem();
				if (stack.getItem() == Items.ender_pearl) {
					((EntityItem) entity).setEntityItemStack(new ItemStack(ItemLoader.enderPearlShard, stack.stackSize * 4));
					iterator.remove();
				} else if (stack.getItem() == ItemLoader.guardianPearl) {
					((EntityItem) entity).setEntityItemStack(new ItemStack(ItemLoader.guardianPearlShard, stack.stackSize * 4));
					iterator.remove();
				}
			}
		}
	}
}
