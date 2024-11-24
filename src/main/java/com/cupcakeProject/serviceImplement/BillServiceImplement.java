package com.cupcakeProject.serviceImplement;

import com.cupcakeProject.jwt.JWTFilter;
import com.cupcakeProject.model.Bill;
import com.cupcakeProject.repository.BillDao;
import com.cupcakeProject.service.BillService;
import com.cupcakeProject.utils.CupcakeProjectUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.cupcakeProject.constants.CupcakeProjectConstants.*;

@Slf4j
@Service
public class BillServiceImplement implements BillService {
    @Autowired
    JWTFilter jwtFilter;
    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generatedReport(Map<String, Object> requestMap) {
        log.info("Inside generated report!");
        try {
            String fileName = "";
            if (validatedRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.containsKey("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                } else {
                    fileName = CupcakeProjectUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }
                String data = "Nome: " + requestMap.get("name") + "\n" + "Número de Contato: " + requestMap.get("contactNumber") +
                        "\n" + "Email: " + requestMap.get("email") + "\n" + "Pagamento: " + requestMap.get("paymentMethod");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(STORE_LOCALTION + "/" + fileName + ".pdf"));
                document.open();
                setRectangularPDF(document);

                Paragraph chunk = new Paragraph("Cupcake Store", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CupcakeProjectUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, CupcakeProjectUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total: " + requestMap.get("totalAmount") + "\n" +
                        "Obrigado pela preferência. Volte sempre!");
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\" uuid\" : \"" + fileName + "\"}", HttpStatus.OK);
            }
            return CupcakeProjectUtils.getResponseEntity(INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            bill.setProductDetails((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());

            billDao.save(bill);
            log.info("bill object save in database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validatedRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("paymentMethod")
                && requestMap.containsKey("productDetails") && requestMap.containsKey("totalAmount");
    }


    private void setRectangularPDF(Document document) throws DocumentException {
        log.info("Inside setRectangularPDF");
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private Font getFont(String type) {
        log.info("inside getFont");
        switch (type) {
            case "Header":
                Font heaferFont = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.BLACK);
                heaferFont.setStyle(Font.BOLD);
                return heaferFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }

    }

    private void addTableHeader(PdfPTable table) {
        log.info("inside addTableHeader");
        Stream.of("Nome", "Categoria", "Quantidade", "Preço", "Total").forEach(columnTitle ->
        {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.PINK);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });

    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell((Double.toString((Double) data.get("price"))));
        table.addCell((Double.toString((Double) data.get("total"))));
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
            List<Bill> list = new ArrayList<>();
            if(jwtFilter.isAdmin()){
                list = billDao.getAllBills();
            }else {
                list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
            }
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
