package net.server.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import client.MapleCharacter;
import net.server.Server;
import net.server.world.World;
import server.TimerManager;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.MonsterListener;
import tools.MaplePacketCreator;

/**
 * @information 怪物偷袭线程 跟随玩家随机出现蝙蝠魔,玩家等级越高 概率越低 时间为每小时的前十分钟,每一分钟出现一次
 */
public class MobSneakAttackTask implements Runnable {
	public static int monster = 8150000;
	private static MobSneakAttackTask instance;
	public static ScheduledFuture<?> activity = null;
	private static List<MapleMonster> spawnedMob = new ArrayList<MapleMonster>();

	public static MobSneakAttackTask getInstance() {
		return instance == null ? new MobSneakAttackTask() : instance;
	}

	@Override
	public void run() {
		for (World world : Server.getInstance().getWorlds()) {
			Server.getInstance().broadcastMessage(world.getId(),
					MaplePacketCreator.serverMessage("[冒险警报]魔族已经悄然接近冒险大陆，请各位冒险家呆在主城等安全区域"));
		}
		TimerManager tman = TimerManager.getInstance();
		activity = tman.register(new Runnable() {
			@Override
			public void run() {
				if (Calendar.getInstance().get(Calendar.MINUTE) >= 10) {
					for (World world : Server.getInstance().getWorlds()) {
						Server.getInstance().broadcastMessage(world.getId(),MaplePacketCreator.serverMessage("[冒险警报]魔族已经暂且离去，它们可能会再次卷土重来"));
					}
					for (MapleMonster mob : spawnedMob) {
						mob.getMap().removeMapObject(mob);
					}
					spawnedMob = new ArrayList<MapleMonster>();
					activity.cancel(true);
				} else {
					for (World world : Server.getInstance().getWorlds()) {
						for (MapleCharacter chr : world.getPlayerStorage().getAllCharacters()) {
							if (isTown(chr.getMapId()))
								continue;
							int ran = (int) (Math.random() * 100);
							int chance = 10;
							if (chr.getLevel() < 10) {
							} else if (chr.getLevel() < 30) {
								chance += 10;
							} else if (chr.getLevel() < 70) {
								chance += 20;
							} else if (chr.getLevel() < 120) {
								chance += 30;
							} else {
								chance += 40;
							}
							if (chr.getMapId() >= 100000000 && ran < chance) {
								final MapleMonster mob = MapleLifeFactory.getMonster(monster);
								mob.addListener(new MonsterListener() {

									@Override
									public void monsterKilled(int aniTime) {
										spawnedMob.remove(mob);
									}

									@Override
									public void monsterHealed(int trueHeal) {
									}

									@Override
									public void monsterDamaged(MapleCharacter from, int trueDmg) {
									}
								});
								chr.getMap().spawnMonsterOnGroundBelow(mob, chr.getPosition());
								spawnedMob.add(mob);
								Server.getInstance().broadcastMessage(world.getId(), MaplePacketCreator.serverMessage(
										"[怪物偷袭]  玩家" + chr.getName() + "在" + chr.getMap().getMapName() + "遭遇了怪物偷袭"));
								chr.dropMessage("你被怪物偷袭了");
							} else {
								chr.dropMessage("你感觉到一丝凉意,原来只是一阵风吹过");
							}
						}
					}
				}
			}
		}, 1 * 60 * 1000);
	}

