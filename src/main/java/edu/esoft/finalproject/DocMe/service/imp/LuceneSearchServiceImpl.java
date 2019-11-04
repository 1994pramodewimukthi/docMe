package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.dto.EmitDto;
import edu.esoft.finalproject.DocMe.dto.ResultDto;
import edu.esoft.finalproject.DocMe.service.LuceneSearchService;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@PropertySource(value = "classpath:application.properties")
public class LuceneSearchServiceImpl implements LuceneSearchService {

    @Value("${storage.pdf}")
    private String storagePdf;

    @Value("${storage.text}")
    private String storageText;

    @Value("${storage.index}")
    private String storageIndex;

    public static final String FIELD_CONTENT="content";
    public static final String FIELD_FILE_NAME = "filename";
    public static final String FIELD_FILE_PATH = "filepath";
    public static final String FIELD_FILE_HASH = "hash";


    @Override
    public void createLuinceSearch(MultipartFile file, String Filename) throws IOException {
//        String hash = DigestUtils.sha256Hex(new Date().toString());

        File pdf = new File(storagePdf, Filename + ".pdf");
        File text = new File(storageText, Filename + ".txt");
        Directory index = FSDirectory.open(new File(storageIndex).toPath());

        file.transferTo(pdf);

        PDDocument pdDocument = PDDocument.load(pdf);
        String content = new PDFTextStripper().getText(pdDocument).replaceAll("[\\n\\t\\r]", " ").replaceAll("\\s+", " ").toLowerCase();

        for (int pageNumber = 0; pageNumber < pdDocument.getNumberOfPages(); pageNumber++) {
            PDPage page = pdDocument.getPage(pageNumber);
            PDDocument pageDocument = new PDDocument();
            pageDocument.addPage(page);

            File pageFile = Paths.get(storagePdf, "pages", Filename, "page-" + Integer.toString(pageNumber) + ".pdf").toFile();
            pageFile.getParentFile().mkdirs();
            pageDocument.save(pageFile);
            pageDocument.close();
        }

        pdDocument.close();
        FileUtils.writeStringToFile(text, content, Charset.defaultCharset());

        Document document = new Document();
        document.add(new TextField(FIELD_CONTENT, content, Field.Store.YES));
        document.add(new StringField(FIELD_FILE_NAME, pdf.getName(), Field.Store.YES));
        document.add(new StringField(FIELD_FILE_PATH, pdf.getPath(), Field.Store.YES));
        document.add(new StringField(FIELD_FILE_HASH, Filename, Field.Store.YES));

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        IndexWriter writer = new IndexWriter(index, indexWriterConfig);
        writer.addDocument(document);
        writer.close();
    }

    @Override
    public ResultDto luenceKeyWordSearch(String key) throws Exception {
        Directory index = FSDirectory.open(new File(storageIndex).toPath());
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        QueryParser queryParser = new QueryParser(FIELD_CONTENT, new StandardAnalyzer());
        TopDocs docs = indexSearcher.search(queryParser.parse(key), 10);

       ResultDto results = new ResultDto();
        List<EmitDto> emits = new ArrayList<>();
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            Document document = indexSearcher.doc(scoreDoc.doc);
            Trie trie = Trie.builder().addKeyword(key).ignoreCase().ignoreOverlaps().onlyWholeWords().build();
            PDDocument pdDocument = PDDocument.load(new File(document.get(FIELD_FILE_PATH)));
            int x=1;
            for (int pageNumber = 0; pageNumber < pdDocument.getNumberOfPages(); pageNumber++) {
                PDPage page = pdDocument.getPage(pageNumber);
                PDDocument pageDocument = new PDDocument();
                pageDocument.addPage(page);
                if (trie.containsMatch(new PDFTextStripper().getText(pageDocument))) {
                    emits.add(new EmitDto(x,pageNumber, key, document.get(FIELD_FILE_HASH)));
                }
                pageDocument.close();
                x++;
            }
            results.setEmits(emits);
        }
        return results;
    }

    @Override
    public byte[] preview(int page, String key, String filename) throws Exception {
        PDDocument pageDocument = PDDocument.load(Paths.get(storagePdf, "pages", filename, "page-" + Integer.toString(page) + ".pdf").toFile());
        Trie trie = Trie.builder().addKeyword(key.toLowerCase()).ignoreCase().ignoreOverlaps().build();


        List<TextPosition> positions = new ArrayList<>();
        StringBuilder body = new StringBuilder();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                positions.addAll(textPositions);
                body.append(text.toLowerCase());
                super.writeString(text, textPositions);
            }
        };

        stripper.setSortByPosition(true);
        stripper.getText(pageDocument);

        PDPage outputPage = pageDocument.getDocumentCatalog().getPages().get(0);
        List annotations = outputPage.getAnnotations();

        for (Emit emit : trie.parseText(body.toString().toLowerCase())) {

            int skip = emit.getStart();
            int limit = (emit.getEnd() - emit.getStart()) + 1;

            List<TextPosition> textPositions = positions.stream().skip(skip).limit(limit).collect(Collectors.toList());

            float posXInit = textPositions.get(0).getXDirAdj();
            float posXEnd = textPositions.get(textPositions.size() - 1).getXDirAdj() + textPositions.get(textPositions.size() - 1).getWidth();
            float posYInit = textPositions.get(0).getPageHeight() - textPositions.get(0).getYDirAdj();
            float posYEnd = textPositions.get(0).getPageHeight() - textPositions.get(textPositions.size() - 1).getYDirAdj();
            float width = textPositions.get(0).getWidthDirAdj();
            float height = textPositions.get(0).getHeightDir();

            PDRectangle position = new PDRectangle();
            position.setLowerLeftX(posXInit);
            position.setLowerLeftY(posYEnd);
            position.setUpperRightX(posXEnd);
            position.setUpperRightY(posYEnd + height);

            PDAnnotationTextMarkup markup = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);
            markup.setRectangle(position);
            float quadPoints[] = {posXInit, posYEnd + height + 2, posXEnd, posYEnd + height + 2, posXInit, posYInit - 2, posXEnd, posYEnd - 2};
            markup.setQuadPoints(quadPoints);
            markup.setColor(new PDColor(new float[]{255, 0, 0}, PDDeviceRGB.INSTANCE));
            annotations.add(markup);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        pageDocument.save(out);
        pageDocument.close();

        return out.toByteArray();
    }
}

