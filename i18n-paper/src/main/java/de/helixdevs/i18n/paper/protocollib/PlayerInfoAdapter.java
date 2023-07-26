package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoAdapter extends PacketAdapter implements ITranslationAdapter {
    public PlayerInfoAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.PLAYER_INFO);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        List<PlayerInfoData> playerInfoData = new ArrayList<>();

        //Using index 1 due to https://github.com/dmulloy2/ProtocolLib/issues/2321#issuecomment-1501833250
        event.getPacket().getPlayerInfoDataLists().read(1).forEach(data -> {
            playerInfoData.add(new PlayerInfoData(data.getProfile(), data.getLatency(),
                    data.getGameMode(), translate(event.getPlayer(), data.getDisplayName()))
            );
        });
        event.getPacket().getPlayerInfoDataLists().write(1, playerInfoData);
    }
}
