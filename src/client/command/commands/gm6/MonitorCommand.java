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
import tools.MapleLogger;
import tools.MaplePacketCreator;

public class MonitorCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 1) {
            player.yellowMessage("使用方法: !监视 <ign>");
            return;
        }
        MapleCharacter victim = c.getWorldServer().getPlayerStorage().getCharacterByName(params[0]);
        if (victim == null) {
            player.message("玩家'" + params[0] + "'在服务器中找不到.");
            return;
        }
        boolean monitored = MapleLogger.monitored.contains(victim.getId());
        if (monitored) {
            MapleLogger.monitored.remove(victim.getId());
        } else {
            MapleLogger.monitored.add(victim.getId());
        }
        player.yellowMessage(victim.getId() + " is " + (!monitored ? "现在被监视." : "不在被监视."));
        String message = player.getName() + (!monitored ? "已开始监视" : "已停止监视") + victim.getId() + ".";
        Server.getInstance().broadcastGMMessage(c.getWorld(), MaplePacketCreator.serverNotice(5, message));

    }
}
