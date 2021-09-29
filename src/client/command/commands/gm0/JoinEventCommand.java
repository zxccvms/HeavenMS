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
package client.command.commands.gm0;

import client.MapleCharacter;
import client.command.Command;
import client.MapleClient;
import server.events.gm.MapleEvent;
import server.maps.FieldLimit;

public class JoinEventCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if(!FieldLimit.CANNOTMIGRATE.check(player.getMap().getFieldLimit())) {
            MapleEvent event = c.getChannelServer().getEvent();
            if(event != null) {
                if(event.getMapId() != player.getMapId()) {
                    if(event.getLimit() > 0) {
                        player.saveLocation("EVENT");

                        if(event.getMapId() == 109080000 || event.getMapId() == 109060001)
                            player.setTeam(event.getLimit() % 2);

                        event.minusLimit();

                        player.saveLocationOnWarp();
                        player.changeMap(event.getMapId());
                    } else {
                        player.dropMessage(5, "已达到该活动的玩家限制.");
                    }
                } else {
                    player.dropMessage(5, "你已经在活动中了。");
                }
            } else {
                player.dropMessage(5, "当前没有正在进行的活动.");
            }
        } else {
            player.dropMessage(5, "您当前处于无法加入活动的状态.");
        }
    }
}
