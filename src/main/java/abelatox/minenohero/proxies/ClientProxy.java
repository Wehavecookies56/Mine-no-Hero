package abelatox.minenohero.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
       // Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Kizunacraft.kiznaiverImplant, 0, new ModelResourceLocation("kizunacraft:" + MineNoHero.kiznaiverImplant.getUnlocalizedName().substring(5), "inventory"));
    }

    @Override
    public EntityPlayer getPlayerEntity (MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
    }

    @Override
    public IThreadListener getThreadFromContext (MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx));
    }
}