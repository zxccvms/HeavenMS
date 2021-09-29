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
import server.MapleItemInformationProvider;
import server.life.MapleMonsterInformationProvider;
import tools.DatabaseConnection;
import tools.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

public class WhoDropsCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 1) {
            player.dropMessage(5, "请使用 @物品出处 <物品名称>");
            return;
        }
        
        if (c.tryacquireClient()) {
            try {
                String searchString = player.getLastCommandMessage();
                String output = "";
                Iterator<Pair<Integer, String>> listIterator = MapleItemInformationProvider.getInstance().getItemDataByName(searchString).iterator();
                if(listIterator.hasNext()) {
                    int count = 1;
                    while(listIterator.hasNext() && count <= 3) {
                        Pair<Integer, String> data = listIterator.next();
                        output += "#b" + data.getRight() + "#k以下都会出:\r\n";
                        try {
                            Connection con = DatabaseConnection.getConnection();
                            PreparedStatement ps = con.prepareStatement("SELECT dropperid FROM drop_data WHERE itemid = ? LIMIT 50");
                            ps.setInt(1, data.getLeft());
                            ResultSet rs = ps.executeQuery();
                            while(rs.next()) {
                                String resultName = MapleMonsterInformationProvider.getInstance().getMobNameFromId(rs.getInt("dropperid"));
                                if (resultName != null) {
                                    output += resultName + ", ";
                                }
                            }
                            rs.close();
                            ps.close();
                            con.close();
                        } catch (Exception e) {
                            player.dropMessage(6, "检索所需数据有问题。请再试一次.");
                            e.printStackTrace();
                            return;
                        }
                        output += "\r\n\r\n";
                        count++;
                    }
                } else {
                    player.dropMessage(5, "您所搜索的项目不存在.");
                    return;
                }
                
                c.getAbstractPlayerInteraction().npcTalk(9010000, output);
            } finally {
                c.releaseClient();
            }
        } else {
            player.dropMessage(5, "请稍等片刻，以便处理您的请求.");
        }
    }
}
