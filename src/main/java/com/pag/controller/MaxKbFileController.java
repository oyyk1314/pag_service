package com.pag.controller;

import com.pag.mapper.MaxKbDocumentMapper;
import com.pag.pojo.MaxKbDocument;
import com.pag.pojo.MaxKbEmbedding;
import com.pag.pojo.MaxKbFile;
import com.pag.pojo.MaxKbParagraph;
import com.pag.service.MaxKbDocumentService;
import com.pag.service.MaxKbEmbeddingService;
import com.pag.service.MaxKbFileService;
import com.pag.service.MaxKbParagraphService;
import com.pag.unit.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class MaxKbFileController {

    @Autowired
    private MaxKbFileService maxKbFileService;

    @Autowired
    MaxKbDocumentService maxKbDocumentService;

    @Autowired
    MaxKbParagraphService maxKbParagraphService;

    @Autowired
    MaxKbEmbeddingService maxKbEmbeddingService;


    @PostMapping("/common/upload")
    @CrossOrigin
    public String uploadFile(MultipartFile file) {

        System.out.println("sdfsdfasdfasdf");
        try {
            String uploadPath = "d:\\upload";
            Map<String, String> map = FileUploadUtils.upload(uploadPath, file);
            String filePath = (String)map.get("pathFullFileName");
            String fileSize = (String)map.get("fileSize");
            String fileName = (String)map.get("fileName");
            String targetName = (String)map.get("targetName");

            //把文件生成MD5以用来验证是否重复上传
            String md5= MD5Generator.generateMD5(filePath);

            MaxKbFile maxKbFile=new MaxKbFile();
            Snowflake snowflake = new Snowflake(1, 1);
            maxKbFile.setId(snowflake.nextId());
            maxKbFile.setFileName(fileName);
            maxKbFile.setFileSize(Integer.parseInt(fileSize));
            maxKbFile.setMd5(md5);
            maxKbFile.setPlatform("测试");
            maxKbFile.setBucketName("测试");
            maxKbFile.setFileId(md5);
            maxKbFile.setTargetName(targetName);
            maxKbFile.setUserId("test");
            //用文件生成的MD5检测是否重复文件
            int count = maxKbFileService.queryByFile(md5);
            if(count>0){
                return "已经上传该文件，请重试";
            }else{
                int flag = maxKbFileService.insert(maxKbFile);
                if(flag>0){
                    try {
                        PDDocument document = PDDocument.load(new File(filePath));
                        // 创建一个PDFTextStripper实例来提取文本
                        PDFTextStripper stripper = new PDFTextStripper();
                        // 提取文本
                        String text = stripper.getText(document);
                            //将提取的文本转换为Markdown格式
                        String markdown=text.replaceAll("\\r?\\n", "\n")
                                .replaceAll("^\\s*(?=\\S)([^\\n]+)\\s*\\n", "# $1\n\n");

                        MaxKbDocument maxKbDocument=new MaxKbDocument();
                        maxKbDocument.setId(snowflake.nextId());
                        maxKbDocument.setName(fileName);
                        maxKbDocument.setCharLength(markdown.length());
                        maxKbDocument.setStatus("1");
                        maxKbDocument.setType("pdf");
                        maxKbDocument.setActive(true);
                        maxKbDocument.setHitHandlingMethod("Markdown");
                        maxKbDocument.setDirectlyReturnSimilarity(3);
                        maxKbDocument.setMeta(markdown);

                        maxKbDocument.setDatasetId(maxKbFile.getId());

                        maxKbDocumentService.insert(maxKbDocument);

                        // 将Markdown内容写入文件
                        String mdfile_path = uploadPath+"\\"+maxKbDocument.getId()+".md";
                        Files.write(Paths.get(mdfile_path), markdown.getBytes(StandardCharsets.UTF_8));
                        //上传文件到腾讯COS
                        TencentCosUtil util=new TencentCosUtil();
                        util.uploadObject(mdfile_path);

                        //将Markdown文件内容拆分成段落
                        List<String> list = MarkdownSplitter.splitMarkdown(mdfile_path);
                        List<MaxKbParagraph> listobj=new ArrayList<MaxKbParagraph>();

                        //将Markdown文件取出
                        File inputTxt = new File(mdfile_path);
                        //加载数据
                        SentenceIterator iter = new LineSentenceIterator(inputTxt);
                        //切词操作
                        TokenizerFactory token = new DefaultTokenizerFactory();
                        //去除特殊符号及大小写转换操作
                        token.setTokenPreProcessor(new CommonPreprocessor());
                        //创建向量
                        Word2Vec vec = new Word2Vec.Builder()
                                .minWordFrequency(5)//词在语料中必须出现的最少次数
                                .iterations(1)
                                .layerSize(100)  //向量维度
                                .seed(42)
                                .windowSize(10) //窗口大小
                                .iterate(iter)
                                .tokenizerFactory(token)
                                .build();
                        vec.fit();

                        for(int i=0;i<list.size();i++){
                            String val=list.get(i);
                            MaxKbParagraph paragraph=new MaxKbParagraph();
                            paragraph.setId(snowflake.nextId());
                            paragraph.setContent(val);
                            paragraph.setTitle("段落"+(i+1));
                            paragraph.setHitNum(i+1);
                            paragraph.setStatus("1");
                            paragraph.setActive(true);
                            paragraph.setDatasetId(maxKbFile.getId());
                            paragraph.setDatasetId(maxKbDocument.getId());
                            listobj.add(paragraph);
                            //插入段落数据
                            maxKbParagraphService.insert(paragraph);

                            //根据文本内容获取对应的词向量列表
                            List<INDArray> listwd = WordVectors.getWordVectors(val,vec);

                            System.out.println("词向量列表=="+listwd.size());
                            if(listwd!=null && listwd.size()>0){
                                double[] xl =new double[listwd.size()];
                                for(int s=0;s<listwd.size();s++){
                                    INDArray arr=listwd.get(s);
                                    System.out.println("向量数据=="+Arrays.toString(arr.data().asDouble()));
                                    //arr.toString();
                                    xl[s]=arr.getDouble(0);
                                }
                                MaxKbEmbedding embedding=new MaxKbEmbedding();
                                embedding.setId(snowflake.nextId());
                                embedding.setDatasetId(maxKbFile.getId());
                                embedding.setDatasetId(maxKbDocument.getId());
                                embedding.setParagraphId(paragraph.getId());
                                embedding.setEmbedding(xl);
                                embedding.setMeta(val);
                                embedding.setActive(true);
                                embedding.setSourceType("0");
                                embedding.setSourceId(paragraph.getId());
                                //embedding.setSearchVector(xl);
                                //插入向量数据
                                maxKbEmbeddingService.insert(embedding);
                            }
                        }
                        //批量插入段落数据
                        //maxKbParagraphService.batchInsert(listobj);

                    }catch (ExportException e){
                        e.printStackTrace();
                    }


                    return "上传成功";
                }else{
                    return "上传失败";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }





}
