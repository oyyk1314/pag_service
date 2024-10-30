package com.pag.unit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PDFToMarkdown {
    public static void main(String[] args) throws IOException {
        // 加载PDF文档
        try (PDDocument document = PDDocument.load(new File("d:\\data.pdf"))) {
            // 创建一个PDFTextStripper实例来提取文本
            PDFTextStripper stripper = new PDFTextStripper();
            // 提取文本
            String text = stripper.getText(document);

            // 将提取的文本转换为Markdown格式（这部分需要您根据文本内容自己实现）
            String markdown = convertToMarkdown(text);

            System.out.println(markdown);

            // 将Markdown内容写入文件
            Files.write(Paths.get("d:\\output2.md"), markdown.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static String convertToMarkdown(String text) {
        // 这里需要实现具体的转换逻辑，可能需要正则表达式等
        // 示例：将段落标记为Markdown的标题
        return "```markdown\n" + text + "\n```";
//        return text.replaceAll("\\r?\\n", "\n")
//                .replaceAll("^\\s*(?=\\S)([^\\n]+)\\s*\\n", "# $1\n\n");
    }
}