package abelatox.minenohero;

import abelatox.minenohero.network.AlignmentSync;
import abelatox.minenohero.network.PacketDispatcher;
import abelatox.minenohero.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    @SubscribeEvent
    public void death(LivingDropsEvent event) {
        if (!event.getEntity().worldObj.isRemote) {
            if (event.getEntity() instanceof EntityPlayer) {
                if (event.getEntity().getCapability(Kiznaivers.KIZNAIVERS, null).hasImplant()) {
                    event.getDrops().add(new EntityItem(event.getEntity().worldObj, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(kiznaiverImplant)));
                }
            }
        }
    }

    @SubscribeEvent
    public void clone(PlayerEvent.Clone event) {
        System.out.println("Clone");
        if (event.isWasDeath()) {
                EntityPlayer oldplayer = event.getOriginal().getServer().getPlayerList().getPlayerByUsername(event.getOriginal().getCapability(Capabilities.ALIGNMENT, null).getKiznaivers().get(i));
                if (kiznaiver != null) {
                    kiznaiver.addChatComponentMessage(new TextComponentString("You're no longer bound by " + event.getOriginal().getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().get(i) + "'s wounds"));
                    if (kiznaiver.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().contains(event.getOriginal().getDisplayNameString()))
                        kiznaiver.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().remove(kiznaiver.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().indexOf(event.getOriginal().getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().get(i)));
                }
            }
        }
    }

    @SubscribeEvent
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
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        DamageSource kiznaiverDamage = new KiznaiverDamage("kizunacraft.kiznaiverDamage", event.getSource().getSourceOfDamage());
        if (event.getSource().getDamageType() != kiznaiverDamage.getDamageType()) {
            if (!event.getEntity().worldObj.isRemote) {
                if (event.getEntity() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) event.getEntity();
                    PacketDispatcher.sendTo(new KiznaiverSync(player.getCapability(Kiznaivers.KIZNAIVERS, null)), (EntityPlayerMP) player);
                    if (!player.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().isEmpty()) {
                        int count = player.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().size();
                        for (int i = 0; i < player.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().size(); i++) {
                            EntityPlayer kiznaiver = player.getServer().getPlayerList().getPlayerByUsername(player.getCapability(Kiznaivers.KIZNAIVERS, null).getKiznaivers().get(i));
                            if (kiznaiver == null)
                                count -= 1;
                            else
                                kiznaiver.attackEntityFrom(kiznaiverDamage, event.getAmount() / (count + 1));
                        }
                        if (count > 0)
                            event.setAmount(event.getAmount() / (count + 1));
                    }
                }
            }
        }
    }
}