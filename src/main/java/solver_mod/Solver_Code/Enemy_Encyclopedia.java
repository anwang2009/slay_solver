package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashMap;
import java.util.Map;

// There's no analogous structure for enemies like the master deck for cards,
// so we'll just have to update this frequently
public class Enemy_Encyclopedia {
    // probably have to use reflection or something for AbstractMonster objects,
    // especially to get important intent info
    public static Map<String, AbstractMonster> dict = new HashMap<String, AbstractMonster>();
    static {
        update();
    }
    
    public static void update() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            dict.put(m.name, m);
        }
    }
}