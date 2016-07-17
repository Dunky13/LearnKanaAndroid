package online.learnkana.android.learnkanaonline.mechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Marc on 7/14/2016.
 */
public class Statics {
    public static double rand(double min, double max)
    {
        return Math.floor(Math.random() * (max - min + 1))+ min;
    }
    public static double round(double value)
    {
        return Statics.round(value, 1.0);
    }
    public static double round(double value, double step)
    {
        return Statics.round(value, step, 0);
    }
    public static double round(double value, double step, int direction)
    {
        double inverseStep = 1.0 / step;
        double round = 0;
        if(direction == 1)
        {
            round = Math.ceil(value * inverseStep) / inverseStep;
        }
        else if(direction == -1)
        {
            round = Math.floor(value * inverseStep) / inverseStep;
        }
        else
        {
            round = Math.round(value * inverseStep) / inverseStep;
        }
        return round;
    }

    public static List<Double> getChanceList(List<JBlock> jBlockArray)
    {
        List<Double> tmpArray = new ArrayList<Double>(jBlockArray.size());
        for(int i = 0; i < jBlockArray.size(); i++)
        {
            tmpArray.add(jBlockArray.get(i).getChance());
        }
        return tmpArray;
    }

    public static List<String> shuffle(List<String> answers)
    {
        long seed = System.nanoTime();
        Collections.shuffle(answers, new Random(seed));
        return answers;
    }

