package abelatox.minenohero;

import abelatox.minenohero.entities.bad.BadMob;
import abelatox.minenohero.entities.good.GoodMob;
import abelatox.minenohero.items.ImpureDNA;
import abelatox.minenohero.network.PacketDispatcher;
import abelatox.minenohero.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = "minenohero", name = "MineNoHero", version = "0.1")
public class MineNoHero {

    public static CreativeTabs minenohero;
    public static Item impureDNA;
    
    @SidedProxy(clientSide = "abelatox.minenohero.proxies.ClientProxy", serverSide = "abelatox.minenohero.proxies.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketDispatcher.registerPackets();
        minenohero = new CreativeTabs("minenohero") {
	        @Override
	        public Item getTabIconItem() {
	            return impureDNA;
	        }
        };
        impureDNA = new ImpureDNA();
        GameRegistry.register(impureDNA);
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
           // PacketDispatcher.sendTo(new AlignmentSync(event.getEntity().getCapability(Capabilities.ALIGNMENT, null)), (EntityPlayerMP) event.getEntity());
        }
    }

    @SubscribeEvent
    public void clone(PlayerEvent.Clone event) {
        System.out.println("Clone");
    }

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