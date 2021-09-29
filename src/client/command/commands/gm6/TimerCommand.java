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
   @Author: MedicOP - Add clock commands
*/
package client.command.commands.gm3;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;
import tools.MaplePacketCreator;

public class TimerCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("使用方法: !计时 <玩家昵称> <秒>|移除");
            return;
        }

        MapleCharacter victim = c.getWorldServer().getPlayerStorage().getCharacterByName(params[0]);
        if (victim != null) {
            if (params[1].equalsIgnoreCase("移除")) {
                victim.announce(MaplePacketCreator.removeClock());
            } else {
                try {
                    victim.announce(MaplePacketCreator.getClock(Integer.parseInt(params[1])));
                } catch (NumberFormatException e) {
                    player.yellowMessage("使用方法: !计时 <玩家昵称> <秒>|移除");
                }
            }
        } else {
            player.message("玩家 '" + params[0] + "'找不到.");
        }
    }
}
