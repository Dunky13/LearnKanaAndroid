package online.learnkana.android.mechanics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JAll {
    private List<JBlock> jBlockList;
    private int currentBlock;
    private String lastAchievement;
    public JAll()
    {
        this.jBlockList = new ArrayList<JBlock>();
        this.currentBlock = 0;
        this.lastAchievement = "CgkI-byp9IcCEAIQHA";
    }
    public JBlock getBlock(int i)
    {
        if(i < 0 || i >= this.jBlockList.size())
            return null;
        return this.jBlockList.get(i);
    }
    public JBlock getCurrentBlock()
    {
        return this.getBlock(this.currentBlock);
    }
    public void findLastActiveBlock()
    {
        while(this.getCurrentBlock().getScore() > 90)
        {
            this.currentBlock++;
        }
        this.calculateNewChances();
    }
    public void calculateNewChances()
    {
        if(this.isGameFinished())
        {
            for(int i = 0; i < this.size(); i++)
            {
                this.setChance(i, 1.0/this.size());
            }
            return;
        }
        double scoreCurrentBlock = this.getCurrentBlock().getScore();
        if(scoreCurrentBlock > 90)
        {
            this.findLastActiveBlock();
            return;
        }

        /**
         * SET HEAD TO:     #/2+0.5
         * SET 2nd TO:      round(0.1212*#+0.3333,0.25)
         * SET c0.5 TO:     round((T#-2)/2,1,1);
         * SET c0.25 TO:    round((T#-2)/2,1,-1)
         */
        int halfDelimiter = (int)Statics.round((this.currentBlock-2)/2,1,1);
        for(int i = this.currentBlock; i >= 0; i--)
        {
            double chance;
            if(i == this.currentBlock) //HEAD
            {
                chance = ((i+1)/2+0.5);
            }
            else if(i == this.currentBlock - 1) //2nd
            {
                chance = Statics.round(0.1212*(i+1)+0.3333,0.25);
            }
            else if(i <= this.currentBlock - 2 && i >= halfDelimiter)
            {
                chance = 0.5;
            }
            else
            {
                chance = 0.25;
            }
            this.setChance(i,chance/(this.currentBlock+1));
        }
    }

    private void setChance(int i, double v)
    {
        this.getBlock(i).setChance(v);
    }

    private int size()
    {
        return this.jBlockList.size();
    }
    public double getGameScore()
    {
        double score = 0;
        for(int i = 0; i < this.size(); i++)
        {
            score += this.getBlock(i).getScore();
        }
        return score / this.size();
    }
    public boolean isGameFinished()
    {
        if(this.currentBlock < this.size() - 1)
            return false;
        boolean score = true;
        for(int i = this.currentBlock; i >= 0; i--)
        {
            if(this.getBlock(i).getScore() < 100)
            {
                score = false;
                break;
            }
        }
        return score;
    }
    public void updateSiblings()
    {
        for(int i = 1; i < this.size(); i++)
        {
            JBlock jBlock = this.getBlock(i);
            jBlock.setPrev(this.getBlock(this.size() - 1));
            jBlock.getPrev().setNext(jBlock);
            jBlock.setParent(this);
        }
    }
    public void addJBlock(int i, JBlock jBlock)
    {
        if(this.size() > 0)
        {
            jBlock.setPrev(this.getBlock(this.size() - 1));
            jBlock.getPrev().setNext(jBlock);
        }
        this.jBlockList.add(i, jBlock);
        jBlock.setParent(this);
    }
    public void addJBlock(JBlock jBlock)
    {
        if(this.size() > 0)
        {
            jBlock.setPrev(this.getBlock(this.size() - 1));
            jBlock.getPrev().setNext(jBlock);
        }
        this.jBlockList.add(jBlock);
        jBlock.setParent(this);
    }
    private JChar getCharacter()
    {
        this.calculateNewChances();
        List<JChar> weighedList = new ArrayList<JChar>();
        for(int i = 0; i <= this.currentBlock; i++)
        {
            JBlock jBlock = this.getBlock(i);
            double jBlockChance = jBlock.getChance()*10.0;
            for(int j = 0; j < jBlock.size(); j++)
            {
                JChar jChar = jBlock.get(j);
                double jCharChance = jChar.getChance() * 100;
                for(int k = 0; k < jBlockChance*jCharChance; k++)
                {
                    weighedList.add(jChar);
                }
            }
        }
        return weighedList.get((int)Statics.rand(0, weighedList.size()));
    }
    public Q getQuestion()
    {
        JChar jChar = this.getCharacter();
        JBlock jCharParent = jChar.getParent();
        double test = Math.random();
        QA qa;
        if(test < 1.0/3.0) //romaji
        {
            qa = new QA(jChar.getRomaji(), jCharParent.getAnswers(JCharEnum.ROMAJI), JCharEnum.ROMAJI);
        }
        else if(test < 2.0/3.0) //hiragana
        {
            qa = new QA(jChar.getHiragana(), jCharParent.getAnswers(JCharEnum.HIRAGANA), JCharEnum.HIRAGANA);
        }
        else //katakana
        {
            qa = new QA(jChar.getKatakana(), jCharParent.getAnswers(JCharEnum.KATAKANA), JCharEnum.KATAKANA);
        }
        return new Q(qa, jChar);
    }
    private JSONObject buildJSON() throws JSONException {
        JSONObject saveObj = new JSONObject();
        for(int i = 0; i < this.size(); i++)
        {
            JBlock jBlock = this.getBlock(i);
            JSONObject blockData = new JSONObject();
            for(int j = 0; j < jBlock.size(); j++)
            {
                JChar jChar = jBlock.get(j);
                JSONObject chrData = new JSONObject();
                chrData.put("hScore", jChar.getHScore());
                chrData.put("kScore", jChar.getKScore());

                blockData.put(jChar.getRomaji(), chrData);
            }
            saveObj.put(jBlock.getBlockChar(), blockData);
        }
        return saveObj;
    }
    public String getJSON()
    {
        JSONObject finalString = new JSONObject();
        try {
            finalString.put("timestamp", System.currentTimeMillis());
            finalString.put("gameScore", this.getGameScore());
            finalString.put("data", this.buildJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalString.toString();
    }

    private void load(String internalJSONData) throws JSONException {
        JSONObject nullTimeObj = new JSONObject();
        nullTimeObj.put("timestamp", 0).put("gameScore", 0);
        String nullTimeJSON         = nullTimeObj.toString();
        JSONObject loadInternal     = new JSONObject(internalJSONData);

        JSONObject saveObj = loadInternal.getJSONObject("data");
        for(int i = 0; i < this.size(); i++)
        {
            JBlock jBlock = this.getBlock(i);
            if(saveObj.has(jBlock.getBlockChar()))
            {
                JSONObject saveObjBlock = saveObj.getJSONObject(jBlock.getBlockChar());
                for(int j = 0; j < jBlock.size(); j++)
                {
                    JChar jChar = jBlock.get(j);
                    if(saveObjBlock.has(jChar.getRomaji()))
                    {
                        JSONObject saveObjChar = saveObjBlock.getJSONObject(jChar.getRomaji());
                        jChar.setHScore(saveObjChar.getDouble("hScore"));
                        jChar.setKScore(saveObjChar.getDouble("kScore"));
                    }
                }
            }
        }
        this.findLastActiveBlock();
    }
    public void loadJSON(String jSONData)
    {
        try {
            this.load(jSONData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