	public static boolean isTown(int mapId) {
		switch (mapId) {
		// 彩虹村
		case 1000000:
			// 彩虹村武器店
		case 1000001:
			// 彩虹村村民家
		case 1000002:
			// 彩虹村杂货店
		case 1000003:
			// 南港
		case 2000000:
			// 射手村
		case 100000000:
			// 射手村民宅
		case 100000001:
			// 射手村集市
		case 100000100:
			// 射手村武器店
		case 100000101:
			// 射手村杂货店
		case 100000102:
			// 射手村整容院
		case 100000103:
			// 射手村美发店
		case 100000104:
			// 射手村护肤中心
		case 100000105:
			// 射手村公园
		case 100000200:
			// 宠物公园
		case 100000202:
			// 射手村游戏中心
		case 100000203:
			// 弓箭手的殿堂
		case 100000204:
			// 魔法密林
		case 101000000:
			// 魔法密林武器店
		case 101000001:
			// 魔法密林杂货店
		case 101000002:
			// 魔法密林图书馆
		case 101000003:
			// 魔法师的殿堂
		case 101000004:
			// 生命之林
		case 101000200:
			// 魔法密林码头
		case 101000300:
			// 候船室
		case 101000301:
			// 勇士部落
		case 102000000:
		case 102000001:
		case 102000002:
		case 102000003:
		case 102000004:
		case 103000000:
		case 103000001:
		case 103000002:
		case 103000003:
		case 103000004:
		case 103000005:
		case 103000006:
		case 103000008:
		case 103000100:
		case 104000000:
		case 104000001:
		case 104000002:
		case 104000003:
		case 104000004:
		case 105040400:
		case 105040401:
		case 105040402:
		case 105040300:
		case 106020000:
		case 140000000:
		case 140000001:
		case 140000010:
		case 140000011:
		case 140000012:
		case 140010110:
		case 200000000:
		case 200000001:
		case 200000002:
		case 200000100:
		case 200000110:
		case 200000111:
		case 200000112:
		case 200000120:
		case 200000121:
		case 200000122:
		case 200000130:
		case 200000131:
		case 200000132:
		case 200000140:
		case 200000141:
		case 200000150:
		case 200000151:
		case 200000152:
		case 200000160:
		case 200000161:
		case 200000200:
		case 200000201:
		case 200000202:
		case 200000203:
		case 200000300:
		case 200000301:
		case 209000000:
		case 211000001:
		case 209080000:
		case 209080100:
		case 211000000:
		case 211000100:
		case 211000101:
		case 211000102:
		case 220000000:
		case 220000001:
		case 220000002:
		case 220000003:
		case 220000004:
		case 220000005:
		case 220000006:
		case 220000100:
		case 220000110:
		case 220000111:
		case 220000300:
		case 220000301:
		case 220000302:
		case 220000303:
		case 220000304:
		case 220000305:
		case 220000306:
		case 220000307:
		case 220000400:
		case 220000500:
		case 221000000:
		case 221000001:
		case 221000100:
		case 221000200:
		case 221000300:
		case 222000000:
		case 222020000:
		case 230000000:
		case 230000001:
		case 230000002:
		case 230000003:
		case 240000000:
		case 240000001:
		case 240000002:
		case 240000003:
		case 240000004:
		case 240000005:
		case 240000006:
		case 240000100:
		case 240000110:
		case 240000111:
		case 250000000:
		case 250000001:
		case 250000002:
		case 250000003:
		case 250000100:
		case 251000000:
		case 260000000:
		case 260000100:
		case 260000110:
		case 260000200:
		case 260000201:
		case 260000202:
		case 260000203:
		case 260000204:
		case 260000205:
		case 260000206:
		case 260000207:
		case 260000300:
		case 260000301:
		case 260000302:
		case 260000303:
		case 261000000:
		case 261000001:
		case 261000002:
		case 261000010:
		case 261000011:
		case 261000020:
		case 261000021:
		case 270010000:
		case 270000000:
		case 300000000:
		case 300000001:
		case 300000002:
		case 300000010:
		case 300000011:
		case 300000012:
		case 500000000:
		case 540000000:
		case 541000000:
		case 550000000:
		case 551000000:
		case 600000000:
		case 600000001:
		case 701000000:
		case 700000000:
		case 700000100:
		case 700000101:
		case 700000200:
		case 701000100:
		case 701000200:
		case 701000201:
		case 701000202:
		case 701000203:
		case 701000210:
		case 702000000:
		case 702050000:
		case 702090102:
		case 741000200:
		case 741000201:
		case 741000202:
		case 741000203:
		case 741000204:
		case 741000205:
		case 741000206:
		case 741000207:
		case 741000208:
		case 800000000:
		case 801000000:
		case 801000001:
		case 801000002:
		case 801000100:
		case 801000110:
		case 801000200:
		case 801000210:
		case 801000300:
		case 810000000:
		case 910000000:
		case 910110000:
		case 930000700:
			return true;
		default:
			return false;
		}
	}
}
