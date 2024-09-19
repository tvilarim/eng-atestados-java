@Service
public class PdfService {
    public DocumentEntity extractDataFromPdf(File pdfFile) throws IOException, TesseractException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        StringBuilder extractedText = new StringBuilder();

        // Usar PDFBox + Tesseract OCR para ler o texto, incluindo imagens
        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage("por");

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);
            String ocrText = tesseract.doOCR(image);
            extractedText.append(ocrText).append("\n");
        }

        document.close();

        LocalDate startDate = extractDate(extractedText.toString(), "Data de início:");
        LocalDate endDate = extractDate(extractedText.toString(), "Conclusão Efetiva:");

        DocumentEntity entity = new DocumentEntity();
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setExtractedContent(extractedText.toString());

        return entity;
    }

    private LocalDate extractDate(String text, String label) {
        Pattern pattern = Pattern.compile(label + "\\s*(\\d{2}/\\d{2}/\\d{4})");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }
}