    public static JAll loadCharacters()
    {
        JAll jAll = new JAll();

        JBlock vowelBlock = new JBlock("v",1);
        jAll.addJBlock(vowelBlock);
        vowelBlock.setAchievementID("CgkI-byp9IcCEAIQAQ");
        vowelBlock.addJChar(new JChar("a", "あ", "ア"));
        vowelBlock.addJChar(new JChar("i", "い", "イ"));
        vowelBlock.addJChar(new JChar("u", "う", "ウ"));
        vowelBlock.addJChar(new JChar("e", "え", "エ"));
        vowelBlock.addJChar(new JChar("o", "お", "オ"));

        JBlock kBlock = new JBlock("k",0);
        jAll.addJBlock(kBlock);
        kBlock.setAchievementID("CgkI-byp9IcCEAIQAg");
        kBlock.addJChar(new JChar("ka", "か", "カ"));
        kBlock.addJChar(new JChar("ki", "き", "キ"));
        kBlock.addJChar(new JChar("ku", "く", "ク"));
        kBlock.addJChar(new JChar("ke", "け", "ケ"));
        kBlock.addJChar(new JChar("ko", "こ", "コ"));

        JBlock sBlock = new JBlock("s",0);
        jAll.addJBlock(sBlock);
        sBlock.setAchievementID("CgkI-byp9IcCEAIQAw");
        sBlock.addJChar(new JChar("sa", "さ", "サ"));
        sBlock.addJChar(new JChar("shi", "し", "シ"));
        sBlock.addJChar(new JChar("su", "す", "ス"));
        sBlock.addJChar(new JChar("se", "せ", "セ"));
        sBlock.addJChar(new JChar("so", "そ", "ソ"));

        JBlock tBlock = new JBlock("t",0);
        jAll.addJBlock(tBlock);
        tBlock.setAchievementID("CgkI-byp9IcCEAIQBA");
        tBlock.addJChar(new JChar("ta", "た", "タ"));
        tBlock.addJChar(new JChar("chi", "ち", "チ"));
        tBlock.addJChar(new JChar("tsu", "つ", "ツ"));
        tBlock.addJChar(new JChar("te", "て", "テ"));
        tBlock.addJChar(new JChar("to", "と", "ト"));

        JBlock nBlock = new JBlock("n",0);
        jAll.addJBlock(nBlock);
        nBlock.setAchievementID("CgkI-byp9IcCEAIQBQ");
        nBlock.addJChar(new JChar("na", "な", "ナ"));
        nBlock.addJChar(new JChar("ni", "に", "ニ"));
        nBlock.addJChar(new JChar("nu", "ぬ", "ヌ"));
        nBlock.addJChar(new JChar("ne", "ね", "ネ"));
        nBlock.addJChar(new JChar("no", "の", "ノ"));

        JBlock hBlock = new JBlock("h",0);
        jAll.addJBlock(hBlock);
        hBlock.setAchievementID("CgkI-byp9IcCEAIQBg");
        hBlock.addJChar(new JChar("ha", "は", "ハ"));
        hBlock.addJChar(new JChar("hi", "ひ", "ヒ"));
        hBlock.addJChar(new JChar("fu", "ふ", "フ"));
        hBlock.addJChar(new JChar("he", "へ", "ヘ"));
        hBlock.addJChar(new JChar("ho", "ほ", "ホ"));

        JBlock mBlock = new JBlock("m",0);
        jAll.addJBlock(mBlock);
        mBlock.setAchievementID("CgkI-byp9IcCEAIQBw");
        mBlock.addJChar(new JChar("ma", "ま", "マ"));
        mBlock.addJChar(new JChar("mi", "み", "ミ"));
        mBlock.addJChar(new JChar("mu", "む", "ム"));
        mBlock.addJChar(new JChar("me", "め", "メ"));
        mBlock.addJChar(new JChar("mo", "も", "モ"));

        JBlock yBlock = new JBlock("y",0);
        jAll.addJBlock(yBlock);
        yBlock.setAchievementID("CgkI-byp9IcCEAIQCA");
        yBlock.addJChar(new JChar("ya", "や", "ヤ"));
        yBlock.addJChar(new JChar("yu", "ゆ", "ユ"));
        yBlock.addJChar(new JChar("yo", "よ", "ヨ"));

        JBlock rBlock = new JBlock("r",0);
        jAll.addJBlock(rBlock);
        rBlock.setAchievementID("CgkI-byp9IcCEAIQCQ");
        rBlock.addJChar(new JChar("ra", "ら", "ラ"));
        rBlock.addJChar(new JChar("ri", "り", "リ"));
        rBlock.addJChar(new JChar("ru", "る", "ル"));
        rBlock.addJChar(new JChar("re", "れ", "レ"));
        rBlock.addJChar(new JChar("ro", "ろ", "ロ"));

        JBlock wBlock = new JBlock("w",0);
        jAll.addJBlock(wBlock);
        wBlock.setAchievementID("CgkI-byp9IcCEAIQCg");
        wBlock.addJChar(new JChar("wa", "わ", "ワ"));
        wBlock.addJChar(new JChar("wo", "を", "ヲ"));
        wBlock.addJChar(new JChar("n", "ん", "ン"));

        JBlock gBlock = new JBlock("g",0);
        jAll.addJBlock(gBlock);
        gBlock.setAchievementID("CgkI-byp9IcCEAIQCw");
        gBlock.addJChar(new JChar("ga", "が", "ガ"));
        gBlock.addJChar(new JChar("gi", "ぎ", "ギ"));
        gBlock.addJChar(new JChar("gu", "ぐ", "グ"));
        gBlock.addJChar(new JChar("ge", "げ", "ゲ"));
        gBlock.addJChar(new JChar("go", "ご", "ゴ"));

        JBlock zBlock = new JBlock("z",0);
        jAll.addJBlock(zBlock);
        zBlock.setAchievementID("CgkI-byp9IcCEAIQDA");
        zBlock.addJChar(new JChar("za", "ざ", "ザ"));
        zBlock.addJChar(new JChar("ji (z)", "じ", "ジ"));
        zBlock.addJChar(new JChar("zu", "ず", "ズ"));
        zBlock.addJChar(new JChar("ze", "ぜ", "ゼ"));
        zBlock.addJChar(new JChar("zo", "ぞ", "ゾ"));

        JBlock dBlock = new JBlock("d",0);
        jAll.addJBlock(dBlock);
        dBlock.setAchievementID("CgkI-byp9IcCEAIQDQ");
        dBlock.addJChar(new JChar("da", "だ", "ダ"));
        dBlock.addJChar(new JChar("ji (d)", "ぢ", "ヂ"));
        dBlock.addJChar(new JChar("zu", "づ", "ヅ"));
        dBlock.addJChar(new JChar("de", "で", "デ"));
        dBlock.addJChar(new JChar("do", "ど", "ド"));

        JBlock bBlock = new JBlock("b",0);
        jAll.addJBlock(bBlock);
        bBlock.setAchievementID("CgkI-byp9IcCEAIQDw");
        bBlock.addJChar(new JChar("ba", "ば", "バ"));
        bBlock.addJChar(new JChar("bi", "び", "ビ"));
        bBlock.addJChar(new JChar("bu", "ぶ", "ブ"));
        bBlock.addJChar(new JChar("be", "べ", "ベ"));
        bBlock.addJChar(new JChar("bo", "ぼ", "ボ"));

        JBlock pBlock = new JBlock("p",0);
        jAll.addJBlock(pBlock);
        pBlock.setAchievementID("CgkI-byp9IcCEAIQDw");
        pBlock.addJChar(new JChar("pa", "ぱ", "パ"));
        pBlock.addJChar(new JChar("pi", "ぴ", "ピ"));
        pBlock.addJChar(new JChar("pu", "ぷ", "プ"));
        pBlock.addJChar(new JChar("pe", "ぺ", "ペ"));
        pBlock.addJChar(new JChar("po", "ぽ", "ポ"));
        pBlock.addJChar(new JChar("vu", "ゔ", "ゔ"));

        JBlock kyBlock = new JBlock("ky",0);
        jAll.addJBlock(kyBlock);
        kyBlock.setAchievementID("CgkI-byp9IcCEAIQEA");
        kyBlock.addJChar(new JChar("kya", "きゃ", "キャ"));
        kyBlock.addJChar(new JChar("kyu", "きゅ", "キュ"));
        kyBlock.addJChar(new JChar("kyo", "きょ", "キョ"));

        JBlock shBlock = new JBlock("sh",0);
        jAll.addJBlock(shBlock);
        shBlock.setAchievementID("CgkI-byp9IcCEAIQEQ");
        shBlock.addJChar(new JChar("sha", "しゃ", "シャ"));
        shBlock.addJChar(new JChar("shu", "しゅ", "シュ"));
        shBlock.addJChar(new JChar("sho", "しょ", "ショ"));

        JBlock chBlock = new JBlock("ch",0);
        jAll.addJBlock(chBlock);
        chBlock.setAchievementID("CgkI-byp9IcCEAIQEg");
        chBlock.addJChar(new JChar("cha", "ちゃ", "チャ"));
        chBlock.addJChar(new JChar("chu", "ちゅ", "チュ"));
        chBlock.addJChar(new JChar("cho", "ちょ", "チョ"));

        JBlock nyBlock = new JBlock("ny",0);
        jAll.addJBlock(nyBlock);
        nyBlock.setAchievementID("CgkI-byp9IcCEAIQEw");
        nyBlock.addJChar(new JChar("nya", "にゃ", "ニャ"));
        nyBlock.addJChar(new JChar("nyu", "にゅ", "ニュ"));
        nyBlock.addJChar(new JChar("nyo", "にょ", "ニョ"));

        JBlock hyBlock = new JBlock("hy",0);
        jAll.addJBlock(hyBlock);
        hyBlock.setAchievementID("CgkI-byp9IcCEAIQFA");
        hyBlock.addJChar(new JChar("hya", "ひゃ", "ヒャ"));
        hyBlock.addJChar(new JChar("hyu", "ひゅ", "ヒュ"));
        hyBlock.addJChar(new JChar("hyo", "ひょ", "ヒョ"));

        JBlock myBlock = new JBlock("my",0);
        jAll.addJBlock(myBlock);
        myBlock.setAchievementID("CgkI-byp9IcCEAIQFQ");
        myBlock.addJChar(new JChar("mya", "みゃ", "ミャ"));
        myBlock.addJChar(new JChar("myu", "みゅ", "ミュ"));
        myBlock.addJChar(new JChar("myo", "みょ", "ミョ"));

        JBlock ryBlock = new JBlock("ry",0);
        jAll.addJBlock(ryBlock);
        ryBlock.setAchievementID("CgkI-byp9IcCEAIQFg");
        ryBlock.addJChar(new JChar("rya", "りゃ", "リャ"));
        ryBlock.addJChar(new JChar("ryu", "りゅ", "リュ"));
        ryBlock.addJChar(new JChar("ryo", "りょ", "リョ"));

        JBlock gyBlock = new JBlock("gy",0);
        jAll.addJBlock(gyBlock);
        gyBlock.setAchievementID("CgkI-byp9IcCEAIQFw");
        gyBlock.addJChar(new JChar("gya", "ぎゃ", "ギャ"));
        gyBlock.addJChar(new JChar("gyu", "ぎゅ", "ギュ"));
        gyBlock.addJChar(new JChar("gyo", "ぎょ", "ギョ"));

        JBlock zjBlock = new JBlock("zj",0);
        jAll.addJBlock(zjBlock);
        zjBlock.setAchievementID("CgkI-byp9IcCEAIQGA");
        zjBlock.addJChar(new JChar("ja (z)", "じゃ", "ジャ"));
        zjBlock.addJChar(new JChar("ju (z)", "じゅ", "ジュ"));
        zjBlock.addJChar(new JChar("jo (z)", "じょ", "ジョ"));

        JBlock djBlock = new JBlock("dj",0);
        jAll.addJBlock(djBlock);
        djBlock.setAchievementID("CgkI-byp9IcCEAIQGQ");
        djBlock.addJChar(new JChar("ja (d)", "ぢゃ", "ヂャ"));
        djBlock.addJChar(new JChar("ju (d)", "ぢゅ", "ヂュ"));
        djBlock.addJChar(new JChar("jo (d)", "ぢょ", "ヂョ"));

        JBlock byBlock = new JBlock("by",0);
        jAll.addJBlock(byBlock);
        byBlock.setAchievementID("CgkI-byp9IcCEAIQGg");
        byBlock.addJChar(new JChar("bya", "びゃ", "ビャ"));
        byBlock.addJChar(new JChar("byu", "びゅ", "ビュ"));
        byBlock.addJChar(new JChar("byo", "びょ", "ビョ"));

        JBlock pyBlock = new JBlock("py",0);
        jAll.addJBlock(pyBlock);
        pyBlock.setAchievementID("CgkI-byp9IcCEAIQGw");
        pyBlock.addJChar(new JChar("pya", "ぴゃ", "ピャ"));
        pyBlock.addJChar(new JChar("pyu", "ぴゅ", "ピュ"));
        pyBlock.addJChar(new JChar("pyo", "ぴょ", "ピョ"));

        return jAll;
    }
}
