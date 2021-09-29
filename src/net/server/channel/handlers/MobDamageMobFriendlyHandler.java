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
package net.server.channel.handlers;

import net.AbstractMaplePacketHandler;
import scripting.event.EventInstanceManager;
import server.life.MapleMonster;
import server.maps.MapleMap;
import tools.MaplePacketCreator;
import tools.Randomizer;
import tools.data.input.SeekableLittleEndianAccessor;
import client.MapleClient;

/**
 *
 * @author Xotic (XoticStory) & BubblesDev
 */

public final class MobDamageMobFriendlyHandler extends AbstractMaplePacketHandler {
        @Override
	public final void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		int attacker = slea.readInt();
		slea.readInt();
		int damaged = slea.readInt();
                
                MapleMap map = c.getPlayer().getMap();
		MapleMonster monster = map.getMonsterByOid(damaged);

		if (monster == null || map.getMonsterByOid(attacker) == null) {
			return;
		}

		int damage = Randomizer.nextInt(((monster.getMaxHp() / 13 + monster.getPADamage() * 10)) * 2 + 500) / 10; // Formula planned by Beng.
                
                if (monster.getHp() - damage < 1) {     // friendly dies
                        if(monster.getId() == 9300102) {
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "守望者被外星人打伤了。祝你下次好运..."));
                        } else if (monster.getId() == 9300061) {  //moon bunny
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "月亮兔因为生病回家了."));
                        } else if(monster.getId() == 9300093) {   //tylus
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "泰勒斯被伏击的压倒性力量击倒了."));
                        } else if(monster.getId() == 9300137) {   //juliet
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "朱丽叶在战斗中晕倒了."));
                        } else if(monster.getId() == 9300138) {   //romeo
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "罗密欧在战斗中晕倒了."));
                        } else if(monster.getId() == 9400322 || monster.getId() == 9400327 || monster.getId() == 9400332) { //snowman
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "雪人在激烈的战斗中融化了."));
                        } else if(monster.getId() == 9300162) {   //delli
                                map.broadcastMessage(MaplePacketCreator.serverNotice(6, "德尔利在埋伏后消失了，床单还在地上..."));
                        }
                        
                        map.killFriendlies(monster);
                } else {
                        EventInstanceManager eim = map.getEventInstance();
                        if (eim != null) {
                                eim.friendlyDamaged(monster);
                        }
                }
                
                monster.applyAndGetHpDamage(damage, false);
                int remainingHp = monster.getHp();
                if(remainingHp <= 0) {
                    remainingHp = 0;
                    map.removeMapObject(monster);
                }

		map.broadcastMessage(MaplePacketCreator.MobDamageMobFriendly(monster, damage, remainingHp), monster.getPosition());
		c.announce(MaplePacketCreator.enableActions());
	}
}