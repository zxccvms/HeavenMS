/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2019 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
   @Author: Arthur L - Refactored command content into modules
*/
package client.command.commands.gm3;

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import net.server.Server;
import server.events.gm.MapleEvent;
import tools.MaplePacketCreator;

public class StartEventCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        int players = 50;
        if (params.length > 1)
            players = Integer.parseInt(params[0]);
        c.getChannelServer().setEvent(new MapleEvent(player.getMapId(), players));
        Server.getInstance().broadcastMessage(c.getWorld(), MaplePacketCreator.earnTitleMessage(
                "[活动] 活动已开启"
                        + player.getMap().getMapName()
                        + "并且欢迎"
                        + players
                        + "玩家参与，使用@活动命令加入活动"));
        Server.getInstance().broadcastMessage(c.getWorld(),
                MaplePacketCreator.serverNotice(6, "[活动] 活动已开始于 "
                        + player.getMap().getMapName()
                        + "并且欢迎"
                        + players
                        + "玩家参与，使用@活动命令加入活动."));
    }
}
