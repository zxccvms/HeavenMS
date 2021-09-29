package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilePrinter {

    public static final String 
            AUTOBAN_WARNING = "游戏/自动警告日志.txt",    // log naming version by Vcoc
            AUTOBAN_DC = "游戏/AutoBanDC.txt",
            ACCOUNT_STUCK = "玩家/AccountStuck.txt",
            COMMAND_GM = "报告/Gm.txt",
            COMMAND_BUG = "报告/Bug.txt",
            LOG_TRADE = "交互/交易记录.txt",
            LOG_EXPEDITION = "交互/Expeditions.txt",
            LOG_LEAF = "交互/MapleLeaves.txt",
            LOG_GACHAPON = "交互/抽奖记录.txt",
            LOG_CHAT = "交互/聊天记录.txt",
            QUEST_RESTORE_ITEM = "游戏/QuestItemRestore.txt",
            EXCEPTION_CAUGHT = "游戏/异常捕捉.txt",
            CLIENT_START = "游戏/客户端启动错误.txt",
            MAPLE_MAP = "游戏/MapleMap.txt",
            ERROR38 = "游戏/38错误.txt",
            PACKET_LOG = "游戏/日志.txt",
            CASHITEM_BOUGHT = "交互/商城购买记录.txt",
            EXCEPTION = "游戏/异常日志.txt",
            LOGIN_EXCEPTION = "游戏/登录异常日志.txt",
            TRADE_EXCEPTION = "游戏/交易异常日志.txt",
            SQL_EXCEPTION = "游戏/数据库异常日志.txt",
            PACKET_HANDLER = "游戏/packethandler/",
            PORTAL = "游戏/portals/",
            PORTAL_STUCK = "游戏/portalblocks/",
            NPC = "游戏/npcs/",
            INVOCABLE = "游戏/调用/",
            REACTOR = "游戏/反应堆/",
            QUEST = "游戏/任务/",
            ITEM = "游戏/items/",
            MOB_MOVEMENT = "游戏/MobMovement.txt",
            MAP_SCRIPT = "游戏/mapscript/",
            DIRECTION = "游戏/directions/",
            GUILD_CHAR_ERROR = "家族/创建家族错误日志.txt",
            SAVE_CHAR = "玩家/SaveToDB.txt",
            INSERT_CHAR = "玩家/InsertCharacter.txt",
            LOAD_CHAR = "玩家/LoadCharFromDB.txt",
            CREATED_CHAR = "玩家/创建角色日志/",
            DELETED_CHAR = "玩家/删除角色日志/",
            UNHANDLED_EVENT = "游戏/DoesNotExist.txt",
            SESSION = "玩家/登录日志.txt",
            DCS = "游戏/断开日志/",
            EXPLOITS = "游戏/伤害日志/",
            STORAGE = "游戏/游戏仓库日志/",
            PACKET_LOGS = "游戏/packetlogs/",
            PACKET_STREAM = "游戏/packetstream/",
            FREDRICK = "游戏/npcs/fredrick/",
            NPC_UNCODED = "游戏/npcs/未指定的NPC.txt",
            QUEST_UNCODED = "游戏/任务/未指定的任务.txt",
            AUTOSAVING_CHARACTER = "玩家/自动保存字符记录.txt",
            SAVING_CHARACTER = "玩家/自动保存记录.txt",
            CHANGE_CHARACTER_NAME = "玩家/名称变更日志.txt",
            WORLD_TRANSFER = "玩家/WorldTransfer.txt",
            FAMILY_ERROR = "玩家/学院错误.txt",
            USED_COMMANDS = "命令/使用命令日志.txt",
            DEADLOCK_ERROR = "deadlocks/Deadlocks.txt",
            DEADLOCK_STACK = "deadlocks/Path.txt",
            DEADLOCK_LOCKS = "deadlocks/Locks.txt",
            DEADLOCK_STATE = "deadlocks/State.txt",
            DISPOSED_LOCKS = "deadlocks/Disposed.txt";
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //for file system purposes, it's nice to use yyyy-MM-dd
    private static final String FILE_PATH = "日志/" + sdf.format(Calendar.getInstance().getTime()) + "/"; // + sdf.format(Calendar.getInstance().getTime()) + "/"
    private static final String ERROR = "错误/";

    public static void printError(final String name, final Throwable t) {
        String stringT = getString(t);
        
    	System.out.println("Error thrown: " + name);
    	System.out.println(stringT);
        System.out.println();
        FileOutputStream out = null;
        final String file = FILE_PATH + ERROR + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write(stringT.getBytes());
            out.write("\r\n---------------------------------\r\n".getBytes());
            out.write("\r\n".getBytes()); // thanks Vcoc for suggesting review body log structure
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }

    public static void printError(final String name, final Throwable t, final String info) {
        String stringT = getString(t);
        
    	System.out.println("Error thrown: " + name);
    	System.out.println(stringT);
        System.out.println();
        FileOutputStream out = null;
        final String file = FILE_PATH + ERROR + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write((info + "\r\n").getBytes());
            out.write(stringT.getBytes());
            out.write("\r\n---------------------------------\r\n".getBytes());
            out.write("\r\n".getBytes());
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }

    public static void printError(final String name, final String s) {
    	System.out.println("Error thrown: " + name);
    	System.out.println(s);
        System.out.println();
        FileOutputStream out = null;
        final String file = FILE_PATH + ERROR + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write(s.getBytes());
            //out.write("\r\n---------------------------------\r\n".getBytes());
            out.write("\r\n".getBytes());
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }

    public static void print(final String name, final String s) {
        print(name, s, true);
    }

    public static void print(final String name, final String s, boolean line) {
    	System.out.println("Log: " + name);
    	System.out.println(s);
        System.out.println();
        FileOutputStream out = null;
        String file = FILE_PATH + name;
        try {
            File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write(s.getBytes());
            if (line) {
                out.write("\r\n---------------------------------\r\n".getBytes());
            }
            out.write("\r\n".getBytes());
        } catch (IOException ess) {
            ess.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
    }

    private static String getString(final Throwable e) {
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (sw != null) {
                    sw.close();
                }
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
        return retValue;
    }
}