package constants.string;

import client.MapleCharacter;

/**
 *
 * @author Drago (Dragohe4rt)
 */
public class LanguageConstants {
    
    enum Language {
        LANG_PRT(0),
        LANG_ESP(1),
        LANG_ENG(2);
        
        int lang;
        
        private Language(int lang) {
            this.lang = lang;
        }
        
        private int getValue() {
            return this.lang;
        }
        
    }
    
    public static String CPQBlue[] = new String[3];
    public static String CPQError[] = new String[3];
    public static String CPQEntry[] = new String[3];
    public static String CPQFindError[] = new String[3];
    public static String CPQRed[] = new String[3];
    public static String CPQPlayerExit[] = new String[3];
    public static String CPQEntryLobby[] = new String[3];
    public static String CPQPickRoom[] = new String[3];
    public static String CPQExtendTime[] = new String[3];
    public static String CPQLeaderNotFound[] = new String[3];
    public static String CPQChallengeRoomAnswer[] = new String[3];
    public static String CPQChallengeRoomSent[] = new String[3];
    public static String CPQChallengeRoomDenied[] = new String[3];
    
    static {
        int lang;
        
        lang = Language.LANG_PRT.getValue();
        LanguageConstants.CPQBlue[lang] = "冒险岛蓝队";
        LanguageConstants.CPQRed[lang] = "冒险岛红队";
        LanguageConstants.CPQExtendTime[lang] = "时间已经延长.";
        LanguageConstants.CPQPlayerExit[lang] = " 离开怪兽嘉年华.";
        LanguageConstants.CPQError[lang] = "发生了一个未知问题。请重新建立一个房间.";
        LanguageConstants.CPQLeaderNotFound[lang] = "找不到队长";
        LanguageConstants.CPQPickRoom[lang] = "报名参加怪物嘉年华!\r\n";            
        LanguageConstants.CPQChallengeRoomAnswer[lang] = "该队伍目前面临的挑战是.";
        LanguageConstants.CPQChallengeRoomSent[lang] = "已经向房间内的发了挑战书。请稍等一下.";
        LanguageConstants.CPQChallengeRoomDenied[lang] = "对面房间里的人取消了你的挑战.";
        LanguageConstants.CPQFindError[lang] = "在这个房间里找不到队伍!";
        LanguageConstants.CPQEntryLobby[lang] = "你现在会收到其他组的挑战。如果您在3分钟内不接受挑战，您将被淘汰。.";
        LanguageConstants.CPQEntry[lang] = "您可以选择 \"召唤怪物\",\"技能\",和\"召唤物\"使用Tab键和F1~F12键可以快速进入!";

        lang = Language.LANG_ESP.getValue();
        LanguageConstants.CPQBlue[lang] = "冒险岛蓝队";
        LanguageConstants.CPQRed[lang] = "冒险岛红队";
        LanguageConstants.CPQPlayerExit[lang] = " 离开怪兽嘉年华.";
        LanguageConstants.CPQExtendTime[lang] = "时间已经延长.";
        LanguageConstants.CPQLeaderNotFound[lang] = "找不到队长.";
        LanguageConstants.CPQError[lang] = "发生了一个未知问题。请重新建立一个房间.";
        LanguageConstants.CPQPickRoom[lang] = "报名参加怪物嘉年华!\r\n";
        LanguageConstants.CPQChallengeRoomAnswer[lang] = "该队伍目前面临的挑战是.";
        LanguageConstants.CPQChallengeRoomSent[lang] = "已经向房间内的发了挑战书。请稍等一下.";
        LanguageConstants.CPQChallengeRoomDenied[lang] = "对面房间里的人取消了你的挑战.";
        LanguageConstants.CPQFindError[lang] = "在这个房间里找不到队伍!";
        LanguageConstants.CPQEntryLobby[lang] = "你现在会收到其他组的挑战。如果您在3分钟内不接受挑战，您将被淘汰。.";
        LanguageConstants.CPQEntry[lang] = "您可以选择 \"召唤怪物\",\"技能\",和\"召唤物\"使用Tab键和F1~F12键可以快速进入!";
        
        lang = Language.LANG_ENG.getValue();
        LanguageConstants.CPQBlue[lang] = "冒险岛蓝队";
        LanguageConstants.CPQRed[lang] = "冒险岛红队";
        LanguageConstants.CPQPlayerExit[lang] = " 离开怪兽嘉年华.";
        LanguageConstants.CPQExtendTime[lang] = "时间已经延长.";
        LanguageConstants.CPQLeaderNotFound[lang] = "找不到队长.";
        LanguageConstants.CPQError[lang] = "发生了一个未知问题。请重新建立一个房间.";
        LanguageConstants.CPQPickRoom[lang] = "报名参加怪物嘉年华!\r\n";
        LanguageConstants.CPQChallengeRoomAnswer[lang] = "该队伍目前面临的挑战是.";
        LanguageConstants.CPQChallengeRoomSent[lang] = "已经向房间内的发了挑战书。请稍等一下.";
        LanguageConstants.CPQChallengeRoomDenied[lang] = "对面房间里的人取消了你的挑战.";
        LanguageConstants.CPQFindError[lang] = "在这个房间里找不到队伍!";
        LanguageConstants.CPQEntryLobby[lang] = "你现在会收到其他组的挑战。如果您在3分钟内不接受挑战，您将被淘汰。.";
        LanguageConstants.CPQEntry[lang] = "您可以选择 \"召唤怪物\",\"技能\",和\"召唤物\"使用Tab键和F1~F12键可以快速进入!";
        
    }

    public static String getMessage(MapleCharacter chr, String[] message) {
        return message[chr.getLanguage()];
    }
}
