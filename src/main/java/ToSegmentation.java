import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * java实现分词
 * 使用 逆向最大匹配 策略
 * 每次删除首字，剩余的去词库匹配，若匹配成功，将匹配的词从待分词字符串中删除，并经其放入结果组中
 * 若到最后一字仍匹配失败，将尾字放入结果组中，删除尾字重新匹配
 */
public class ToSegmentation {
    /**
     * 传入的待分词字符串
     */
    private static String HandleWords;
    /**
     * 每次做处理的待分词字符串
     */
    private static String LeftoverWords;
    /**
     * 词库
     */
    private static String[] Thesaurus = {"java", "创造", "时间","生发", "简单", "爱迪生", "发明", "一步登天", "使用", "一个", "慢慢", "费劲", "复杂", "急功近利", "以后", "工具", "分词", "这是", "实现"};
    /**
     * 结果组
     */
    private static List<String> ResultWords = new ArrayList<String>();

    public ToSegmentation() {
    }

    public static void main(String[] args) {
        String str = "这是使用java写的一个分词工具，简单的还可以实现，复杂的就费劲了，这是第一步，以后有时间再慢慢搞吧。";
        System.out.println(str);
        List<String> test = new ToSegmentation().segmentation(str);
        System.out.println(test.toString().replaceAll(",", "/").replaceAll("\\s", ""));
    }

    public List<String> segmentation(String handleWords) {
        HandleWords = handleWords;
        LeftoverWords = HandleWords;
        this.getResultByReverseOrder();
        Collections.reverse(ResultWords);
        return ResultWords;
    }

    /**
     * 倒序分词获取结果
     */
    private void getResultByReverseOrder() {
        while (HandleWords.length() > 0) {
            boolean isAdded = false;
            String lastWord = null;
            while (LeftoverWords.length() > 0) {
                //判断剩余词若包含在词库中
                if (this.isExistThesaurus(LeftoverWords)) {
                    //将剩余词插入结果组
                    this.changeEle(null);
                    isAdded = true;
                } else {
                    lastWord = this.removeFirstWord();
                    isAdded = false;
                }
            }
            //当最后一个词不在库中时，做特殊处理
            if (!isAdded) {
                this.changeEle(lastWord);
            }
        }
    }

    private void changeEle(String word) {
        if (null == word) {
            ResultWords.add(LeftoverWords);
            HandleWords = HandleWords.substring(0, HandleWords.lastIndexOf(LeftoverWords));
            LeftoverWords = HandleWords;
            this.getResultByReverseOrder();
        } else {
            ResultWords.add(word);
            HandleWords = HandleWords.substring(0, HandleWords.lastIndexOf(word));
            LeftoverWords = HandleWords;
            this.getResultByReverseOrder();
        }
    }

    public boolean isExistThesaurus(String str) {
        for (String word : Thesaurus) {
            if (word.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 切除做处理的待分词字符串的第一个词
     */
    private String removeFirstWord() {
        String firstWord = LeftoverWords.substring(0, 1);
        LeftoverWords = LeftoverWords.substring(1);
        return firstWord;
    }
}
