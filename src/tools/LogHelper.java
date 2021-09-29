package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import client.MapleClient;
import net.server.Server;
import server.MapleItemInformationProvider;
import server.MapleTrade;
import server.expeditions.MapleExpedition;
import client.MapleCharacter;
import client.inventory.Item;

public class LogHelper {

	public static void logTrade(MapleTrade trade1, MapleTrade trade2) {
		String name1 = trade1.getChr().getName();
		String name2 = trade2.getChr().getName();
		String log = name1 + " 和 " + name2 + "进行交易" + "\r\n";
		//Trade 1 to trade 2
		//log += trade1.getExchangeMesos() + "金币" + name1 + " 交易给 " + name2 + " \r\n";
                log += "[" + name2 + "]交易给[" + name1 + "] " + trade1.getExchangeMesos() + "金币" + " \r\n";
		for (Item item : trade2.getItems()){
			String itemName = MapleItemInformationProvider.getInstance().getName(item.getItemId()) + "(" + item.getItemId() + ")";
			//log += item.getQuantity() + "个 " + itemName + " " + name2 + " 交易给 " + name1 + " \r\n";;
                        log += "[" + name2 + "]交易给[" + name1 + "] " + item.getQuantity() + "个 " + itemName + " \r\n";;
		}
                
                log += "\r\n";

		//Trade 2 to trade 1
		//log += trade2.getExchangeMesos() + " 金币 " + name2 + " 交易给 " + name1 + " \r\n";
                log += "[" + name1 + "]交易给[" + name2 + "] " + trade2.getExchangeMesos() + "金币" + " \r\n";
		for (Item item : trade1.getItems()){
			String itemName = MapleItemInformationProvider.getInstance().getName(item.getItemId()) + "(" + item.getItemId() + ")";
			//log += item.getQuantity() + "个 " + itemName + " "  + name1 + " 交易给 " + name2 + " \r\n";;
                        log += "[" + name1 + "]交易给[" + name2 + "] " + item.getQuantity() + "个 " + itemName + " \r\n";;
		}
		log += "\r\n\r\n";
		FilePrinter.print(FilePrinter.LOG_TRADE, log);
	}

	public static void logExpedition(MapleExpedition expedition) {
		Server.getInstance().broadcastGMMessage(expedition.getLeader().getWorld(), MaplePacketCreator.serverNotice(6, expedition.getType().toString() + " Expedition with leader " + expedition.getLeader().getName() + " finished after " + getTimeString(expedition.getStartTime())));

		String log = expedition.getType().toString() + " 远征队\r\n";
		log += getTimeString(expedition.getStartTime()) + "\r\n";

		for (String memberName : expedition.getMembers().values()){
			log += ">>" + memberName + "\r\n";
		}
		log += "杀死BOSS的时间记录\r\n";
		for (String message: expedition.getBossLogs()){
			log += message;
		}
		log += "\r\n";
		FilePrinter.print(FilePrinter.LOG_EXPEDITION, log);
	}
	
	public static String getTimeString(long then){
		long duration = System.currentTimeMillis() - then;
		int seconds = (int) (duration / 1000) % 60 ;
		int minutes = (int) ((duration / (1000*60)) % 60);
		return "共耗时" + minutes + "分" + seconds + "秒";
	}

	public static void logLeaf(MapleCharacter player, boolean gotPrize, String operation) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String log = player.getName() + (gotPrize ? " used a maple leaf to buy " + operation : " redeemed " + operation + " VP for a leaf") + " - " + timeStamp;
		FilePrinter.print(FilePrinter.LOG_LEAF, log);
	}
	
	public static void logGacha(MapleCharacter player, int itemid, String map) {
		String itemName = MapleItemInformationProvider.getInstance().getName(itemid);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String log = player.getName() + " 从【" + map + "快乐百宝箱中】获得【" + itemName + "(" + itemid + ")】" + "时间:" + timeStamp;
		FilePrinter.print(FilePrinter.LOG_GACHAPON, log);
	}

	public static void logChat(MapleClient player, String chatType, String text){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		FilePrinter.print(FilePrinter.LOG_CHAT, "[" + sdf.format(Calendar.getInstance().getTime()) + "] (" + chatType + ") " +player.getPlayer().getName() + ": " + text);
	}

}
