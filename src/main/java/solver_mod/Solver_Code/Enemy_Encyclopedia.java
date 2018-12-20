package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.ArrayList;

// There's no analogous structure for enemies like the master deck for cards,
// so we'll just have to update this frequently
public class Enemy_Encyclopedia {
    // probably have to use reflection or something for AbstractMonster objects,
    // especially to get important intent info

    public static List<AbstractMonster> enemies = new ArrayList<>();
    
    public static boolean contains(AbstractMonster monster) {
        for (AbstractMonster m : enemies) {
            if (monster.name.equals(m.name)) {
                return true;
            }
        }
        return false;
    }
}