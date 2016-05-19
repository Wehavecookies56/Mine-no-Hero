package abelatox.minenohero.network;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import abelatox.minenohero.Capabilities;
import abelatox.minenohero.MineNoHero;

public class AlignmentSync extends AbstractMessage.AbstractClientMessage<AlignmentSync> {

    public AlignmentSync() {}

    private String alignmentType;

    public AlignmentSync(Capabilities.IAlignment alignment) {
        this.alignmentType = alignment.getAlignment();
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
    	alignmentType = buffer.readStringFromBuffer(40);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(alignmentType);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        final Capabilities.IAlignment alignment = player.getCapability(Capabilities.ALIGNMENT, null);
        alignment.setAlignment(alignmentType);
        alignment.setHasAlignment(true);
    }
}
