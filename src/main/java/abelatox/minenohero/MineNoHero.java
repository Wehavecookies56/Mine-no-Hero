package abelatox.minenohero;

import abelatox.minenohero.entities.bad.BadMob;
import abelatox.minenohero.entities.good.GoodMob;
import abelatox.minenohero.network.AlignmentSync;
import abelatox.minenohero.network.PacketDispatcher;
import abelatox.minenohero.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "minenohero", name = "MineNoHero", version = "0.1")
public class MineNoHero {

    //public static Item kiznaiverImplant;
    public static CreativeTabs minenohero;

    @SidedProxy(clientSide = "abelatox.minenohero.proxies.ClientProxy", serverSide = "abelatox.minenohero.proxies.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketDispatcher.registerPackets();
       /* minenohero = new CreativeTabs("minenohero") {
	        @Override
	        public Item getTabIconItem() {
	            return kiznaiverImplant;
	        }
        };
        kiznaiverImplant = new KizunaImplant();
        GameRegistry.register(kiznaiverImplant);*/
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
       
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Capabilities());
        Capabilities.register();
        proxy.init();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
       // event.registerServerCommand(new DisplayKiznaivers());
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote && event.getEntity() instanceof EntityPlayer) {
        	EntityPlayer player = (EntityPlayer) event.getEntity();
        	if(!player.getCapability(Capabilities.ALIGNMENT, null).hasAlignment())
        	{
        	//TODO GUI
        	}
            PacketDispatcher.sendTo(new AlignmentSync(event.getEntity().getCapability(Capabilities.ALIGNMENT, null)), (EntityPlayerMP) event.getEntity());
        }
    }

   /* @SubscribeEvent
    public void death(LivingDropsEvent event) {
        if (!event.getEntity().worldObj.isRemote) {
            if (event.getEntity() instanceof EntityPlayer) {
                if (event.getEntity().getCapability(Capabilities.ALIGNMENT, null).hasImplant()) {
                    event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(kiznaiverImplant)));
                }
            }
        }
    }*/

    @SubscribeEvent
    public void clone(PlayerEvent.Clone event) {
        System.out.println("Clone");
    }

  /*  @SubscribeEvent
    public void interact(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand().equals(EnumHand.MAIN_HAND)) {
            if (!event.getWorld().isRemote) {
                System.out.println("Interacted");
                if (((EntityPlayer) event.getEntity()).getHeldItemMainhand() == null) {
                    if (event.getTarget() instanceof EntityPlayer) {
                        if (event.getEntity().getCapability(Kiznaivers.KIZNAIVERS, null).hasImplant() && event.getTarget().getCapability(Kiznaivers.KIZNAIVERS, null).hasImplant()) {
                            if (event.getEntity().getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().contains(((EntityPlayer) event.getTarget()).getDisplayNameString())) {
                                ((EntityPlayerMP) event.getEntity()).addChatComponentMessage(new TextComponentString("You are already bound by " + ((EntityPlayerMP) event.getTarget()).getDisplayNameString() + "'s wounds"));
                            } else {
                                event.getEntity().getCapability(Kiznaivers.KIZNAIVERS, null).bindKiznaiver(((EntityPlayer) event.getTarget()).getDisplayNameString());
                                event.getTarget().getCapability(Kiznaivers.KIZNAIVERS, null).bindKiznaiver(((EntityPlayer) event.getEntity()).getDisplayNameString());
                                PacketDispatcher.sendTo(new KiznaiverSync(event.getEntity().getCapability(Kiznaivers.KIZNAIVERS, null)), (EntityPlayerMP) event.getEntity());
                                PacketDispatcher.sendTo(new KiznaiverSync(event.getTarget().getCapability(Kiznaivers.KIZNAIVERS, null)), (EntityPlayerMP) event.getTarget());
                                ((EntityPlayerMP) event.getEntity()).addChatComponentMessage(new TextComponentString("You have been bound by " + ((EntityPlayerMP) event.getTarget()).getDisplayNameString() + "'s wounds"));
                                ((EntityPlayerMP) event.getTarget()).addChatComponentMessage(new TextComponentString("You have been bound by " + ((EntityPlayerMP) event.getEntity()).getDisplayNameString() + "'s wounds"));
                            }
                        } else if (event.getEntity().getCapability(Kiznaivers.KIZNAIVERS, null).hasImplant()) {
                            event.getEntity().addChatMessage(new TextComponentString("This person is not a Kiznaiver therefore, they cannot be bound by your wounds."));
                        }
                    }
                }
            }
        }
    }*/

    @SubscribeEvent
    public void onEntityDead(LivingDeathEvent event) {
        if(event.getEntity() instanceof GoodMob)
        {
        	// Add points
        }
        else if(event.getEntity() instanceof BadMob)
        {
        	//Substract points
        }
    }
}