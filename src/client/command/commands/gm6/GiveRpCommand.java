package client.command.commands.gm3;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;

public class GiveRpCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient client, String[] params) {
        MapleCharacter player = client.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("使用方法: !给奖励积分 <玩家昵称> <值>");
            return;
        }

        MapleCharacter victim = client.getWorldServer().getPlayerStorage().getCharacterByName(params[0]);
        if (victim != null) {
            victim.setRewardPoints(victim.getRewardPoints() + Integer.parseInt(params[1]));
            player.message("给予了奖励积分. 玩家" + params[0] + "现在拥有" + victim.getRewardPoints()
                    + "奖励积分." );
        } else {
            player.message("Player '" + params[0] + "' could not be found.");
        }
    }
}
