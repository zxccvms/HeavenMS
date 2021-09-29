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
package client.command.commands.gm6;

import client.MapleBuffStat;
import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;

public class CheckDmgCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        MapleCharacter victim = c.getWorldServer().getPlayerStorage().getCharacterByName(params[0]);
        if (victim != null) {
            int maxBase = victim.calculateMaxBaseDamage(victim.getTotalWatk());
            Integer watkBuff = victim.getBuffedValue(MapleBuffStat.WATK);
            Integer matkBuff = victim.getBuffedValue(MapleBuffStat.MATK);
            Integer blessing = victim.getSkillLevel(10000000 * player.getJobType() + 12);
            if (watkBuff == null) watkBuff = 0;
            if (matkBuff == null) matkBuff = 0;

            player.dropMessage(5, "当前力量值: " + victim.getTotalStr() + "当前敏捷值: " + victim.getTotalDex() + "当前智力值: " + victim.getTotalInt() + "当前运气值: " + victim.getTotalLuk());
            player.dropMessage(5, "Cur WATK: " + victim.getTotalWatk() + " Cur MATK: " + victim.getTotalMagic());
            player.dropMessage(5, "Cur WATK Buff: " + watkBuff + " Cur MATK Buff: " + matkBuff + "当前祝福等级: " + blessing);
            player.dropMessage(5, victim.getName() + "的最大基础伤害（技能之前）是" + maxBase);
        } else {
            player.message("玩家'" + params[0] + "'在服务器中找不到.");
        }
    }
}
