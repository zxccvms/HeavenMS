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

/*GM命令
   @Author: Arthur L - Refactored command content into modules
*/
package client.command;

import client.command.commands.gm0.*;
import client.command.commands.gm1.*;
import client.command.commands.gm2.*;
import client.command.commands.gm3.*;
import client.command.commands.gm4.*;
import client.command.commands.gm5.*;
import client.command.commands.gm6.*;

import client.MapleClient;

import tools.FilePrinter;
import tools.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class CommandsExecutor {
    
    public static CommandsExecutor instance = new CommandsExecutor();
    
    public static CommandsExecutor getInstance() {
        return instance;
    }
    
    private static final char USER_HEADING = '@';
    private static final char GM_HEADING = '!';
    
    public static boolean isCommand(MapleClient client, String content){
        char heading = content.charAt(0);
        if (client.getPlayer().isGM()){
            return heading == USER_HEADING || heading == GM_HEADING;
        }
        return heading == USER_HEADING;
    }

    private HashMap<String, Command> registeredCommands = new HashMap<>();
    private Pair<List<String>, List<String>> levelCommandsCursor;
    private List<Pair<List<String>, List<String>>> commandsNameDesc = new ArrayList<>();

    private CommandsExecutor(){
        registerLv0Commands();
        registerLv1Commands();
        registerLv2Commands();
        registerLv3Commands();
        registerLv4Commands();
        registerLv5Commands();
        registerLv6Commands();
    }

    public List<Pair<List<String>, List<String>>> getGmCommands() {
        return commandsNameDesc;
    }
    
    public void handle(MapleClient client, String message){
        if (client.tryacquireClient()) {
            try {
                handleInternal(client, message);
            } finally {
                client.releaseClient();
            }
        } else {
            client.getPlayer().dropMessage(5, "过一会儿再试一次..... 最新命令正在处理中.");
        }
    }
    
    private void handleInternal(MapleClient client, String message){
        if (client.getPlayer().getMapId() == 300000012) {
            client.getPlayer().yellowMessage("你在监狱里没有使用命令的权限.");
            return;
        }
        final String splitRegex = "[ ]";
        String[] splitedMessage = message.substring(1).split(splitRegex, 2);
        if (splitedMessage.length < 2) {
            splitedMessage = new String[]{splitedMessage[0], ""};
        }
        
        client.getPlayer().setLastCommandMessage(splitedMessage[1]);    // thanks Tochi & Nulliphite for noticing string messages being marshalled lowercase
        final String commandName = splitedMessage[0].toLowerCase();
        final String[] lowercaseParams = splitedMessage[1].toLowerCase().split(splitRegex);
        
        final Command command = registeredCommands.get(commandName);
        if (command == null){
            client.getPlayer().yellowMessage("命令 '" + commandName + "' 不可用。有关可用命令的列表，请参见@指令大全.");
            return;
        }
        if (client.getPlayer().gmLevel() < command.getRank()){
            client.getPlayer().yellowMessage("您没有使用此命令的权限.");
            return;
        }
        String[] params;
        if (lowercaseParams.length > 0 && !lowercaseParams[0].isEmpty()) {
            params = Arrays.copyOfRange(lowercaseParams, 0, lowercaseParams.length);
        } else {
            params = new String[]{};
        }
        
        command.execute(client, params);
        writeLog(client, message);
    }

    private void writeLog(MapleClient client, String command){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //yyyy-MM-dd HH:mm:ss
        FilePrinter.print(FilePrinter.USED_COMMANDS, client.getPlayer().getName() + " 使用命令: " + command + " 于 "
                + sdf.format(Calendar.getInstance().getTime()));
    }

    private void addCommandInfo(String name, Class<? extends Command> commandClass) {
        try {
            levelCommandsCursor.getRight().add(commandClass.newInstance().getDescription());
            levelCommandsCursor.getLeft().add(name);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addCommand(String[] syntaxs, Class<? extends Command> commandClass){
        for (String syntax : syntaxs){
            addCommand(syntax, 0, commandClass);
        }
    }
    private void addCommand(String syntax, Class<? extends Command> commandClass){
        //for (String syntax : syntaxs){
            addCommand(syntax, 0, commandClass);
        //}
    }

    private void addCommand(String[] surtaxes, int rank, Class<? extends Command> commandClass){
        for (String syntax : surtaxes){
            addCommand(syntax, rank, commandClass);
        }
    }

    private void addCommand(String syntax, int rank,  Class<? extends Command> commandClass){
        if (registeredCommands.containsKey(syntax.toLowerCase())){
            System.out.println("代码中存在语法错误问题，名称: " + syntax + ". 已存在.");
            return;
        }
        
        String commandName = syntax.toLowerCase();
        addCommandInfo(commandName, commandClass);
        
        try {
            Command commandInstance = commandClass.newInstance();     // thanks Halcyon for noticing commands getting reinstanced every call
            commandInstance.setRank(rank);
            
            registeredCommands.put(commandName, commandInstance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void registerLv0Commands(){
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());
        
        addCommand(new String[]{"帮助", "指令大全"}, HelpCommand.class);
        addCommand("爆啥", WhatDropsFromCommand.class);
        addCommand("物品出处", WhoDropsCommand.class);   
        addCommand("参加活动", JoinEventCommand.class);
        addCommand("排行榜", RanksCommand.class);
        addCommand("服务器运行时间", UptimeCommand.class);
 
        commandsNameDesc.add(levelCommandsCursor);
    }

    private void registerLv1Commands() {
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());
        
        commandsNameDesc.add(levelCommandsCursor);
    }
    
    private void registerLv2Commands(){
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());
        
        commandsNameDesc.add(levelCommandsCursor);
    }
    
    private void registerLv3Commands() {
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());

        commandsNameDesc.add(levelCommandsCursor);
    }
    
    private void registerLv4Commands(){
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());
        
        commandsNameDesc.add(levelCommandsCursor);
    }
    
    private void registerLv5Commands(){
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());
        
        commandsNameDesc.add(levelCommandsCursor);
    }
    
    private void registerLv6Commands(){
        levelCommandsCursor = new Pair<>((List<String>) new ArrayList<String>(), (List<String>) new ArrayList<String>());
        
        addCommand("go", 6, GotoCommand.class);
        addCommand("隐身", 6, HideCommand.class);
        addCommand("取消隐身", 6, UnHideCommand.class);
        addCommand("小力量", 6, BuffMeCommand.class);
        addCommand("大力量", 6, EmpowerMeCommand.class);
        addCommand("给所有人小buff", 6, BuffMapCommand.class);
        addCommand("当前地图信息", 6, WhereaMiCommand.class);
        addCommand("丢炸弹", 6, BombCommand.class); //在当前位置丢一个炸弹
        addCommand(new String[]{"召唤玩家", "召唤"}, 6, SummonCommand.class);
        addCommand(new String[]{"传送到", "到达", "跟踪"}, 6, ReachCommand.class);
        addCommand("飞", 6, WarpCommand.class);
        addCommand("GM商店", 6, GmShopCommand.class);
        addCommand("刷", 6, ItemCommand.class);
        addCommand("丢", 6, ItemDropCommand.class);
        addCommand("等级", 6, LevelCommand.class);
        addCommand("最高等级", 6, LevelProCommand.class);
        addCommand("职业", 6, JobCommand.class);
        addCommand("刷怪", 6, SpawnCommand.class);
        addCommand("公告", 6, NoticeCommand.class);
        addCommand("在线1", OnlineCommand.class);
        addCommand("在线2", 6, OnlineTwoCommand.class);
        addCommand("扎昆", 6, ZakumCommand.class);
        addCommand("黑龙", 6, HorntailCommand.class);
        addCommand("品克缤", 6, PinkbeanCommand.class);
        addCommand("闹钟", 6, PapCommand.class);
        addCommand("皮亚奴斯", 6, PianusCommand.class);
        addCommand("巨型蛋糕", 6, CakeCommand.class);
        addCommand("killall", 6, KillAllCommand.class);
        addCommand("重新加载事件", 6, ReloadEventsCommand.class);
        addCommand("重新加载掉落", 6, ReloadDropsCommand.class);
        addCommand("重新加载传送点", 6, ReloadPortalsCommand.class);
        addCommand("重新加载地图", 6, ReloadMapCommand.class);
        addCommand("重新加载商店", 6, ReloadShopsCommand.class);
        addCommand("开始任务", 6, QuestStartCommand.class);
        addCommand("完成任务", 6, QuestCompleteCommand.class);
        addCommand("重置任务", 6, QuestResetCommand.class);
        addCommand("经验倍率", 6, ExpRateCommand.class);
        addCommand("金币倍率", 6, MesoRateCommand.class);
        addCommand("掉落倍率", 6, DropRateCommand.class);
        addCommand("boss掉落倍率", 6, BossDropRateCommand.class);
        addCommand("任务倍率", 6, QuestRateCommand.class);
        addCommand("钓鱼倍率", 6, FishingRateCommand.class);
        addCommand("断开所有玩家", 6, DCAllCommand.class);
        addCommand("关闭服务器", 6, ShutdownCommand.class);
        addCommand("保存所有", 6, SaveAllCommand.class);
        addCommand("监狱", 6, JailCommand.class);
        addCommand("结束监狱生活", 6, UnJailCommand.class);
        /*以下这些还没整理的
        addCommand("地上物品数量", DropLimitCommand.class);
        addCommand("服务器时间", TimeCommand.class);
        addCommand("服务端历史", StaffCommand.class);//召唤了一个npc出来 好像是服务端历史
        addCommand("回购", BuyBackCommand.class);
        addCommand("抽奖", GachaCommand.class); //查询抽奖每个地区的物品
        addCommand("假死", DisposeCommand.class);
        addCommand("changel", ChangeLanguageCommand.class); //这个不知道是什么
        addCommand("装备等级",  EquipLvCommand.class);
        addCommand("服务器倍率", ShowRatesCommand.class);
        addCommand("玩家倍率", RatesCommand.class);
        addCommand("找GM", 6, GmCommand.class);
        addCommand("提交Bug", 6, ReportBugCommand.class);
        addCommand("积分", 6, ReadPointsCommand.class);
        addCommand("离开事件", 6, LeaveEventCommand.class);//传送到了某一个地图
         addCommand("力量", 6, StatStrCommand.class);
        addCommand("敏捷", 6, StatDexCommand.class);
        addCommand("智力", 6, StatIntCommand.class);
        addCommand("运气", 6, StatLukCommand.class); 
        addCommand("enableauth", 6, EnableAuthCommand.class);
        addCommand("toggleexp", 6, ToggleExpCommand.class);//不知道什么
        addCommand("领地", 6, MapOwnerClaimCommand.class);//不知道什么
        addCommand("BOSSHP", 6, BossHpCommand.class);
        addCommand("怪物HP", 6, MobHpCommand.class);
        addCommand("充值", 6, RechargeCommand.class);
        addCommand("sp", 6, SpCommand.class);
        addCommand("ap", 6, ApCommand.class);
        addCommand("buff", 6, BuffCommand.class);
        addCommand("dc", 6, DcCommand.class);
        addCommand("清理掉落物品", 6, ClearDropsCommand.class);
        addCommand("清空背包物品", 6, ClearSlotCommand.class);
        addCommand("清除保存的地点", 6, ClearSavedLocationsCommand.class);
        addCommand("治愈", 6, HealCommand.class);
        addCommand("设置栏位", 6, SetSlotCommand.class);
        addCommand("设置状态", 6, SetStatCommand.class);
        addCommand("最高状态", 6, MaxStatCommand.class);
        addCommand("满技能", 6, MaxSkillCommand.class);
        addCommand("重置技能", 6, ResetSkillCommand.class);
        addCommand("搜索", 6, SearchCommand.class);
        addCommand("unbug", 6, UnBugCommand.class);
        addCommand("id", 6, IdCommand.class);
        addCommand("抽奖列表", 6, GachaListCommand.class);
        addCommand("战利品", 6, LootCommand.class);
        addCommand("debuff", 6, DebuffCommand.class);
        addCommand("飞行模式", 6, FlyCommand.class);
        addCommand("静音模式", 6, MuteMapCommand.class);
        addCommand("查数据", 6, CheckDmgCommand.class);
        addCommand("当前地图", 6, InMapCommand.class);
        addCommand("hpmp", 6, HpMpCommand.class);
        addCommand("满hpmp", 6, MaxHpMpCommand.class);
        addCommand("音乐", 6, MusicCommand.class);
        addCommand("监视", 6, MonitorCommand.class);
        addCommand("监视中", 6, MonitorsCommand.class);
        addCommand("忽视", 6, IgnoreCommand.class);
        addCommand("忽视中", 6, IgnoredCommand.class);
        addCommand("位置", 6, PosCommand.class);
        addCommand("切换coupon", 6, ToggleCouponCommand.class);
        addCommand("切换聊天颜色", 6, ChatCommand.class);
        addCommand("人气", 6, FameCommand.class);
        addCommand("给点劵", 6, GiveNxCommand.class);
        addCommand("投票点", 6, GiveVpCommand.class);
        addCommand("给金币", 6, GiveMesosCommand.class);
        addCommand("给奖励积分", 6, GiveRpCommand.class);
        addCommand("远征队", 6, ExpedsCommand.class);
        addCommand("kill", 6, KillCommand.class);
        addCommand("seed", 6, SeedCommand.class);
        addCommand("maxenergy", 6, MaxEnergyCommand.class);//不知道最大是啥
        addCommand("rip", 6, RipCommand.class); //不知道是啥
        addCommand("openportal", 6, OpenPortalCommand.class); //不知道是啥入口 -开启
        addCommand("closeportal", 6, ClosePortalCommand.class); //不知道是啥入口 -关闭
        addCommand("pe", 6, PeCommand.class); //不知道是啥
        addCommand("开启活动", 6, StartEventCommand.class);
        addCommand("结束活动", 6, EndEventCommand.class);
        addCommand("启动地图事件", 6, StartMapEventCommand.class);
        addCommand("停止地图事件", 6, StopMapEventCommand.class);
        addCommand("封", 6, BanCommand.class);
        addCommand("解除封", 6, UnBanCommand.class);
        addCommand("治疗地图上的人", 6, HealMapCommand.class);
        addCommand("治疗指定的人", 6, HealPersonCommand.class);
        addCommand("hurt", 6, HurtCommand.class);
        addCommand("杀死地图上的人", 6, KillMapCommand.class);
        addCommand("晚上", 6, NightCommand.class);
        addCommand("npc", 6, NpcCommand.class);
        addCommand("脸", 6, FaceCommand.class);
        addCommand("头", 6, HairCommand.class);
        addCommand("计时", 6, TimerCommand.class);
        addCommand("地图计时", 6, TimerMapCommand.class);
        addCommand("对所有计时", 6, TimerAllCommand.class);
        addCommand("传送到地图", 6, WarpMapCommand.class);
        addCommand("传送城市", 6, WarpAreaCommand.class);
        addCommand("服务器消息", 6, ServerMessageCommand.class);
        addCommand("物品属性", 6, ProItemCommand.class);
        addCommand("seteqstat", 6, SetEqStatCommand.class);
        addCommand("travelrate", 6, TravelRateCommand.class);
        addCommand("itemvac", 6, ItemVacCommand.class);
        addCommand("forcevac", 6, ForceVacCommand.class);
        addCommand("玩家npc", 6, PlayerNpcCommand.class);
        addCommand("删除玩家npc", 6, PlayerNpcRemoveCommand.class);
        addCommand("设置npc", 6, PnpcCommand.class);
        addCommand("删除npc", 6, PnpcRemoveCommand.class);
        addCommand("pmob", 6, PmobCommand.class);
        addCommand("删除pmob", 6, PmobRemoveCommand.class);
        addCommand("debug", 6, DebugCommand.class);
        addCommand("设置", 6, SetCommand.class);
        addCommand("显示数据包", 6, ShowPacketsCommand.class);
        addCommand("showmovelife", 6, ShowMoveLifeCommand.class);
        addCommand("showsessions", 6, ShowSessionsCommand.class);
        addCommand("IP列表", 6, IpListCommand.class);
        addCommand("设置GM等级", 6, SetGmLevelCommand.class);
        addCommand("传送服务器", 6, WarpWorldCommand.class);
        addCommand("保存所有", 6, SaveAllCommand.class);
        addCommand("地图玩家", 6, MapPlayersCommand.class);
        addCommand("获取玩家账号", 6, GetAccCommand.class);
        addCommand("清除全部任务缓存", 6, ClearQuestCacheCommand.class);
        addCommand("清除指定任务缓存", 6, ClearQuestCommand.class);
        addCommand("supplyratecoupon", 6, SupplyRateCouponCommand.class);
        addCommand("生成所有NPC命令", 6, SpawnAllPNpcsCommand.class);
        addCommand("删除所有NPC命令", 6, EraseAllPNpcsCommand.class);
        addCommand("新增频道", 6, ServerAddChannelCommand.class);
        addCommand("新增服务器", 6, ServerAddWorldCommand.class);
        addCommand("删除频道", 6, ServerRemoveChannelCommand.class);
        addCommand("删除服务器", 6, ServerRemoveWorldCommand.class);
*/
        commandsNameDesc.add(levelCommandsCursor);
    }

}