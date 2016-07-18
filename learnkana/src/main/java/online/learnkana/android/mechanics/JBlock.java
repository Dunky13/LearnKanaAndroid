package online.learnkana.android.mechanics;

import java.util.ArrayList;
import java.util.List;

public class JBlock {
    private List<JChar> jCharList;
    private JAll parent;
    private JBlock prev, next;
    private String blockIdentifier;
    private double minChance;
    private double chance;
    private String achievementId;
    public JBlock(String blockIdentifier, double chance)
    {
        this.jCharList = new ArrayList<JChar>();
        this.blockIdentifier = blockIdentifier;
        this.minChance = 0;
        this.chance = chance < this.minChance ? this.minChance : chance;
    }
    public void addJChar(JChar jChar)
    {
        this.jCharList.add(jChar);
        jChar.setParent(this);
    }
    public double getChance()
    {
        return this.chance;
    }
    public void setChance(double c)
    {
        this.chance = chance < this.minChance ? this.minChance : chance;
    }
    public int size()
    {
        return this.jCharList.size();
    }
    public JChar get(int i){return this.getChar(i);}
    public JChar getChar(int i)
    {
        if(i < 0 || i >= this.jCharList.size())
        {
            return null;
        }
        return this.jCharList.get(i);
    }
    public List<JChar> getChars()
    {
        return this.jCharList;
    }

    public String getAchievementID()
    {
        return this.achievementId;
    }

    public void setAchievementID(String achievementId) {
        this.achievementId = achievementId;
    }

    public List<JChar> getRandom(int length)
    {
        List<JChar> randomList = new ArrayList<JChar>(length);
        if(this.size() < length)
        {
            int j = length - this.size();
            if(this.prev != null)
            {
                randomList = this.prev.getRandom(j);
            }
            else if(this.next != null)
            {
                randomList = this.next.getRandom(j);
            }
            length -= j;
        }
        while (randomList.size() < length)
        {
            int rand = (int)Statics.rand(0,this.size()-1);
            JChar randChar = this.getChar(rand);
            if(!randomList.contains(randChar))
            {
                randomList.add(randChar);
            }
        }
        return randomList;
    }
    public double getScore()
    {
        double score = 0;
        for(int i = 0; i < this.size(); i++)
        {
            score += this.get(i).getScore()*10.0;
        }
        return score/this.size();
    }

    public List<String> getAnswers(JCharEnum type)
    {
        int numberOfAnswers = 10;
        List<String> answers = new ArrayList<String>(numberOfAnswers);

        List<JChar> tmpList = new ArrayList<JChar>();

        tmpList.addAll(this.getChars());

        while(tmpList.size() < numberOfAnswers/2)
        {
            if(this.prev != null)
            {
                tmpList.addAll(this.prev.getRandom((numberOfAnswers/2)-tmpList.size()));
            }
            else if(this.next != null)
            {
                tmpList.addAll(this.next.getRandom((numberOfAnswers/2)-tmpList.size()));
            }
            else
            {
                break;
            }
        }


        for(int i = 0; i < tmpList.size(); i++)
        {
            JChar jChar = tmpList.get(i);
            if(type != JCharEnum.ROMAJI)      answers.add(jChar.getRomaji());
            if(type != JCharEnum.HIRAGANA)    answers.add(jChar.getHiragana());
            if(type != JCharEnum.KATAKANA)    answers.add(jChar.getKatakana());
        }
        return Statics.shuffle(answers);
    }

    public JBlock getPrev() {
        return this.prev;
    }

    public void setPrev(JBlock prev) {
        this.prev = prev;
    }

    public JBlock getNext() {
        return this.next;
    }

    public void setNext(JBlock next) {
        this.next = next;
    }

    public void setParent(JAll parent) {
        this.parent = parent;
    }

    public String getBlockChar() {
        return this.blockIdentifier;
    }
}
