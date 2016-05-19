package abelatox.minenohero.items;


import java.util.List;

import abelatox.minenohero.MineNoHero;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ImpureDNA extends Item{
	 public ImpureDNA() {
	        this.setUnlocalizedName("impureDNA");
	        this.setRegistryName("impureDNA");
	        this.setCreativeTab(MineNoHero.minenohero);
	        this.setMaxStackSize(64);
	    }
	 
	 @Override
	    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
	        tooltip.add("This DNA will be used for whatever it has to be");
	        super.addInformation(stack, playerIn, tooltip, advanced);
	    }
}
