/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

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

package server.events.gm;

import client.MapleCharacter;
import java.util.concurrent.ScheduledFuture;
import server.TimerManager;
import tools.MaplePacketCreator;

/**
 *
 * @author kevintjuh93
 */
public class MapleFitness {
       private MapleCharacter chr;
       private long time = 0;
       private long timeStarted = 0;
       private ScheduledFuture<?> schedule = null;
       private ScheduledFuture<?> schedulemsg = null;
       
       public MapleFitness(final MapleCharacter chr) {
           this.chr = chr;
           this.schedule = TimerManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
            if (chr.getMapId() >= 109040000 && chr.getMapId() <= 109040004)
                chr.changeMap(chr.getMap().getReturnMap());
            }
           }, 900000);
       }
       
       public void startFitness() {
           chr.getMap().startEvent();
           chr.getClient().announce(MaplePacketCreator.getClock(900));
           this.timeStarted = System.currentTimeMillis();
           this.time = 900000;  
           checkAndMessage();         

           chr.getMap().getPortal("join00").setPortalStatus(true);
           chr.getClient().announce(MaplePacketCreator.serverNotice(0, "现在门户已经打开。按入口处的向上箭头键进入."));
       }
       
        public boolean isTimerStarted() {
            return time > 0 && timeStarted > 0;
        }    
    
       public long getTime() {
           return time;
       }

       public void resetTimes() {
           this.time = 0;
           this.timeStarted = 0;
           schedule.cancel(false);
           schedulemsg.cancel(false);
       }

       public long getTimeLeft() {
           return time - (System.currentTimeMillis() - timeStarted);
       }

       public void checkAndMessage() {
           this.schedulemsg = TimerManager.getInstance().register(new Runnable() {
            @Override
            public void run() {
                if (chr.getFitness() == null) {
                    resetTimes();
                }
            if (chr.getMap().getId() >= 109040000 && chr.getMap().getId() <= 109040004) {
             if (getTimeLeft() > 9000 && getTimeLeft() < 11000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "你还有10秒的时间。没能赢的朋友们，希望你们下次能赢! 大家辛苦了！回头见!"));
             } else if (getTimeLeft() > 99000 && getTimeLeft() < 101000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "好了，你的时间不多了。请你快一点!"));
             } else if (getTimeLeft() > 239000 && getTimeLeft() < 241000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "第四阶段是【MapleStory体能测试】的最后一个阶段。请大家不要在最后一刻放弃，要努力去做。奖励就在最前面等着你。!"));
             } else if (getTimeLeft() > 299000 && getTimeLeft() < 301000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "第3阶段提供的陷阱，你可能会看到，但你无法踩到它们。请在上路的时候小心点。."));
             } else if (getTimeLeft() > 359000 && getTimeLeft() < 361000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "有重度滞后的人，请一定要慢慢移动，避免因为滞后而一路跌倒。."));
             } else if (getTimeLeft() > 499000 && getTimeLeft() < 501000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "请记住，如果你在活动中死亡，将被淘汰出局。如果你的HP快用完了，请先吃药水或恢复HP再继续前进。"));
             } else if (getTimeLeft() > 599000 && getTimeLeft() < 601000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "为了避免被猴子们扔出的香蕉，最重要的事情是 *时机 *时机就是一切!"));
             } else if (getTimeLeft() > 659000 && getTimeLeft() < 661000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "第2阶段提供猴子扔香蕉。请务必在适当的时间内避开它们。."));
             } else if (getTimeLeft() > 699000 && getTimeLeft() < 701000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "请记住，如果你在活动中死亡，将被淘汰出局。你还有很多时间，所以先吃药水或恢复HP再继续前进。."));
             } else if (getTimeLeft() > 779000 && getTimeLeft() < 781000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "凡是按时通过【MapleStory体能测试】的人，无论完成的先后顺序如何，都将获得一个项目，所以只要放松心情，慢慢来，通关4个阶段就可以了。."));
             } else if (getTimeLeft() > 839000 && getTimeLeft() < 841000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "可能会因为很多用户在1阶段的时候一下子出现严重的滞后。这并不难，所以请注意不要因为严重的滞后而倒下。."));
             } else if (getTimeLeft() > 869000 && getTimeLeft() < 871000) {
                 chr.getClient().announce(MaplePacketCreator.serverNotice(0, "[MapleStory体能测试]由4个阶段组成，如果你在游戏中碰巧死亡，就会被淘汰出局，所以请大家一定要注意。."));
             }
            } else {
             resetTimes();
            }
            }
           }, 5000, 29500);
       }
       // 14:30 [Notice][MapleStory Physical Fitness Test] consists of 4 stages, and if you happen to die during the game, you'll be eliminated from the game, so please be careful of that.
       // 14:00 [Notice]There may be a heavy lag due to many users at stage 1 all at once. It won't be difficult, so please make sure not to fall down because of heavy lag.
       // 13:00 [Notice]Everyone that clears [The Maple Physical Fitness Test] on time will be given an item, regardless of the order of finish, so just relax, take your time, and clear the 4 stages.
       // 11:40 [Notice]Please remember that if you die during the event, you'll be eliminated from the game. You still have plenty of time left, so either take a potion or recover HP first before moving on.
       // 11:00 [Notice]The 2nd stage offers monkeys throwing bananas. Please make sure to avoid them by moving along at just the right timing.
       // 10:00 [Notice]The most important thing you'll need to know to avoid the bananas thrown by the monkeys is *Timing* Timing is everything in this!
       // 8:20 [Notice]Please remember that if you die during the event, you'll be eliminated from the game. If you're running out of HP, either take a potion or recover HP first before moving on.
       // 6:00 [Notice]For those who have heavy lags, please make sure to move slowly to avoid falling all the way down because of lags.
       // 5:00 [Notice]The 3rd stage offers traps where you may see them, but you won't be able to step on them. Please be careful of them as you make your way up.
       // 4:00 [Notice]The 4th stage is the last one for [The Maple Physical Fitness Test]. Please don't give up at the last minute and try your best. The reward is waiting for you at the very top!
       // 1:40 [Notice]Alright, you don't have much time remaining. Please hurry up a little!
       // 0:10 [Notice]You have 10 sec left. Those of you unable to beat the game, we hope you beat it next time! Great job everyone!! See you later~
}
