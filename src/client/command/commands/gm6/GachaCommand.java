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

import client.command.Command;
import client.MapleClient;
import server.MapleItemInformationProvider;
import server.gachapon.MapleGachapon;

public class GachaCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleGachapon.Gachapon gacha = null;
        String search = c.getPlayer().getLastCommandMessage();
        String gachaName = "";
        String [] names = {"射手村", "魔法密林", "勇士部落", "废弃都市", "林中之城", "蘑菇神殿", "桑拿房(男)", "桑拿房(女)", "新叶城", "诺特勒斯"};
        int [] ids = {9100100, 9100101, 9100102, 9100103, 9100104, 9100105, 9100106, 9100107, 9100109, 9100117};
        for (int i = 0; i < names.length; i++){
            if (search.equalsIgnoreCase(names[i])){
                gachaName = names[i];
                gacha = MapleGachapon.Gachapon.getByNpcId(ids[i]);
            }
        }
        if (gacha == null){
            c.getPlayer().yellowMessage("请使用@抽奖 <名称>，其中name对应于以下其中之一。");
            for (String name : names){
                c.getPlayer().yellowMessage(name);
            }
            return;
        }
        String talkStr = "#b" + gachaName + "#k快乐百宝箱包含以下项目.\r\n\r\n";
        for (int i = 0; i < 2; i++){
            for (int id : gacha.getItems(i)){
                talkStr += "-" + MapleItemInformationProvider.getInstance().getName(id) + "\r\n";
            }
        }
        talkStr += "\r\n请记住，有些物品是所有武器中的，这里没有列出。.";
        
        c.getAbstractPlayerInteraction().npcTalk(9010000, talkStr);
    }
}
