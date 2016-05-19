package abelatox.minenohero.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

    public void init () {

    }

    public EntityPlayer getPlayerEntity (MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    public IThreadListener getThreadFromContext (MessageContext ctx) {
        return ctx.getServerHandler().playerEntity.getServer();
    }

}