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
import config.YamlConfig;

public class ShowRatesCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        String showMsg = "#e经验倍率#n" + "\r\n";
        showMsg += "服务器经验倍率: #k" + c.getWorldServer().getExpRate() + "倍#k" + "\r\n";
        showMsg += "玩家经验倍率: #k" + player.getRawExpRate() + "倍#k" + "\r\n";
        if(player.getCouponExpRate() != 1) showMsg += "Coupon经验倍率: #k" + player.getCouponExpRate() + "倍#k" + "\r\n";
        showMsg += "经验倍率: #e#b" + player.getExpRate() + "x#k#n" + (player.hasNoviceExpRate() ? " - 新手倍率" : "") + "\r\n";

        showMsg += "\r\n" + "#e金币爆率#n" + "\r\n";
        showMsg += "服务器金爆倍率: #k" + c.getWorldServer().getMesoRate() + "倍#k" + "\r\n";
        showMsg += "玩家金币爆率: #k" + player.getRawMesoRate() + "倍#k" + "\r\n";
        if(player.getCouponMesoRate() != 1) showMsg += "Coupon金币爆率: #k" + player.getCouponMesoRate() + "倍#k" + "\r\n";
        showMsg += "金币爆率: #e#b" + player.getMesoRate() + "倍#k#n" + "\r\n";

        showMsg += "\r\n" + "#e物品爆率#n" + "\r\n";
        showMsg += "服务器物品爆率: #k" + c.getWorldServer().getDropRate() + "倍#k" + "\r\n";
        showMsg += "玩家物品爆率: #k" + player.getRawDropRate() + "倍#k" + "\r\n";
        if(player.getCouponDropRate() != 1) showMsg += "Coupon物品爆率: #k" + player.getCouponDropRate() + "倍#k" + "\r\n";
        showMsg += "物品爆率: #e#b" + player.getDropRate() + "倍#k#n" + "\r\n";
        
        showMsg += "\r\n" + "#eBOSS爆率#n" + "\r\n";
        showMsg += "服务器BOSS爆率: #k" + c.getWorldServer().getBossDropRate() + "倍#k" + "\r\n";
        showMsg += "玩家爆率: #k" + player.getRawDropRate() + "倍#k" + "\r\n";
        if(player.getCouponDropRate() != 1) showMsg += "Coupon爆率: #k" + player.getCouponDropRate() + "倍#k" + "\r\n";
        showMsg += "BOSS爆率: #e#b" + player.getBossDropRate() + "x#k#n" + "\r\n";

        if(YamlConfig.config.server.USE_QUEST_RATE) {
            showMsg += "\r\n" + "#e任务倍率#n" + "\r\n";
            showMsg += "服务器任务倍率: #e#b" + c.getWorldServer().getQuestRate() + "倍#k#n" + "\r\n";
        }
        
        showMsg += "\r\n";
        showMsg += "服务器TRAVEL倍率: #e#b" + c.getWorldServer().getTravelRate() + "倍#k#n" + "\r\n";

        player.showHint(showMsg, 300);
    }
}
