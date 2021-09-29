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
import net.server.channel.Channel;
import server.expeditions.MapleExpedition;

import java.util.List;
import java.util.Map.Entry;

public class ExpedsCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        for (Channel ch : Server.getInstance().getChannelsFromWorld(c.getWorld())) {
            List<MapleExpedition> expeds = ch.getExpeditions();
            if (expeds.isEmpty()) {
                player.yellowMessage("频道：" + ch.getId() + "没有远征队伍");
                continue;
            }
            player.yellowMessage("频道：" + ch.getId() + "有远征队伍");
            int id = 0;
            for (MapleExpedition exped : expeds) {
                id++;
                player.yellowMessage("> 远征队 " + id);
                player.yellowMessage(">> 分类: " + exped.getType().toString());
                player.yellowMessage(">> 状态: " + (exped.isRegistering() ? "登记" : "进行中"));
                player.yellowMessage(">> 成员数量: " + exped.getMembers().size());
                player.yellowMessage(">> 队长: " + exped.getLeader().getName());
                int memId = 2;
                for (Entry<Integer, String> e : exped.getMembers().entrySet()) {
                    if (exped.isLeader(e.getKey())) {
                        continue;
                    }
                    player.yellowMessage(">>> 成员 " + memId + ": " + e.getValue());
                    memId++;
                }
            }
        }
    }
}
