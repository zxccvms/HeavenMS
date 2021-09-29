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
import net.server.Server;
import tools.FilePrinter;
import tools.MaplePacketCreator;
import tools.Randomizer;

public class GmCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        String[] tips = {
                "请在紧急的情况下使用@gm或直接群里联系UP.",
                "要提交BUG错误或提出建议，请直接群里联系UP.",
                "请不要试图使用@gm查看管理是否在线!",
                "使用@gm时请不要问是否可以帮助你解决问题,请直接描述你遇到的问题！",
                "不要说'我有一个BUG要提交', 请直接把你需要提交的BUG描述出来!.",
        };
        MapleCharacter player = c.getPlayer();
        if (params.length < 1 || params[0].length() < 3) { // #goodbye 'hi'
            player.dropMessage(5, "你输入的内容太少了,请尽可能描述得详细一些.");
            return;
        }
        String message = player.getLastCommandMessage();
        Server.getInstance().broadcastGMMessage(c.getWorld(), MaplePacketCreator.sendYellowTip("[GM消息]:" + MapleCharacter.makeMapleReadable(player.getName()) + ": " + message));
        Server.getInstance().broadcastGMMessage(c.getWorld(), MaplePacketCreator.serverNotice(1, message));
        FilePrinter.printError(FilePrinter.COMMAND_GM, MapleCharacter.makeMapleReadable(player.getName()) + ": " + message);
        player.dropMessage(5, "你的消息 '" + message + "' 已经发送给管理员了.");
        player.dropMessage(5, tips[Randomizer.nextInt(tips.length)]);
    }
}
