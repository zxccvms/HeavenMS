package client.command.commands.gm0;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;

public class ReadPointsCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient client, String[] params) {

        MapleCharacter player = client.getPlayer();
        if (params.length > 2) {
            player.yellowMessage("使用方法: @积分 (rp|vp|all)");
            return;
        } else if (params.length == 0) {
            player.yellowMessage("RewardPoints: " + player.getRewardPoints() + " | "
                    + "VotePoints: " + player.getClient().getVotePoints());
            return;
        }

        switch (params[0]) {
            case "rp":
                player.yellowMessage("奖励积分: " + player.getRewardPoints());
                break;
            case "vp":
                player.yellowMessage("投票积分: " + player.getClient().getVotePoints());
                break;
            default:
                player.yellowMessage("奖励积分: " + player.getRewardPoints() + " | "
                        + "投票积分: " + player.getClient().getVotePoints());
                break;
        }
    }
}