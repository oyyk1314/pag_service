package com.pag.unit;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class WordVectors {

    /**
     * 根据文本内容获取对应的词向量列表
     * @param text 文本内容
     * @param model 词向量模型
     * @return 词向量列表
     */
    public static List<INDArray> getWordVectors(String text, Word2Vec model) {

        //SentenceIterator iter = new BasicLineIterator("afasdfasdf");
        // 将文本分词
        List<String> words = segmentWords(text.toLowerCase(Locale.getDefault()));
        // 创建一个列表来存储词向量
        List<INDArray> wordVectors = new ArrayList<>(words.size());
        for (String word : words) {
            if (model.hasWord(word)) {
                wordVectors.add(model.getWordVectorMatrix(word));
            } else {
                // 如果单词不在词汇表中，使用默认向量（这里使用零向量）
                int vectorSize = model.getLayerSize(); // 获取词向量的大小
                INDArray defaultVector = Nd4j.zeros(1, vectorSize); // 创建零向量
                wordVectors.add(defaultVector);
            }
        }
        return wordVectors;
    }

    /**
     * 对句子进行分词处理
     * @param sentence 待分词的句子
     * @return 分词后的词语列表
     */
    private static List<String> segmentWords(String sentence) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        return segmenter.sentenceProcess(sentence).stream()
                .filter(e -> !" ".equals(e) && !e.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 计算两个向量的余弦相似度
     * @param vec1 第一个向量
     * @param vec2 第二个向量
     * @return 余弦相似度值
     */
    private static double cosineSimilarity(INDArray vec1, INDArray vec2) {
        // 计算两个向量的点积
        double dotProduct = vec1.mulRowVector(vec2).sumNumber().doubleValue();
        // 计算两个向量的模长
        double norm1 = vec1.norm2Number().doubleValue();
        double norm2 = vec2.norm2Number().doubleValue();
        // 计算余弦相似度
        return dotProduct / (norm1 * norm2);
    }

    /**
     * 计算两个句子的相似度
     * @param sentence1 第一个句子
     * @param sentence2 第二个句子
     * @param model 词向量模型
     * @return 句子相似度值
     */
    private static double sentenceSimilarity(String sentence1, String sentence2, Word2Vec model) {
        List<INDArray> vectors1 = getWordVectors(sentence1, model);
        List<INDArray> vectors2 = getWordVectors(sentence2, model);
        INDArray avgVector1 = getAverageVector(vectors1, model.getLayerSize());
        INDArray avgVector2 = getAverageVector(vectors2, model.getLayerSize());
        return cosineSimilarity(avgVector1, avgVector2);
    }

    /**
     * 计算一组向量的平均值向量
     * @param vectors 向量列表
     * @param modelSize 向量维度大小
     * @return 平均向量
     */
    private static INDArray getAverageVector(List<INDArray> vectors, int modelSize) {
        INDArray sumVector = Nd4j.zeros(1, modelSize); // 创建一个与第一个向量形状相同的零向量
        for (INDArray vector : vectors) {
            sumVector.addiRowVector(vector); // 使用addi进行原地操作
        }
        INDArray indArray = sumVector.div(vectors.size());
        sumVector.close();
        return indArray; // 将总和除以向量数量以获得平均值
    }
}
