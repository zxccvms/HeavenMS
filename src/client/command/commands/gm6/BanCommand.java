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

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import net.server.Server;
import server.TimerManager;
import tools.DatabaseConnection;
import tools.MaplePacketCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BanCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("使用方法: !封 <玩家昵称> <理由> (请描述一下理由)");
            return;
        }
        String ign = params[0];
        String reason = joinStringFrom(params, 1);
        MapleCharacter target = c.getChannelServer().getPlayerStorage().getCharacterByName(ign);
        if (target != null) {
            String readableTargetName = MapleCharacter.makeMapleReadable(target.getName());
            String ip = target.getClient().getSession().getRemoteAddress().toString().split(":")[0];
            //Ban ip
            PreparedStatement ps = null;
            try {
                Connection con = DatabaseConnection.getConnection();
                if (ip.matches("/[0-9]{1,3}\\..*")) {
                    ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?, ?)");
                    ps.setString(1, ip);
                    ps.setString(2, String.valueOf(target.getClient().getAccID()));

                    ps.executeUpdate();
                    ps.close();
                }

                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                c.getPlayer().message("封禁IP地址时出错");
                c.getPlayer().message(target.getName() + "'IP未被封禁: " + ip);
            }
            target.getClient().banMacs();
            reason = c.getPlayer().getName() + "被封禁" + readableTargetName + " for " + reason + " (IP: " + ip + ") " + "(MAC: " + c.getMacs() + ")";
            target.ban(reason);
            target.yellowMessage("#b" + c.getPlayer().getName() + "#k，你已经被封禁了 .");
            target.yellowMessage("理由: " + reason);
            c.announce(MaplePacketCreator.getGMEffect(4, (byte) 0));
            final MapleCharacter rip = target;
            TimerManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    rip.getClient().disconnect(false, false);
                }
            }, 5000); //5 Seconds
            Server.getInstance().broadcastMessage(c.getWorld(), MaplePacketCreator.serverNotice(6, "[封禁公告]: " + ign + "已经被封禁了."));
        } else if (MapleCharacter.ban(ign, reason, false)) {
            c.announce(MaplePacketCreator.getGMEffect(4, (byte) 0));
            Server.getInstance().broadcastMessage(c.getWorld(), MaplePacketCreator.serverNotice(6, "[封禁公告]: " + ign + "已经被封禁了."));
        } else {
            c.announce(MaplePacketCreator.getGMEffect(6, (byte) 1));
        }
    }
}
