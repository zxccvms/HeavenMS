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

/*
   @Author: Arthur L - Refactored command content into modules
*/
package client.command.commands.gm6;

import client.command.Command;
import client.MapleClient;
import client.MapleCharacter;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.inventory.manipulator.MapleInventoryManipulator;

public class ClearSlotCommand extends Command {
    {
        setDescription("");
    }

    @Override
    public void execute(MapleClient c, String[] params) {
        MapleCharacter player = c.getPlayer();
        if (params.length < 1) {
            player.yellowMessage("Syntax: !清空背包物品 <所有, 装备, 消耗, 设置, 其他，特殊.>");
            return;
        }
        String type = params[0];
        switch (type) {
            case "所有":
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, (byte) i, tempItem.getQuantity(), false, false);
                }
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.USE).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, (byte) i, tempItem.getQuantity(), false, false);
                }
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.ETC).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.ETC, (byte) i, tempItem.getQuantity(), false, false);
                }
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.SETUP).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.SETUP, (byte) i, tempItem.getQuantity(), false, false);
                }
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.CASH, (byte) i, tempItem.getQuantity(), false, false);
                }
                player.yellowMessage("所有栏已清空");
                break;
            case "装备":
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, (byte) i, tempItem.getQuantity(), false, false);
                }
                player.yellowMessage("装备栏已清空.");
                break;
            case "消耗":
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.USE).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, (byte) i, tempItem.getQuantity(), false, false);
                }
                player.yellowMessage("消耗栏已清空.");
                break;
            case "设置":
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.SETUP).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.SETUP, (byte) i, tempItem.getQuantity(), false, false);
                }
                player.yellowMessage("设置栏已清空.");
                break;
            case "其他":
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.ETC).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.ETC, (byte) i, tempItem.getQuantity(), false, false);
                }
                player.yellowMessage("其他栏已清空.");
                break;
            case "特殊":
                for (int i = 0; i < 101; i++) {
                    Item tempItem = c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte) i);
                    if (tempItem == null)
                        continue;
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.CASH, (byte) i, tempItem.getQuantity(), false, false);
                }
                player.yellowMessage("特殊栏已清空.");
                break;
            default:
                player.yellowMessage(type + "栏不存在！");
                break;
        }
    }
}
