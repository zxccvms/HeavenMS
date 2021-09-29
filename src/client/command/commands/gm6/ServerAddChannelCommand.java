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

import client.MapleCharacter;
import client.command.Command;
import client.MapleClient;
import net.server.Server;
import server.ThreadManager;

public class ServerAddChannelCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        final MapleCharacter player = c.getPlayer();
        
        if (params.length < 1) {
            player.dropMessage(5, "使用方法: @新增频道 <服务器ID>");
            return;
        }

        final int worldid = Integer.parseInt(params[0]);

        ThreadManager.getInstance().newTask(new Runnable() {
            @Override
            public void run() {
                int chid = Server.getInstance().addChannel(worldid);
                if(player.isLoggedinWorld()) {
                    if(chid >= 0) {
                        player.dropMessage(5, "新的频道" + chid + "已成功部署在" + worldid + ".");
                    } else {
                        if(chid == -3) {
                            player.dropMessage(5, "检测到无效的世界ID。通道创建已中止.");
                        } else if(chid == -2) {
                            player.dropMessage(5, "世界ID：" + worldid + "已达到频道限制，创建频道终止.");
                        } else if(chid == -1) {
                            player.dropMessage(5, "加载时检测到错误 'world.ini' 文件。频道创建中止。");
                        } else {
                            player.dropMessage(5, "无法部署新频道。检查所需的端口是否已在使用中或正在发生其他限制.");
                        }
                    }
                }
            }
        });
    }
}
