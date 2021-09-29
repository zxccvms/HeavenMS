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

public class TimerMapCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 1) {
            player.yellowMessage("使用方法: !地图计时 <秒>|移除");
            return;
        }

        if (params[0].equalsIgnoreCase("移除")) {
            for (MapleCharacter victim : player.getMap().getCharacters()) {
                victim.announce(MaplePacketCreator.removeClock());
            }
        } else {
            try {
                int seconds = Integer.parseInt(params[0]);
                for (MapleCharacter victim : player.getMap().getCharacters()) {
                    victim.announce(MaplePacketCreator.getClock(seconds));
                }
            } catch (NumberFormatException e) {
                player.yellowMessage("使用方法: !地图计时 <秒>|移除");
            }
        }
    }
}
