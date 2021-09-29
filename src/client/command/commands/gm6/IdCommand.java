package client.command.commands.gm2;

import client.MapleCharacter;
import client.MapleClient;
import client.command.Command;
import tools.exceptions.IdTypeNotSupportedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import server.ThreadManager;

public class IdCommand extends Command {
    {
        setDescription("");
    }

    private final Map<String, String> handbookDirectory = new HashMap<>();
    private final Map<String, HashMap<String, String>> itemMap = new HashMap<>();

    public IdCommand() {
        handbookDirectory.put("map", "handbook/Map.txt");
        handbookDirectory.put("etc", "handbook/Etc.txt");
        handbookDirectory.put("npc", "handbook/NPC.txt");
        handbookDirectory.put("use", "handbook/Use.txt");
        handbookDirectory.put("weapon", "handbook/Equip/Weapon.txt"); // TODO add more into this
    }

    @Override
    public void execute(MapleClient client, final String[] params) {
        final MapleCharacter player = client.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("使用说明: !id <分类> <查询>");
            return;
        }
        final String queryItem = joinStringArr(Arrays.copyOfRange(params, 1, params.length), " ");
        player.yellowMessage("正在查询条目。。。可能需要一些时间。。。请尝试完善您的搜索.");
        Runnable queryRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    populateIdMap(params[0].toLowerCase());

                    Map<String, String> resultList = fetchResults(itemMap.get(params[0]), queryItem);
                    StringBuilder sb = new StringBuilder();
                    
                    if (resultList.size() > 0) {
                        int count = 0;
                        for (Map.Entry<String, String> entry: resultList.entrySet()) {
                            sb.append(String.format("%s的ID是: #b%s#k", entry.getKey(), entry.getValue()) + "\r\n");
                            if (++count > 100) {
                                break;
                            }
                        }
                        sb.append(String.format("找到结果: #r%d#k | 返回: #b%d#k/100 | 优化搜索查询以缩短时间.", resultList.size(), count) + "\r\n");
                        
                        player.getAbstractPlayerInteraction().npcTalk(9010000, sb.toString());
                    } else {
                        player.yellowMessage(String.format("找不到项目的Id: %s, 分类: %s.", queryItem, params[0]));
                    }
                } catch (IdTypeNotSupportedException e) {
                    player.yellowMessage("不支持您的查询类型.");
                } catch (IOException e) {
                    player.yellowMessage("读取文件时出错，请与管理员联系.");
                }
            }
        };
        
        ThreadManager.getInstance().newTask(queryRunnable);
    }

    private void populateIdMap(String type) throws IdTypeNotSupportedException, IOException {
        if (!handbookDirectory.containsKey(type)) {
            throw new IdTypeNotSupportedException();
        }
        itemMap.put(type, new HashMap<String, String>());
        BufferedReader reader = new BufferedReader(new FileReader(handbookDirectory.get(type)));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(" - ", 2);
            if (row.length == 2) {
                itemMap.get(type).put(row[1].toLowerCase(), row[0]);
            }
        }
    }

    private String joinStringArr(String[] arr, String separator) {
        if (null == arr || 0 == arr.length) return "";
        StringBuilder sb = new StringBuilder(256);
        sb.append(arr[0]);
        for (int i = 1; i < arr.length; i++) sb.append(separator).append(arr[i]);
        return sb.toString();
    }

    private Map<String, String> fetchResults(Map<String, String> queryMap, String queryItem) {
        Map<String, String> results = new HashMap<>();
        for (String item: queryMap.keySet()) {
            if (item.indexOf(queryItem) != -1) {
                results.put(item, queryMap.get(item));
            }
        }
        return results;
    }
}
