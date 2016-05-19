package abelatox.minenohero;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class Capabilities {

	 @CapabilityInject(IAlignment.class)
	    public static final Capability<IAlignment> ALIGNMENT = null;

	    public static void register() {
	        CapabilityManager.INSTANCE.register(IAlignment.class, new Capabilities.Storage(), Capabilities.Default.class);
	    }

	    public interface IAlignment {
	        boolean hasAlignment();
	        String getAlignment();

	        void setAlignment(String type);
	        void setHasAlignment(boolean alignment);
	    }

	    @SubscribeEvent
	    public void onEntityConstructing(AttachCapabilitiesEvent.Entity event) {
	        event.addCapability(new ResourceLocation("minenohero", "IAlignment"), new ICapabilitySerializable<NBTTagCompound>() {
	            IAlignment inst = ALIGNMENT.getDefaultInstance();

	            @Override
	            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
	                return capability == ALIGNMENT;
	            }

	            @Override
	            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	                return capability == ALIGNMENT ? (T) inst : null;
	            }

	            @Override
	            public NBTTagCompound serializeNBT() {
	                return (NBTTagCompound) ALIGNMENT.getStorage().writeNBT(ALIGNMENT, inst, null);
	            }

	            @Override
	            public void deserializeNBT(NBTTagCompound nbt) {
	            	ALIGNMENT.getStorage().readNBT(ALIGNMENT, inst, null, nbt);
	            }
	        });
	    }

	    public static class Storage implements Capability.IStorage<IAlignment> {

	        @Override
	        public NBTBase writeNBT(Capability<IAlignment> capability, IAlignment instance, EnumFacing side) {
	            NBTTagCompound properties = new NBTTagCompound();

	            properties.setBoolean("HasAlignment", instance.hasAlignment());
	            properties.setString("AlignmentType", instance.getAlignment());

	            return properties;
	        }

	        @Override
	        public void readNBT(Capability<IAlignment> capability, IAlignment instance, EnumFacing side, NBTBase nbt) {
	            NBTTagCompound properties = (NBTTagCompound) nbt;
	            instance.setHasAlignment(properties.getBoolean("HasAlignment")); 
	            instance.setAlignment(properties.getString("AlignmentType"));
	        }
	    }


	    public static class Default implements IAlignment{
	        private boolean hasAlignment = false;
	        private String AlignmentType = null;

			@Override
			public boolean hasAlignment() {
				return this.hasAlignment;
			}

			@Override
			public String getAlignment() {
				return this.AlignmentType;
			}

			@Override
			public void setAlignment(String type) {
				this.AlignmentType = type; 
			}

			@Override
			public void setHasAlignment(boolean alignment) {
				this.hasAlignment = alignment;
			}
	    }
}
