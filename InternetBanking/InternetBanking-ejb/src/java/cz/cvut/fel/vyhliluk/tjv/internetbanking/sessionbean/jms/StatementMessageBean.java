package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.jms;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CustomerDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.annotation.security.RunAs;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lucky
 */
@MessageDriven(mappedName = "jms/IBMailQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
//@DeclareRoles({"Manager"})
@RunAs("Manager")
public class StatementMessageBean implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(StatementMessageBean.class);
    @EJB
    private CustomerDao custBean;
    @EJB
    private AccountDao accountBean;
    @EJB
    private BankTransactionDao btBean;
    @Resource(name = "jmail/gmail")
    private Session gmailSession;

    public StatementMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            MapMessage map = (MapMessage) message;
            Long customerId = map.getLong("customerId");
            Long accountId = map.getLong("accountId");

            Customer cust = this.custBean.findById(customerId);

            String mailTo = cust.getEmail();
            String subject = "Statement from account "+ accountId;
            String body = "Dobrý den, \nVy budete pan "+ cust.getFirstName() +" "+ cust.getSurname() +", že?\n\nZasíláme Vám výpis z Vašeho účtu.";

            this.sendStatementMail(mailTo, subject, body, this.createAttachment(accountId), "application/pdf");
        } catch (NamingException ex) {
            LOG.error("Error during the mail send: {}", ex.getMessage());
        } catch (MessagingException ex) {
            LOG.error("Error during the mail send: {}", ex.getMessage());
        } catch (JMSException ex) {
            LOG.error("Error during message processing: " + ex.getMessage());
            //Logger.getLogger(StatementMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendStatementMail(String email, String subject, String body, byte[] data, String mimetype) throws NamingException, MessagingException {
        MimeMessage message = new MimeMessage(gmailSession);
        message.setSubject(subject);
        message.setRecipients(RecipientType.TO, InternetAddress.parse(email, false));

        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(body);

        MimeBodyPart mbp2 = new MimeBodyPart();
        DataSource ds = new ByteArrayDataSource(data, mimetype);
        mbp2.setDataHandler(new DataHandler(ds));
        mbp2.setFileName("statement.pdf");

        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        mp.addBodyPart(mbp2);

        message.setContent(mp);
        message.setSentDate(new Date());

        Transport.send(message);

        LOG.info("Mail sent for user: {}", email);
    }

    private byte[] createAttachment(Long accountId) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, os);
            doc.open();

            Anchor anchor = new Anchor("Statement");
            Chapter chapter = new Chapter(new Paragraph(anchor), 1);
            Paragraph para = new Paragraph("Table");
            Section subCatPart = chapter.addSection(para);

            this.createTable(subCatPart, accountId);
            doc.add(chapter);
            doc.close();
        } catch (DocumentException ex) {
            LOG.error("Error during PDF creation: {}", ex.getMessage());
        }

        return os.toByteArray();
    }

    private void createTable(Section el, Long accountId) {
        Account acc = this.accountBean.findById(accountId);
        List<BankTransaction> bt = this.btBean.getTransByAccount(acc);

        PdfPTable table = new PdfPTable(3);

        PdfPCell c1 = new PdfPCell(new Phrase("Date"));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Trans Type\naccount\nbank"));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (BankTransaction trans : bt) {
            table.addCell(trans.getDateTime().toString());

            String type;
            String account;
            String bank;
            String amount;
            if (trans.getBankFrom() == null && trans.getAccountFrom().equals(accountId) && trans.getBankTo() == null && trans.getAccountTo().equals(accountId)) {
                type = "Interest";
                account = "";
                bank = "";
                Account accObj = this.accountBean.findById(trans.getAccountTo());
                amount = trans.getAmountTo() + " " + accObj.getCurrency().getCode();
            } else if (trans.getBankFrom() == null && trans.getAccountFrom().equals(accountId)) {
                type = "Outgouing payment";
                account = trans.getAccountTo().toString();
                bank = "our bank";
                Account accObj = this.accountBean.findById(trans.getAccountTo());
                amount = trans.getAmountTo() + " " + accObj.getCurrency().getCode();
            } else {
                type = "Incoming payment";
                account = trans.getAccountFrom().toString();
                bank = "our bank";
                Account accObj = this.accountBean.findById(trans.getAccountFrom());
                amount = trans.getAmountFrom() + " " + accObj.getCurrency().getCode();
            }
            table.addCell(type + "\n" + account + "\n" + bank);
            table.addCell(amount);
        }
        el.add(table);
    }
}
