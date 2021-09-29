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
import net.server.world.MapleParty;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.MaplePacketCreator;
import client.MapleCharacter;
import client.MapleClient;
import net.server.world.World;

/**
 *
 * @author XoticStory
 * @author BubblesDev
 * @author Ronan
 */
public class PartySearchStartHandler extends AbstractMaplePacketHandler {
        @Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
            int min = slea.readInt();
            int max = slea.readInt();

            MapleCharacter chr = c.getPlayer();
            if (min > max) {
                chr.dropMessage(1, "最小值高于最大值！");
                c.announce(MaplePacketCreator.enableActions());
                return;
            }

            if (max - min > 30) {
                chr.dropMessage(1, "只能搜索30级范围内的成员。");
                c.announce(MaplePacketCreator.enableActions());
                return;
            }

            if (chr.getLevel() < min || chr.getLevel() > max) {
                chr.dropMessage(1, "搜索级别的范围必须包括您自己的级别.");
                c.announce(MaplePacketCreator.enableActions());
                return;
            }

            slea.readInt(); // members
            int jobs = slea.readInt();

            MapleParty party = c.getPlayer().getParty();
            if (party == null || !c.getPlayer().isPartyLeader()) return;

            World world = c.getWorldServer();
            world.getPartySearchCoordinator().registerPartyLeader(chr, min, max, jobs);
	}
}